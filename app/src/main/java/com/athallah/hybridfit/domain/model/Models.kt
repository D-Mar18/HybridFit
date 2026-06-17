package com.athallah.hybridfit.domain.model

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate

enum class FitnessGoal(val label: String) {
    GENERAL_FITNESS("Kebugaran umum"),
    WEIGHT_LOSS("Penurunan berat badan"),
    MUSCLE_GAIN("Peningkatan massa otot"),
    ENDURANCE("Daya tahan")
}

enum class FitnessLevel(val label: String) {
    BEGINNER("Pemula"),
    INTERMEDIATE("Menengah"),
    ADVANCED("Lanjutan")
}

enum class WorkoutCategory(val label: String) {
    STRENGTH("Strength"),
    CARDIO("Cardio"),
    MOBILITY("Mobility"),
    RECOVERY("Recovery")
}

enum class PlanStatus {
    DRAFT,
    ACTIVE,
    ARCHIVED
}

enum class SessionPlannerState {
    PLANNED,
    SKIPPED,
    COMPLETED
}

enum class RecommendationAction(val label: String) {
    INCREASE("Naik bertahap"),
    MAINTAIN("Pertahankan"),
    RECOVER("Pulihkan")
}

enum class ProgressTrend(val label: String) {
    NO_DATA("Belum ada data"),
    BUILDING("Mulai terbentuk"),
    NEEDS_CONSISTENCY("Perlu konsistensi"),
    IMPROVING("Meningkat"),
    STABLE("Stabil"),
    NEEDS_ATTENTION("Perlu penyesuaian")
}

data class UserProfile(
    val id: Long,
    val fullName: String,
    val age: Int,
    val weightKg: Double,
    val heightCm: Int,
    val genderLabel: String,
    val goal: FitnessGoal,
    val fitnessLevel: FitnessLevel,
    val preferredFocus: WorkoutCategory,
    val workoutDaysPerWeek: Int,
    val sessionDurationMinutes: Int,
    val experienceNotes: String,
    val createdAt: Instant
)

data class WorkoutSession(
    val id: Long,
    val planId: Long,
    val dayOfWeek: DayOfWeek,
    val title: String,
    val category: WorkoutCategory,
    val focusArea: String,
    val targetDurationMinutes: Int,
    val targetDistanceKm: Double?,
    val targetSets: Int?,
    val targetReps: Int?,
    val restSeconds: Int,
    val guidance: String,
    val orderInDay: Int,
    val plannerState: SessionPlannerState,
    val sessionNotes: String
)

data class StrengthExerciseEntry(
    val id: Long,
    val workoutLogId: Long,
    val exerciseName: String,
    val actualSets: Int,
    val actualReps: Int,
    val actualWeightKg: Double,
    val notes: String
)

data class StrengthExerciseDraft(
    val exerciseName: String,
    val actualSets: Int,
    val actualReps: Int,
    val actualWeightKg: Double,
    val notes: String
)

data class WorkoutPlan(
    val id: Long,
    val userId: Long,
    val title: String,
    val goal: FitnessGoal,
    val startDate: LocalDate,
    val sessionsPerWeek: Int,
    val status: PlanStatus,
    val sessions: List<WorkoutSession>
)

data class WorkoutLog(
    val id: Long,
    val sessionId: Long,
    val sessionTitle: String,
    val performedOn: LocalDate,
    val actualDurationMinutes: Int,
    val actualDistanceKm: Double?,
    val averagePaceSecondsPerKm: Int?,
    val completedSets: Int?,
    val completedReps: Int?,
    val completionPercent: Int,
    val exertionScore: Int,
    val notes: String,
    val wasCompleted: Boolean,
    val exerciseEntries: List<StrengthExerciseEntry>
)

data class RecoveryCheckIn(
    val id: Long,
    val userId: Long,
    val recordedOn: LocalDate,
    val sleepHours: Double,
    val recoveryScore: Int,
    val energyLevel: Int,
    val sorenessLevel: Int,
    val notes: String
)

data class Recommendation(
    val id: Long,
    val userId: Long,
    val createdAt: Instant,
    val title: String,
    val summary: String,
    val action: RecommendationAction,
    val targetDurationMinutes: Int,
    val targetDistanceKm: Double?,
    val targetSets: Int?,
    val targetReps: Int?,
    val restSeconds: Int,
    val rationale: String
)

data class RecommendationFeedback(
    val id: Long,
    val recommendationId: Long,
    val wasHelpful: Boolean,
    val rating: Int,
    val notes: String,
    val recordedAt: Instant
)

data class ProgressSummary(
    val totalSessionsLogged: Int,
    val weeklyConsistencyPercent: Int,
    val averageCompletionPercent: Int,
    val averageExertionScore: Int,
    val bestHighlight: String,
    val trend: ProgressTrend
) {
    companion object {
        fun empty() = ProgressSummary(
            totalSessionsLogged = 0,
            weeklyConsistencyPercent = 0,
            averageCompletionPercent = 0,
            averageExertionScore = 0,
            bestHighlight = "Belum ada data latihan.",
            trend = ProgressTrend.NO_DATA
        )
    }
}

data class DashboardSnapshot(
    val profile: UserProfile?,
    val activePlan: WorkoutPlan?,
    val recentLogs: List<WorkoutLog>,
    val latestRecommendation: Recommendation?,
    val feedbackCount: Int,
    val progressSummary: ProgressSummary,
    val latestRecovery: RecoveryCheckIn?,
    val recoveryHistory: List<RecoveryCheckIn>
)
