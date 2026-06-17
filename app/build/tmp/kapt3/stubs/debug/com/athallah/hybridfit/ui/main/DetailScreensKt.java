package com.athallah.hybridfit.ui.main;

import androidx.compose.foundation.text.KeyboardOptions;
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
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import com.athallah.hybridfit.R;
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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u009a\u0001\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0002\u001a\u00aa\u0001\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0001\u001aJ\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\b2\u0010\b\u0002\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\fH\u0001\u001a\u0088\u0001\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\n2\u0018\u0010%\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0001\u001a\u0010\u0010(\u001a\u00020\u00012\u0006\u0010)\u001a\u00020\u0004H\u0001\u001a8\u0010*\u001a\u00020\u00012\u0006\u0010)\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u00062\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0001\u001al\u0010,\u001a\u00020\u00012\f\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u00032\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\n2\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0001\u001aT\u00103\u001a\u00020\u00012\u0006\u00104\u001a\u00020\b2\u0006\u00105\u001a\u00020\b2\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\n2\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u00107\u001a\u00020\u00062\b\b\u0002\u00108\u001a\u00020\u00062\b\b\u0002\u00109\u001a\u00020:H\u0001\u001a\u0010\u0010;\u001a\u00020\u00012\u0006\u0010<\u001a\u00020=H\u0003\u001ax\u0010>\u001a\u00020\u00012\u0006\u0010?\u001a\u00020.2\b\b\u0002\u0010@\u001a\u00020\u00062\f\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0018\u0010B\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010&2\u0018\u0010C\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010&2\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0001\u001a\u00b4\u0002\u0010F\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010G\u001a\u0004\u0018\u00010H2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u00032\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0012\u0010M\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\n2\u001e\u0010N\u001a\u001a\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010O2\u001e\u0010P\u001a\u001a\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010O2\u0012\u0010Q\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010R\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\n2\f\u0010S\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0001\u001a2\u0010T\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\b2\u0006\u0010V\u001a\u00020\b2\u0006\u0010W\u001a\u00020X2\u0010\b\u0002\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\fH\u0001\u001a*\u0010Y\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\b2\u0010\b\u0002\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\fH\u0001\u001a&\u0010Z\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\b2\u0006\u0010V\u001a\u00020\b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0001\u001a\u0018\u0010[\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\b2\u0006\u0010\\\u001a\u00020\u0010H\u0001\u001a\u0094\u0001\u0010]\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010G\u001a\u0004\u0018\u00010H2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010^\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010_\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010`\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0001\u001a2\u0010a\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\b2\u0006\u0010b\u001a\u00020\b2\u0006\u0010c\u001a\u00020d2\u0010\b\u0002\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\fH\u0001\u001aH\u0010e\u001a\u00020\u00012\u0006\u00104\u001a\u00020\b2\u0006\u0010f\u001a\u00020\b2\u0006\u0010g\u001a\u00020h2\n\b\u0002\u0010i\u001a\u0004\u0018\u00010\b2\u0010\b\u0002\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\fH\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\bj\u0010k\u001a<\u0010l\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\b2\u0006\u00105\u001a\u00020\b2\u0006\u0010W\u001a\u00020X2\b\b\u0002\u0010\u0013\u001a\u00020\u00142\u0010\b\u0002\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\fH\u0001\u001a\u0013\u0010m\u001a\u0004\u0018\u00010n*\u00020\bH\u0002\u00a2\u0006\u0002\u0010o\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006p"}, d2 = {"AiCoachChatScreen", "", "messages", "", "Lcom/athallah/hybridfit/ui/main/CoachMessage;", "isSending", "", "input", "", "onInputChange", "Lkotlin/Function1;", "onBack", "Lkotlin/Function0;", "onTopAction", "onSuggestion", "onApplyProposal", "", "onCancelProposal", "onSend", "modifier", "Landroidx/compose/ui/Modifier;", "AiTrackingCard", "actionLabel", "statOneLabel", "statOneValue", "statTwoLabel", "statTwoValue", "body", "onClick", "AiWorkoutDetailScreen", "snapshot", "Lcom/athallah/hybridfit/domain/model/DashboardSnapshot;", "selectedProgramFilter", "Lcom/athallah/hybridfit/ui/main/ProgramFilter;", "onOpenCoach", "onSummaryClick", "onMetricClick", "onRoutineClick", "Lkotlin/Function2;", "onStartWorkout", "ChatBubble", "message", "CoachMessageRow", "showAvatar", "CompletionFeedbackScreen", "exercises", "Lcom/athallah/hybridfit/ui/main/GymExerciseUi;", "onBackToHome", "onSeeProgram", "onTotalClick", "onOptimizationClick", "DetailInputField", "label", "value", "onValueChange", "singleLine", "enabled", "keyboardOptions", "Landroidx/compose/foundation/text/KeyboardOptions;", "ExerciseAiInsightCard", "feedback", "Lcom/athallah/hybridfit/ui/main/ExerciseAiFeedbackUi;", "ExpandableExerciseCard", "exercise", "isReadOnly", "onToggle", "onRepsChange", "onWeightChange", "onPrimaryAction", "onTimerTick", "GymWorkoutDetailScreen", "selectedLog", "Lcom/athallah/hybridfit/domain/model/WorkoutLog;", "onMore", "onShare", "onHeroClick", "onInsightClick", "onToggleExercise", "onExerciseRepsChange", "Lkotlin/Function3;", "onExerciseWeightChange", "onExercisePrimaryAction", "onExerciseTimerTick", "onFinishSession", "HeroIllustration", "title", "subtitle", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "InsightCard", "ProgramRoutineRow", "RestTimerCard", "restSeconds", "RunDetailScreen", "onSplitClick", "onEditLog", "onDeleteLog", "SplitRow", "pace", "progress", "", "StatHeroCard", "mainValue", "accent", "Landroidx/compose/ui/graphics/Color;", "badgeText", "StatHeroCard-XO-JAsU", "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Lkotlin/jvm/functions/Function0;)V", "TinyMetricCard", "parseFlexibleDecimal", "", "(Ljava/lang/String;)Ljava/lang/Double;", "app_debug"})
@kotlin.Suppress(names = {"UNUSED_PARAMETER"})
public final class DetailScreensKt {
    
    @androidx.compose.runtime.Composable()
    public static final void RunDetailScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.WorkoutLog selectedLog, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onShare, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSummaryClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onMetricClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSplitClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEditLog, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteLog) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AiWorkoutDetailScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.ProgramFilter selectedProgramFilter, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenCoach, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSummaryClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onMetricClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onRoutineClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStartWorkout, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void GymWorkoutDetailScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.WorkoutLog selectedLog, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.ui.main.GymExerciseUi> exercises, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMore, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onShare, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSummaryClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onMetricClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onHeroClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onInsightClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStartWorkout, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onToggleExercise, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Integer, ? super java.lang.Integer, ? super java.lang.String, kotlin.Unit> onExerciseRepsChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Integer, ? super java.lang.Integer, ? super java.lang.String, kotlin.Unit> onExerciseWeightChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onExercisePrimaryAction, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onExerciseTimerTick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFinishSession) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CompletionFeedbackScreen(@org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.ui.main.GymExerciseUi> exercises, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackToHome, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSeeProgram, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenCoach, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTotalClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOptimizationClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AiCoachChatScreen(@org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.ui.main.CoachMessage> messages, boolean isSending, @org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onInputChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onTopAction, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSuggestion, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onApplyProposal, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onCancelProposal, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSend, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void TinyMetricCard(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String value, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SplitRow(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String pace, float progress, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void InsightCard(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String body, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AiTrackingCard(@org.jetbrains.annotations.NotNull()
    java.lang.String actionLabel, @org.jetbrains.annotations.NotNull()
    java.lang.String statOneLabel, @org.jetbrains.annotations.NotNull()
    java.lang.String statOneValue, @org.jetbrains.annotations.NotNull()
    java.lang.String statTwoLabel, @org.jetbrains.annotations.NotNull()
    java.lang.String statTwoValue, @org.jetbrains.annotations.NotNull()
    java.lang.String body, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void HeroIllustration(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProgramRoutineRow(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ExpandableExerciseCard(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GymExerciseUi exercise, boolean isReadOnly, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.String, kotlin.Unit> onRepsChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.String, kotlin.Unit> onWeightChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPrimaryAction, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onTimerTick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ExerciseAiInsightCard(com.athallah.hybridfit.ui.main.ExerciseAiFeedbackUi feedback) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DetailInputField(@org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    java.lang.String value, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean singleLine, boolean enabled, @org.jetbrains.annotations.NotNull()
    androidx.compose.foundation.text.KeyboardOptions keyboardOptions) {
    }
    
    private static final java.lang.Double parseFlexibleDecimal(java.lang.String $this$parseFlexibleDecimal) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RestTimerCard(@org.jetbrains.annotations.NotNull()
    java.lang.String title, int restSeconds) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CoachMessageRow(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.CoachMessage message, boolean showAvatar, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onApplyProposal, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCancelProposal) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ChatBubble(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.CoachMessage message) {
    }
}