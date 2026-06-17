package com.athallah.hybridfit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.athallah.hybridfit.data.local.converter.DbConverters
import com.athallah.hybridfit.data.local.dao.RecoveryCheckInDao
import com.athallah.hybridfit.data.local.dao.RecommendationDao
import com.athallah.hybridfit.data.local.dao.RecommendationFeedbackDao
import com.athallah.hybridfit.data.local.dao.StrengthExerciseEntryDao
import com.athallah.hybridfit.data.local.dao.UserProfileDao
import com.athallah.hybridfit.data.local.dao.WorkoutLogDao
import com.athallah.hybridfit.data.local.dao.WorkoutPlanDao
import com.athallah.hybridfit.data.local.entity.RecoveryCheckInEntity
import com.athallah.hybridfit.data.local.entity.RecommendationEntity
import com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity
import com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity
import com.athallah.hybridfit.data.local.entity.UserProfileEntity
import com.athallah.hybridfit.data.local.entity.WorkoutLogEntity
import com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity
import com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity

@Database(
    entities = [
        UserProfileEntity::class,
        WorkoutPlanEntity::class,
        WorkoutSessionEntity::class,
        WorkoutLogEntity::class,
        StrengthExerciseEntryEntity::class,
        RecoveryCheckInEntity::class,
        RecommendationEntity::class,
        RecommendationFeedbackEntity::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(DbConverters::class)
abstract class HybridFitDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun workoutPlanDao(): WorkoutPlanDao
    abstract fun workoutLogDao(): WorkoutLogDao
    abstract fun strengthExerciseEntryDao(): StrengthExerciseEntryDao
    abstract fun recoveryCheckInDao(): RecoveryCheckInDao
    abstract fun recommendationDao(): RecommendationDao
    abstract fun recommendationFeedbackDao(): RecommendationFeedbackDao
}
