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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b)\b\u0087\b\u0018\u00002\u00020\u0001Bo\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\b\u0012\b\u0010\r\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\u000e\u001a\u00020\b\u0012\u0006\u0010\u000f\u001a\u00020\b\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\u0002\u0010\u0014J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\bH\u00c6\u0003J\t\u0010,\u001a\u00020\u0011H\u00c6\u0003J\t\u0010-\u001a\u00020\u0013H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0006H\u00c6\u0003J\t\u00100\u001a\u00020\bH\u00c6\u0003J\u0010\u00101\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u00102\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u0010\u00103\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u0010\u00104\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\t\u00105\u001a\u00020\bH\u00c6\u0003J\u008e\u0001\u00106\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\u000e\u001a\u00020\b2\b\b\u0002\u0010\u000f\u001a\u00020\b2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u00c6\u0001\u00a2\u0006\u0002\u00107J\u0013\u00108\u001a\u00020\u00132\b\u00109\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010:\u001a\u00020\bH\u00d6\u0001J\t\u0010;\u001a\u00020\u0011H\u00d6\u0001R\u0015\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0015\u0010\r\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001d\u0010\u001bR\u0015\u0010\f\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001e\u0010\u001bR\u0011\u0010\u000e\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0011\u0010\u000f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b'\u0010\"R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)\u00a8\u0006<"}, d2 = {"Lcom/athallah/hybridfit/data/local/entity/WorkoutLogEntity;", "", "id", "", "sessionId", "performedOn", "Ljava/time/LocalDate;", "actualDurationMinutes", "", "actualDistanceKm", "", "averagePaceSecondsPerKm", "completedSets", "completedReps", "completionPercent", "exertionScore", "notes", "", "wasCompleted", "", "(JJLjava/time/LocalDate;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;IILjava/lang/String;Z)V", "getActualDistanceKm", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getActualDurationMinutes", "()I", "getAveragePaceSecondsPerKm", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getCompletedReps", "getCompletedSets", "getCompletionPercent", "getExertionScore", "getId", "()J", "getNotes", "()Ljava/lang/String;", "getPerformedOn", "()Ljava/time/LocalDate;", "getSessionId", "getWasCompleted", "()Z", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JJLjava/time/LocalDate;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;IILjava/lang/String;Z)Lcom/athallah/hybridfit/data/local/entity/WorkoutLogEntity;", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "workout_logs", foreignKeys = {@androidx.room.ForeignKey(entity = com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity.class, parentColumns = {"id"}, childColumns = {"sessionId"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"sessionId"})})
public final class WorkoutLogEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final long sessionId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalDate performedOn = null;
    private final int actualDurationMinutes = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double actualDistanceKm = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer averagePaceSecondsPerKm = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer completedSets = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer completedReps = null;
    private final int completionPercent = 0;
    private final int exertionScore = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String notes = null;
    private final boolean wasCompleted = false;
    
    public WorkoutLogEntity(long id, long sessionId, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate performedOn, int actualDurationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double actualDistanceKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer averagePaceSecondsPerKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer completedSets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer completedReps, int completionPercent, int exertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, boolean wasCompleted) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getSessionId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate getPerformedOn() {
        return null;
    }
    
    public final int getActualDurationMinutes() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getActualDistanceKm() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getAveragePaceSecondsPerKm() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getCompletedSets() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getCompletedReps() {
        return null;
    }
    
    public final int getCompletionPercent() {
        return 0;
    }
    
    public final int getExertionScore() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNotes() {
        return null;
    }
    
    public final boolean getWasCompleted() {
        return false;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final int component10() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    public final boolean component12() {
        return false;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate component3() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component8() {
        return null;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.data.local.entity.WorkoutLogEntity copy(long id, long sessionId, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate performedOn, int actualDurationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double actualDistanceKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer averagePaceSecondsPerKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer completedSets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer completedReps, int completionPercent, int exertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, boolean wasCompleted) {
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