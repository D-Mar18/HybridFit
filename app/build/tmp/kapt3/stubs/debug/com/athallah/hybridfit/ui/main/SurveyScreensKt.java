package com.athallah.hybridfit.ui.main;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.text.KeyboardOptions;
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
import androidx.compose.ui.text.input.KeyboardType;
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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a.\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0001\u001aR\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u00052\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0001\u001a\u0010\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u001a\u0010\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u001a\u0090\u0001\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00052\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\r2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\r2\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0001\u001av\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u00052\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u001c\u0010!\u001a\u0018\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00010\r\u00a2\u0006\u0002\b#\u00a2\u0006\u0002\b$H\u0001\u001aX\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020'2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\u00010\r2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0001\u001aX\u0010*\u001a\u00020\u00012\u0006\u0010+\u001a\u00020,2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u00010\r2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0001\u001at\u0010-\u001a\u00020\u00012\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u00052\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u00020\u00010\r2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\r2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0001\u001aX\u00101\u001a\u00020\u00012\u0006\u00102\u001a\u0002032\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u00020\u00010\r2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0001\u001aF\u00104\u001a\u00020\u00012\u0006\u00105\u001a\u00020\u00052\u0006\u00106\u001a\u00020\u00052\u0006\u00107\u001a\u00020\u00052\u0006\u00108\u001a\u0002092\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\rH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b;\u0010<\u001a@\u0010=\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010>\u001a\u00020?2\b\b\u0002\u0010@\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0001\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006A"}, d2 = {"FrequencyCard", "", "selected", "", "title", "", "subtitle", "onClick", "Lkotlin/Function0;", "HealthOptionCard", "isOther", "notes", "onNotesChange", "Lkotlin/Function1;", "SelectionCheckCircle", "SelectionCircle", "SurveyBodyMetricsScreen", "age", "weightKg", "heightCm", "onAgeChange", "onWeightChange", "onHeightChange", "onFinish", "onBack", "onMenu", "modifier", "Landroidx/compose/ui/Modifier;", "SurveyContainer", "progressLabelStart", "progressLabelEnd", "progress", "", "content", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "SurveyFrequencyScreen", "selectedFrequency", "Lcom/athallah/hybridfit/ui/main/FrequencyOption;", "onSelect", "onNext", "SurveyGoalScreen", "selectedGoal", "Lcom/athallah/hybridfit/ui/main/GoalOption;", "SurveyHealthScreen", "selectedHealth", "Lcom/athallah/hybridfit/ui/main/HealthOption;", "healthNotes", "SurveyLevelScreen", "selectedExperience", "Lcom/athallah/hybridfit/ui/main/ExperienceOption;", "SurveyMetricField", "label", "value", "placeholder", "keyboardType", "Landroidx/compose/ui/text/input/KeyboardType;", "onValueChange", "SurveyMetricField-Y1LhbgI", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILkotlin/jvm/functions/Function1;)V", "SurveyOptionCard", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "showTrailingIndicator", "app_debug"})
@kotlin.Suppress(names = {"UNUSED_PARAMETER"})
public final class SurveyScreensKt {
    
    @androidx.compose.runtime.Composable()
    public static final void SurveyGoalScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.GoalOption selectedGoal, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.ui.main.GoalOption, kotlin.Unit> onSelect, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNext, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMenu, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SurveyLevelScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.ExperienceOption selectedExperience, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.ui.main.ExperienceOption, kotlin.Unit> onSelect, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNext, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMenu, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SurveyFrequencyScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.FrequencyOption selectedFrequency, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.ui.main.FrequencyOption, kotlin.Unit> onSelect, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNext, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMenu, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SurveyHealthScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.HealthOption selectedHealth, @org.jetbrains.annotations.NotNull()
    java.lang.String healthNotes, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.ui.main.HealthOption, kotlin.Unit> onSelect, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNotesChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNext, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMenu, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SurveyBodyMetricsScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String age, @org.jetbrains.annotations.NotNull()
    java.lang.String weightKg, @org.jetbrains.annotations.NotNull()
    java.lang.String heightCm, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onAgeChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onWeightChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onHeightChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFinish, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMenu, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SurveyContainer(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    java.lang.String progressLabelStart, @org.jetbrains.annotations.Nullable()
    java.lang.String progressLabelEnd, float progress, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMenu, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.ColumnScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SurveyOptionCard(boolean selected, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, boolean showTrailingIndicator, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FrequencyCard(boolean selected, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SelectionCircle(boolean selected) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SelectionCheckCircle(boolean selected) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void HealthOptionCard(boolean selected, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, boolean isOther, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNotesChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}