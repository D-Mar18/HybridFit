package com.athallah.hybridfit.ui.main;

import androidx.compose.material.icons.Icons;
import com.athallah.hybridfit.domain.model.DashboardSnapshot;
import com.athallah.hybridfit.domain.model.FitnessGoal;
import com.athallah.hybridfit.domain.model.FitnessLevel;
import com.athallah.hybridfit.domain.model.Recommendation;
import com.athallah.hybridfit.domain.model.SessionPlannerState;
import com.athallah.hybridfit.domain.model.UserProfile;
import com.athallah.hybridfit.domain.model.WorkoutCategory;
import com.athallah.hybridfit.domain.model.WorkoutLog;
import com.athallah.hybridfit.domain.model.WorkoutSession;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u00a4\u0001\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0002\u001a\u0018\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001a0\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0001H\u0002\u001aK\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u00072\b\u0010\u001a\u001a\u0004\u0018\u00010\u00012\b\u0010\u001b\u001a\u0004\u0018\u00010\u0007H\u0000\u00a2\u0006\u0002\u0010\u001c\u001a\u0010\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u001fH\u0002\u001a\u0010\u0010 \u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001a\u0010\u0010!\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\u0002\u001a8\u0010#\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0001H\u0002\u001a\u0012\u0010+\u001a\b\u0012\u0004\u0012\u00020,0\u0012*\u00020-H\u0000\u001a\f\u0010.\u001a\u00020\u000b*\u00020\u001fH\u0000\u001a\u0016\u0010/\u001a\u00020\u0007*\u0002002\b\u00101\u001a\u0004\u0018\u000102H\u0000\u001a\u0012\u00103\u001a\b\u0012\u0004\u0012\u0002040\u0012*\u00020-H\u0000\u001a\u0014\u00105\u001a\u00020\u0007*\u00020-2\u0006\u00106\u001a\u000207H\u0000\u001a\u001a\u00108\u001a\b\u0012\u0004\u0012\u0002000\u0012*\u00020-2\u0006\u00109\u001a\u00020:H\u0002\u001a\u0014\u0010;\u001a\u00020\u0001*\u00020-2\u0006\u00106\u001a\u000207H\u0000\u001a\f\u0010<\u001a\u00020\u0007*\u00020-H\u0000\u001a\f\u0010=\u001a\u00020\u0007*\u00020-H\u0000\u001a\f\u0010>\u001a\u00020\u0007*\u00020-H\u0000\u001a\f\u0010?\u001a\u00020\u000b*\u00020\u0007H\u0000\u001a\u0014\u0010@\u001a\u00020A*\u00020-2\u0006\u00109\u001a\u00020:H\u0002\u001a\f\u0010B\u001a\u00020A*\u00020-H\u0000\u001a\f\u0010C\u001a\u00020A*\u00020-H\u0000\u001a\f\u0010D\u001a\u000204*\u00020-H\u0000\u001a\f\u0010E\u001a\u000200*\u00020-H\u0000\u001a\f\u0010F\u001a\u00020\u000b*\u00020-H\u0000\u001a\f\u0010G\u001a\u00020\u000b*\u00020-H\u0000\u001a\f\u0010H\u001a\u00020\u000b*\u00020\u000bH\u0000\u001a\f\u0010I\u001a\u00020A*\u000200H\u0000\u001a\u000e\u0010J\u001a\u0004\u0018\u00010\u001f*\u00020-H\u0000\u001a\f\u0010K\u001a\u00020\u0007*\u00020\u001fH\u0000\u001a\u001a\u0010L\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0012*\u00020-2\u0006\u00106\u001a\u000207H\u0000\u001a\u001a\u0010M\u001a\b\u0012\u0004\u0012\u00020N0\u0012*\u00020-2\u0006\u00106\u001a\u000207H\u0000\u001a\f\u0010O\u001a\u00020\u000b*\u00020\u001fH\u0000\u001a\u000e\u0010P\u001a\u0004\u0018\u00010\u001f*\u00020-H\u0000\u001a\f\u0010Q\u001a\u000200*\u00020-H\u0000\u001a\f\u0010R\u001a\u00020N*\u000200H\u0000\u001a\u001a\u0010S\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0012*\u00020-2\u0006\u0010T\u001a\u000204H\u0000\u001a\f\u0010U\u001a\u00020V*\u000200H\u0000\u001a\u0014\u0010W\u001a\u00020\u0001*\u00020-2\u0006\u00106\u001a\u000207H\u0000\u001a\u0014\u0010X\u001a\u00020\u000b*\u00020-2\u0006\u0010Y\u001a\u000200H\u0000\u001a\f\u0010Z\u001a\u00020\u0007*\u000200H\u0000\u001a\u0014\u0010[\u001a\u000200*\u00020-2\u0006\u0010T\u001a\u000204H\u0000\u001a\u0014\u0010\\\u001a\u00020N*\u00020-2\u0006\u0010Y\u001a\u000200H\u0000\u001a\u0014\u0010]\u001a\u00020\u000b*\u00020-2\u0006\u0010Y\u001a\u000200H\u0000\u001a\f\u0010^\u001a\u00020\u0007*\u00020-H\u0000\u001a\u0014\u0010_\u001a\u00020\u0007*\u00020-2\u0006\u00106\u001a\u000207H\u0000\u001a9\u0010`\u001a\b\u0012\u0004\u0012\u0002Ha0\u0012\"\u0004\b\u0000\u0010a*\b\u0012\u0004\u0012\u0002Ha0\u00122\u0018\u0010b\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u0002Ha\u0012\u0004\u0012\u00020A0cH\u0082\b\u001a\f\u0010d\u001a\u00020\u0001*\u00020-H\u0000\u001a\f\u0010e\u001a\u00020\u0007*\u00020-H\u0000\u001a\u0014\u0010f\u001a\u00020\u0007*\u00020-2\u0006\u00106\u001a\u000207H\u0000\u00a8\u0006g"}, d2 = {"bodyMassFromBmi", "", "heightMeter", "bmi", "bodyMassIndex", "weightKg", "heightCm", "", "buildGymExercise", "Lcom/athallah/hybridfit/ui/main/GymExerciseUi;", "name", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "sets", "reps", "workingWeightKg", "buildStarterGymExercises", "", "profile", "Lcom/athallah/hybridfit/domain/model/UserProfile;", "fallbackGoal", "Lcom/athallah/hybridfit/ui/main/GoalOption;", "fallbackExperience", "Lcom/athallah/hybridfit/ui/main/ExperienceOption;", "fallbackAge", "fallbackWeightKg", "fallbackHeightCm", "(Lcom/athallah/hybridfit/domain/model/UserProfile;Lcom/athallah/hybridfit/ui/main/GoalOption;Lcom/athallah/hybridfit/ui/main/ExperienceOption;Ljava/lang/Integer;Ljava/lang/Double;Ljava/lang/Integer;)Ljava/util/List;", "dayLogScore", "log", "Lcom/athallah/hybridfit/domain/model/WorkoutLog;", "estimatedWeightFromHeight", "roundToNearestTwoPointFive", "value", "suggestedWorkingWeightKg", "bodyWeightKg", "age", "fitnessLevel", "Lcom/athallah/hybridfit/domain/model/FitnessLevel;", "goal", "Lcom/athallah/hybridfit/domain/model/FitnessGoal;", "liftBias", "achievementCards", "Lcom/athallah/hybridfit/ui/main/AchievementCardUi;", "Lcom/athallah/hybridfit/domain/model/DashboardSnapshot;", "activitySubtitle", "aiTargetCalories", "Lcom/athallah/hybridfit/domain/model/WorkoutSession;", "recommendation", "Lcom/athallah/hybridfit/domain/model/Recommendation;", "availableProgramFilters", "Lcom/athallah/hybridfit/ui/main/ProgramFilter;", "caloriesForRange", "range", "Lcom/athallah/hybridfit/ui/main/StatsRange;", "categorySessions", "category", "Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "distanceForRange", "estimatedCalories", "estimatedSteps", "estimatedStreakDays", "formatThousands", "hasCompletedCategory", "", "hasProgram", "hasRecentActivity", "homeFeaturedProgramFilter", "homeFeaturedSession", "homeFeaturedTrainingDescription", "homeFeaturedTrainingTitle", "initials", "isIntervalRun", "latestStrengthLog", "loggedStrengthVolume", "logsForRange", "overviewSeries", "", "paceLabel", "primaryRunLog", "primarySession", "programProgress", "recentLogsForFilter", "filter", "runProgramType", "Lcom/athallah/hybridfit/ui/main/RunProgramType;", "runningComparison", "sessionBadgeLabel", "session", "sessionConfidence", "sessionForFilter", "sessionProgress", "sessionProgressCaption", "sleepQualityScore", "strengthComparison", "takeWhileIndexed", "T", "predicate", "Lkotlin/Function2;", "totalDistanceKm", "totalStrengthVolume", "volumeForRange", "app_debug"})
public final class MainUiExtensionsKt {
    
    @org.jetbrains.annotations.NotNull()
    public static final com.athallah.hybridfit.domain.model.WorkoutSession primarySession(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$primarySession) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final com.athallah.hybridfit.domain.model.WorkoutLog primaryRunLog(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$primaryRunLog) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final com.athallah.hybridfit.domain.model.WorkoutLog latestStrengthLog(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$latestStrengthLog) {
        return null;
    }
    
    public static final boolean hasProgram(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$hasProgram) {
        return false;
    }
    
    public static final boolean hasRecentActivity(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$hasRecentActivity) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.athallah.hybridfit.domain.model.WorkoutLog> recentLogsForFilter(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$recentLogsForFilter, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.ProgramFilter filter) {
        return null;
    }
    
    private static final java.util.List<com.athallah.hybridfit.domain.model.WorkoutSession> categorySessions(com.athallah.hybridfit.domain.model.DashboardSnapshot $this$categorySessions, com.athallah.hybridfit.domain.model.WorkoutCategory category) {
        return null;
    }
    
    private static final boolean hasCompletedCategory(com.athallah.hybridfit.domain.model.DashboardSnapshot $this$hasCompletedCategory, com.athallah.hybridfit.domain.model.WorkoutCategory category) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.athallah.hybridfit.ui.main.ProgramFilter> availableProgramFilters(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$availableProgramFilters) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.athallah.hybridfit.ui.main.ProgramFilter homeFeaturedProgramFilter(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$homeFeaturedProgramFilter) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.athallah.hybridfit.domain.model.WorkoutSession homeFeaturedSession(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$homeFeaturedSession) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String homeFeaturedTrainingTitle(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$homeFeaturedTrainingTitle) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String homeFeaturedTrainingDescription(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$homeFeaturedTrainingDescription) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.athallah.hybridfit.domain.model.WorkoutSession sessionForFilter(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$sessionForFilter, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.ProgramFilter filter) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.athallah.hybridfit.ui.main.RunProgramType runProgramType(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutSession $this$runProgramType) {
        return null;
    }
    
    public static final boolean isIntervalRun(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutSession $this$isIntervalRun) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String sessionBadgeLabel(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$sessionBadgeLabel, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutSession session) {
        return null;
    }
    
    public static final float sessionProgress(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$sessionProgress, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutSession session) {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String sessionProgressCaption(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$sessionProgressCaption, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutSession session) {
        return null;
    }
    
    public static final int estimatedSteps(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$estimatedSteps) {
        return 0;
    }
    
    public static final int estimatedCalories(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$estimatedCalories) {
        return 0;
    }
    
    public static final double totalDistanceKm(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$totalDistanceKm) {
        return 0.0;
    }
    
    public static final int totalStrengthVolume(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$totalStrengthVolume) {
        return 0;
    }
    
    public static final int sleepQualityScore(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$sleepQualityScore) {
        return 0;
    }
    
    public static final int caloriesForRange(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$caloriesForRange, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.StatsRange range) {
        return 0;
    }
    
    public static final double distanceForRange(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$distanceForRange, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.StatsRange range) {
        return 0.0;
    }
    
    public static final int volumeForRange(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$volumeForRange, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.StatsRange range) {
        return 0;
    }
    
    public static final int estimatedStreakDays(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$estimatedStreakDays) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.athallah.hybridfit.ui.main.AchievementCardUi> achievementCards(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$achievementCards) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.athallah.hybridfit.domain.model.WorkoutLog> logsForRange(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$logsForRange, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.StatsRange range) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<java.lang.Float> overviewSeries(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$overviewSeries, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.StatsRange range) {
        return null;
    }
    
    public static final double runningComparison(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$runningComparison, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.StatsRange range) {
        return 0.0;
    }
    
    public static final int strengthComparison(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot $this$strengthComparison, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.StatsRange range) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String activitySubtitle(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutLog $this$activitySubtitle) {
        return null;
    }
    
    public static final int sessionConfidence(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutSession $this$sessionConfidence) {
        return 0;
    }
    
    public static final float programProgress(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutSession $this$programProgress) {
        return 0.0F;
    }
    
    public static final int aiTargetCalories(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutSession $this$aiTargetCalories, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.Recommendation recommendation) {
        return 0;
    }
    
    public static final int loggedStrengthVolume(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutLog $this$loggedStrengthVolume) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String paceLabel(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutLog $this$paceLabel) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String initials(@org.jetbrains.annotations.NotNull()
    java.lang.String $this$initials) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String formatThousands(int $this$formatThousands) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.athallah.hybridfit.ui.main.GymExerciseUi> buildStarterGymExercises(@org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.UserProfile profile, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GoalOption fallbackGoal, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.ExperienceOption fallbackExperience, @org.jetbrains.annotations.Nullable()
    java.lang.Integer fallbackAge, @org.jetbrains.annotations.Nullable()
    java.lang.Double fallbackWeightKg, @org.jetbrains.annotations.Nullable()
    java.lang.Integer fallbackHeightCm) {
        return null;
    }
    
    private static final int dayLogScore(com.athallah.hybridfit.domain.model.WorkoutLog log) {
        return 0;
    }
    
    private static final com.athallah.hybridfit.ui.main.GymExerciseUi buildGymExercise(java.lang.String name, androidx.compose.ui.graphics.vector.ImageVector icon, int sets, int reps, double workingWeightKg) {
        return null;
    }
    
    private static final double suggestedWorkingWeightKg(double bodyWeightKg, int age, int heightCm, com.athallah.hybridfit.domain.model.FitnessLevel fitnessLevel, com.athallah.hybridfit.domain.model.FitnessGoal goal, double liftBias) {
        return 0.0;
    }
    
    private static final double estimatedWeightFromHeight(int heightCm) {
        return 0.0;
    }
    
    private static final double bodyMassIndex(double weightKg, int heightCm) {
        return 0.0;
    }
    
    private static final double bodyMassFromBmi(double heightMeter, double bmi) {
        return 0.0;
    }
    
    private static final double roundToNearestTwoPointFive(double value) {
        return 0.0;
    }
    
    private static final <T extends java.lang.Object>java.util.List<T> takeWhileIndexed(java.util.List<? extends T> $this$takeWhileIndexed, kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super T, java.lang.Boolean> predicate) {
        return null;
    }
}