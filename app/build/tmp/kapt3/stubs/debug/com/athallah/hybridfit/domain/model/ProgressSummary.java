package com.athallah.hybridfit.domain.model;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\b\u0018\u0000 !2\u00020\u0001:\u0001!B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001a\u001a\u00020\nH\u00c6\u0003JE\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00d6\u0001J\t\u0010 \u001a\u00020\bH\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\r\u00a8\u0006\""}, d2 = {"Lcom/athallah/hybridfit/domain/model/ProgressSummary;", "", "totalSessionsLogged", "", "weeklyConsistencyPercent", "averageCompletionPercent", "averageExertionScore", "bestHighlight", "", "trend", "Lcom/athallah/hybridfit/domain/model/ProgressTrend;", "(IIIILjava/lang/String;Lcom/athallah/hybridfit/domain/model/ProgressTrend;)V", "getAverageCompletionPercent", "()I", "getAverageExertionScore", "getBestHighlight", "()Ljava/lang/String;", "getTotalSessionsLogged", "getTrend", "()Lcom/athallah/hybridfit/domain/model/ProgressTrend;", "getWeeklyConsistencyPercent", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "Companion", "app_debug"})
public final class ProgressSummary {
    private final int totalSessionsLogged = 0;
    private final int weeklyConsistencyPercent = 0;
    private final int averageCompletionPercent = 0;
    private final int averageExertionScore = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String bestHighlight = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.model.ProgressTrend trend = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.athallah.hybridfit.domain.model.ProgressSummary.Companion Companion = null;
    
    public ProgressSummary(int totalSessionsLogged, int weeklyConsistencyPercent, int averageCompletionPercent, int averageExertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String bestHighlight, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.ProgressTrend trend) {
        super();
    }
    
    public final int getTotalSessionsLogged() {
        return 0;
    }
    
    public final int getWeeklyConsistencyPercent() {
        return 0;
    }
    
    public final int getAverageCompletionPercent() {
        return 0;
    }
    
    public final int getAverageExertionScore() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBestHighlight() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.ProgressTrend getTrend() {
        return null;
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.ProgressTrend component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.ProgressSummary copy(int totalSessionsLogged, int weeklyConsistencyPercent, int averageCompletionPercent, int averageExertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String bestHighlight, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.ProgressTrend trend) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/athallah/hybridfit/domain/model/ProgressSummary$Companion;", "", "()V", "empty", "Lcom/athallah/hybridfit/domain/model/ProgressSummary;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.athallah.hybridfit.domain.model.ProgressSummary empty() {
            return null;
        }
    }
}