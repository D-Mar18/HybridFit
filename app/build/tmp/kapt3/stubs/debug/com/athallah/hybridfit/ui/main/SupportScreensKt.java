package com.athallah.hybridfit.ui.main;

import android.content.Context;
import android.net.Uri;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.OutlinedTextFieldDefaults;
import androidx.compose.material3.SwitchDefaults;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.style.TextAlign;
import androidx.activity.result.contract.ActivityResultContracts;
import com.athallah.hybridfit.domain.model.DashboardSnapshot;
import com.athallah.hybridfit.domain.model.SessionPlannerState;
import com.athallah.hybridfit.domain.model.WorkoutCategory;
import com.athallah.hybridfit.domain.model.WorkoutSession;
import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u00c4\u0001\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u00b0\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00032\b\u0010\b\u001a\u0004\u0018\u00010\u00032\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0001\u001a\"\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\u0010\b\u0002\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000fH\u0003\u001aJ\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\n2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0001\u001aL\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0001\u001a \u0010!\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\u00032\u0006\u0010$\u001a\u00020%H\u0003\u001a\u009e\u0001\u0010&\u001a\u00020\u00012\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020(2\u0006\u0010*\u001a\u00020(2\u0006\u0010+\u001a\u00020(2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0001\u001a \u00100\u001a\u00020\u00012\u0006\u0010$\u001a\u00020%2\u0006\u0010\"\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\u0003H\u0003\u001a\u0018\u00101\u001a\u00020\u00012\u0006\u00102\u001a\u00020\u00032\u0006\u00103\u001a\u00020\u0003H\u0003\u001a\u00b2\u0001\u00104\u001a\u00020\u00012\u0006\u00105\u001a\u0002062\f\u00107\u001a\b\u0012\u0004\u0012\u000209082\f\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;2\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020(2\u0012\u0010@\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010A\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010B\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010C\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0001\u001aX\u0010E\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00032\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0001\u001aX\u0010H\u001a\u00020\u00012\u0006\u0010I\u001a\u00020\u00032\u0012\u0010J\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0001\u001a\u001e\u0010L\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020M2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u000fH\u0003\u001aP\u0010N\u001a\u00020\u00012\f\u0010O\u001a\b\u0012\u0004\u0012\u00020M0;2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010P\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\u0012\u0010Q\u001a\u000e\u0012\u0004\u0012\u00020M\u0012\u0004\u0012\u00020\u00010\n2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0001\u001a$\u0010R\u001a\u00020\u00012\u0006\u0010S\u001a\u00020T2\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u00020T\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001aV\u0010V\u001a\u00020\u00012\u0006\u0010W\u001a\u00020<2\f\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010[\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00010\u000fH\u0003\u001a:\u0010]\u001a\u00020\u00012\u0006\u0010^\u001a\u00020\u00032\f\u0010_\u001a\b\u0012\u0004\u0012\u00020\u00030;2\u0006\u0010`\u001a\u00020\u00032\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001a\u0010\u0010a\u001a\u00020\u00012\u0006\u0010b\u001a\u00020cH\u0003\u001a\u00b4\u0001\u0010d\u001a\u00020\u00012\u0006\u0010e\u001a\u00020(2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010g\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010i\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010j\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010l\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010m\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0001\u001a$\u0010p\u001a\u00020\u00012\u0006\u0010q\u001a\u00020r2\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u00020r\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001a&\u0010s\u001a\u00020\u00012\u0006\u0010t\u001a\u00020u2\u0006\u0010v\u001a\u00020(2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u000fH\u0003\u001a<\u0010w\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u00032\u0006\u0010x\u001a\u00020\u00032\u0006\u0010$\u001a\u00020%2\u0006\u0010y\u001a\u00020(2\u0012\u0010z\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001a\u001e\u0010{\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u00032\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000fH\u0003\u001a\u00e5\u0002\u0010|\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010}\u001a\u00020~2\u0006\u0010\u007f\u001a\u00020\u00032\u0007\u0010\u0080\u0001\u001a\u00020\u00032\b\u0010\u0081\u0001\u001a\u00030\u0082\u00012\b\u0010\u0083\u0001\u001a\u00030\u0084\u00012\u0013\u0010\u0085\u0001\u001a\u000e\u0012\u0004\u0012\u00020~\u0012\u0004\u0012\u00020\u00010\n2\u0013\u0010\u0086\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\u0013\u0010\u0087\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\u0014\u0010\u0088\u0001\u001a\u000f\u0012\u0005\u0012\u00030\u0082\u0001\u0012\u0004\u0012\u00020\u00010\n2\r\u0010\u0089\u0001\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\r\u0010\u008a\u0001\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\u0013\u0010\u008b\u0001\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020\u00010\n2\u0013\u0010\u008c\u0001\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020\u00010\n2\u0013\u0010\u008d\u0001\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020\u00010\n2\u0013\u0010\u008e\u0001\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020\u00010\n2\u0013\u0010\u008f\u0001\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020\u00010\n2\u0014\u0010\u0090\u0001\u001a\u000f\u0012\u0005\u0012\u00030\u0084\u0001\u0012\u0004\u0012\u00020\u00010\n2\r\u0010\u0091\u0001\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\r\u0010\u0092\u0001\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0001\u001a/\u0010\u0093\u0001\u001a\u00020\u00012\f\u00107\u001a\b\u0012\u0004\u0012\u000209082\f\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0003\u001a\u001f\u0010\u0094\u0001\u001a\u0004\u0018\u00010\u00032\b\u0010\u0095\u0001\u001a\u00030\u0096\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u0001H\u0002\u001a\u0010\u0010\u0099\u0001\u001a\u00030\u009a\u0001H\u0003\u00a2\u0006\u0003\u0010\u009b\u0001\u001a\r\u0010\u009c\u0001\u001a\u00020\u0003*\u000209H\u0002\u001a\r\u0010\u009d\u0001\u001a\u00020\u0003*\u00020<H\u0002\u001a\r\u0010\u009e\u0001\u001a\u00020\u0003*\u00020~H\u0002\u00a8\u0006\u009f\u0001"}, d2 = {"AccountScreen", "", "displayName", "", "email", "goalLabel", "experienceLabel", "notes", "avatarUrl", "onDisplayNameChange", "Lkotlin/Function1;", "onEmailChange", "onNotesChange", "onAvatarChange", "onBack", "Lkotlin/Function0;", "onSave", "modifier", "Landroidx/compose/ui/Modifier;", "AchievementDetailRow", "item", "Lcom/athallah/hybridfit/ui/main/AchievementCardUi;", "onClick", "AchievementsScreen", "snapshot", "Lcom/athallah/hybridfit/domain/model/DashboardSnapshot;", "onTopAction", "onOpenAchievement", "AiHubScreen", "selectedProgramFilter", "Lcom/athallah/hybridfit/ui/main/ProgramFilter;", "onOpenCoach", "onOpenProgram", "AiSignalCard", "title", "body", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "AppSettingsScreen", "reminderEnabled", "", "smartRecommendationEnabled", "sleepInsightEnabled", "dataSaverEnabled", "onReminderChange", "onSmartRecommendationChange", "onSleepInsightChange", "onDataSaverChange", "EmptyStateCard", "FaqCard", "question", "answer", "FeatureInfoScreen", "info", "Lcom/athallah/hybridfit/ui/main/FeatureInfoUi;", "workoutDates", "", "Ljava/time/LocalDate;", "plannedSessions", "", "Lcom/athallah/hybridfit/domain/model/WorkoutSession;", "runLogDraft", "Lcom/athallah/hybridfit/ui/main/RunLogDraftUi;", "showSimpleRunInput", "onRunDistanceChange", "onRunDurationChange", "onRunDurationSecondsChange", "onRunNotesChange", "onPrimaryAction", "ForgotPasswordScreen", "onSubmit", "onUseGoogle", "HelpCenterScreen", "supportMessage", "onSupportMessageChange", "onSend", "NotificationCard", "Lcom/athallah/hybridfit/ui/main/NotificationItemUi;", "NotificationsScreen", "items", "onMarkAllRead", "onOpenNotification", "PlannerDaySelector", "selectedDay", "Ljava/time/DayOfWeek;", "onSelected", "PlannerSessionCard", "session", "onEdit", "onMove", "onSkip", "onComplete", "onDelete", "ProgramVariantSelector", "label", "options", "selectedOption", "QuickActionCard", "action", "Lcom/athallah/hybridfit/ui/main/QuickActionCardUi;", "QuickActionsScreen", "isAuthenticated", "onAiHub", "onNotifications", "onAccount", "onWeeklyTarget", "onSettings", "onHelp", "onAchievements", "onForgotPassword", "onRegister", "onGoogleLogin", "SessionCategorySelector", "selectedCategory", "Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "TargetFrequencyCard", "option", "Lcom/athallah/hybridfit/ui/main/FrequencyOption;", "selected", "ToggleSettingRow", "subtitle", "checked", "onCheckedChange", "UtilityTopBar", "WeeklyTargetScreen", "selectedDays", "", "stepGoal", "sleepGoal", "plannerEditor", "Lcom/athallah/hybridfit/ui/main/PlannerEditorUi;", "recoveryDraft", "Lcom/athallah/hybridfit/ui/main/RecoveryDraftUi;", "onSelectDays", "onStepGoalChange", "onSleepGoalChange", "onPlannerEditorChange", "onPlannerSave", "onPlannerReset", "onPlannerEdit", "onPlannerMove", "onPlannerSkip", "onPlannerComplete", "onPlannerDelete", "onRecoveryDraftChange", "onRecoverySave", "onSaveTargets", "WorkoutCalendarCard", "copyAvatarToInternalStorage", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "infoCardHighlight", "Landroidx/compose/ui/graphics/Color;", "()J", "toCalendarLabel", "toCalendarNote", "toCountdownLabel", "app_debug"})
@kotlin.Suppress(names = {"UNUSED_PARAMETER", "UNUSED_VARIABLE"})
public final class SupportScreensKt {
    
    @androidx.compose.runtime.Composable()
    public static final void QuickActionsScreen(boolean isAuthenticated, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAiHub, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNotifications, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAccount, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onWeeklyTarget, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSettings, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onHelp, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAchievements, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onForgotPassword, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRegister, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onGoogleLogin, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ForgotPasswordScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEmailChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSubmit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onUseGoogle, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void NotificationsScreen(@org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.ui.main.NotificationItemUi> items, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMarkAllRead, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.ui.main.NotificationItemUi, kotlin.Unit> onOpenNotification, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AccountScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String displayName, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String goalLabel, @org.jetbrains.annotations.NotNull()
    java.lang.String experienceLabel, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.String avatarUrl, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onDisplayNameChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEmailChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNotesChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onAvatarChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSave, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    private static final java.lang.String copyAvatarToInternalStorage(android.content.Context context, android.net.Uri uri) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void WeeklyTargetScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, int selectedDays, @org.jetbrains.annotations.NotNull()
    java.lang.String stepGoal, @org.jetbrains.annotations.NotNull()
    java.lang.String sleepGoal, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.PlannerEditorUi plannerEditor, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.RecoveryDraftUi recoveryDraft, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onSelectDays, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onStepGoalChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSleepGoalChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.ui.main.PlannerEditorUi, kotlin.Unit> onPlannerEditorChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPlannerSave, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPlannerReset, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.domain.model.WorkoutSession, kotlin.Unit> onPlannerEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.domain.model.WorkoutSession, kotlin.Unit> onPlannerMove, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.domain.model.WorkoutSession, kotlin.Unit> onPlannerSkip, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.domain.model.WorkoutSession, kotlin.Unit> onPlannerComplete, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.domain.model.WorkoutSession, kotlin.Unit> onPlannerDelete, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.ui.main.RecoveryDraftUi, kotlin.Unit> onRecoveryDraftChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRecoverySave, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSaveTargets, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AppSettingsScreen(boolean reminderEnabled, boolean smartRecommendationEnabled, boolean sleepInsightEnabled, boolean dataSaverEnabled, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onReminderChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onSmartRecommendationChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onSleepInsightChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onDataSaverChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSave, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void HelpCenterScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String supportMessage, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSupportMessageChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenCoach, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSend, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AiHubScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.ProgramFilter selectedProgramFilter, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenCoach, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenProgram, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AchievementsScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onTopAction, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.ui.main.AchievementCardUi, kotlin.Unit> onOpenAchievement, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AiSignalCard(java.lang.String title, java.lang.String body, androidx.compose.ui.graphics.vector.ImageVector icon) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FeatureInfoScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.FeatureInfoUi info, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.time.LocalDate> workoutDates, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.WorkoutSession> plannedSessions, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.RunLogDraftUi runLogDraft, boolean showSimpleRunInput, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRunDistanceChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRunDurationChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRunDurationSecondsChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRunNotesChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPrimaryAction, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void WorkoutCalendarCard(java.util.Set<java.time.LocalDate> workoutDates, java.util.List<com.athallah.hybridfit.domain.model.WorkoutSession> plannedSessions, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final long infoCardHighlight() {
        return 0L;
    }
    
    private static final java.lang.String toCalendarLabel(java.time.LocalDate $this$toCalendarLabel) {
        return null;
    }
    
    private static final java.lang.String toCalendarNote(com.athallah.hybridfit.domain.model.WorkoutSession $this$toCalendarNote) {
        return null;
    }
    
    private static final java.lang.String toCountdownLabel(int $this$toCountdownLabel) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    private static final void QuickActionCard(com.athallah.hybridfit.ui.main.QuickActionCardUi action) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void NotificationCard(com.athallah.hybridfit.ui.main.NotificationItemUi item, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TargetFrequencyCard(com.athallah.hybridfit.ui.main.FrequencyOption option, boolean selected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PlannerSessionCard(com.athallah.hybridfit.domain.model.WorkoutSession session, kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, kotlin.jvm.functions.Function0<kotlin.Unit> onMove, kotlin.jvm.functions.Function0<kotlin.Unit> onSkip, kotlin.jvm.functions.Function0<kotlin.Unit> onComplete, kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SessionCategorySelector(com.athallah.hybridfit.domain.model.WorkoutCategory selectedCategory, kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.domain.model.WorkoutCategory, kotlin.Unit> onSelected) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable()
    private static final void ProgramVariantSelector(java.lang.String label, java.util.List<java.lang.String> options, java.lang.String selectedOption, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSelected) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable()
    private static final void PlannerDaySelector(java.time.DayOfWeek selectedDay, kotlin.jvm.functions.Function1<? super java.time.DayOfWeek, kotlin.Unit> onSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ToggleSettingRow(java.lang.String title, java.lang.String subtitle, androidx.compose.ui.graphics.vector.ImageVector icon, boolean checked, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FaqCard(java.lang.String question, java.lang.String answer) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AchievementDetailRow(com.athallah.hybridfit.ui.main.AchievementCardUi item, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyStateCard(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String title, java.lang.String body) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void UtilityTopBar(java.lang.String title, kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
}