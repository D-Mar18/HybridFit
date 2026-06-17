package com.athallah.hybridfit.domain.service;

import com.athallah.hybridfit.domain.model.Recommendation;
import com.athallah.hybridfit.domain.model.RecommendationAction;
import com.athallah.hybridfit.domain.model.RecommendationFeedback;
import com.athallah.hybridfit.domain.model.RecoveryCheckIn;
import com.athallah.hybridfit.domain.model.SessionPlannerState;
import com.athallah.hybridfit.domain.model.UserProfile;
import com.athallah.hybridfit.domain.model.WorkoutLog;
import com.athallah.hybridfit.domain.model.WorkoutSession;
import java.time.Instant;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002JB\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\n2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J\u001a\u0010\u0011\u001a\u00020\u0012*\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0014H\u0002J\u0014\u0010\u0011\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0012H\u0002\u00a8\u0006\u0016"}, d2 = {"Lcom/athallah/hybridfit/domain/service/AdaptiveRecommendationEngine;", "", "()V", "fallbackSession", "Lcom/athallah/hybridfit/domain/model/WorkoutSession;", "profile", "Lcom/athallah/hybridfit/domain/model/UserProfile;", "generate", "Lcom/athallah/hybridfit/domain/model/Recommendation;", "sessions", "", "recentLogs", "Lcom/athallah/hybridfit/domain/model/WorkoutLog;", "feedback", "Lcom/athallah/hybridfit/domain/model/RecommendationFeedback;", "latestRecovery", "Lcom/athallah/hybridfit/domain/model/RecoveryCheckIn;", "ifNaN", "", "fallback", "Lkotlin/Function0;", "value", "app_debug"})
public final class AdaptiveRecommendationEngine {
    
    public AdaptiveRecommendationEngine() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.Recommendation generate(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.UserProfile profile, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.WorkoutSession> sessions, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.WorkoutLog> recentLogs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.RecommendationFeedback> feedback, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.RecoveryCheckIn latestRecovery) {
        return null;
    }
    
    private final com.athallah.hybridfit.domain.model.WorkoutSession fallbackSession(com.athallah.hybridfit.domain.model.UserProfile profile) {
        return null;
    }
    
    private final double ifNaN(double $this$ifNaN, kotlin.jvm.functions.Function0<java.lang.Double> fallback) {
        return 0.0;
    }
    
    private final double ifNaN(double $this$ifNaN, double value) {
        return 0.0;
    }
}