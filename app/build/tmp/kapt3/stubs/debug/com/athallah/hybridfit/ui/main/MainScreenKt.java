package com.athallah.hybridfit.ui.main;

import android.content.Context;
import android.content.Intent;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005H\u0001\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0001\u001a\u001e\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\rH\u0007\u00a8\u0006\u000e"}, d2 = {"BottomBar", "", "selected", "Lcom/athallah/hybridfit/ui/main/MainTab;", "onSelected", "Lkotlin/Function1;", "LoadingScreen", "innerPadding", "Landroidx/compose/foundation/layout/PaddingValues;", "MainScreen", "viewModel", "Lcom/athallah/hybridfit/ui/main/MainViewModel;", "onGoogleLoginRequested", "Lkotlin/Function0;", "app_debug"})
public final class MainScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void MainScreen(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.MainViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onGoogleLoginRequested) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LoadingScreen(@org.jetbrains.annotations.NotNull()
    androidx.compose.foundation.layout.PaddingValues innerPadding) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void BottomBar(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.ui.main.MainTab selected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.athallah.hybridfit.ui.main.MainTab, kotlin.Unit> onSelected) {
    }
}