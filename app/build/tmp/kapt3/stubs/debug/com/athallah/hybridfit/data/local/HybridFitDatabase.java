package com.athallah.hybridfit.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.athallah.hybridfit.data.local.converter.DbConverters;
import com.athallah.hybridfit.data.local.dao.RecoveryCheckInDao;
import com.athallah.hybridfit.data.local.dao.RecommendationDao;
import com.athallah.hybridfit.data.local.dao.RecommendationFeedbackDao;
import com.athallah.hybridfit.data.local.dao.StrengthExerciseEntryDao;
import com.athallah.hybridfit.data.local.dao.UserProfileDao;
import com.athallah.hybridfit.data.local.dao.WorkoutLogDao;
import com.athallah.hybridfit.data.local.dao.WorkoutPlanDao;
import com.athallah.hybridfit.data.local.entity.RecoveryCheckInEntity;
import com.athallah.hybridfit.data.local.entity.RecommendationEntity;
import com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity;
import com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity;
import com.athallah.hybridfit.data.local.entity.UserProfileEntity;
import com.athallah.hybridfit.data.local.entity.WorkoutLogEntity;
import com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity;
import com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&\u00a8\u0006\u0011"}, d2 = {"Lcom/athallah/hybridfit/data/local/HybridFitDatabase;", "Landroidx/room/RoomDatabase;", "()V", "recommendationDao", "Lcom/athallah/hybridfit/data/local/dao/RecommendationDao;", "recommendationFeedbackDao", "Lcom/athallah/hybridfit/data/local/dao/RecommendationFeedbackDao;", "recoveryCheckInDao", "Lcom/athallah/hybridfit/data/local/dao/RecoveryCheckInDao;", "strengthExerciseEntryDao", "Lcom/athallah/hybridfit/data/local/dao/StrengthExerciseEntryDao;", "userProfileDao", "Lcom/athallah/hybridfit/data/local/dao/UserProfileDao;", "workoutLogDao", "Lcom/athallah/hybridfit/data/local/dao/WorkoutLogDao;", "workoutPlanDao", "Lcom/athallah/hybridfit/data/local/dao/WorkoutPlanDao;", "app_debug"})
@androidx.room.Database(entities = {com.athallah.hybridfit.data.local.entity.UserProfileEntity.class, com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity.class, com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity.class, com.athallah.hybridfit.data.local.entity.WorkoutLogEntity.class, com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity.class, com.athallah.hybridfit.data.local.entity.RecoveryCheckInEntity.class, com.athallah.hybridfit.data.local.entity.RecommendationEntity.class, com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity.class}, version = 4, exportSchema = false)
@androidx.room.TypeConverters(value = {com.athallah.hybridfit.data.local.converter.DbConverters.class})
public abstract class HybridFitDatabase extends androidx.room.RoomDatabase {
    
    public HybridFitDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.athallah.hybridfit.data.local.dao.UserProfileDao userProfileDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.athallah.hybridfit.data.local.dao.WorkoutPlanDao workoutPlanDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.athallah.hybridfit.data.local.dao.WorkoutLogDao workoutLogDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.athallah.hybridfit.data.local.dao.StrengthExerciseEntryDao strengthExerciseEntryDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.athallah.hybridfit.data.local.dao.RecoveryCheckInDao recoveryCheckInDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.athallah.hybridfit.data.local.dao.RecommendationDao recommendationDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.athallah.hybridfit.data.local.dao.RecommendationFeedbackDao recommendationFeedbackDao();
}