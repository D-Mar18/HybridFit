package com.athallah.hybridfit.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.athallah.hybridfit.auth.GoogleAccountProfile;
import com.athallah.hybridfit.data.local.OnboardingPreferenceStore;
import com.athallah.hybridfit.data.remote.AuthSession;
import com.athallah.hybridfit.data.remote.AuthSessionStore;
import com.athallah.hybridfit.data.remote.CoachChatMessage;
import com.athallah.hybridfit.data.remote.CoachChatResponse;
import com.athallah.hybridfit.data.remote.HybridFitAiApi;
import com.athallah.hybridfit.data.remote.HybridFitAuthApi;
import com.athallah.hybridfit.data.remote.RemoteAuthUser;
import com.athallah.hybridfit.data.repository.HybridFitRepository;
import com.athallah.hybridfit.domain.model.DashboardSnapshot;
import com.athallah.hybridfit.domain.model.FitnessGoal;
import com.athallah.hybridfit.domain.model.FitnessLevel;
import com.athallah.hybridfit.domain.model.SessionPlannerState;
import com.athallah.hybridfit.domain.model.StrengthExerciseDraft;
import com.athallah.hybridfit.domain.model.WorkoutCategory;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00d4\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0002\u0087\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJa\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$2\b\u0010%\u001a\u0004\u0018\u00010\"2\b\u0010&\u001a\u0004\u0018\u00010\"2\u0006\u0010'\u001a\u00020\"2\u0006\u0010(\u001a\u00020\u001b\u00a2\u0006\u0002\u0010)JP\u0010*\u001a\u00020\u00192\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020\"2\u0006\u0010/\u001a\u00020\"2\u0006\u00100\u001a\u00020\u001b2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u0019022\u0012\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001904J\u0006\u00105\u001a\u00020\u0019J\u001a\u00106\u001a\u00020\u001b2\u0006\u00107\u001a\u00020\u001b2\b\u00108\u001a\u0004\u0018\u000109H\u0002J\u000e\u0010:\u001a\u00020\u00192\u0006\u0010;\u001a\u00020<J\u0006\u0010=\u001a\u00020\u0019J\u0006\u0010>\u001a\u00020\u0019J\u0006\u0010?\u001a\u00020\u0019J2\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u001b2\u0006\u0010C\u001a\u00020\u001b2\u0006\u0010D\u001a\u00020\u001b2\u0006\u0010E\u001a\u00020\u001b2\b\u0010F\u001a\u0004\u0018\u00010\u001bH\u0002JV\u0010G\u001a\u00020\u00192\u0006\u0010D\u001a\u00020\u001b2\u0006\u0010H\u001a\u00020\"2\u0006\u0010I\u001a\u00020$2\u0006\u0010J\u001a\u00020\"2\u0006\u0010+\u001a\u00020,2\u0006\u0010K\u001a\u00020L2\u0006\u0010-\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020\"2\u0006\u0010/\u001a\u00020\"2\u0006\u0010M\u001a\u00020\u001bJ\u000e\u0010N\u001a\u00020\u00192\u0006\u0010O\u001a\u00020PJ\u000e\u0010Q\u001a\u00020\u00192\u0006\u0010R\u001a\u00020PJ\u000e\u0010S\u001a\u00020\u00192\u0006\u0010T\u001a\u00020\u001bJ\u0006\u0010U\u001a\u00020\u0019J\u0016\u0010V\u001a\u00020\u00192\u0006\u0010C\u001a\u00020\u001b2\u0006\u0010W\u001a\u00020\u001bJ\u0006\u0010X\u001a\u00020\u0019J\u000e\u0010Y\u001a\u00020\u00192\u0006\u0010O\u001a\u00020PJ\u0006\u0010Z\u001a\u00020\u0019J\u001e\u0010[\u001a\u00020\u00192\u0006\u0010D\u001a\u00020\u001b2\u0006\u0010C\u001a\u00020\u001b2\u0006\u0010W\u001a\u00020\u001bJ\u0012\u0010\\\u001a\u00020]2\b\u0010^\u001a\u0004\u0018\u00010_H\u0002J.\u0010`\u001a\u00020\u00192\u0006\u0010a\u001a\u00020$2\u0006\u0010b\u001a\u00020\"2\u0006\u0010c\u001a\u00020\"2\u0006\u0010d\u001a\u00020\"2\u0006\u0010(\u001a\u00020\u001bJ.\u0010e\u001a\u00020\u00192\u0006\u0010O\u001a\u00020P2\u0006\u0010f\u001a\u00020$2\u0006\u0010g\u001a\u00020\"2\u0006\u0010h\u001a\u00020\"2\u0006\u0010(\u001a\u00020\u001bJ4\u0010i\u001a\u00020\u00192\u0006\u0010O\u001a\u00020P2\u0006\u0010g\u001a\u00020\"2\u0006\u0010h\u001a\u00020\"2\u0006\u0010(\u001a\u00020\u001b2\f\u0010j\u001a\b\u0012\u0004\u0012\u00020l0kJC\u0010m\u001a\u00020\u00192\f\u0010n\u001a\b\u0012\u0004\u0012\u00020o0k2\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u00020p\u0012\u0004\u0012\u00020\u0019042\u0012\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001904H\u0000\u00a2\u0006\u0002\bqJ\u0006\u0010r\u001a\u00020\u0019J\u0017\u0010s\u001a\u00020\u00192\b\u0010t\u001a\u0004\u0018\u00010PH\u0002\u00a2\u0006\u0002\u0010uJ\u000e\u0010v\u001a\u00020\u00192\u0006\u0010w\u001a\u00020]J\u0012\u0010x\u001a\u00020\u00192\b\u0010^\u001a\u0004\u0018\u00010_H\u0002J\u000e\u0010y\u001a\u00020\u00192\u0006\u0010F\u001a\u00020\u001bJi\u0010z\u001a\u00020\u00192\u0006\u0010O\u001a\u00020P2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$2\b\u0010%\u001a\u0004\u0018\u00010\"2\b\u0010&\u001a\u0004\u0018\u00010\"2\u0006\u0010'\u001a\u00020\"2\u0006\u0010(\u001a\u00020\u001b\u00a2\u0006\u0002\u0010{J\u0016\u0010|\u001a\u00020\u00192\u0006\u0010O\u001a\u00020P2\u0006\u0010}\u001a\u00020~J.\u0010\u007f\u001a\u00020\u00192\u0006\u0010R\u001a\u00020P2\u0006\u0010f\u001a\u00020$2\u0006\u0010g\u001a\u00020\"2\u0006\u0010h\u001a\u00020\"2\u0006\u0010(\u001a\u00020\u001bJ5\u0010\u0080\u0001\u001a\u00020\u00192\u0006\u0010R\u001a\u00020P2\u0006\u0010g\u001a\u00020\"2\u0006\u0010h\u001a\u00020\"2\u0006\u0010(\u001a\u00020\u001b2\f\u0010j\u001a\b\u0012\u0004\u0012\u00020l0kJ1\u0010\u0081\u0001\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\"2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u0019022\u0012\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001904J\u000e\u0010\u0082\u0001\u001a\u00020]*\u00030\u0083\u0001H\u0002J\u000e\u0010\u0084\u0001\u001a\u00020]*\u00030\u0083\u0001H\u0002J\u0017\u0010\u0085\u0001\u001a\u00020\u001b*\u00030\u0083\u00012\u0007\u0010\u0086\u0001\u001a\u00020\u001bH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R+\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@BX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0088\u0001"}, d2 = {"Lcom/athallah/hybridfit/ui/main/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/athallah/hybridfit/data/repository/HybridFitRepository;", "authApi", "Lcom/athallah/hybridfit/data/remote/HybridFitAuthApi;", "aiApi", "Lcom/athallah/hybridfit/data/remote/HybridFitAiApi;", "authSessionStore", "Lcom/athallah/hybridfit/data/remote/AuthSessionStore;", "onboardingPreferenceStore", "Lcom/athallah/hybridfit/data/local/OnboardingPreferenceStore;", "(Lcom/athallah/hybridfit/data/repository/HybridFitRepository;Lcom/athallah/hybridfit/data/remote/HybridFitAuthApi;Lcom/athallah/hybridfit/data/remote/HybridFitAiApi;Lcom/athallah/hybridfit/data/remote/AuthSessionStore;Lcom/athallah/hybridfit/data/local/OnboardingPreferenceStore;)V", "dashboardObservationJob", "Lkotlinx/coroutines/Job;", "<set-?>", "Lcom/athallah/hybridfit/ui/main/MainUiState;", "uiState", "getUiState", "()Lcom/athallah/hybridfit/ui/main/MainUiState;", "setUiState", "(Lcom/athallah/hybridfit/ui/main/MainUiState;)V", "uiState$delegate", "Landroidx/compose/runtime/MutableState;", "addPlannerSession", "", "title", "", "category", "Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "focusArea", "dayOfWeek", "Ljava/time/DayOfWeek;", "durationMinutes", "", "targetDistanceKm", "", "targetSets", "targetReps", "restSeconds", "notes", "(Ljava/lang/String;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;Ljava/lang/String;Ljava/time/DayOfWeek;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;)V", "applyCoachProgramProposal", "goal", "Lcom/athallah/hybridfit/domain/model/FitnessGoal;", "preferredFocus", "workoutDaysPerWeek", "sessionDurationMinutes", "requestSummary", "onSuccess", "Lkotlin/Function0;", "onFailure", "Lkotlin/Function1;", "beginGoogleAuth", "buildLocalCoachReply", "latestPrompt", "snapshot", "Lcom/athallah/hybridfit/domain/model/DashboardSnapshot;", "completeGoogleAuth", "accountProfile", "Lcom/athallah/hybridfit/auth/GoogleAccountProfile;", "completeIntroSurvey", "consumeAuthStatusMessage", "consumeInfoMessage", "createLocalProviderSession", "Lcom/athallah/hybridfit/data/remote/AuthSession;", "id", "email", "fullName", "authProvider", "avatarUrl", "createStarterProgram", "age", "weightKg", "heightCm", "fitnessLevel", "Lcom/athallah/hybridfit/domain/model/FitnessLevel;", "experienceNotes", "deletePlannerSession", "sessionId", "", "deleteWorkoutLog", "logId", "failGoogleAuth", "message", "loginWithAppleDemo", "loginWithEmail", "password", "logout", "movePlannerSessionToNextDay", "refreshRecommendation", "registerWithEmail", "resolveSurveyCompleted", "", "user", "Lcom/athallah/hybridfit/data/remote/RemoteAuthUser;", "saveRecoveryCheckIn", "sleepHours", "recoveryScore", "energyLevel", "sorenessLevel", "saveRunWorkout", "distanceKm", "actualDurationMinutes", "exertionScore", "saveStrengthWorkout", "exercises", "", "Lcom/athallah/hybridfit/domain/model/StrengthExerciseDraft;", "sendCoachChatMessage", "messages", "Lcom/athallah/hybridfit/ui/main/CoachMessage;", "Lcom/athallah/hybridfit/data/remote/CoachChatResponse;", "sendCoachChatMessage$app_debug", "simulateWorkoutCompletion", "startDashboardObservation", "userId", "(Ljava/lang/Long;)V", "submitFeedback", "wasHelpful", "syncSurveyPreference", "updateCurrentUserAvatar", "updatePlannerSession", "(JLjava/lang/String;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;Ljava/lang/String;Ljava/time/DayOfWeek;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;)V", "updatePlannerSessionState", "plannerState", "Lcom/athallah/hybridfit/domain/model/SessionPlannerState;", "updateRunWorkout", "updateStrengthWorkout", "updateWeeklyTargets", "isBackendConnectionError", "", "isTemporaryAiBusyError", "toUserFriendlyAuthMessage", "fallback", "Factory", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.repository.HybridFitRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.remote.HybridFitAuthApi authApi = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.remote.HybridFitAiApi aiApi = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.remote.AuthSessionStore authSessionStore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.local.OnboardingPreferenceStore onboardingPreferenceStore = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState uiState$delegate = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job dashboardObservationJob;
    
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.repository.HybridFitRepository repository, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.remote.HybridFitAuthApi authApi, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.remote.HybridFitAiApi aiApi, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.remote.AuthSessionStore authSessionStore, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.local.OnboardingPreferenceStore onboardingPreferenceStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.ui.main.MainUiState getUiState() {
        return null;
    }
    
    private final void setUiState(com.athallah.hybridfit.ui.main.MainUiState p0) {
    }
    
    public final void simulateWorkoutCompletion() {
    }
    
    public final void refreshRecommendation() {
    }
    
    public final void submitFeedback(boolean wasHelpful) {
    }
    
    public final void updateCurrentUserAvatar(@org.jetbrains.annotations.NotNull()
    java.lang.String avatarUrl) {
    }
    
    public final void saveStrengthWorkout(long sessionId, int actualDurationMinutes, int exertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.StrengthExerciseDraft> exercises) {
    }
    
    public final void saveRunWorkout(long sessionId, double distanceKm, int actualDurationMinutes, int exertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String notes) {
    }
    
    public final void updateStrengthWorkout(long logId, int actualDurationMinutes, int exertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.StrengthExerciseDraft> exercises) {
    }
    
    public final void updateRunWorkout(long logId, double distanceKm, int actualDurationMinutes, int exertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String notes) {
    }
    
    public final void deleteWorkoutLog(long logId) {
    }
    
    public final void saveRecoveryCheckIn(double sleepHours, int recoveryScore, int energyLevel, int sorenessLevel, @org.jetbrains.annotations.NotNull()
    java.lang.String notes) {
    }
    
    public final void addPlannerSession(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory category, @org.jetbrains.annotations.NotNull()
    java.lang.String focusArea, @org.jetbrains.annotations.NotNull()
    java.time.DayOfWeek dayOfWeek, int durationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetDistanceKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetSets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetReps, int restSeconds, @org.jetbrains.annotations.NotNull()
    java.lang.String notes) {
    }
    
    public final void updatePlannerSession(long sessionId, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory category, @org.jetbrains.annotations.NotNull()
    java.lang.String focusArea, @org.jetbrains.annotations.NotNull()
    java.time.DayOfWeek dayOfWeek, int durationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetDistanceKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetSets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetReps, int restSeconds, @org.jetbrains.annotations.NotNull()
    java.lang.String notes) {
    }
    
    public final void movePlannerSessionToNextDay(long sessionId) {
    }
    
    public final void updatePlannerSessionState(long sessionId, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.SessionPlannerState plannerState) {
    }
    
    public final void deletePlannerSession(long sessionId) {
    }
    
    public final void completeIntroSurvey() {
    }
    
    public final void createStarterProgram(@org.jetbrains.annotations.NotNull()
    java.lang.String fullName, int age, double weightKg, int heightCm, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessGoal goal, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessLevel fitnessLevel, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory preferredFocus, int workoutDaysPerWeek, int sessionDurationMinutes, @org.jetbrains.annotations.NotNull()
    java.lang.String experienceNotes) {
    }
    
    public final void applyCoachProgramProposal(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessGoal goal, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory preferredFocus, int workoutDaysPerWeek, int sessionDurationMinutes, @org.jetbrains.annotations.NotNull()
    java.lang.String requestSummary, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onFailure) {
    }
    
    public final void updateWeeklyTargets(int workoutDaysPerWeek, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onFailure) {
    }
    
    public final void registerWithEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String fullName, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password) {
    }
    
    public final void loginWithEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password) {
    }
    
    public final void beginGoogleAuth() {
    }
    
    public final void loginWithAppleDemo() {
    }
    
    public final void completeGoogleAuth(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.auth.GoogleAccountProfile accountProfile) {
    }
    
    public final void failGoogleAuth(@org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
    
    public final void logout() {
    }
    
    public final void sendCoachChatMessage$app_debug(@org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.ui.main.CoachMessage> messages, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.data.remote.CoachChatResponse, kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onFailure) {
    }
    
    public final void consumeAuthStatusMessage() {
    }
    
    public final void consumeInfoMessage() {
    }
    
    private final boolean resolveSurveyCompleted(com.athallah.hybridfit.data.remote.RemoteAuthUser user) {
        return false;
    }
    
    private final void syncSurveyPreference(com.athallah.hybridfit.data.remote.RemoteAuthUser user) {
    }
    
    private final void startDashboardObservation(java.lang.Long userId) {
    }
    
    private final com.athallah.hybridfit.data.remote.AuthSession createLocalProviderSession(java.lang.String id, java.lang.String email, java.lang.String fullName, java.lang.String authProvider, java.lang.String avatarUrl) {
        return null;
    }
    
    private final java.lang.String toUserFriendlyAuthMessage(java.lang.Throwable $this$toUserFriendlyAuthMessage, java.lang.String fallback) {
        return null;
    }
    
    private final boolean isBackendConnectionError(java.lang.Throwable $this$isBackendConnectionError) {
        return false;
    }
    
    private final boolean isTemporaryAiBusyError(java.lang.Throwable $this$isTemporaryAiBusyError) {
        return false;
    }
    
    private final java.lang.String buildLocalCoachReply(java.lang.String latestPrompt, com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ%\u0010\r\u001a\u0002H\u000e\"\b\b\u0000\u0010\u000e*\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0011H\u0016\u00a2\u0006\u0002\u0010\u0012R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/athallah/hybridfit/ui/main/MainViewModel$Factory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "repository", "Lcom/athallah/hybridfit/data/repository/HybridFitRepository;", "authApi", "Lcom/athallah/hybridfit/data/remote/HybridFitAuthApi;", "aiApi", "Lcom/athallah/hybridfit/data/remote/HybridFitAiApi;", "authSessionStore", "Lcom/athallah/hybridfit/data/remote/AuthSessionStore;", "onboardingPreferenceStore", "Lcom/athallah/hybridfit/data/local/OnboardingPreferenceStore;", "(Lcom/athallah/hybridfit/data/repository/HybridFitRepository;Lcom/athallah/hybridfit/data/remote/HybridFitAuthApi;Lcom/athallah/hybridfit/data/remote/HybridFitAiApi;Lcom/athallah/hybridfit/data/remote/AuthSessionStore;Lcom/athallah/hybridfit/data/local/OnboardingPreferenceStore;)V", "create", "T", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "app_debug"})
    public static final class Factory implements androidx.lifecycle.ViewModelProvider.Factory {
        @org.jetbrains.annotations.NotNull()
        private final com.athallah.hybridfit.data.repository.HybridFitRepository repository = null;
        @org.jetbrains.annotations.NotNull()
        private final com.athallah.hybridfit.data.remote.HybridFitAuthApi authApi = null;
        @org.jetbrains.annotations.NotNull()
        private final com.athallah.hybridfit.data.remote.HybridFitAiApi aiApi = null;
        @org.jetbrains.annotations.NotNull()
        private final com.athallah.hybridfit.data.remote.AuthSessionStore authSessionStore = null;
        @org.jetbrains.annotations.NotNull()
        private final com.athallah.hybridfit.data.local.OnboardingPreferenceStore onboardingPreferenceStore = null;
        
        public Factory(@org.jetbrains.annotations.NotNull()
        com.athallah.hybridfit.data.repository.HybridFitRepository repository, @org.jetbrains.annotations.NotNull()
        com.athallah.hybridfit.data.remote.HybridFitAuthApi authApi, @org.jetbrains.annotations.NotNull()
        com.athallah.hybridfit.data.remote.HybridFitAiApi aiApi, @org.jetbrains.annotations.NotNull()
        com.athallah.hybridfit.data.remote.AuthSessionStore authSessionStore, @org.jetbrains.annotations.NotNull()
        com.athallah.hybridfit.data.local.OnboardingPreferenceStore onboardingPreferenceStore) {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public <T extends androidx.lifecycle.ViewModel>T create(@org.jetbrains.annotations.NotNull()
        java.lang.Class<T> modelClass) {
            return null;
        }
    }
}