package com.athallah.hybridfit.domain.model;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b.\b\u0086\b\u0018\u00002\u00020\u0001B\u0083\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u0010\u001a\u00020\n\u0012\u0006\u0010\u0011\u001a\u00020\n\u0012\u0006\u0010\u0012\u001a\u00020\u0006\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016\u00a2\u0006\u0002\u0010\u0018J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\nH\u00c6\u0003J\t\u00103\u001a\u00020\nH\u00c6\u0003J\t\u00104\u001a\u00020\u0006H\u00c6\u0003J\t\u00105\u001a\u00020\u0014H\u00c6\u0003J\u000f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\u0006H\u00c6\u0003J\t\u00109\u001a\u00020\bH\u00c6\u0003J\t\u0010:\u001a\u00020\nH\u00c6\u0003J\u0010\u0010;\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\u0010\u0010<\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010=\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010>\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u00a8\u0001\u0010?\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u0010\u001a\u00020\n2\b\b\u0002\u0010\u0011\u001a\u00020\n2\b\b\u0002\u0010\u0012\u001a\u00020\u00062\b\b\u0002\u0010\u0013\u001a\u00020\u00142\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u00c6\u0001\u00a2\u0006\u0002\u0010@J\u0013\u0010A\u001a\u00020\u00142\b\u0010B\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010C\u001a\u00020\nH\u00d6\u0001J\t\u0010D\u001a\u00020\u0006H\u00d6\u0001R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0015\u0010\r\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b\u001e\u0010\u001fR\u0015\u0010\u000f\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b!\u0010\u001fR\u0015\u0010\u000e\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b\"\u0010\u001fR\u0011\u0010\u0010\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001dR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010\u0011\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001dR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0011\u0010\u0012\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010(R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010*R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100\u00a8\u0006E"}, d2 = {"Lcom/athallah/hybridfit/domain/model/WorkoutLog;", "", "id", "", "sessionId", "sessionTitle", "", "performedOn", "Ljava/time/LocalDate;", "actualDurationMinutes", "", "actualDistanceKm", "", "averagePaceSecondsPerKm", "completedSets", "completedReps", "completionPercent", "exertionScore", "notes", "wasCompleted", "", "exerciseEntries", "", "Lcom/athallah/hybridfit/domain/model/StrengthExerciseEntry;", "(JJLjava/lang/String;Ljava/time/LocalDate;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;IILjava/lang/String;ZLjava/util/List;)V", "getActualDistanceKm", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getActualDurationMinutes", "()I", "getAveragePaceSecondsPerKm", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getCompletedReps", "getCompletedSets", "getCompletionPercent", "getExerciseEntries", "()Ljava/util/List;", "getExertionScore", "getId", "()J", "getNotes", "()Ljava/lang/String;", "getPerformedOn", "()Ljava/time/LocalDate;", "getSessionId", "getSessionTitle", "getWasCompleted", "()Z", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JJLjava/lang/String;Ljava/time/LocalDate;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;IILjava/lang/String;ZLjava/util/List;)Lcom/athallah/hybridfit/domain/model/WorkoutLog;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class WorkoutLog {
    private final long id = 0L;
    private final long sessionId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sessionTitle = null;
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
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.athallah.hybridfit.domain.model.StrengthExerciseEntry> exerciseEntries = null;
    
    public WorkoutLog(long id, long sessionId, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionTitle, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate performedOn, int actualDurationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double actualDistanceKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer averagePaceSecondsPerKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer completedSets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer completedReps, int completionPercent, int exertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, boolean wasCompleted, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.StrengthExerciseEntry> exerciseEntries) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getSessionId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSessionTitle() {
        return null;
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.athallah.hybridfit.domain.model.StrengthExerciseEntry> getExerciseEntries() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final int component11() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    public final boolean component13() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.athallah.hybridfit.domain.model.StrengthExerciseEntry> component14() {
        return null;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component6() {
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.WorkoutLog copy(long id, long sessionId, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionTitle, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate performedOn, int actualDurationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double actualDistanceKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer averagePaceSecondsPerKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer completedSets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer completedReps, int completionPercent, int exertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, boolean wasCompleted, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.StrengthExerciseEntry> exerciseEntries) {
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