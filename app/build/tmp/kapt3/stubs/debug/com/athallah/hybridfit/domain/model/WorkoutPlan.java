package com.athallah.hybridfit.domain.model;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\u0002\u0010\u0012J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0006H\u00c6\u0003J\t\u0010%\u001a\u00020\bH\u00c6\u0003J\t\u0010&\u001a\u00020\nH\u00c6\u0003J\t\u0010'\u001a\u00020\fH\u00c6\u0003J\t\u0010(\u001a\u00020\u000eH\u00c6\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u00c6\u0003J_\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u00c6\u0001J\u0013\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010.\u001a\u00020\fH\u00d6\u0001J\t\u0010/\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0016\u00a8\u00060"}, d2 = {"Lcom/athallah/hybridfit/domain/model/WorkoutPlan;", "", "id", "", "userId", "title", "", "goal", "Lcom/athallah/hybridfit/domain/model/FitnessGoal;", "startDate", "Ljava/time/LocalDate;", "sessionsPerWeek", "", "status", "Lcom/athallah/hybridfit/domain/model/PlanStatus;", "sessions", "", "Lcom/athallah/hybridfit/domain/model/WorkoutSession;", "(JJLjava/lang/String;Lcom/athallah/hybridfit/domain/model/FitnessGoal;Ljava/time/LocalDate;ILcom/athallah/hybridfit/domain/model/PlanStatus;Ljava/util/List;)V", "getGoal", "()Lcom/athallah/hybridfit/domain/model/FitnessGoal;", "getId", "()J", "getSessions", "()Ljava/util/List;", "getSessionsPerWeek", "()I", "getStartDate", "()Ljava/time/LocalDate;", "getStatus", "()Lcom/athallah/hybridfit/domain/model/PlanStatus;", "getTitle", "()Ljava/lang/String;", "getUserId", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class WorkoutPlan {
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
    private final java.util.List<com.athallah.hybridfit.domain.model.WorkoutSession> sessions = null;
    
    public WorkoutPlan(long id, long userId, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessGoal goal, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, int sessionsPerWeek, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.PlanStatus status, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.WorkoutSession> sessions) {
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
    public final java.util.List<com.athallah.hybridfit.domain.model.WorkoutSession> getSessions() {
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
    public final java.util.List<com.athallah.hybridfit.domain.model.WorkoutSession> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.WorkoutPlan copy(long id, long userId, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessGoal goal, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, int sessionsPerWeek, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.PlanStatus status, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.WorkoutSession> sessions) {
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