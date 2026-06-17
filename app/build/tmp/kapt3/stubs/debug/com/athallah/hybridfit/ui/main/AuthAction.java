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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2 = {"Lcom/athallah/hybridfit/ui/main/AuthAction;", "", "(Ljava/lang/String;I)V", "NONE", "LOGIN", "REGISTER", "GOOGLE_LOGIN", "APPLE_LOGIN", "LOGOUT", "app_debug"})
public enum AuthAction {
    /*public static final*/ NONE /* = new NONE() */,
    /*public static final*/ LOGIN /* = new LOGIN() */,
    /*public static final*/ REGISTER /* = new REGISTER() */,
    /*public static final*/ GOOGLE_LOGIN /* = new GOOGLE_LOGIN() */,
    /*public static final*/ APPLE_LOGIN /* = new APPLE_LOGIN() */,
    /*public static final*/ LOGOUT /* = new LOGOUT() */;
    
    AuthAction() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.athallah.hybridfit.ui.main.AuthAction> getEntries() {
        return null;
    }
}