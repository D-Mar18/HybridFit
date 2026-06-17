package com.athallah.hybridfit.ui.main;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.NavigationBarItemDefaults;
import androidx.compose.material3.OutlinedTextFieldDefaults;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.PathEffect;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import com.athallah.hybridfit.domain.model.DashboardSnapshot;
import com.athallah.hybridfit.domain.model.FitnessGoal;
import com.athallah.hybridfit.domain.model.FitnessLevel;
import com.athallah.hybridfit.domain.model.ProgressTrend;
import com.athallah.hybridfit.domain.model.Recommendation;
import com.athallah.hybridfit.domain.model.UserProfile;
import com.athallah.hybridfit.domain.model.WorkoutCategory;
import com.athallah.hybridfit.domain.model.WorkoutLog;
import com.athallah.hybridfit.domain.model.WorkoutSession;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000\u001a\u0012\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\u00020\u0002H\u0000\u001a\u0012\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004*\u00020\bH\u0000\u001a\u001b\u0010\t\u001a\u00020\u0002*\u00020\u00022\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0000\u00a2\u0006\u0002\u0010\f\u001a\f\u0010\r\u001a\u00020\b*\u00020\bH\u0000\u001a\f\u0010\u000e\u001a\u00020\u000f*\u00020\bH\u0000\u001a\u0014\u0010\u0010\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0012H\u0000\u001a\u0014\u0010\u0013\u001a\u00020\u000f*\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0012H\u0000\u001a\f\u0010\u0015\u001a\u00020\u0012*\u00020\bH\u0000\u001a\f\u0010\u0016\u001a\u00020\u0012*\u00020\bH\u0000\u001a\f\u0010\u0017\u001a\u00020\u0012*\u00020\bH\u0000\u001a\f\u0010\u0018\u001a\u00020\u0012*\u00020\bH\u0000\u001a\f\u0010\u0019\u001a\u00020\u0001*\u00020\u0002H\u0000\u001a\f\u0010\u001a\u001a\u00020\u0001*\u00020\u0005H\u0000\u001a\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u000b*\u00020\u0001H\u0002\u00a2\u0006\u0002\u0010\u001c\u001a\f\u0010\u001d\u001a\u00020\u000f*\u00020\bH\u0000\u001a\f\u0010\u001e\u001a\u00020\u0001*\u00020\bH\u0000\u001a\f\u0010\u001f\u001a\u00020\u0001*\u00020\u0002H\u0000\u001a\f\u0010 \u001a\u00020\u000b*\u00020\bH\u0000\u001a\u000e\u0010!\u001a\u0004\u0018\u00010\u0001*\u00020\bH\u0000\u001a\u000e\u0010\"\u001a\u0004\u0018\u00010#*\u00020\bH\u0000\u001a\f\u0010$\u001a\u00020\u0001*\u00020\u000bH\u0002\u001a\u0012\u0010%\u001a\u00020&*\b\u0012\u0004\u0012\u00020\b0\u0004H\u0000\u001a\f\u0010'\u001a\u00020\u0001*\u00020\u0012H\u0002\u001a\f\u0010(\u001a\u00020\u000b*\u00020\u0002H\u0000\u001a\f\u0010)\u001a\u00020\u0012*\u00020\u0002H\u0000\u001a\u0013\u0010*\u001a\u0004\u0018\u00010\u0012*\u00020\u0002H\u0000\u00a2\u0006\u0002\u0010+\u001a\f\u0010,\u001a\u00020\b*\u00020\bH\u0000\u00a8\u0006-"}, d2 = {"averagePaceLabelFromSegments", "", "Lcom/athallah/hybridfit/ui/main/RunLogDraftUi;", "completedSegments", "", "Lcom/athallah/hybridfit/ui/main/RunSegmentUi;", "completedSetEntries", "Lcom/athallah/hybridfit/ui/main/GymSetEntryUi;", "Lcom/athallah/hybridfit/ui/main/GymExerciseUi;", "ensureSegmentEntries", "targetDistanceKm", "", "(Lcom/athallah/hybridfit/ui/main/RunLogDraftUi;Ljava/lang/Double;)Lcom/athallah/hybridfit/ui/main/RunLogDraftUi;", "ensureSetEntries", "hasExerciseDraft", "", "isSegmentEditable", "segmentIndex", "", "isSetEditable", "setIndex", "loggedAverageReps", "loggedSetCount", "loggedTotalReps", "loggedTotalVolume", "manualPaceLabel", "paceLabel", "parseSetWeight", "(Ljava/lang/String;)Ljava/lang/Double;", "primarySetActionEnabled", "primarySetActionLabel", "segmentSummaryText", "suggestedWeightPerSetKg", "timerStatusLabel", "toAiFeedback", "Lcom/athallah/hybridfit/ui/main/ExerciseAiFeedbackUi;", "toInputString", "toSessionAiSummary", "Lcom/athallah/hybridfit/ui/main/SessionAiSummaryUi;", "toTimerLabel", "totalDistanceKmFromSegments", "totalDurationSecondsFromSegments", "totalManualDurationSeconds", "(Lcom/athallah/hybridfit/ui/main/RunLogDraftUi;)Ljava/lang/Integer;", "withDerivedMetrics", "app_debug"})
public final class MainUiModelsKt {
    
    private static final java.lang.String toInputString(double $this$toInputString) {
        return null;
    }
    
    public static final double suggestedWeightPerSetKg(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$suggestedWeightPerSetKg) {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.athallah.hybridfit.ui.main.GymExerciseUi ensureSetEntries(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$ensureSetEntries) {
        return null;
    }
    
    private static final java.lang.Double parseSetWeight(java.lang.String $this$parseSetWeight) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.athallah.hybridfit.ui.main.GymSetEntryUi> completedSetEntries(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$completedSetEntries) {
        return null;
    }
    
    public static final int loggedSetCount(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$loggedSetCount) {
        return 0;
    }
    
    public static final int loggedTotalReps(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$loggedTotalReps) {
        return 0;
    }
    
    public static final int loggedAverageReps(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$loggedAverageReps) {
        return 0;
    }
    
    public static final int loggedTotalVolume(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$loggedTotalVolume) {
        return 0;
    }
    
    public static final boolean hasExerciseDraft(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$hasExerciseDraft) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.athallah.hybridfit.ui.main.GymExerciseUi withDerivedMetrics(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$withDerivedMetrics) {
        return null;
    }
    
    private static final java.lang.String toTimerLabel(int $this$toTimerLabel) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String primarySetActionLabel(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$primarySetActionLabel) {
        return null;
    }
    
    public static final boolean primarySetActionEnabled(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$primarySetActionEnabled) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final java.lang.String timerStatusLabel(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$timerStatusLabel) {
        return null;
    }
    
    public static final boolean isSetEditable(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$isSetEditable, int setIndex) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final com.athallah.hybridfit.ui.main.ExerciseAiFeedbackUi toAiFeedback(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi $this$toAiFeedback) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.athallah.hybridfit.ui.main.SessionAiSummaryUi toSessionAiSummary(@org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.ui.main.GymExerciseUi> $this$toSessionAiSummary) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.athallah.hybridfit.ui.main.RunLogDraftUi ensureSegmentEntries(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.RunLogDraftUi $this$ensureSegmentEntries, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetDistanceKm) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.athallah.hybridfit.ui.main.RunSegmentUi> completedSegments(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.RunLogDraftUi $this$completedSegments) {
        return null;
    }
    
    public static final boolean isSegmentEditable(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.RunLogDraftUi $this$isSegmentEditable, int segmentIndex) {
        return false;
    }
    
    public static final double totalDistanceKmFromSegments(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.RunLogDraftUi $this$totalDistanceKmFromSegments) {
        return 0.0;
    }
    
    public static final int totalDurationSecondsFromSegments(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.RunLogDraftUi $this$totalDurationSecondsFromSegments) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String averagePaceLabelFromSegments(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.RunLogDraftUi $this$averagePaceLabelFromSegments) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final java.lang.Integer totalManualDurationSeconds(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.RunLogDraftUi $this$totalManualDurationSeconds) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String manualPaceLabel(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.RunLogDraftUi $this$manualPaceLabel) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String segmentSummaryText(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.RunLogDraftUi $this$segmentSummaryText) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String paceLabel(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.RunSegmentUi $this$paceLabel) {
        return null;
    }
}