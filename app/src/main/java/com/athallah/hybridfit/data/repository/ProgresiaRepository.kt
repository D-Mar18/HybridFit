package com.athallah.hybridfit.data.repository
import androidx.room.withTransaction
import com.athallah.hybridfit.data.local.HybridFitDatabase
import com.athallah.hybridfit.data.local.entity.RecoveryCheckInEntity
import com.athallah.hybridfit.data.local.entity.RecommendationEntity
import com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity
import com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity
import com.athallah.hybridfit.data.local.entity.UserProfileEntity
import com.athallah.hybridfit.data.local.entity.WorkoutLogEntity
import com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity
import com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity
import com.athallah.hybridfit.data.local.relation.WorkoutPlanWithSessions
import com.athallah.hybridfit.domain.model.DashboardSnapshot
import com.athallah.hybridfit.domain.model.FitnessGoal
import com.athallah.hybridfit.domain.model.FitnessLevel
import com.athallah.hybridfit.domain.model.PlanStatus
import com.athallah.hybridfit.domain.model.ProgressSummary
import com.athallah.hybridfit.domain.model.RecoveryCheckIn
import com.athallah.hybridfit.domain.model.Recommendation
import com.athallah.hybridfit.domain.model.RecommendationFeedback
import com.athallah.hybridfit.domain.model.SessionPlannerState
import com.athallah.hybridfit.domain.model.StrengthExerciseDraft
import com.athallah.hybridfit.domain.model.StrengthExerciseEntry
import com.athallah.hybridfit.domain.model.UserProfile
import com.athallah.hybridfit.domain.model.WorkoutCategory
import com.athallah.hybridfit.domain.model.WorkoutLog
import com.athallah.hybridfit.domain.model.WorkoutPlan
import com.athallah.hybridfit.domain.model.WorkoutSession
import com.athallah.hybridfit.domain.service.AdaptiveRecommendationEngine
import com.athallah.hybridfit.domain.service.ProgressAnalyzer
import com.athallah.hybridfit.domain.service.SessionBlueprint
import com.athallah.hybridfit.domain.service.StarterPlanBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import kotlin.math.roundToInt

class HybridFitRepository(
    private val database: HybridFitDatabase,
    private val starterPlanBuilder: StarterPlanBuilder,
    private val recommendationEngine: AdaptiveRecommendationEngine,
    private val progressAnalyzer: ProgressAnalyzer
) {
    private val userProfileDao = database.userProfileDao()
    private val workoutPlanDao = database.workoutPlanDao()
    private val workoutLogDao = database.workoutLogDao()
    private val strengthExerciseEntryDao = database.strengthExerciseEntryDao()
    private val recoveryCheckInDao = database.recoveryCheckInDao()
    private val recommendationDao = database.recommendationDao()
    private val feedbackDao = database.recommendationFeedbackDao()

    fun observeDashboard(userId: Long?): Flow<DashboardSnapshot> {
        if (userId == null) {
            return flowOf(emptyDashboardSnapshot())
        }

        val profileAndPlanFlow = combine(
            userProfileDao.observeProfile(userId),
            workoutPlanDao.observeActivePlanWithSessions(userId)
        ) { profileEntity, planWithSessions ->
            profileEntity to planWithSessions
        }
        val logAndExerciseFlow = combine(
            workoutLogDao.observeAllLogs(userId),
            strengthExerciseEntryDao.observeAllEntries(userId)
        ) { logs, exerciseEntries ->
            logs to exerciseEntries
        }
        val recommendationBundleFlow = combine(
            recoveryCheckInDao.observeAllRecovery(userId),
            recommendationDao.observeLatestRecommendation(userId),
            feedbackDao.observeAllFeedback(userId)
        ) { recoveryEntries, recommendationEntity, feedbackEntities ->
            Triple(recoveryEntries, recommendationEntity, feedbackEntities)
        }

        return combine(
            profileAndPlanFlow,
            logAndExerciseFlow,
            recommendationBundleFlow
        ) { profileAndPlan, logAndExercise, recommendationBundle ->
            val (profileEntity, planWithSessions) = profileAndPlan
            val (logs, exerciseEntries) = logAndExercise
            val (recoveryEntries, recommendationEntity, feedbackEntities) = recommendationBundle
            val profile = profileEntity?.toDomain()
            val activePlan = planWithSessions?.toDomain()
            val sessionTitles = activePlan?.sessions?.associateBy({ it.id }, { it.title }).orEmpty()
            val entriesByLog = exerciseEntries.groupBy { it.workoutLogId }
            val recentLogs = logs.map { log ->
                log.toDomain(
                    sessionTitle = sessionTitles[log.sessionId] ?: "Sesi latihan",
                    exerciseEntries = entriesByLog[log.id].orEmpty().map { it.toDomain() }
                )
            }
            val feedback = feedbackEntities.map { it.toDomain() }
            val recoveryHistory = recoveryEntries.map { it.toDomain() }
            val latestRecovery = recoveryHistory.firstOrNull()
            DashboardSnapshot(
                profile = profile,
                activePlan = activePlan,
                recentLogs = recentLogs,
                latestRecommendation = recommendationEntity?.toDomain(),
                feedbackCount = feedback.size,
                progressSummary = progressAnalyzer.analyze(
                    plannedSessionsPerWeek = activePlan?.sessionsPerWeek ?: 0,
                    logs = recentLogs,
                    latestRecovery = latestRecovery
                ),
                latestRecovery = latestRecovery,
                recoveryHistory = recoveryHistory
            )
        }
    }

    suspend fun bootstrap() {
        // Akun baru dimulai tanpa data demo, program, maupun riwayat latihan.
    }

    suspend fun clearFitnessWorkspace() {
        withContext(Dispatchers.IO) {
            database.clearAllTables()
        }
    }

    suspend fun createStarterProgram(
        userId: Long,
        fullName: String,
        age: Int,
        weightKg: Double,
        heightCm: Int,
        goal: FitnessGoal,
        fitnessLevel: FitnessLevel,
        preferredFocus: WorkoutCategory,
        workoutDaysPerWeek: Int,
        sessionDurationMinutes: Int,
        experienceNotes: String
    ) {
        database.withTransaction {
            val existingPlan = workoutPlanDao.getActivePlanWithSessions(userId)
            if (existingPlan != null) return@withTransaction

            val profileEntity = UserProfileEntity(
                id = userId,
                fullName = fullName,
                age = age,
                weightKg = weightKg,
                heightCm = heightCm,
                genderLabel = "Belum diisi",
                goal = goal,
                fitnessLevel = fitnessLevel,
                preferredFocus = preferredFocus,
                workoutDaysPerWeek = workoutDaysPerWeek,
                sessionDurationMinutes = sessionDurationMinutes,
                experienceNotes = experienceNotes,
                createdAt = Instant.now()
            )
            userProfileDao.upsertProfile(profileEntity)

            val profile = profileEntity.toDomain()
            val draft = starterPlanBuilder.build(profile)
            val planId = workoutPlanDao.insertPlan(
                WorkoutPlanEntity(
                    userId = profile.id,
                    title = draft.title,
                    goal = profile.goal,
                    startDate = draft.startDate,
                    sessionsPerWeek = draft.sessionsPerWeek,
                    status = PlanStatus.ACTIVE,
                    createdAt = Instant.now()
                )
            )

            val sessionEntities = draft.sessions.map { it.toEntity(planId) }
            val sessionIds = workoutPlanDao.insertSessions(sessionEntities)
            val persistedSessions = sessionEntities.mapIndexed { index, entity ->
                entity.copy(id = sessionIds[index])
            }

            val recommendation = recommendationEngine.generate(
                profile = profile,
                sessions = persistedSessions.map { it.toDomain() },
                recentLogs = emptyList(),
                feedback = emptyList(),
                latestRecovery = null
            )
            recommendationDao.insertRecommendation(recommendation.toEntity())
        }
    }

    suspend fun replaceProgramFromAi(
        userId: Long,
        goal: FitnessGoal,
        preferredFocus: WorkoutCategory,
        workoutDaysPerWeek: Int,
        sessionDurationMinutes: Int,
        requestSummary: String
    ) {
        database.withTransaction {
            val existingProfile = userProfileDao.getProfile(userId)
                ?: throw IllegalStateException("Profil pengguna belum tersedia. Selesaikan survey terlebih dahulu.")

            val updatedProfileEntity = existingProfile.copy(
                goal = goal,
                preferredFocus = preferredFocus,
                workoutDaysPerWeek = workoutDaysPerWeek.coerceIn(1, 7),
                sessionDurationMinutes = sessionDurationMinutes.coerceIn(15, 120),
                experienceNotes = requestSummary.ifBlank { existingProfile.experienceNotes }
            )
            userProfileDao.upsertProfile(updatedProfileEntity)

            workoutPlanDao.getActivePlanWithSessions(userId)?.let { activePlan ->
                workoutPlanDao.updatePlan(
                    activePlan.plan.copy(status = PlanStatus.ARCHIVED)
                )
            }

            val updatedProfile = updatedProfileEntity.toDomain()
            val draft = starterPlanBuilder.build(updatedProfile)
            val planId = workoutPlanDao.insertPlan(
                WorkoutPlanEntity(
                    userId = updatedProfile.id,
                    title = draft.title,
                    goal = updatedProfile.goal,
                    startDate = draft.startDate,
                    sessionsPerWeek = draft.sessionsPerWeek,
                    status = PlanStatus.ACTIVE,
                    createdAt = Instant.now()
                )
            )

            val sessionEntities = draft.sessions.map { it.toEntity(planId) }
            val sessionIds = workoutPlanDao.insertSessions(sessionEntities)
            val persistedSessions = sessionEntities.mapIndexed { index, entity ->
                entity.copy(id = sessionIds[index])
            }

            val recommendation = recommendationEngine.generate(
                profile = updatedProfile,
                sessions = persistedSessions.map { it.toDomain() },
                recentLogs = emptyList(),
                feedback = emptyList(),
                latestRecovery = recoveryCheckInDao.getLatestRecovery(userId)?.toDomain()
            )
            recommendationDao.insertRecommendation(recommendation.toEntity())
        }
    }

    suspend fun refreshRecommendation(userId: Long) {
        val profileEntity = userProfileDao.getProfile(userId) ?: return
        val activePlan = workoutPlanDao.getActivePlanWithSessions(userId) ?: return
        val logs = workoutLogDao.getAllLogs(userId)
        val exerciseEntries = strengthExerciseEntryDao.getAllEntries(userId)
        val feedback = feedbackDao.getAllFeedback(userId)
        val latestRecovery = recoveryCheckInDao.getLatestRecovery(userId)?.toDomain()

        val sessions = activePlan.sessions.map { it.toDomain() }
        val sessionTitles = sessions.associateBy({ it.id }, { it.title })
        val entriesByLog = exerciseEntries.groupBy { it.workoutLogId }
        val recommendation = recommendationEngine.generate(
            profile = profileEntity.toDomain(),
            sessions = sessions,
            recentLogs = logs.map { log ->
                log.toDomain(
                    sessionTitle = sessionTitles[log.sessionId] ?: "Sesi latihan",
                    exerciseEntries = entriesByLog[log.id].orEmpty().map { it.toDomain() }
                )
            },
            feedback = feedback.map { it.toDomain() },
            latestRecovery = latestRecovery
        )
        recommendationDao.insertRecommendation(recommendation.toEntity())
    }

    suspend fun recordQuickWorkout(userId: Long) {
        var shouldRefresh = false
        database.withTransaction {
            val activePlan = workoutPlanDao.getActivePlanWithSessions(userId) ?: return@withTransaction
            if (activePlan.sessions.isEmpty()) return@withTransaction

            val logs = workoutLogDao.getAllLogs(userId)
            val latestRecommendation = recommendationDao.getLatestRecommendation(userId)
            val targetSession = activePlan.sessions[logs.size % activePlan.sessions.size]
            val actualDistance = latestRecommendation?.targetDistanceKm ?: targetSession.targetDistanceKm
            val actualDuration = latestRecommendation?.targetDurationMinutes ?: targetSession.targetDurationMinutes

            val logId = workoutLogDao.insertLog(
                WorkoutLogEntity(
                    sessionId = targetSession.id,
                    performedOn = LocalDate.now(),
                    actualDurationMinutes = actualDuration,
                    actualDistanceKm = actualDistance,
                    averagePaceSecondsPerKm = actualDistance?.takeIf { it > 0.0 }?.let {
                        ((actualDuration * 60) / it).roundToInt()
                    },
                    completedSets = latestRecommendation?.targetSets ?: targetSession.targetSets,
                    completedReps = latestRecommendation?.targetReps ?: targetSession.targetReps,
                    completionPercent = 90,
                    exertionScore = 7,
                    notes = "Latihan baseline berhasil diselesaikan.",
                    wasCompleted = true
                )
            )
            if (targetSession.category == WorkoutCategory.STRENGTH) {
                val sets = latestRecommendation?.targetSets ?: targetSession.targetSets ?: 3
                val reps = latestRecommendation?.targetReps ?: targetSession.targetReps ?: 10
                strengthExerciseEntryDao.insertEntries(
                    listOf(
                        StrengthExerciseEntryEntity(
                            workoutLogId = logId,
                            exerciseName = targetSession.title,
                            actualSets = sets,
                            actualReps = reps,
                            actualWeightKg = 20.0,
                            notes = "Pencatatan cepat dari mode demo."
                        )
                    )
                )
            }
            updateSessionStateInternal(targetSession.id, SessionPlannerState.COMPLETED)
            shouldRefresh = true
        }

        if (shouldRefresh) {
            refreshRecommendation(userId)
        }
    }

    suspend fun saveStrengthWorkout(
        userId: Long,
        sessionId: Long,
        actualDurationMinutes: Int,
        exertionScore: Int,
        notes: String,
        exercises: List<StrengthExerciseDraft>
    ) {
        var shouldRefresh = false
        database.withTransaction {
            val session = findUserSession(userId, sessionId) ?: return@withTransaction
            val safeExercises = exercises.filter {
                it.actualSets > 0 && it.actualReps > 0 && it.actualWeightKg > 0
            }
            val totalSets = safeExercises.sumOf { it.actualSets }
            val totalReps = safeExercises.sumOf { it.actualSets * it.actualReps }
            val completionPercent = strengthCompletionPercent(session, safeExercises)

            val logId = workoutLogDao.insertLog(
                WorkoutLogEntity(
                    sessionId = sessionId,
                    performedOn = LocalDate.now(),
                    actualDurationMinutes = actualDurationMinutes,
                    actualDistanceKm = null,
                    averagePaceSecondsPerKm = null,
                    completedSets = totalSets.takeIf { it > 0 },
                    completedReps = totalReps.takeIf { it > 0 },
                    completionPercent = completionPercent,
                    exertionScore = exertionScore.coerceIn(1, 10),
                    notes = notes,
                    wasCompleted = true
                )
            )
            if (safeExercises.isNotEmpty()) {
                strengthExerciseEntryDao.insertEntries(
                    safeExercises.map {
                        StrengthExerciseEntryEntity(
                            workoutLogId = logId,
                            exerciseName = it.exerciseName,
                            actualSets = it.actualSets,
                            actualReps = it.actualReps,
                            actualWeightKg = it.actualWeightKg,
                            notes = it.notes
                        )
                    }
                )
            }
            updateSessionStateInternal(sessionId, SessionPlannerState.COMPLETED)
            shouldRefresh = true
        }

        if (shouldRefresh) {
            refreshRecommendation(userId)
        }
    }

    suspend fun saveRunWorkout(
        userId: Long,
        sessionId: Long,
        distanceKm: Double,
        actualDurationMinutes: Int,
        exertionScore: Int,
        notes: String
    ) {
        var shouldRefresh = false
        database.withTransaction {
            val session = findUserSession(userId, sessionId) ?: return@withTransaction
            val completionPercent = runCompletionPercent(session, distanceKm, actualDurationMinutes)
            workoutLogDao.insertLog(
                WorkoutLogEntity(
                    sessionId = sessionId,
                    performedOn = LocalDate.now(),
                    actualDurationMinutes = actualDurationMinutes,
                    actualDistanceKm = distanceKm,
                    averagePaceSecondsPerKm = if (distanceKm > 0.0) {
                        ((actualDurationMinutes * 60) / distanceKm).roundToInt()
                    } else {
                        null
                    },
                    completedSets = null,
                    completedReps = null,
                    completionPercent = completionPercent,
                    exertionScore = exertionScore.coerceIn(1, 10),
                    notes = notes,
                    wasCompleted = true
                )
            )
            updateSessionStateInternal(sessionId, SessionPlannerState.COMPLETED)
            shouldRefresh = true
        }

        if (shouldRefresh) {
            refreshRecommendation(userId)
        }
    }

    suspend fun updateStrengthWorkout(
        userId: Long,
        logId: Long,
        actualDurationMinutes: Int,
        exertionScore: Int,
        notes: String,
        exercises: List<StrengthExerciseDraft>
    ) {
        var shouldRefresh = false
        database.withTransaction {
            val existingLog = workoutLogDao.getLogById(userId, logId) ?: return@withTransaction
            val session = findUserSession(userId, existingLog.sessionId) ?: return@withTransaction
            val safeExercises = exercises.filter {
                it.actualSets > 0 && it.actualReps > 0 && it.actualWeightKg > 0
            }
            val totalSets = safeExercises.sumOf { it.actualSets }
            val totalReps = safeExercises.sumOf { it.actualSets * it.actualReps }
            val completionPercent = strengthCompletionPercent(session, safeExercises)

            workoutLogDao.updateLog(
                existingLog.copy(
                    actualDurationMinutes = actualDurationMinutes,
                    actualDistanceKm = null,
                    averagePaceSecondsPerKm = null,
                    completedSets = totalSets.takeIf { it > 0 },
                    completedReps = totalReps.takeIf { it > 0 },
                    completionPercent = completionPercent,
                    exertionScore = exertionScore.coerceIn(1, 10),
                    notes = notes,
                    wasCompleted = true
                )
            )
            strengthExerciseEntryDao.deleteEntriesForLog(logId)
            if (safeExercises.isNotEmpty()) {
                strengthExerciseEntryDao.insertEntries(
                    safeExercises.map {
                        StrengthExerciseEntryEntity(
                            workoutLogId = logId,
                            exerciseName = it.exerciseName,
                            actualSets = it.actualSets,
                            actualReps = it.actualReps,
                            actualWeightKg = it.actualWeightKg,
                            notes = it.notes
                        )
                    }
                )
            }
            updateSessionStateInternal(existingLog.sessionId, SessionPlannerState.COMPLETED)
            shouldRefresh = true
        }

        if (shouldRefresh) {
            refreshRecommendation(userId)
        }
    }

    suspend fun updateRunWorkout(
        userId: Long,
        logId: Long,
        distanceKm: Double,
        actualDurationMinutes: Int,
        exertionScore: Int,
        notes: String
    ) {
        var shouldRefresh = false
        database.withTransaction {
            val existingLog = workoutLogDao.getLogById(userId, logId) ?: return@withTransaction
            val session = findUserSession(userId, existingLog.sessionId) ?: return@withTransaction
            val completionPercent = runCompletionPercent(session, distanceKm, actualDurationMinutes)

            workoutLogDao.updateLog(
                existingLog.copy(
                    actualDurationMinutes = actualDurationMinutes,
                    actualDistanceKm = distanceKm,
                    averagePaceSecondsPerKm = if (distanceKm > 0.0) {
                        ((actualDurationMinutes * 60) / distanceKm).roundToInt()
                    } else {
                        null
                    },
                    completedSets = null,
                    completedReps = null,
                    completionPercent = completionPercent,
                    exertionScore = exertionScore.coerceIn(1, 10),
                    notes = notes,
                    wasCompleted = true
                )
            )
            updateSessionStateInternal(existingLog.sessionId, SessionPlannerState.COMPLETED)
            shouldRefresh = true
        }

        if (shouldRefresh) {
            refreshRecommendation(userId)
        }
    }

    suspend fun deleteWorkoutLog(userId: Long, logId: Long) {
        var shouldRefresh = false
        database.withTransaction {
            val existingLog = workoutLogDao.getLogById(userId, logId) ?: return@withTransaction
            val sessionId = existingLog.sessionId
            strengthExerciseEntryDao.deleteEntriesForLog(logId)
            workoutLogDao.deleteLog(logId)
            if (workoutLogDao.countLogsForSession(sessionId) == 0) {
                updateSessionStateInternal(sessionId, SessionPlannerState.PLANNED)
            }
            shouldRefresh = true
        }

        if (shouldRefresh) {
            refreshRecommendation(userId)
        }
    }

    suspend fun saveRecoveryCheckIn(
        userId: Long,
        sleepHours: Double,
        recoveryScore: Int,
        energyLevel: Int,
        sorenessLevel: Int,
        notes: String
    ) {
        database.withTransaction {
            val today = LocalDate.now()
            recoveryCheckInDao.deleteByDate(userId, today)
            recoveryCheckInDao.insertRecovery(
                RecoveryCheckInEntity(
                    userId = userId,
                    recordedOn = today,
                    sleepHours = sleepHours,
                    recoveryScore = recoveryScore.coerceIn(0, 100),
                    energyLevel = energyLevel.coerceIn(1, 10),
                    sorenessLevel = sorenessLevel.coerceIn(1, 10),
                    notes = notes
                )
            )
        }
        refreshRecommendation(userId)
    }

    suspend fun addPlannerSession(
        userId: Long,
        title: String,
        category: WorkoutCategory,
        focusArea: String,
        dayOfWeek: DayOfWeek,
        durationMinutes: Int,
        targetDistanceKm: Double?,
        targetSets: Int?,
        targetReps: Int?,
        restSeconds: Int,
        notes: String
    ) {
        database.withTransaction {
            val activePlan = workoutPlanDao.getActivePlanWithSessions(userId) ?: return@withTransaction
            val nextOrder = (activePlan.sessions.maxOfOrNull { it.orderInDay } ?: 0) + 1
            workoutPlanDao.insertSession(
                WorkoutSessionEntity(
                    planId = activePlan.plan.id,
                    dayOfWeek = dayOfWeek,
                    title = title,
                    category = category,
                    focusArea = focusArea,
                    targetDurationMinutes = durationMinutes,
                    targetDistanceKm = targetDistanceKm,
                    targetSets = targetSets,
                    targetReps = targetReps,
                    restSeconds = restSeconds,
                    guidance = if (notes.isBlank()) {
                        "Sesi ditambahkan manual dari planner."
                    } else {
                        notes
                    },
                    orderInDay = nextOrder,
                    plannerState = SessionPlannerState.PLANNED,
                    sessionNotes = notes
                )
            )
        }
        refreshRecommendation(userId)
    }

    suspend fun updatePlannerSession(
        userId: Long,
        sessionId: Long,
        title: String,
        category: WorkoutCategory,
        focusArea: String,
        dayOfWeek: DayOfWeek,
        durationMinutes: Int,
        targetDistanceKm: Double?,
        targetSets: Int?,
        targetReps: Int?,
        restSeconds: Int,
        notes: String
    ) {
        database.withTransaction {
            val session = findUserSession(userId, sessionId) ?: return@withTransaction
            workoutPlanDao.updateSession(
                session.copy(
                    title = title,
                    category = category,
                    focusArea = focusArea,
                    dayOfWeek = dayOfWeek,
                    targetDurationMinutes = durationMinutes,
                    targetDistanceKm = targetDistanceKm,
                    targetSets = targetSets,
                    targetReps = targetReps,
                    restSeconds = restSeconds,
                    guidance = if (notes.isBlank()) session.guidance else notes,
                    sessionNotes = notes
                )
            )
        }
        refreshRecommendation(userId)
    }

    suspend fun movePlannerSessionToNextDay(userId: Long, sessionId: Long) {
        database.withTransaction {
            val session = findUserSession(userId, sessionId) ?: return@withTransaction
            workoutPlanDao.updateSession(
                session.copy(dayOfWeek = session.dayOfWeek.next())
            )
        }
    }

    suspend fun updatePlannerSessionState(
        userId: Long,
        sessionId: Long,
        plannerState: SessionPlannerState
    ) {
        database.withTransaction {
            findUserSession(userId, sessionId) ?: return@withTransaction
            updateSessionStateInternal(sessionId, plannerState)
        }
    }

    suspend fun deletePlannerSession(userId: Long, sessionId: Long) {
        database.withTransaction {
            findUserSession(userId, sessionId) ?: return@withTransaction
            workoutPlanDao.deleteSession(sessionId)
        }
    }

    suspend fun recordFeedback(userId: Long, wasHelpful: Boolean) {
        var shouldRefresh = false
        database.withTransaction {
            val latestRecommendation = recommendationDao.getLatestRecommendation(userId) ?: return@withTransaction
            feedbackDao.insertFeedback(
                RecommendationFeedbackEntity(
                    recommendationId = latestRecommendation.id,
                    wasHelpful = wasHelpful,
                    rating = if (wasHelpful) 4 else 2,
                    notes = if (wasHelpful) {
                        "Rekomendasi terasa cocok dengan kemampuan saat ini."
                    } else {
                        "Rekomendasi masih perlu disesuaikan lagi."
                    },
                    recordedAt = Instant.now()
                )
            )
            shouldRefresh = true
        }

        if (shouldRefresh) {
            refreshRecommendation(userId)
        }
    }

    private suspend fun findUserSession(userId: Long, sessionId: Long): WorkoutSessionEntity? {
        val activePlan = workoutPlanDao.getActivePlanWithSessions(userId) ?: return null
        return activePlan.sessions.firstOrNull { it.id == sessionId }
    }

    private suspend fun updateSessionStateInternal(
        sessionId: Long,
        plannerState: SessionPlannerState
    ) {
        val session = workoutPlanDao.getSessionById(sessionId) ?: return
        workoutPlanDao.updateSession(
            session.copy(plannerState = plannerState)
        )
    }

    private fun strengthCompletionPercent(
        session: WorkoutSessionEntity,
        exercises: List<StrengthExerciseDraft>
    ): Int {
        if (exercises.isEmpty()) return 0
        val groupedExercises = exercises.groupBy { it.exerciseName }
        val exerciseCompletion = ((groupedExercises.count { (_, drafts) ->
            drafts.any { it.actualSets > 0 && it.actualReps > 0 }
        }.toDouble() / groupedExercises.size.coerceAtLeast(1)) * 100)
            .roundToInt()
        val targetSets = session.targetSets ?: 0
        val targetReps = session.targetReps ?: 0
        if (targetSets <= 0 || targetReps <= 0) return exerciseCompletion.coerceIn(0, 100)

        val actualWork = exercises.sumOf { it.actualSets * it.actualReps }
        val targetWork = (targetSets * targetReps * groupedExercises.size.coerceAtLeast(1)).coerceAtLeast(1)
        val workCompletion = ((actualWork.toDouble() / targetWork) * 100).roundToInt()
        return ((exerciseCompletion + workCompletion) / 2).coerceIn(0, 100)
    }

    private fun runCompletionPercent(
        session: WorkoutSessionEntity,
        distanceKm: Double,
        durationMinutes: Int
    ): Int {
        val distanceScore = session.targetDistanceKm?.takeIf { it > 0.0 }?.let {
            (distanceKm / it) * 100
        } ?: 100.0
        val durationScore = session.targetDurationMinutes.takeIf { it > 0 }?.let {
            (durationMinutes.toDouble() / it) * 100
        } ?: 100.0
        return ((distanceScore + durationScore) / 2.0).roundToInt().coerceIn(0, 100)
    }

    private fun emptyDashboardSnapshot() = DashboardSnapshot(
        profile = null,
        activePlan = null,
        recentLogs = emptyList(),
        latestRecommendation = null,
        feedbackCount = 0,
        progressSummary = ProgressSummary.empty(),
        latestRecovery = null,
        recoveryHistory = emptyList()
    )

    private fun SessionBlueprint.toEntity(planId: Long): WorkoutSessionEntity = WorkoutSessionEntity(
        planId = planId,
        dayOfWeek = dayOfWeek,
        title = title,
        category = category,
        focusArea = focusArea,
        targetDurationMinutes = targetDurationMinutes,
        targetDistanceKm = targetDistanceKm,
        targetSets = targetSets,
        targetReps = targetReps,
        restSeconds = restSeconds,
        guidance = guidance,
        orderInDay = orderInDay,
        plannerState = SessionPlannerState.PLANNED,
        sessionNotes = ""
    )

    private fun UserProfileEntity.toDomain() = UserProfile(
        id = id,
        fullName = fullName,
        age = age,
        weightKg = weightKg,
        heightCm = heightCm,
        genderLabel = genderLabel,
        goal = goal,
        fitnessLevel = fitnessLevel,
        preferredFocus = preferredFocus,
        workoutDaysPerWeek = workoutDaysPerWeek,
        sessionDurationMinutes = sessionDurationMinutes,
        experienceNotes = experienceNotes,
        createdAt = createdAt
    )

    private fun WorkoutPlanWithSessions.toDomain() = WorkoutPlan(
        id = plan.id,
        userId = plan.userId,
        title = plan.title,
        goal = plan.goal,
        startDate = plan.startDate,
        sessionsPerWeek = plan.sessionsPerWeek,
        status = plan.status,
        sessions = sessions
            .sortedWith(compareBy<WorkoutSessionEntity> { it.dayOfWeek.value }.thenBy { it.orderInDay })
            .map { it.toDomain() }
    )

    private fun WorkoutSessionEntity.toDomain() = WorkoutSession(
        id = id,
        planId = planId,
        dayOfWeek = dayOfWeek,
        title = title,
        category = category,
        focusArea = focusArea,
        targetDurationMinutes = targetDurationMinutes,
        targetDistanceKm = targetDistanceKm,
        targetSets = targetSets,
        targetReps = targetReps,
        restSeconds = restSeconds,
        guidance = guidance,
        orderInDay = orderInDay,
        plannerState = plannerState,
        sessionNotes = sessionNotes
    )

    private fun WorkoutLogEntity.toDomain(
        sessionTitle: String,
        exerciseEntries: List<StrengthExerciseEntry>
    ) = WorkoutLog(
        id = id,
        sessionId = sessionId,
        sessionTitle = sessionTitle,
        performedOn = performedOn,
        actualDurationMinutes = actualDurationMinutes,
        actualDistanceKm = actualDistanceKm,
        averagePaceSecondsPerKm = averagePaceSecondsPerKm,
        completedSets = completedSets,
        completedReps = completedReps,
        completionPercent = completionPercent,
        exertionScore = exertionScore,
        notes = notes,
        wasCompleted = wasCompleted,
        exerciseEntries = exerciseEntries
    )

    private fun StrengthExerciseEntryEntity.toDomain() = StrengthExerciseEntry(
        id = id,
        workoutLogId = workoutLogId,
        exerciseName = exerciseName,
        actualSets = actualSets,
        actualReps = actualReps,
        actualWeightKg = actualWeightKg,
        notes = notes
    )

    private fun RecoveryCheckInEntity.toDomain() = RecoveryCheckIn(
        id = id,
        userId = userId,
        recordedOn = recordedOn,
        sleepHours = sleepHours,
        recoveryScore = recoveryScore,
        energyLevel = energyLevel,
        sorenessLevel = sorenessLevel,
        notes = notes
    )

    private fun RecommendationEntity.toDomain() = Recommendation(
        id = id,
        userId = userId,
        createdAt = createdAt,
        title = title,
        summary = summary,
        action = action,
        targetDurationMinutes = targetDurationMinutes,
        targetDistanceKm = targetDistanceKm,
        targetSets = targetSets,
        targetReps = targetReps,
        restSeconds = restSeconds,
        rationale = rationale
    )

    private fun RecommendationFeedbackEntity.toDomain() = RecommendationFeedback(
        id = id,
        recommendationId = recommendationId,
        wasHelpful = wasHelpful,
        rating = rating,
        notes = notes,
        recordedAt = recordedAt
    )

    private fun Recommendation.toEntity() = RecommendationEntity(
        userId = userId,
        createdAt = createdAt,
        title = title,
        summary = summary,
        action = action,
        targetDurationMinutes = targetDurationMinutes,
        targetDistanceKm = targetDistanceKm,
        targetSets = targetSets,
        targetReps = targetReps,
        restSeconds = restSeconds,
        rationale = rationale
    )

    private fun DayOfWeek.next(): DayOfWeek = DayOfWeek.of((value % 7) + 1)
}
