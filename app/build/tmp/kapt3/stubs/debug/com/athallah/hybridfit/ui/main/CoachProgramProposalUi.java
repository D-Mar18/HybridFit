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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0080\b\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\nH\u00c6\u0003J\t\u0010 \u001a\u00020\nH\u00c6\u0003J\t\u0010!\u001a\u00020\rH\u00c6\u0003JO\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\u0007H\u00d6\u0001J\t\u0010'\u001a\u00020\nH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016\u00a8\u0006("}, d2 = {"Lcom/athallah/hybridfit/ui/main/CoachProgramProposalUi;", "", "goal", "Lcom/athallah/hybridfit/domain/model/FitnessGoal;", "preferredFocus", "Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "workoutDaysPerWeek", "", "sessionDurationMinutes", "programLabel", "", "summaryLabel", "status", "Lcom/athallah/hybridfit/ui/main/CoachProgramProposalStatus;", "(Lcom/athallah/hybridfit/domain/model/FitnessGoal;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;IILjava/lang/String;Ljava/lang/String;Lcom/athallah/hybridfit/ui/main/CoachProgramProposalStatus;)V", "getGoal", "()Lcom/athallah/hybridfit/domain/model/FitnessGoal;", "getPreferredFocus", "()Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "getProgramLabel", "()Ljava/lang/String;", "getSessionDurationMinutes", "()I", "getStatus", "()Lcom/athallah/hybridfit/ui/main/CoachProgramProposalStatus;", "getSummaryLabel", "getWorkoutDaysPerWeek", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class CoachProgramProposalUi {
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.model.FitnessGoal goal = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.model.WorkoutCategory preferredFocus = null;
    private final int workoutDaysPerWeek = 0;
    private final int sessionDurationMinutes = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String programLabel = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String summaryLabel = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.ui.main.CoachProgramProposalStatus status = null;
    
    public CoachProgramProposalUi(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessGoal goal, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory preferredFocus, int workoutDaysPerWeek, int sessionDurationMinutes, @org.jetbrains.annotations.NotNull()
    java.lang.String programLabel, @org.jetbrains.annotations.NotNull()
    java.lang.String summaryLabel, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.CoachProgramProposalStatus status) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.FitnessGoal getGoal() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.WorkoutCategory getPreferredFocus() {
        return null;
    }
    
    public final int getWorkoutDaysPerWeek() {
        return 0;
    }
    
    public final int getSessionDurationMinutes() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getProgramLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSummaryLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.ui.main.CoachProgramProposalStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.FitnessGoal component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.WorkoutCategory component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.ui.main.CoachProgramProposalStatus component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.ui.main.CoachProgramProposalUi copy(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessGoal goal, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory preferredFocus, int workoutDaysPerWeek, int sessionDurationMinutes, @org.jetbrains.annotations.NotNull()
    java.lang.String programLabel, @org.jetbrains.annotations.NotNull()
    java.lang.String summaryLabel, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.CoachProgramProposalStatus status) {
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