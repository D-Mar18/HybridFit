package com.athallah.hybridfit.data.local.converter;

import androidx.room.TypeConverter;
import com.athallah.hybridfit.domain.model.FitnessGoal;
import com.athallah.hybridfit.domain.model.FitnessLevel;
import com.athallah.hybridfit.domain.model.PlanStatus;
import com.athallah.hybridfit.domain.model.RecommendationAction;
import com.athallah.hybridfit.domain.model.SessionPlannerState;
import com.athallah.hybridfit.domain.model.WorkoutCategory;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\nH\u0007J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\fH\u0007J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u000eH\u0007J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0010H\u0007J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0012H\u0007J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0014H\u0007J\u0010\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u001a\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u001d\u001a\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u001f\u001a\u00020\u00162\u0006\u0010\u0005\u001a\u00020\u0004H\u0007\u00a8\u0006 "}, d2 = {"Lcom/athallah/hybridfit/data/local/converter/DbConverters;", "", "()V", "fromDayOfWeek", "", "value", "Ljava/time/DayOfWeek;", "fromFitnessGoal", "Lcom/athallah/hybridfit/domain/model/FitnessGoal;", "fromFitnessLevel", "Lcom/athallah/hybridfit/domain/model/FitnessLevel;", "fromInstant", "Ljava/time/Instant;", "fromLocalDate", "Ljava/time/LocalDate;", "fromPlanStatus", "Lcom/athallah/hybridfit/domain/model/PlanStatus;", "fromRecommendationAction", "Lcom/athallah/hybridfit/domain/model/RecommendationAction;", "fromSessionPlannerState", "Lcom/athallah/hybridfit/domain/model/SessionPlannerState;", "fromWorkoutCategory", "Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "toDayOfWeek", "toFitnessGoal", "toFitnessLevel", "toInstant", "toLocalDate", "toPlanStatus", "toRecommendationAction", "toSessionPlannerState", "toWorkoutCategory", "app_debug"})
public final class DbConverters {
    
    public DbConverters() {
        super();
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromFitnessGoal(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessGoal value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.FitnessGoal toFitnessGoal(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromFitnessLevel(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessLevel value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.FitnessLevel toFitnessLevel(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromWorkoutCategory(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.WorkoutCategory toWorkoutCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromPlanStatus(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.PlanStatus value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.PlanStatus toPlanStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromSessionPlannerState(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.SessionPlannerState value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.SessionPlannerState toSessionPlannerState(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromRecommendationAction(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.RecommendationAction value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.RecommendationAction toRecommendationAction(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromDayOfWeek(@org.jetbrains.annotations.NotNull()
    java.time.DayOfWeek value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.time.DayOfWeek toDayOfWeek(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromLocalDate(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate toLocalDate(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromInstant(@org.jetbrains.annotations.NotNull()
    java.time.Instant value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant toInstant(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
}