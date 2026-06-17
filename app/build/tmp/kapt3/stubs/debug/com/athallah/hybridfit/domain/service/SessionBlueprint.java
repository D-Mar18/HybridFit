package com.athallah.hybridfit.domain.service;

import com.athallah.hybridfit.domain.model.FitnessGoal;
import com.athallah.hybridfit.domain.model.FitnessLevel;
import com.athallah.hybridfit.domain.model.UserProfile;
import com.athallah.hybridfit.domain.model.WorkoutCategory;
import java.time.DayOfWeek;
import java.time.LocalDate;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b'\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001Bc\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000f\u001a\u00020\n\u0012\u0006\u0010\u0010\u001a\u00020\u0005\u0012\u0006\u0010\u0011\u001a\u00020\n\u00a2\u0006\u0002\u0010\u0012J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010'\u001a\u00020\u0005H\u00c6\u0003J\t\u0010(\u001a\u00020\nH\u00c6\u0003J\t\u0010)\u001a\u00020\u0005H\u00c6\u0003J\t\u0010*\u001a\u00020\u0007H\u00c6\u0003J\t\u0010+\u001a\u00020\u0005H\u00c6\u0003J\t\u0010,\u001a\u00020\nH\u00c6\u0003J\u0010\u0010-\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001eJ\u0010\u0010.\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\"J\u0010\u0010/\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\"J\t\u00100\u001a\u00020\nH\u00c6\u0003J\u0082\u0001\u00101\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000f\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\u00052\b\b\u0002\u0010\u0011\u001a\u00020\nH\u00c6\u0001\u00a2\u0006\u0002\u00102J\u0013\u00103\u001a\u0002042\b\u00105\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00106\u001a\u00020\nH\u00d6\u0001J\t\u00107\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0010\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0011\u0010\u0011\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u000f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0015\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\n\n\u0002\u0010\u001f\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001bR\u0015\u0010\u000e\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010#\u001a\u0004\b!\u0010\"R\u0015\u0010\r\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010#\u001a\u0004\b$\u0010\"R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0018\u00a8\u00068"}, d2 = {"Lcom/athallah/hybridfit/domain/service/SessionBlueprint;", "", "dayOfWeek", "Ljava/time/DayOfWeek;", "title", "", "category", "Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "focusArea", "targetDurationMinutes", "", "targetDistanceKm", "", "targetSets", "targetReps", "restSeconds", "guidance", "orderInDay", "(Ljava/time/DayOfWeek;Ljava/lang/String;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;Ljava/lang/String;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;I)V", "getCategory", "()Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "getDayOfWeek", "()Ljava/time/DayOfWeek;", "getFocusArea", "()Ljava/lang/String;", "getGuidance", "getOrderInDay", "()I", "getRestSeconds", "getTargetDistanceKm", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getTargetDurationMinutes", "getTargetReps", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getTargetSets", "getTitle", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/time/DayOfWeek;Ljava/lang/String;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;Ljava/lang/String;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;I)Lcom/athallah/hybridfit/domain/service/SessionBlueprint;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class SessionBlueprint {
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
    
    public SessionBlueprint(@org.jetbrains.annotations.NotNull()
    java.time.DayOfWeek dayOfWeek, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory category, @org.jetbrains.annotations.NotNull()
    java.lang.String focusArea, int targetDurationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetDistanceKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetSets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetReps, int restSeconds, @org.jetbrains.annotations.NotNull()
    java.lang.String guidance, int orderInDay) {
        super();
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
    public final java.time.DayOfWeek component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    public final int component11() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.WorkoutCategory component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
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
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.service.SessionBlueprint copy(@org.jetbrains.annotations.NotNull()
    java.time.DayOfWeek dayOfWeek, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory category, @org.jetbrains.annotations.NotNull()
    java.lang.String focusArea, int targetDurationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetDistanceKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetSets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetReps, int restSeconds, @org.jetbrains.annotations.NotNull()
    java.lang.String guidance, int orderInDay) {
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