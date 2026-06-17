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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0080\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/athallah/hybridfit/ui/main/RunProgramType;", "", "(Ljava/lang/String;I)V", "EASY", "LONG", "TEMPO", "INTERVAL", "STANDARD", "app_debug"})
public enum RunProgramType {
    /*public static final*/ EASY /* = new EASY() */,
    /*public static final*/ LONG /* = new LONG() */,
    /*public static final*/ TEMPO /* = new TEMPO() */,
    /*public static final*/ INTERVAL /* = new INTERVAL() */,
    /*public static final*/ STANDARD /* = new STANDARD() */;
    
    RunProgramType() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.athallah.hybridfit.ui.main.RunProgramType> getEntries() {
        return null;
    }
}