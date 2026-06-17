package com.athallah.hybridfit.domain.service;

import com.athallah.hybridfit.domain.model.FitnessGoal;
import com.athallah.hybridfit.domain.model.FitnessLevel;
import com.athallah.hybridfit.domain.model.UserProfile;
import com.athallah.hybridfit.domain.model.WorkoutCategory;
import java.time.DayOfWeek;
import java.time.LocalDate;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001*B\u0005\u00a2\u0006\u0002\u0010\u0002J!\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004H\u0002\u00a2\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\nH\u0002J)\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\nH\u0002J!\u0010\u0013\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0014\u001a\u0004\u0018\u00010\nH\u0002\u00a2\u0006\u0002\u0010\u0015J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0005\u001a\u00020\u0006J3\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\b\u0010\u001b\u001a\u0004\u0018\u00010\n2\b\u0010\u001c\u001a\u0004\u0018\u00010\n2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0004H\u0002\u00a2\u0006\u0002\u0010\u001eJ\u0010\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!H\u0002J\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\u0006\u0010 \u001a\u00020!2\u0006\u0010%\u001a\u00020\nH\u0002J\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00020'0#2\u0006\u0010%\u001a\u00020\nH\u0002J\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020$0#2\u0006\u0010 \u001a\u00020!H\u0002J\f\u0010)\u001a\u00020\u0004*\u00020\u0006H\u0002\u00a8\u0006+"}, d2 = {"Lcom/athallah/hybridfit/domain/service/StarterPlanBuilder;", "", "()V", "adjustedDistance", "", "profile", "Lcom/athallah/hybridfit/domain/model/UserProfile;", "baseDistanceKm", "(Lcom/athallah/hybridfit/domain/model/UserProfile;Ljava/lang/Double;)Ljava/lang/Double;", "adjustedDuration", "", "baseDuration", "adjustedReps", "baseReps", "category", "Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "(Lcom/athallah/hybridfit/domain/model/UserProfile;Ljava/lang/Integer;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;)Ljava/lang/Integer;", "adjustedRestSeconds", "baseRestSeconds", "adjustedSets", "baseSets", "(Lcom/athallah/hybridfit/domain/model/UserProfile;Ljava/lang/Integer;)Ljava/lang/Integer;", "build", "Lcom/athallah/hybridfit/domain/service/StarterPlanDraft;", "buildGuidance", "", "baseGuidance", "targetSets", "targetReps", "targetDistanceKm", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Double;)Ljava/lang/String;", "buildPlanTitle", "goal", "Lcom/athallah/hybridfit/domain/model/FitnessGoal;", "expandTemplates", "", "Lcom/athallah/hybridfit/domain/service/StarterPlanBuilder$SessionTemplate;", "totalDays", "pickDays", "Ljava/time/DayOfWeek;", "templatesForGoal", "bmi", "SessionTemplate", "app_debug"})
public final class StarterPlanBuilder {
    
    public StarterPlanBuilder() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.service.StarterPlanDraft build(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.UserProfile profile) {
        return null;
    }
    
    private final java.lang.String buildPlanTitle(com.athallah.hybridfit.domain.model.FitnessGoal goal) {
        return null;
    }
    
    private final java.util.List<java.time.DayOfWeek> pickDays(int totalDays) {
        return null;
    }
    
    private final java.util.List<com.athallah.hybridfit.domain.service.StarterPlanBuilder.SessionTemplate> expandTemplates(com.athallah.hybridfit.domain.model.FitnessGoal goal, int totalDays) {
        return null;
    }
    
    private final int adjustedDuration(com.athallah.hybridfit.domain.model.UserProfile profile, int baseDuration) {
        return 0;
    }
    
    private final java.lang.Double adjustedDistance(com.athallah.hybridfit.domain.model.UserProfile profile, java.lang.Double baseDistanceKm) {
        return null;
    }
    
    private final java.lang.Integer adjustedSets(com.athallah.hybridfit.domain.model.UserProfile profile, java.lang.Integer baseSets) {
        return null;
    }
    
    private final java.lang.Integer adjustedReps(com.athallah.hybridfit.domain.model.UserProfile profile, java.lang.Integer baseReps, com.athallah.hybridfit.domain.model.WorkoutCategory category) {
        return null;
    }
    
    private final int adjustedRestSeconds(com.athallah.hybridfit.domain.model.UserProfile profile, int baseRestSeconds) {
        return 0;
    }
    
    private final java.lang.String buildGuidance(java.lang.String baseGuidance, java.lang.Integer targetSets, java.lang.Integer targetReps, java.lang.Double targetDistanceKm) {
        return null;
    }
    
    private final java.util.List<com.athallah.hybridfit.domain.service.StarterPlanBuilder.SessionTemplate> templatesForGoal(com.athallah.hybridfit.domain.model.FitnessGoal goal) {
        return null;
    }
    
    private final double bmi(com.athallah.hybridfit.domain.model.UserProfile $this$bmi) {
        return 0.0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b!\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\b\u0018\u00002\u00020\u0001BS\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\r\u001a\u00020\b\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000fJ\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\bH\u00c6\u0003J\u0010\u0010$\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010%\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u0010&\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\t\u0010'\u001a\u00020\bH\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003Jn\u0010)\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u000e\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010*J\u0013\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010.\u001a\u00020\bH\u00d6\u0001J\t\u0010/\u001a\u00020\u0003H\u00d6\u0001R\u0015\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0015\u0010\f\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0018\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001cR\u0011\u0010\r\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001c\u00a8\u00060"}, d2 = {"Lcom/athallah/hybridfit/domain/service/StarterPlanBuilder$SessionTemplate;", "", "title", "", "category", "Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "focusArea", "baseDuration", "", "baseDistanceKm", "", "baseSets", "baseReps", "restSeconds", "guidance", "(Ljava/lang/String;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;Ljava/lang/String;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;)V", "getBaseDistanceKm", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getBaseDuration", "()I", "getBaseReps", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getBaseSets", "getCategory", "()Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "getFocusArea", "()Ljava/lang/String;", "getGuidance", "getRestSeconds", "getTitle", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;Ljava/lang/String;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;)Lcom/athallah/hybridfit/domain/service/StarterPlanBuilder$SessionTemplate;", "equals", "", "other", "hashCode", "toString", "app_debug"})
    static final class SessionTemplate {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String title = null;
        @org.jetbrains.annotations.NotNull()
        private final com.athallah.hybridfit.domain.model.WorkoutCategory category = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String focusArea = null;
        private final int baseDuration = 0;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Double baseDistanceKm = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer baseSets = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer baseReps = null;
        private final int restSeconds = 0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String guidance = null;
        
        public SessionTemplate(@org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.NotNull()
        com.athallah.hybridfit.domain.model.WorkoutCategory category, @org.jetbrains.annotations.NotNull()
        java.lang.String focusArea, int baseDuration, @org.jetbrains.annotations.Nullable()
        java.lang.Double baseDistanceKm, @org.jetbrains.annotations.Nullable()
        java.lang.Integer baseSets, @org.jetbrains.annotations.Nullable()
        java.lang.Integer baseReps, int restSeconds, @org.jetbrains.annotations.NotNull()
        java.lang.String guidance) {
            super();
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
        
        public final int getBaseDuration() {
            return 0;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double getBaseDistanceKm() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getBaseSets() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getBaseReps() {
            return null;
        }
        
        public final int getRestSeconds() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getGuidance() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.athallah.hybridfit.domain.model.WorkoutCategory component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
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
        
        public final int component8() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component9() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.athallah.hybridfit.domain.service.StarterPlanBuilder.SessionTemplate copy(@org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.NotNull()
        com.athallah.hybridfit.domain.model.WorkoutCategory category, @org.jetbrains.annotations.NotNull()
        java.lang.String focusArea, int baseDuration, @org.jetbrains.annotations.Nullable()
        java.lang.Double baseDistanceKm, @org.jetbrains.annotations.Nullable()
        java.lang.Integer baseSets, @org.jetbrains.annotations.Nullable()
        java.lang.Integer baseReps, int restSeconds, @org.jetbrains.annotations.NotNull()
        java.lang.String guidance) {
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
}