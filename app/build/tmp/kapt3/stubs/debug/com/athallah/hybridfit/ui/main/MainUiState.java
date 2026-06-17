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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\"\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u00a3\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0010\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\u0002\u0010\u0017J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003J\t\u0010)\u001a\u00020\u0012H\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003J\t\u0010+\u001a\u00020\u0012H\u00c6\u0003J\t\u0010,\u001a\u00020\u0016H\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\u00a7\u0001\u00105\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00102\b\b\u0002\u0010\u0014\u001a\u00020\u00122\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u00c6\u0001J\u0013\u00106\u001a\u00020\u00032\b\u00107\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00108\u001a\u000209H\u00d6\u0001J\t\u0010:\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0014\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001bR\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u001fR\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u001fR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u001fR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u001fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u001fR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u001fR\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u001fR\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%\u00a8\u0006;"}, d2 = {"Lcom/athallah/hybridfit/ui/main/MainUiState;", "", "isLoading", "", "snapshot", "Lcom/athallah/hybridfit/domain/model/DashboardSnapshot;", "isCredentialAuthInProgress", "isGoogleAuthInProgress", "isLogoutInProgress", "isProgramCreationInProgress", "isCoachChatSending", "isAuthenticated", "hasCompletedIntroSurvey", "currentUser", "Lcom/athallah/hybridfit/data/remote/RemoteAuthUser;", "authStatusMessage", "", "authEventId", "", "infoMessage", "infoEventId", "lastAuthAction", "Lcom/athallah/hybridfit/ui/main/AuthAction;", "(ZLcom/athallah/hybridfit/domain/model/DashboardSnapshot;ZZZZZZZLcom/athallah/hybridfit/data/remote/RemoteAuthUser;Ljava/lang/String;JLjava/lang/String;JLcom/athallah/hybridfit/ui/main/AuthAction;)V", "getAuthEventId", "()J", "getAuthStatusMessage", "()Ljava/lang/String;", "getCurrentUser", "()Lcom/athallah/hybridfit/data/remote/RemoteAuthUser;", "getHasCompletedIntroSurvey", "()Z", "getInfoEventId", "getInfoMessage", "getLastAuthAction", "()Lcom/athallah/hybridfit/ui/main/AuthAction;", "getSnapshot", "()Lcom/athallah/hybridfit/domain/model/DashboardSnapshot;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class MainUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable()
    private final com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot = null;
    private final boolean isCredentialAuthInProgress = false;
    private final boolean isGoogleAuthInProgress = false;
    private final boolean isLogoutInProgress = false;
    private final boolean isProgramCreationInProgress = false;
    private final boolean isCoachChatSending = false;
    private final boolean isAuthenticated = false;
    private final boolean hasCompletedIntroSurvey = false;
    @org.jetbrains.annotations.Nullable()
    private final com.athallah.hybridfit.data.remote.RemoteAuthUser currentUser = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String authStatusMessage = null;
    private final long authEventId = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String infoMessage = null;
    private final long infoEventId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.ui.main.AuthAction lastAuthAction = null;
    
    public MainUiState(boolean isLoading, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, boolean isCredentialAuthInProgress, boolean isGoogleAuthInProgress, boolean isLogoutInProgress, boolean isProgramCreationInProgress, boolean isCoachChatSending, boolean isAuthenticated, boolean hasCompletedIntroSurvey, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.data.remote.RemoteAuthUser currentUser, @org.jetbrains.annotations.Nullable()
    java.lang.String authStatusMessage, long authEventId, @org.jetbrains.annotations.Nullable()
    java.lang.String infoMessage, long infoEventId, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.AuthAction lastAuthAction) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.athallah.hybridfit.domain.model.DashboardSnapshot getSnapshot() {
        return null;
    }
    
    public final boolean isCredentialAuthInProgress() {
        return false;
    }
    
    public final boolean isGoogleAuthInProgress() {
        return false;
    }
    
    public final boolean isLogoutInProgress() {
        return false;
    }
    
    public final boolean isProgramCreationInProgress() {
        return false;
    }
    
    public final boolean isCoachChatSending() {
        return false;
    }
    
    public final boolean isAuthenticated() {
        return false;
    }
    
    public final boolean getHasCompletedIntroSurvey() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.athallah.hybridfit.data.remote.RemoteAuthUser getCurrentUser() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAuthStatusMessage() {
        return null;
    }
    
    public final long getAuthEventId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getInfoMessage() {
        return null;
    }
    
    public final long getInfoEventId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.ui.main.AuthAction getLastAuthAction() {
        return null;
    }
    
    public MainUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.athallah.hybridfit.data.remote.RemoteAuthUser component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    public final long component12() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component13() {
        return null;
    }
    
    public final long component14() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.ui.main.AuthAction component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.athallah.hybridfit.domain.model.DashboardSnapshot component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.ui.main.MainUiState copy(boolean isLoading, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.domain.model.DashboardSnapshot snapshot, boolean isCredentialAuthInProgress, boolean isGoogleAuthInProgress, boolean isLogoutInProgress, boolean isProgramCreationInProgress, boolean isCoachChatSending, boolean isAuthenticated, boolean hasCompletedIntroSurvey, @org.jetbrains.annotations.Nullable()
    com.athallah.hybridfit.data.remote.RemoteAuthUser currentUser, @org.jetbrains.annotations.Nullable()
    java.lang.String authStatusMessage, long authEventId, @org.jetbrains.annotations.Nullable()
    java.lang.String infoMessage, long infoEventId, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.AuthAction lastAuthAction) {
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