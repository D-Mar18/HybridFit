package com.athallah.hybridfit.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.athallah.hybridfit.domain.model.FitnessGoal
import com.athallah.hybridfit.domain.model.FitnessLevel
import com.athallah.hybridfit.domain.model.PlanStatus
import com.athallah.hybridfit.domain.model.RecommendationAction
import com.athallah.hybridfit.domain.model.SessionPlannerState
import com.athallah.hybridfit.domain.model.WorkoutCategory
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate

const val DEFAULT_USER_ID = 1L

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey val id: Long = DEFAULT_USER_ID,
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

@Entity(
    tableName = "workout_plans",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class WorkoutPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val title: String,
    val goal: FitnessGoal,
    val startDate: LocalDate,
    val sessionsPerWeek: Int,
    val status: PlanStatus,
    val createdAt: Instant
)

@Entity(
    tableName = "workout_sessions",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutPlanEntity::class,
            parentColumns = ["id"],
            childColumns = ["planId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("planId")]
)
data class WorkoutSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
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

@Entity(
    tableName = "workout_logs",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutSessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("sessionId")]
)
data class WorkoutLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val performedOn: LocalDate,
    val actualDurationMinutes: Int,
    val actualDistanceKm: Double?,
    val averagePaceSecondsPerKm: Int?,
    val completedSets: Int?,
    val completedReps: Int?,
    val completionPercent: Int,
    val exertionScore: Int,
    val notes: String,
    val wasCompleted: Boolean
)

@Entity(
    tableName = "strength_exercise_entries",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutLogEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutLogId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("workoutLogId")]
)
data class StrengthExerciseEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val workoutLogId: Long,
    val exerciseName: String,
    val actualSets: Int,
    val actualReps: Int,
    val actualWeightKg: Double,
    val notes: String
)

@Entity(
    tableName = "recovery_check_ins",
    indices = [Index("userId")]
)
data class RecoveryCheckInEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val recordedOn: LocalDate,
    val sleepHours: Double,
    val recoveryScore: Int,
    val energyLevel: Int,
    val sorenessLevel: Int,
    val notes: String
)

@Entity(
    tableName = "recommendations",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class RecommendationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
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

@Entity(
    tableName = "recommendation_feedback",
    foreignKeys = [
        ForeignKey(
            entity = RecommendationEntity::class,
            parentColumns = ["id"],
            childColumns = ["recommendationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("recommendationId")]
)
data class RecommendationFeedbackEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recommendationId: Long,
    val wasHelpful: Boolean,
    val rating: Int,
    val notes: String,
    val recordedAt: Instant
)
