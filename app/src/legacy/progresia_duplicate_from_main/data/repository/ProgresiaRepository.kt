package com.athallah.hybridfit.data.repository

import androidx.room.withTransaction
import com.athallah.hybridfit.data.local.HybridFitDatabase
import com.athallah.hybridfit.data.local.entity.DEFAULT_USER_ID
import com.athallah.hybridfit.data.local.entity.RecommendationEntity
import com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity
import com.athallah.hybridfit.data.local.entity.UserProfileEntity
import com.athallah.hybridfit.data.local.entity.WorkoutLogEntity
import com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity
import com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity
import com.athallah.hybridfit.data.local.relation.WorkoutPlanWithSessions
import com.athallah.hybridfit.domain.model.DashboardSnapshot
import com.athallah.hybridfit.domain.model.FitnessGoal
import com.athallah.hybridfit.domain.model.FitnessLevel
import com.athallah.hybridfit.domain.model.PlanStatus
import com.athallah.hybridfit.domain.model.Recommendation
import com.athallah.hybridfit.domain.model.RecommendationFeedback
import com.athallah.hybridfit.domain.model.UserProfile
import com.athallah.hybridfit.domain.model.WorkoutCategory
import com.athallah.hybridfit.domain.model.WorkoutLog
import com.athallah.hybridfit.domain.model.WorkoutPlan
import com.athallah.hybridfit.domain.model.WorkoutSession
import com.athallah.hybridfit.domain.service.AdaptiveRecommendationEngine
import com.athallah.hybridfit.domain.service.ProgressAnalyzer
import com.athallah.hybridfit.domain.service.SessionBlueprint
import com.athallah.hybridfit.domain.service.StarterPlanBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.Instant
import java.time.LocalDate

class HybridFitRepository(
    private val database: HybridFitDatabase,
    private val starterPlanBuilder: StarterPlanBuilder,
    private val recommendationEngine: AdaptiveRecommendationEngine,
    private val progressAnalyzer: ProgressAnalyzer
) {
    private val userProfileDao = database.userProfileDao()
    private val workoutPlanDao = database.workoutPlanDao()
    private val workoutLogDao = database.workoutLogDao()
    private val recommendationDao = database.recommendationDao()
    private val feedbackDao = database.recommendationFeedbackDao()

    fun observeDashboard(): Flow<DashboardSnapshot> {
        return combine(
            userProfileDao.observeProfile(),
            workoutPlanDao.observeActivePlanWithSessions(),
            workoutLogDao.observeAllLogs(),
            recommendationDao.observeLatestRecommendation(),
            feedbackDao.observeAllFeedback()
        ) { profileEntity, planWithSessions, logs, recommendationEntity, feedbackEntities ->
            val profile = profileEntity?.toDomain()
            val activePlan = planWithSessions?.toDomain()
            val sessionTitles = activePlan?.sessions?.associateBy({ it.id }, { it.title }).orEmpty()
            val recentLogs = logs.map { it.toDomain(sessionTitles[it.sessionId] ?: "Sesi latihan") }
            val feedback = feedbackEntities.map { it.toDomain() }
            DashboardSnapshot(
                profile = profile,
                activePlan = activePlan,
                recentLogs = recentLogs,
                latestRecommendation = recommendationEntity?.toDomain(),
                feedbackCount = feedback.size,
                progressSummary = progressAnalyzer.analyze(
                    plannedSessionsPerWeek = activePlan?.sessionsPerWeek ?: 0,
                    logs = recentLogs
                )
            )
        }
    }

    suspend fun bootstrap() {
        if (userProfileDao.countProfiles() > 0) return

        database.withTransaction {
            val profileEntity = UserProfileEntity(
                id = DEFAULT_USER_ID,
                fullName = "Pengguna Demo",
                age = 21,
                genderLabel = "Tidak ditentukan",
                goal = FitnessGoal.GENERAL_FITNESS,
                fitnessLevel = FitnessLevel.BEGINNER,
                preferredFocus = WorkoutCategory.STRENGTH,
                workoutDaysPerWeek = 4,
                sessionDurationMinutes = 40,
                experienceNotes = "Membutuhkan program latihan yang adaptif dan mudah diikuti.",
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

            val seededLogs = buildSeedLogs(persistedSessions)
            workoutLogDao.insertLogs(seededLogs)

            val recommendation = recommendationEngine.generate(
                profile = profile,
                sessions = persistedSessions.map { it.toDomain() },
                recentLogs = seededLogs.map { log ->
                    log.toDomain(
                        sessionTitle = persistedSessions.firstOrNull { it.id == log.sessionId }?.title
                            ?: "Sesi latihan"
                    )
                },
                feedback = emptyList()
            )
            recommendationDao.insertRecommendation(recommendation.toEntity())
        }
    }

    suspend fun refreshRecommendation() {
        val profileEntity = userProfileDao.getProfile() ?: return
        val activePlan = workoutPlanDao.getActivePlanWithSessions() ?: return
        val logs = workoutLogDao.getAllLogs()
        val feedback = feedbackDao.getAllFeedback()

        val sessions = activePlan.sessions.map { it.toDomain() }
        val sessionTitles = sessions.associateBy({ it.id }, { it.title })
        val recommendation = recommendationEngine.generate(
            profile = profileEntity.toDomain(),
            sessions = sessions,
            recentLogs = logs.map { it.toDomain(sessionTitles[it.sessionId] ?: "Sesi latihan") },
            feedback = feedback.map { it.toDomain() }
        )
        recommendationDao.insertRecommendation(recommendation.toEntity())
    }

    suspend fun recordQuickWorkout() {
        var shouldRefresh = false
        database.withTransaction {
            val activePlan = workoutPlanDao.getActivePlanWithSessions() ?: return@withTransaction
            if (activePlan.sessions.isEmpty()) return@withTransaction

            val logs = workoutLogDao.getAllLogs()
            val latestRecommendation = recommendationDao.getLatestRecommendation()
            val targetSession = activePlan.sessions[logs.size % activePlan.sessions.size]

            val log = WorkoutLogEntity(
                sessionId = targetSession.id,
                performedOn = LocalDate.now(),
                actualDurationMinutes = latestRecommendation?.targetDurationMinutes
                    ?: targetSession.targetDurationMinutes,
                actualDistanceKm = latestRecommendation?.targetDistanceKm ?: targetSession.targetDistanceKm,
                completedSets = latestRecommendation?.targetSets ?: targetSession.targetSets,
                completedReps = latestRecommendation?.targetReps ?: targetSession.targetReps,
                completionPercent = 90,
                exertionScore = 7,
                userFeedback = "Latihan selesai dengan baik dan masih terasa realistis.",
                wasCompleted = true
            )
            workoutLogDao.insertLog(log)
            shouldRefresh = true
        }

        if (shouldRefresh) {
            refreshRecommendation()
        }
    }

    suspend fun recordFeedback(wasHelpful: Boolean) {
        var shouldRefresh = false
        database.withTransaction {
            val latestRecommendation = recommendationDao.getLatestRecommendation() ?: return@withTransaction
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
            refreshRecommendation()
        }
    }

    private fun buildSeedLogs(sessions: List<WorkoutSessionEntity>): List<WorkoutLogEntity> {
        val baseDate = LocalDate.now().minusDays(9)
        return sessions.take(3).mapIndexed { index, session ->
            WorkoutLogEntity(
                sessionId = session.id,
                performedOn = baseDate.plusDays((index * 2).toLong()),
                actualDurationMinutes = session.targetDurationMinutes + if (index == 0) -5 else 0,
                actualDistanceKm = session.targetDistanceKm?.let { (it - 0.2 + (index * 0.2)) },
                completedSets = session.targetSets?.let { if (index == 0) it - 1 else it },
                completedReps = session.targetReps,
                completionPercent = when (index) {
                    0 -> 72
                    1 -> 84
                    else -> 90
                },
                exertionScore = when (index) {
                    0 -> 8
                    1 -> 7
                    else -> 6
                },
                userFeedback = when (index) {
                    0 -> "Masih adaptasi dengan ritme latihan."
                    1 -> "Sudah mulai terasa lebih nyaman."
                    else -> "Sesi berjalan lancar dan target tercapai."
                },
                wasCompleted = true
            )
        }
    }

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
        orderInDay = orderInDay
    )

    private fun UserProfileEntity.toDomain() = UserProfile(
        id = id,
        fullName = fullName,
        age = age,
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
        orderInDay = orderInDay
    )

    private fun WorkoutLogEntity.toDomain(sessionTitle: String) = WorkoutLog(
        id = id,
        sessionId = sessionId,
        sessionTitle = sessionTitle,
        performedOn = performedOn,
        actualDurationMinutes = actualDurationMinutes,
        actualDistanceKm = actualDistanceKm,
        completedSets = completedSets,
        completedReps = completedReps,
        completionPercent = completionPercent,
        exertionScore = exertionScore,
        userFeedback = userFeedback,
        wasCompleted = wasCompleted
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
}
