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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u0085\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\r\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u0012\u001a\u00020\r\u0012\u0006\u0010\u0013\u001a\u00020\b\u0012\u0006\u0010\u0014\u001a\u00020\r\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\u0006\u0010\u0017\u001a\u00020\b\u00a2\u0006\u0002\u0010\u0018J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\u0010\u00103\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010.J\t\u00104\u001a\u00020\rH\u00c6\u0003J\t\u00105\u001a\u00020\bH\u00c6\u0003J\t\u00106\u001a\u00020\rH\u00c6\u0003J\t\u00107\u001a\u00020\u0016H\u00c6\u0003J\t\u00108\u001a\u00020\bH\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0006H\u00c6\u0003J\t\u0010;\u001a\u00020\bH\u00c6\u0003J\t\u0010<\u001a\u00020\nH\u00c6\u0003J\t\u0010=\u001a\u00020\bH\u00c6\u0003J\t\u0010>\u001a\u00020\rH\u00c6\u0003J\u0010\u0010?\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0002\u0010*J\u0010\u0010@\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010.J\u00aa\u0001\u0010A\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\b2\b\b\u0002\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010\u0012\u001a\u00020\r2\b\b\u0002\u0010\u0013\u001a\u00020\b2\b\b\u0002\u0010\u0014\u001a\u00020\r2\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\bH\u00c6\u0001\u00a2\u0006\u0002\u0010BJ\u0013\u0010C\u001a\u00020D2\b\u0010E\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010F\u001a\u00020\rH\u00d6\u0001J\t\u0010G\u001a\u00020\bH\u00d6\u0001R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u000b\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0013\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001eR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0014\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010!R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0012\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b'\u0010#R\u0011\u0010\u0017\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001eR\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\n\n\u0002\u0010+\u001a\u0004\b)\u0010*R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010#R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\r\u00a2\u0006\n\n\u0002\u0010/\u001a\u0004\b-\u0010.R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\r\u00a2\u0006\n\n\u0002\u0010/\u001a\u0004\b0\u0010.R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001e\u00a8\u0006H"}, d2 = {"Lcom/athallah/hybridfit/data/local/entity/WorkoutSessionEntity;", "", "id", "", "planId", "dayOfWeek", "Ljava/time/DayOfWeek;", "title", "", "category", "Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "focusArea", "targetDurationMinutes", "", "targetDistanceKm", "", "targetSets", "targetReps", "restSeconds", "guidance", "orderInDay", "plannerState", "Lcom/athallah/hybridfit/domain/model/SessionPlannerState;", "sessionNotes", "(JJLjava/time/DayOfWeek;Ljava/lang/String;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;Ljava/lang/String;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;ILcom/athallah/hybridfit/domain/model/SessionPlannerState;Ljava/lang/String;)V", "getCategory", "()Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "getDayOfWeek", "()Ljava/time/DayOfWeek;", "getFocusArea", "()Ljava/lang/String;", "getGuidance", "getId", "()J", "getOrderInDay", "()I", "getPlanId", "getPlannerState", "()Lcom/athallah/hybridfit/domain/model/SessionPlannerState;", "getRestSeconds", "getSessionNotes", "getTargetDistanceKm", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getTargetDurationMinutes", "getTargetReps", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getTargetSets", "getTitle", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JJLjava/time/DayOfWeek;Ljava/lang/String;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;Ljava/lang/String;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;ILcom/athallah/hybridfit/domain/model/SessionPlannerState;Ljava/lang/String;)Lcom/athallah/hybridfit/data/local/entity/WorkoutSessionEntity;", "equals", "", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "workout_sessions", foreignKeys = {@androidx.room.ForeignKey(entity = com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity.class, parentColumns = {"id"}, childColumns = {"planId"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"planId"})})
public final class WorkoutSessionEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final long planId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.time.DayOfWeek dayOfWeek = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.model.WorkoutCategory category = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String focusArea = null;
    private final int targetDurationMinutes = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double targetDistanceKm = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer targetSets = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer targetReps = null;
    private final int restSeconds = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String guidance = null;
    private final int orderInDay = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.model.SessionPlannerState plannerState = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sessionNotes = null;
    
    public WorkoutSessionEntity(long id, long planId, @org.jetbrains.annotations.NotNull()
    java.time.DayOfWeek dayOfWeek, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory category, @org.jetbrains.annotations.NotNull()
    java.lang.String focusArea, int targetDurationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetDistanceKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetSets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetReps, int restSeconds, @org.jetbrains.annotations.NotNull()
    java.lang.String guidance, int orderInDay, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.SessionPlannerState plannerState, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionNotes) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getPlanId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.DayOfWeek getDayOfWeek() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.WorkoutCategory getCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFocusArea() {
        return null;
    }
    
    public final int getTargetDurationMinutes() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTargetDistanceKm() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getTargetSets() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getTargetReps() {
        return null;
    }
    
    public final int getRestSeconds() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGuidance() {
        return null;
    }
    
    public final int getOrderInDay() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.SessionPlannerState getPlannerState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSessionNotes() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component10() {
        return null;
    }
    
    public final int component11() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    public final int component13() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.SessionPlannerState component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.DayOfWeek component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.WorkoutCategory component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    public final int component7() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity copy(long id, long planId, @org.jetbrains.annotations.NotNull()
    java.time.DayOfWeek dayOfWeek, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory category, @org.jetbrains.annotations.NotNull()
    java.lang.String focusArea, int targetDurationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetDistanceKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetSets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetReps, int restSeconds, @org.jetbrains.annotations.NotNull()
    java.lang.String guidance, int orderInDay, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.SessionPlannerState plannerState, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionNotes) {
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