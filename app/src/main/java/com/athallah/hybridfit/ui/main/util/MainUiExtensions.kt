package com.athallah.hybridfit.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.SportsGymnastics
import androidx.compose.ui.graphics.Color
import com.athallah.hybridfit.domain.model.DashboardSnapshot
import com.athallah.hybridfit.domain.model.FitnessGoal
import com.athallah.hybridfit.domain.model.FitnessLevel
import com.athallah.hybridfit.domain.model.Recommendation
import com.athallah.hybridfit.domain.model.SessionPlannerState
import com.athallah.hybridfit.domain.model.UserProfile
import com.athallah.hybridfit.domain.model.WorkoutCategory
import com.athallah.hybridfit.domain.model.WorkoutLog
import com.athallah.hybridfit.domain.model.WorkoutSession
import com.athallah.hybridfit.ui.theme.AquaBright
import com.athallah.hybridfit.ui.theme.AquaTeal
import com.athallah.hybridfit.ui.theme.OceanBlue
import com.athallah.hybridfit.ui.theme.SurfaceTint
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.pow
import kotlin.math.roundToInt

internal enum class RunProgramType {
    EASY,
    LONG,
    TEMPO,
    INTERVAL,
    STANDARD
}

internal fun DashboardSnapshot.primarySession(): WorkoutSession {
    return activePlan?.sessions?.firstOrNull() ?: WorkoutSession(
        id = 0,
        planId = 0,
        dayOfWeek = java.time.DayOfWeek.MONDAY,
        title = "Belum ada program",
        category = WorkoutCategory.STRENGTH,
        focusArea = "Program pertama Anda",
        targetDurationMinutes = 0,
        targetDistanceKm = null,
        targetSets = null,
        targetReps = null,
        restSeconds = 0,
        guidance = "Buat program pertama agar latihan hari ini bisa mulai disusun.",
        orderInDay = 1,
        plannerState = SessionPlannerState.PLANNED,
        sessionNotes = ""
    )
}

internal fun DashboardSnapshot.primaryRunLog(): WorkoutLog? =
    recentLogs.firstOrNull { it.actualDistanceKm != null }

internal fun DashboardSnapshot.latestStrengthLog(): WorkoutLog? =
    recentLogs.firstOrNull { it.exerciseEntries.isNotEmpty() || it.actualDistanceKm == null }

internal fun DashboardSnapshot.hasProgram(): Boolean = activePlan?.sessions?.isNotEmpty() == true

internal fun DashboardSnapshot.hasRecentActivity(): Boolean = recentLogs.isNotEmpty()

internal fun DashboardSnapshot.recentLogsForFilter(filter: ProgramFilter): List<WorkoutLog> {
    val isRun = filter == ProgramFilter.RUN
    return recentLogs.filter { log ->
        if (isRun) log.actualDistanceKm != null else log.actualDistanceKm == null
    }.sortedByDescending { it.performedOn }
}

private fun DashboardSnapshot.categorySessions(category: WorkoutCategory): List<WorkoutSession> =
    activePlan?.sessions.orEmpty().filter { it.category == category }

private fun DashboardSnapshot.hasCompletedCategory(category: WorkoutCategory): Boolean {
    val sessionIds = categorySessions(category).map { it.id }.toSet()
    if (sessionIds.isEmpty()) return false
    return activePlan?.sessions.orEmpty().any { session ->
        session.category == category && session.plannerState == SessionPlannerState.COMPLETED
    } || recentLogs.any { log ->
        log.sessionId in sessionIds && log.wasCompleted
    }
}

internal fun DashboardSnapshot.availableProgramFilters(): List<ProgramFilter> {
    val sessions = activePlan?.sessions.orEmpty()
    if (sessions.isEmpty()) return ProgramFilter.entries

    val filters = buildList {
        if (sessions.any { it.category == WorkoutCategory.STRENGTH }) add(ProgramFilter.GYM)
        if (sessions.any { it.category == WorkoutCategory.CARDIO }) add(ProgramFilter.RUN)
    }

    return filters.ifEmpty { listOf(ProgramFilter.GYM) }
}

internal fun DashboardSnapshot.homeFeaturedProgramFilter(): ProgramFilter {
    val hasStrengthProgram = categorySessions(WorkoutCategory.STRENGTH).isNotEmpty()
    val hasRunProgram = categorySessions(WorkoutCategory.CARDIO).isNotEmpty()

    return when {
        hasStrengthProgram && hasRunProgram && !hasCompletedCategory(WorkoutCategory.STRENGTH) -> ProgramFilter.GYM
        hasStrengthProgram && hasRunProgram -> ProgramFilter.RUN
        hasStrengthProgram -> ProgramFilter.GYM
        hasRunProgram -> ProgramFilter.RUN
        else -> ProgramFilter.GYM
    }
}

internal fun DashboardSnapshot.homeFeaturedSession(): WorkoutSession {
    val filter = homeFeaturedProgramFilter()
    return sessionForFilter(filter)
}

internal fun DashboardSnapshot.homeFeaturedTrainingTitle(): String {
    return when (homeFeaturedProgramFilter()) {
        ProgramFilter.GYM -> "Latihan Beban"
        ProgramFilter.RUN -> "Latihan Lari"
    }
}

internal fun DashboardSnapshot.homeFeaturedTrainingDescription(): String {
    val session = homeFeaturedSession()
    val hasStrengthProgram = categorySessions(WorkoutCategory.STRENGTH).isNotEmpty()
    val hasRunProgram = categorySessions(WorkoutCategory.CARDIO).isNotEmpty()
    val sessionName = session.title.ifBlank { session.focusArea }
    val guidance = session.guidance.ifBlank { "Ikuti target latihan sesuai program agar progres tetap terarah." }

    return when {
        hasStrengthProgram && hasRunProgram && homeFeaturedProgramFilter() == ProgramFilter.GYM ->
            "Program hybrid Anda dimulai dari $sessionName. $guidance"
        hasStrengthProgram && hasRunProgram ->
            "Sesi beban sudah selesai. Sekarang lanjut ke $sessionName. $guidance"
        else ->
            "$sessionName siap dijalankan. $guidance"
    }
}

internal fun DashboardSnapshot.sessionForFilter(filter: ProgramFilter): WorkoutSession {
    val targetCategory = if (filter == ProgramFilter.RUN) WorkoutCategory.CARDIO else WorkoutCategory.STRENGTH
    return activePlan?.sessions?.firstOrNull { it.category == targetCategory } ?: primarySession()
}

internal fun WorkoutSession.runProgramType(): RunProgramType {
    val normalizedTitle = title.lowercase()
    return when {
        normalizedTitle.contains("interval") -> RunProgramType.INTERVAL
        normalizedTitle.contains("easy") -> RunProgramType.EASY
        normalizedTitle.contains("long") -> RunProgramType.LONG
        normalizedTitle.contains("tempo") -> RunProgramType.TEMPO
        else -> RunProgramType.STANDARD
    }
}

internal fun WorkoutSession.isIntervalRun(): Boolean = runProgramType() == RunProgramType.INTERVAL

internal fun DashboardSnapshot.sessionBadgeLabel(session: WorkoutSession): String {
    val latestLog = recentLogs.firstOrNull { it.sessionId == session.id }
    return when {
        latestLog != null -> "${latestLog.completionPercent}%"
        session.plannerState == SessionPlannerState.COMPLETED -> "Selesai"
        session.plannerState == SessionPlannerState.SKIPPED -> "Skip"
        else -> "Baru"
    }
}

internal fun DashboardSnapshot.sessionProgress(session: WorkoutSession): Float {
    val latestLog = recentLogs.firstOrNull { it.sessionId == session.id }
    return when {
        latestLog != null -> (latestLog.completionPercent / 100f).coerceIn(0f, 1f)
        session.plannerState == SessionPlannerState.COMPLETED -> 1f
        else -> 0f
    }
}

internal fun DashboardSnapshot.sessionProgressCaption(session: WorkoutSession): String {
    val latestLog = recentLogs.firstOrNull { it.sessionId == session.id }
    return when {
        latestLog != null -> "Progress terakhir ${latestLog.completionPercent}%"
        session.plannerState == SessionPlannerState.SKIPPED -> "Sesi minggu ini dilewati"
        session.plannerState == SessionPlannerState.COMPLETED -> "Sesi minggu ini selesai"
        else -> "Belum ada progres latihan"
    }
}

internal fun DashboardSnapshot.estimatedSteps(): Int {
    return recentLogs.sumOf {
        val distance = it.actualDistanceKm ?: 0.0
        (distance * 1560).roundToInt()
    }
}

internal fun DashboardSnapshot.estimatedCalories(): Int {
    return recentLogs.sumOf { log ->
        val runCalories = ((log.actualDistanceKm ?: 0.0) * 60).roundToInt()
        val durationCalories = log.actualDurationMinutes * if (log.actualDistanceKm != null) 5 else 4
        val strengthCalories = (log.loggedStrengthVolume() * 0.08).roundToInt()
        runCalories + durationCalories + strengthCalories
    }
}

internal fun DashboardSnapshot.totalDistanceKm(): Double {
    return recentLogs.sumOf { it.actualDistanceKm ?: 0.0 }
}

internal fun DashboardSnapshot.totalStrengthVolume(): Int {
    return recentLogs.sumOf { it.loggedStrengthVolume() }
}

internal fun DashboardSnapshot.sleepQualityScore(): Int {
    latestRecovery?.recoveryScore?.let { return it }
    if (recentLogs.isEmpty()) return 0
    val stepScore = (estimatedSteps() / 80).coerceIn(0, 45)
    val calorieScore = (estimatedCalories() / 12).coerceIn(0, 35)
    val consistencyScore = (estimatedStreakDays() * 5).coerceIn(0, 20)
    return (stepScore + calorieScore + consistencyScore).coerceIn(0, 100)
}

internal fun DashboardSnapshot.caloriesForRange(range: StatsRange): Int {
    return logsForRange(range).sumOf { log ->
        val runCalories = ((log.actualDistanceKm ?: 0.0) * 60).roundToInt()
        val durationCalories = log.actualDurationMinutes * if (log.actualDistanceKm != null) 5 else 4
        val strengthCalories = (log.loggedStrengthVolume() * 0.08).roundToInt()
        runCalories + durationCalories + strengthCalories
    }
}

internal fun DashboardSnapshot.distanceForRange(range: StatsRange): Double {
    return logsForRange(range).sumOf { it.actualDistanceKm ?: 0.0 }
}

internal fun DashboardSnapshot.volumeForRange(range: StatsRange): Int {
    return logsForRange(range).sumOf { it.loggedStrengthVolume() }
}

internal fun DashboardSnapshot.estimatedStreakDays(): Int {
    return recentLogs.sortedByDescending { it.performedOn }
        .distinctBy { it.performedOn }
        .takeWhileIndexed { index, log ->
            log.performedOn == LocalDate.now().minusDays(index.toLong())
        }
        .size
}

internal fun DashboardSnapshot.achievementCards(): List<AchievementCardUi> {
    if (recentLogs.isEmpty()) return emptyList()

    return listOf(
        AchievementCardUi("Streak", "Aktif ${estimatedStreakDays()} hari", Icons.Outlined.EmojiEvents, AquaTeal, Color(0xFFDFFFF7)),
        AchievementCardUi("Goal Crusher", progressSummary.trend.label, Icons.Outlined.LocalFireDepartment, OceanBlue, SurfaceTint),
        AchievementCardUi("Recovery Ready", "${sleepQualityScore()}% recovery", Icons.AutoMirrored.Outlined.ShowChart, AquaBright, Color(0xFFEAF9FF))
    )
}

internal fun DashboardSnapshot.logsForRange(range: StatsRange): List<WorkoutLog> {
    val today = LocalDate.now()
    return when (range) {
        StatsRange.DAY -> recentLogs.filter { it.performedOn == today }
        StatsRange.WEEK -> recentLogs.filter { it.performedOn >= today.minusDays(6) }
        StatsRange.MONTH -> recentLogs.filter { it.performedOn >= today.minusDays(29) }
        StatsRange.YEAR -> recentLogs.filter { it.performedOn >= today.minusDays(364) }
    }
}

internal fun DashboardSnapshot.overviewSeries(range: StatsRange): List<Float> {
    val logs = logsForRange(range)
    if (logs.isEmpty()) return List(7) { 0f }

    val today = LocalDate.now()
    val buckets = when (range) {
        StatsRange.DAY -> List(7) { today }
        StatsRange.WEEK -> (0..6).map { today.minusDays((6 - it).toLong()) }
        StatsRange.MONTH -> (0..6).map { today.minusDays(((6 - it) * 5).toLong()) }
        StatsRange.YEAR -> (0..6).map { today.minusDays(((6 - it) * 52).toLong()) }
    }

    val values = buckets.mapIndexed { index, bucketDate ->
        when (range) {
            StatsRange.DAY -> logs.filter { it.performedOn == today }.sumOf { dayLogScore(it) }.toFloat()
            StatsRange.WEEK -> logs.filter { it.performedOn == bucketDate }.sumOf { dayLogScore(it) }.toFloat()
            StatsRange.MONTH -> {
                val start = today.minusDays(((6 - index) * 5).toLong())
                val end = if (index == 6) today else today.minusDays(((5 - index) * 5).toLong() + 1)
                logs.filter { it.performedOn in start..end }.sumOf { dayLogScore(it) }.toFloat()
            }
            StatsRange.YEAR -> {
                val start = today.minusDays(((6 - index) * 52).toLong())
                val end = if (index == 6) today else today.minusDays(((5 - index) * 52).toLong() + 1)
                logs.filter { it.performedOn in start..end }.sumOf { dayLogScore(it) }.toFloat()
            }
        }
    }
    val max = values.maxOrNull()?.takeIf { it > 0f } ?: return List(7) { 0.08f }
    return values.map { ((it / max) * 0.8f).coerceIn(0.08f, 0.92f) }
}

internal fun DashboardSnapshot.runningComparison(range: StatsRange): Double {
    val today = LocalDate.now()
    val current = distanceForRange(range)
    val previous = when (range) {
        StatsRange.DAY -> recentLogs.filter { it.performedOn == today.minusDays(1) }.sumOf { it.actualDistanceKm ?: 0.0 }
        StatsRange.WEEK -> recentLogs.filter { it.performedOn in today.minusDays(13)..today.minusDays(7) }.sumOf { it.actualDistanceKm ?: 0.0 }
        StatsRange.MONTH -> recentLogs.filter { it.performedOn in today.minusDays(59)..today.minusDays(30) }.sumOf { it.actualDistanceKm ?: 0.0 }
        StatsRange.YEAR -> recentLogs.filter { it.performedOn in today.minusDays(729)..today.minusDays(365) }.sumOf { it.actualDistanceKm ?: 0.0 }
    }
    return current - previous
}

internal fun DashboardSnapshot.strengthComparison(range: StatsRange): Int {
    val today = LocalDate.now()
    val current = volumeForRange(range)
    val previous = when (range) {
        StatsRange.DAY -> recentLogs.filter { it.performedOn == today.minusDays(1) }.sumOf { it.loggedStrengthVolume() }
        StatsRange.WEEK -> recentLogs.filter { it.performedOn in today.minusDays(13)..today.minusDays(7) }.sumOf { it.loggedStrengthVolume() }
        StatsRange.MONTH -> recentLogs.filter { it.performedOn in today.minusDays(59)..today.minusDays(30) }.sumOf { it.loggedStrengthVolume() }
        StatsRange.YEAR -> recentLogs.filter { it.performedOn in today.minusDays(729)..today.minusDays(365) }.sumOf { it.loggedStrengthVolume() }
    }
    return current - previous
}

internal fun WorkoutLog.activitySubtitle(): String {
    val dayGap = ChronoUnit.DAYS.between(performedOn, LocalDate.now()).toInt()
    val dayLabel = when (dayGap) {
        0 -> "Hari ini"
        1 -> "Kemarin"
        else -> "$dayGap hari lalu"
    }
    val detail = when {
        actualDistanceKm != null -> "${"%.2f".format(actualDistanceKm)} km"
        loggedStrengthVolume() > 0 -> "${loggedStrengthVolume()} kg"
        else -> "${actualDurationMinutes} menit"
    }
    return "$dayLabel - $detail"
}

internal fun WorkoutSession.sessionConfidence(): Int {
    return when (plannerState) {
        SessionPlannerState.COMPLETED -> 100
        SessionPlannerState.SKIPPED -> 35
        SessionPlannerState.PLANNED -> 0
    }
}

internal fun WorkoutSession.programProgress(): Float {
    return when (plannerState) {
        SessionPlannerState.COMPLETED -> 1f
        SessionPlannerState.SKIPPED -> 0.1f
        SessionPlannerState.PLANNED -> 0f
    }
}

internal fun WorkoutSession.aiTargetCalories(recommendation: Recommendation?): Int {
    val targetDuration = recommendation?.targetDurationMinutes ?: targetDurationMinutes
    val targetDistance = recommendation?.targetDistanceKm ?: targetDistanceKm ?: 0.0
    val strengthLoad = ((recommendation?.targetSets ?: targetSets ?: 0) * (recommendation?.targetReps ?: targetReps ?: 0))
    return ((targetDuration * 6) + (targetDistance * 60).roundToInt() + (strengthLoad * 3)).coerceAtLeast(0)
}

internal fun WorkoutLog.loggedStrengthVolume(): Int {
    val fromEntries = exerciseEntries.sumOf { entry ->
        (entry.actualSets * entry.actualReps * entry.actualWeightKg).roundToInt()
    }
    if (fromEntries > 0) return fromEntries
    return ((completedSets ?: 0) * (completedReps ?: 0) * 10).coerceAtLeast(0)
}

internal fun WorkoutLog.paceLabel(): String {
    val seconds = averagePaceSecondsPerKm ?: return "-"
    val minutes = seconds / 60
    val remaining = seconds % 60
    return "%d'%02d\"".format(minutes, remaining)
}

internal fun String.initials(): String {
    return split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercaseChar().toString() }
        .ifBlank { "HF" }
}

internal fun Int.formatThousands(): String {
    return "%,d".format(this).replace(',', '.')
}

internal fun buildStarterGymExercises(
    profile: UserProfile?,
    fallbackGoal: GoalOption,
    fallbackExperience: ExperienceOption,
    fallbackAge: Int?,
    fallbackWeightKg: Double?,
    fallbackHeightCm: Int?
): List<GymExerciseUi> {
    val resolvedGoal = profile?.goal ?: fallbackGoal.mappedGoal
    val resolvedLevel = profile?.fitnessLevel ?: fallbackExperience.mappedLevel
    val resolvedAge = (profile?.age ?: fallbackAge ?: 22).coerceIn(12, 80)
    val resolvedHeightCm = (profile?.heightCm ?: fallbackHeightCm ?: 170).coerceIn(135, 220)
    val resolvedWeightKg = (profile?.weightKg ?: fallbackWeightKg ?: estimatedWeightFromHeight(resolvedHeightCm))
        .coerceIn(35.0, 180.0)

    val baseSets = when (resolvedGoal) {
        FitnessGoal.MUSCLE_GAIN -> 4
        FitnessGoal.WEIGHT_LOSS -> 3
        FitnessGoal.ENDURANCE -> 2
        FitnessGoal.GENERAL_FITNESS -> 3
    } + when (resolvedLevel) {
        FitnessLevel.BEGINNER -> 0
        FitnessLevel.INTERMEDIATE -> 1
        FitnessLevel.ADVANCED -> 1
    }

    val baseReps = when (resolvedGoal) {
        FitnessGoal.MUSCLE_GAIN -> 8
        FitnessGoal.WEIGHT_LOSS -> 12
        FitnessGoal.ENDURANCE -> 10
        FitnessGoal.GENERAL_FITNESS -> 10
    } + when (resolvedLevel) {
        FitnessLevel.ADVANCED -> -1
        else -> 0
    }

    val benchSets = baseSets.coerceIn(3, 5)
    val benchReps = baseReps.coerceIn(8, 14)
    val squatSets = (baseSets + if (resolvedGoal == FitnessGoal.MUSCLE_GAIN) 0 else -1).coerceIn(3, 5)
    val squatReps = (baseReps + if (resolvedGoal == FitnessGoal.WEIGHT_LOSS) 2 else 0).coerceIn(8, 15)
    val deadliftSets = (baseSets - 1).coerceIn(2, 4)
    val deadliftReps = (baseReps - 2).coerceIn(6, 12)

    val benchWeight = suggestedWorkingWeightKg(
        bodyWeightKg = resolvedWeightKg,
        age = resolvedAge,
        heightCm = resolvedHeightCm,
        fitnessLevel = resolvedLevel,
        goal = resolvedGoal,
        liftBias = 0.78
    )
    val squatWeight = suggestedWorkingWeightKg(
        bodyWeightKg = resolvedWeightKg,
        age = resolvedAge,
        heightCm = resolvedHeightCm,
        fitnessLevel = resolvedLevel,
        goal = resolvedGoal,
        liftBias = 1.08
    )
    val deadliftWeight = suggestedWorkingWeightKg(
        bodyWeightKg = resolvedWeightKg,
        age = resolvedAge,
        heightCm = resolvedHeightCm,
        fitnessLevel = resolvedLevel,
        goal = resolvedGoal,
        liftBias = 1.28
    )

    return listOf(
        buildGymExercise(
            name = "Bench Press",
            icon = Icons.Outlined.FitnessCenter,
            sets = benchSets,
            reps = benchReps,
            workingWeightKg = benchWeight
        ),
        buildGymExercise(
            name = "Squat",
            icon = Icons.Outlined.SportsGymnastics,
            sets = squatSets,
            reps = squatReps,
            workingWeightKg = squatWeight
        ),
        buildGymExercise(
            name = "Deadlift",
            icon = Icons.Outlined.FitnessCenter,
            sets = deadliftSets,
            reps = deadliftReps,
            workingWeightKg = deadliftWeight
        )
    )
}

private fun dayLogScore(log: WorkoutLog): Int {
    return when {
        log.actualDistanceKm != null -> (log.actualDistanceKm * 100).roundToInt()
        else -> log.loggedStrengthVolume().coerceAtLeast(log.actualDurationMinutes * 10)
    }
}

private fun buildGymExercise(
    name: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    sets: Int,
    reps: Int,
    workingWeightKg: Double
): GymExerciseUi {
    val totalVolume = (sets * reps * workingWeightKg).roundToInt().coerceAtLeast(0)
    return GymExerciseUi(
        name = name,
        subtitle = "Target awal - $sets set - $reps reps - ${totalVolume}kg total",
        icon = icon,
        defaultSets = sets,
        defaultReps = reps,
        defaultVolume = totalVolume
    )
}

private fun suggestedWorkingWeightKg(
    bodyWeightKg: Double,
    age: Int,
    heightCm: Int,
    fitnessLevel: FitnessLevel,
    goal: FitnessGoal,
    liftBias: Double
): Double {
    val bmi = bodyMassIndex(bodyWeightKg, heightCm)
    val levelFactor = when (fitnessLevel) {
        FitnessLevel.BEGINNER -> 0.34
        FitnessLevel.INTERMEDIATE -> 0.46
        FitnessLevel.ADVANCED -> 0.58
    }
    val goalFactor = when (goal) {
        FitnessGoal.MUSCLE_GAIN -> 1.1
        FitnessGoal.GENERAL_FITNESS -> 1.0
        FitnessGoal.WEIGHT_LOSS -> 0.9
        FitnessGoal.ENDURANCE -> 0.82
    }
    val ageFactor = when {
        age in 12..17 -> 0.9
        age >= 45 -> 0.88
        else -> 1.0
    }
    val bmiFactor = when {
        bmi < 19.0 -> 0.92
        bmi >= 30.0 -> 0.94
        else -> 1.0
    }
    return roundToNearestTwoPointFive(
        bodyWeightKg * levelFactor * goalFactor * ageFactor * bmiFactor * liftBias
    ).coerceAtLeast(10.0)
}

private fun estimatedWeightFromHeight(heightCm: Int): Double {
    val heightMeter = heightCm / 100.0
    return bodyMassFromBmi(heightMeter, 22.0)
}

private fun bodyMassIndex(weightKg: Double, heightCm: Int): Double {
    val heightMeter = (heightCm / 100.0).coerceAtLeast(1.0)
    return weightKg / heightMeter.pow(2)
}

private fun bodyMassFromBmi(heightMeter: Double, bmi: Double): Double {
    return heightMeter.pow(2) * bmi
}

private fun roundToNearestTwoPointFive(value: Double): Double {
    return (value / 2.5).roundToInt() * 2.5
}

private inline fun <T> List<T>.takeWhileIndexed(predicate: (Int, T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    forEachIndexed { index, value ->
        if (predicate(index, value)) {
            result += value
        } else {
            return result
        }
    }
    return result
}
