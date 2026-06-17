package com.athallah.hybridfit.domain.model;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BY\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u0007\u00a2\u0006\u0002\u0010\u0012J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\t\u0010&\u001a\u00020\fH\u00c6\u0003J\t\u0010'\u001a\u00020\u000eH\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00100\u0007H\u00c6\u0003Jm\u0010*\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u0007H\u00c6\u0001J\u0013\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010.\u001a\u00020\fH\u00d6\u0001J\t\u0010/\u001a\u000200H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010 \u00a8\u00061"}, d2 = {"Lcom/athallah/hybridfit/domain/model/DashboardSnapshot;", "", "profile", "Lcom/athallah/hybridfit/domain/model/UserProfile;", "activePlan", "Lcom/athallah/hybridfit/domain/model/WorkoutPlan;", "recentLogs", "", "Lcom/athallah/hybridfit/domain/model/WorkoutLog;", "latestRecommendation", "Lcom/athallah/hybridfit/domain/model/Recommendation;", "feedbackCount", "", "progressSummary", "Lcom/athallah/hybridfit/domain/model/ProgressSummary;", "latestRecovery", "Lcom/athallah/hybridfit/domain/model/RecoveryCheckIn;", "recoveryHistory", "(Lcom/athallah/hybridfit/domain/model/UserProfile;Lcom/athallah/hybridfit/domain/model/WorkoutPlan;Ljava/util/List;Lcom/athallah/hybridfit/domain/model/Recommendation;ILcom/athallah/hybridfit/domain/model/ProgressSummary;Lcom/athallah/hybridfit/domain/model/RecoveryCheckIn;Ljava/util/List;)V", "getActivePlan", "()Lcom/athallah/hybridfit/domain/model/WorkoutPlan;", "getFeedbackCount", "()I", "getLatestRecommendation", "()Lcom/athallah/hybridfit/domain/model/Recommendation;", "getLatestRecovery", "()Lcom/athallah/hybridfit/domain/model/RecoveryCheckIn;", "getProfile", "()Lcom/athallah/hybridfit/domain/model/UserProfile;", "getProgressSummary", "()Lcom/athallah/hybridfit/domain/model/ProgressSummary;", "getRecentLogs", "()Ljava/util/List;", "getRecoveryHistory", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class DashboardSnapshot {
    @org.jetbrains.annotations.Nullable()
    private final com.athallah.hybridfit.domain.model.UserProfile profile = null;
    @org.jetbrains.annotations.Nullable()
    private final com.athallah.hybridfit.domain.model.WorkoutPlan activePlan = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.athallah.hybridfit.domain.model.WorkoutLog> recentLogs = null;
    @org.jetbrains.annotations.Nullable()
    private final com.athallah.hybridfit.domain.model.Recommendation latestRecommendation = null;
    private final int feedbackCount = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.model.ProgressSummary progressSummary = null;
    @org.jetbrains.annotations.Nullable()
    private final com.athallah.hybridfit.domain.model.RecoveryCheckIn latestRecovery = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.athallah.hybridfit.domain.model.RecoveryCheckIn> recoveryHistory = null;
    
    public DashboardSnapshot(@org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.UserProfile profile, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.WorkoutPlan activePlan, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.WorkoutLog> recentLogs, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.Recommendation latestRecommendation, int feedbackCount, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.ProgressSummary progressSummary, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.RecoveryCheckIn latestRecovery, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.RecoveryCheckIn> recoveryHistory) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.athallah.hybridfit.domain.model.UserProfile getProfile() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.athallah.hybridfit.domain.model.WorkoutPlan getActivePlan() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.athallah.hybridfit.domain.model.WorkoutLog> getRecentLogs() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.athallah.hybridfit.domain.model.Recommendation getLatestRecommendation() {
        return null;
    }
    
    public final int getFeedbackCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.ProgressSummary getProgressSummary() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.athallah.hybridfit.domain.model.RecoveryCheckIn getLatestRecovery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.athallah.hybridfit.domain.model.RecoveryCheckIn> getRecoveryHistory() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.athallah.hybridfit.domain.model.UserProfile component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.athallah.hybridfit.domain.model.WorkoutPlan component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.athallah.hybridfit.domain.model.WorkoutLog> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.athallah.hybridfit.domain.model.Recommendation component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.ProgressSummary component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.athallah.hybridfit.domain.model.RecoveryCheckIn component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.athallah.hybridfit.domain.model.RecoveryCheckIn> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.DashboardSnapshot copy(@org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.UserProfile profile, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.WorkoutPlan activePlan, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.WorkoutLog> recentLogs, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.Recommendation latestRecommendation, int feedbackCount, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.ProgressSummary progressSummary, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.RecoveryCheckIn latestRecovery, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.RecoveryCheckIn> recoveryHistory) {
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