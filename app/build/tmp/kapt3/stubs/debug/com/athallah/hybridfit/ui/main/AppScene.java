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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u001a\b\u0080\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001a\u00a8\u0006\u001b"}, d2 = {"Lcom/athallah/hybridfit/ui/main/AppScene;", "", "(Ljava/lang/String;I)V", "WELCOME", "REGISTER", "LOGIN", "FORGOT_PASSWORD", "SURVEY_GOAL", "SURVEY_LEVEL", "SURVEY_FREQUENCY", "SURVEY_HEALTH", "SURVEY_BODY", "QUICK_ACTIONS", "NOTIFICATIONS", "ACCOUNT", "WEEKLY_TARGET", "APP_SETTINGS", "AI_HUB", "HELP_CENTER", "ACHIEVEMENTS", "FEATURE_INFO", "DASHBOARD", "RUN_DETAIL", "AI_DETAIL", "GYM_DETAIL", "COMPLETION", "AI_CHAT", "app_debug"})
public enum AppScene {
    /*public static final*/ WELCOME /* = new WELCOME() */,
    /*public static final*/ REGISTER /* = new REGISTER() */,
    /*public static final*/ LOGIN /* = new LOGIN() */,
    /*public static final*/ FORGOT_PASSWORD /* = new FORGOT_PASSWORD() */,
    /*public static final*/ SURVEY_GOAL /* = new SURVEY_GOAL() */,
    /*public static final*/ SURVEY_LEVEL /* = new SURVEY_LEVEL() */,
    /*public static final*/ SURVEY_FREQUENCY /* = new SURVEY_FREQUENCY() */,
    /*public static final*/ SURVEY_HEALTH /* = new SURVEY_HEALTH() */,
    /*public static final*/ SURVEY_BODY /* = new SURVEY_BODY() */,
    /*public static final*/ QUICK_ACTIONS /* = new QUICK_ACTIONS() */,
    /*public static final*/ NOTIFICATIONS /* = new NOTIFICATIONS() */,
    /*public static final*/ ACCOUNT /* = new ACCOUNT() */,
    /*public static final*/ WEEKLY_TARGET /* = new WEEKLY_TARGET() */,
    /*public static final*/ APP_SETTINGS /* = new APP_SETTINGS() */,
    /*public static final*/ AI_HUB /* = new AI_HUB() */,
    /*public static final*/ HELP_CENTER /* = new HELP_CENTER() */,
    /*public static final*/ ACHIEVEMENTS /* = new ACHIEVEMENTS() */,
    /*public static final*/ FEATURE_INFO /* = new FEATURE_INFO() */,
    /*public static final*/ DASHBOARD /* = new DASHBOARD() */,
    /*public static final*/ RUN_DETAIL /* = new RUN_DETAIL() */,
    /*public static final*/ AI_DETAIL /* = new AI_DETAIL() */,
    /*public static final*/ GYM_DETAIL /* = new GYM_DETAIL() */,
    /*public static final*/ COMPLETION /* = new COMPLETION() */,
    /*public static final*/ AI_CHAT /* = new AI_CHAT() */;
    
    AppScene() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.athallah.hybridfit.ui.main.AppScene> getEntries() {
        return null;
    }
}