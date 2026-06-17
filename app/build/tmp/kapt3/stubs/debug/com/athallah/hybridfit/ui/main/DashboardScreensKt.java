package com.athallah.hybridfit.ui.main;

import androidx.compose.foundation.layout.Arrangement;
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
import androidx.compose.ui.layout.ContentScale;
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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0086\u0001\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0014\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0001\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0001\u001a*\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u0011\u001a<\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0005H\u0001\u001aD\u0010\u0018\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\b\b\u0002\u0010\u0016\u001a\u00020\u00172\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0005H\u0001\u001a\u0018\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u0014H\u0001\u001a\u0010\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u001eH\u0003\u001a.\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u00142\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0001\u001a@\u0010\"\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010#\u001a\u00020$2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0001\u001a2\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020'2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020'0)2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\u00010+H\u0001\u001a\u009a\u0001\u0010,\u001a\u00020\u00012\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u00142\u0006\u00100\u001a\u00020$2\u0006\u00101\u001a\u00020$2\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010+2\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u00104\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u00108\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0001\u001a\u001e\u00109\u001a\u00020\u00012\u0006\u0010:\u001a\u00020;2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0001\u001a4\u0010<\u001a\u00020\u00012\u0006\u0010-\u001a\u00020.2\u0006\u0010=\u001a\u00020>2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0005H\u0001\u001a\u0096\u0001\u0010?\u001a\u00020\u00012\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u00142\u0006\u0010@\u001a\u00020\u00142\u0006\u0010A\u001a\u00020\u00142\b\u0010B\u001a\u0004\u0018\u00010\u00142\f\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010E\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010+2\u0012\u0010F\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010+2\u0006\u0010G\u001a\u00020$2\f\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0001\u001a>\u0010I\u001a\u00020\u00012\u0006\u0010J\u001a\u00020\u00142\u0006\u0010@\u001a\u00020\u00142\u0006\u0010A\u001a\u00020\u00142\n\b\u0002\u0010B\u001a\u0004\u0018\u00010\u00142\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0005H\u0001\u001a.\u0010K\u001a\u00020\u00012\u0006\u0010-\u001a\u00020.2\u0006\u0010L\u001a\u00020M2\u0006\u0010N\u001a\u00020\u00142\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0001\u001a~\u0010O\u001a\u00020\u00012\u0006\u0010-\u001a\u00020.2\u0006\u0010P\u001a\u00020'2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020'0)2\u0006\u00100\u001a\u00020$2\u0006\u00101\u001a\u00020$2\u0012\u0010Q\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\u00010+2\f\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010S\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010T\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0001\u001a\u001e\u0010U\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0001\u001a\u009e\u0001\u0010V\u001a\u00020\u00012\u0006\u0010-\u001a\u00020.2\u0006\u0010W\u001a\u00020>2\u0012\u0010X\u001a\u000e\u0012\u0004\u0012\u00020>\u0012\u0004\u0012\u00020\u00010+2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010[\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010]\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010^\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010_\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010+2\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0001\u001a$\u0010`\u001a\u00020\u00012\u0006\u0010&\u001a\u00020>2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00020>\u0012\u0004\u0012\u00020\u00010+H\u0001\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006a"}, d2 = {"AchievementCard", "", "item", "Lcom/athallah/hybridfit/ui/main/AchievementCardUi;", "onClick", "Lkotlin/Function0;", "ActivityRow", "log", "Lcom/athallah/hybridfit/domain/model/WorkoutLog;", "CircularMetricIcon", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "color", "Landroidx/compose/ui/graphics/Color;", "progress", "", "CircularMetricIcon-bw27NRU", "(Landroidx/compose/ui/graphics/vector/ImageVector;JF)V", "CompactStatCard", "title", "", "value", "modifier", "Landroidx/compose/ui/Modifier;", "ComparisonRow", "subtitle", "DashboardEmptyCard", "body", "DashboardQuickActionCard", "action", "Lcom/athallah/hybridfit/ui/main/DashboardQuickAction;", "EmptyTrainingCard", "description", "buttonText", "FeaturedTrainingCard", "enabled", "", "FilterRow", "selected", "Lcom/athallah/hybridfit/ui/main/ProgramFilter;", "availableFilters", "", "onSelected", "Lkotlin/Function1;", "HomeDashboardScreen", "snapshot", "Lcom/athallah/hybridfit/domain/model/DashboardSnapshot;", "displayName", "hasProgram", "isProgramCreationInProgress", "onMetricClick", "onOpenHero", "onCreateProgram", "onMenu", "onAiHub", "onNotifications", "onAccount", "MetricCard", "metric", "Lcom/athallah/hybridfit/ui/main/DashboardMetric;", "OverviewStatCard", "range", "Lcom/athallah/hybridfit/ui/main/StatsRange;", "ProfileDashboardScreen", "goalLabel", "experienceLabel", "avatarUrl", "onNotification", "onProfileHeroClick", "onProfileStatClick", "onSettingClick", "isLogoutInProgress", "onLogout", "ProfileHero", "name", "ProgramPreviewCard", "session", "Lcom/athallah/hybridfit/domain/model/WorkoutSession;", "difficultyLabel", "ProgramsDashboardScreen", "selectedFilter", "onFilterSelected", "onEditProgram", "onOpenProgram", "onStartProgram", "SettingsRow", "StatsDashboardScreen", "selectedRange", "onRangeSelected", "onBack", "onCalendar", "onShare", "onAchievement", "onViewAll", "onOverviewClick", "onComparisonClick", "TimeFilterRow", "app_debug"})
@kotlin.Suppress(names = {"UNUSED_PARAMETER"})
public final class DashboardScreensKt {
    
    @androidx.compose.runtime.Composable()
    public static final void HomeDashboardScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    java.lang.String displayName, boolean hasProgram, boolean isProgramCreationInProgress, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onMetricClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenHero, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCreateProgram, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMenu, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAiHub, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNotifications, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAccount, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProgramsDashboardScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.ProgramFilter selectedFilter, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.athallah.hybridfit.ui.main.ProgramFilter> availableFilters, boolean hasProgram, boolean isProgramCreationInProgress, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.ui.main.ProgramFilter, kotlin.Unit> onFilterSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEditProgram, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenProgram, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStartProgram, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StatsDashboardScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.StatsRange selectedRange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.ui.main.StatsRange, kotlin.Unit> onRangeSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCalendar, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onShare, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAchievement, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onViewAll, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOverviewClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onComparisonClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProfileDashboardScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    java.lang.String displayName, @org.jetbrains.annotations.NotNull()
    java.lang.String goalLabel, @org.jetbrains.annotations.NotNull()
    java.lang.String experienceLabel, @org.jetbrains.annotations.Nullable()
    java.lang.String avatarUrl, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNotification, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onProfileHeroClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onProfileStatClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSettingClick, boolean isLogoutInProgress, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void MetricCard(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.DashboardMetric metric, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FeaturedTrainingCard(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String buttonText, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, boolean enabled, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DashboardQuickActionCard(com.athallah.hybridfit.ui.main.DashboardQuickAction action) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmptyTrainingCard(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String buttonText, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DashboardEmptyCard(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String body) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ActivityRow(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutLog log) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FilterRow(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.ProgramFilter selected, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.athallah.hybridfit.ui.main.ProgramFilter> availableFilters, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.ui.main.ProgramFilter, kotlin.Unit> onSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProgramPreviewCard(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutSession session, @org.jetbrains.annotations.NotNull()
    java.lang.String difficultyLabel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void OverviewStatCard(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.StatsRange range, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void TimeFilterRow(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.StatsRange selected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.ui.main.StatsRange, kotlin.Unit> onSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AchievementCard(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.AchievementCardUi item, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ComparisonRow(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull()
    java.lang.String value, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProfileHero(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String goalLabel, @org.jetbrains.annotations.NotNull()
    java.lang.String experienceLabel, @org.jetbrains.annotations.Nullable()
    java.lang.String avatarUrl, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CompactStatCard(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String value, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsRow(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}