package com.athallah.hybridfit.data.local.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.athallah.hybridfit.domain.model.FitnessGoal;
import com.athallah.hybridfit.domain.model.FitnessLevel;
import com.athallah.hybridfit.domain.model.PlanStatus;
import com.athallah.hybridfit.domain.model.RecommendationAction;
import com.athallah.hybridfit.domain.model.SessionPlannerState;
import com.athallah.hybridfit.domain.model.WorkoutCategory;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001BG\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\u0002\u0010\u0011J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0006H\u00c6\u0003J\t\u0010$\u001a\u00020\bH\u00c6\u0003J\t\u0010%\u001a\u00020\nH\u00c6\u0003J\t\u0010&\u001a\u00020\fH\u00c6\u0003J\t\u0010'\u001a\u00020\u000eH\u00c6\u0003J\t\u0010(\u001a\u00020\u0010H\u00c6\u0003JY\u0010)\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u00c6\u0001J\u0013\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010-\u001a\u00020\fH\u00d6\u0001J\t\u0010.\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017\u00a8\u0006/"}, d2 = {"Lcom/athallah/hybridfit/data/local/entity/WorkoutPlanEntity;", "", "id", "", "userId", "title", "", "goal", "Lcom/athallah/hybridfit/domain/model/FitnessGoal;", "startDate", "Ljava/time/LocalDate;", "sessionsPerWeek", "", "status", "Lcom/athallah/hybridfit/domain/model/PlanStatus;", "createdAt", "Ljava/time/Instant;", "(JJLjava/lang/String;Lcom/athallah/hybridfit/domain/model/FitnessGoal;Ljava/time/LocalDate;ILcom/athallah/hybridfit/domain/model/PlanStatus;Ljava/time/Instant;)V", "getCreatedAt", "()Ljava/time/Instant;", "getGoal", "()Lcom/athallah/hybridfit/domain/model/FitnessGoal;", "getId", "()J", "getSessionsPerWeek", "()I", "getStartDate", "()Ljava/time/LocalDate;", "getStatus", "()Lcom/athallah/hybridfit/domain/model/PlanStatus;", "getTitle", "()Ljava/lang/String;", "getUserId", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "workout_plans", foreignKeys = {@androidx.room.ForeignKey(entity = com.athallah.hybridfit.data.local.entity.UserProfileEntity.class, parentColumns = {"id"}, childColumns = {"userId"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"userId"})})
public final class WorkoutPlanEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final long userId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.model.FitnessGoal goal = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalDate startDate = null;
    private final int sessionsPerWeek = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.model.PlanStatus status = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant createdAt = null;
    
    public WorkoutPlanEntity(long id, long userId, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessGoal goal, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, int sessionsPerWeek, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.PlanStatus status, @org.jetbrains.annotations.NotNull()
    java.time.Instant createdAt) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getUserId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.FitnessGoal getGoal() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate getStartDate() {
        return null;
    }
    
    public final int getSessionsPerWeek() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.PlanStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getCreatedAt() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.FitnessGoal component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.PlanStatus component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity copy(long id, long userId, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessGoal goal, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, int sessionsPerWeek, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.PlanStatus status, @org.jetbrains.annotations.NotNull()
    java.time.Instant createdAt) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}