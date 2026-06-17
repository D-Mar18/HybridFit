@file:Suppress("UNUSED_PARAMETER")

package com.athallah.hybridfit.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.SportsGymnastics
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.athallah.hybridfit.R
import com.athallah.hybridfit.domain.model.DashboardSnapshot
import com.athallah.hybridfit.domain.model.FitnessGoal
import com.athallah.hybridfit.domain.model.FitnessLevel
import com.athallah.hybridfit.domain.model.ProgressTrend
import com.athallah.hybridfit.domain.model.Recommendation
import com.athallah.hybridfit.domain.model.UserProfile
import com.athallah.hybridfit.domain.model.WorkoutCategory
import com.athallah.hybridfit.domain.model.WorkoutLog
import com.athallah.hybridfit.domain.model.WorkoutSession
import com.athallah.hybridfit.ui.theme.AquaBright
import com.athallah.hybridfit.ui.theme.AquaTeal
import com.athallah.hybridfit.ui.theme.BorderSoft
import com.athallah.hybridfit.ui.theme.OceanBlue
import com.athallah.hybridfit.ui.theme.OceanBlueDark
import com.athallah.hybridfit.ui.theme.SurfaceSoft
import com.athallah.hybridfit.ui.theme.SurfaceTint
import com.athallah.hybridfit.ui.theme.SurfaceMuted
import com.athallah.hybridfit.ui.theme.SurfaceWhite
import com.athallah.hybridfit.ui.theme.TextPrimary
import com.athallah.hybridfit.ui.theme.TextSecondary
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.roundToInt

@Composable
internal fun RunDetailScreen(
    snapshot: DashboardSnapshot,
    modifier: Modifier = Modifier,
    selectedLog: WorkoutLog? = null,
    onBack: () -> Unit,
    onShare: () -> Unit,
    onSummaryClick: () -> Unit,
    onMetricClick: (String) -> Unit,
    onSplitClick: (String) -> Unit,
    onEditLog: () -> Unit,
    onDeleteLog: () -> Unit
) {
    val log = selectedLog ?: snapshot.primaryRunLog()
    val caloriesValue = when {
        log?.actualDistanceKm != null -> ((log.actualDistanceKm * 60) + (log.actualDurationMinutes * 4)).roundToInt()
        log != null -> (log.actualDurationMinutes * 5.5).roundToInt()
        else -> 0
    }
    val splits = if (log?.actualDistanceKm != null && log.actualDistanceKm > 0.0) {
        val totalSegments = log.actualDistanceKm.roundToInt().coerceAtLeast(1)
        List(totalSegments.coerceAtMost(4)) { index ->
            Triple(
                "Kilometer ${index + 1}",
                log.paceLabel(),
                (0.62f + (index * 0.08f)).coerceIn(0.62f, 0.92f)
            )
        }
    } else {
        emptyList()
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailTopBar(
                title = "Detail Aktivitas",
                onBack = onBack
            )
        }
        item {
            Text(
                text = log?.sessionTitle ?: "Belum Ada Aktivitas",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary
            )
        }
        item {
            Text(
                text = log?.performedOn?.toString() ?: "Belum ada tanggal aktivitas",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
        item {
            StatHeroCard(
                label = "",
                mainValue = "${"%.2f".format(log?.actualDistanceKm ?: 0.0)} km",
                accent = OceanBlue,
                badgeText = null,
                onClick = onSummaryClick
            )
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                TinyMetricCard(
                    "Duration",
                    if (log != null) "${log.actualDurationMinutes} min" else "0 min",
                    Icons.Outlined.AccessTime,
                    Modifier.weight(1f),
                    onClick = { onMetricClick("Duration") }
                )
                TinyMetricCard(
                    "Calories",
                    caloriesValue.toString(),
                    Icons.Outlined.LocalFireDepartment,
                    Modifier.weight(1f),
                    onClick = { onMetricClick("Calories") }
                )
                TinyMetricCard(
                    "Avg Pace",
                    log?.paceLabel() ?: "-",
                    Icons.AutoMirrored.Outlined.ShowChart,
                    Modifier.weight(1f),
                    onClick = { onMetricClick("Avg Pace") }
                )
            }
        }
        if (splits.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "Splits",
                    actionLabel = "km / pace"
                )
            }
            items(splits) { split ->
                SplitRow(
                    title = split.first,
                    pace = split.second,
                    progress = split.third,
                    onClick = { onSplitClick(split.first) }
                )
            }
        }
        if (!log?.notes.isNullOrBlank()) {
            item {
                InsightCard(
                    title = "Catatan Pelari",
                    body = log?.notes.orEmpty()
                )
            }
        }
        if (log != null) {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SecondaryOutlineButton(
                        text = "Edit Log",
                        onClick = onEditLog,
                        modifier = Modifier.weight(1f)
                    )
                    SecondaryOutlineButton(
                        text = "Hapus Log",
                        onClick = onDeleteLog,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        item {
            PrimaryGradientButton(
                text = "Bagikan Aktivitas",
                onClick = onShare,
                leadingIcon = Icons.Outlined.Share,
                trailingIcon = null
            )
        }
    }
}

@Composable
internal fun AiWorkoutDetailScreen(
    snapshot: DashboardSnapshot,
    selectedProgramFilter: ProgramFilter,
    onBack: () -> Unit,
    onOpenCoach: () -> Unit,
    onSummaryClick: () -> Unit,
    onMetricClick: (String) -> Unit,
    onRoutineClick: (String, String) -> Unit,
    onStartWorkout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val recommendation = snapshot.latestRecommendation
    val session = snapshot.sessionForFilter(selectedProgramFilter)
    val isRunningProgram = selectedProgramFilter == ProgramFilter.RUN
    val runProgramType = session.runProgramType()
    val isIntervalRun = isRunningProgram && runProgramType == RunProgramType.INTERVAL
    val title = if (isRunningProgram) session.title.ifBlank { "Sesi Lari Adaptif" } else "Sesi Hybrid Adaptif"
    val routines = if (isRunningProgram) {
        listOf(
            "Pemanasan Dinamis" to "5 menit - Intensitas rendah",
            (if (isIntervalRun) "Lari Interval Stabil" else session.title.ifBlank { "Sesi Lari" }) to
                "${session.targetDurationMinutes} menit - ${session.focusArea.ifBlank { "Intensitas menengah" }}",
            "Pendinginan & Regangan" to "10 menit - Relaksasi"
        )
    } else {
        listOf(
            "Aktivasi Upper Body" to "10 menit - Mobilitas bahu",
            "Superset Kekuatan" to "25 menit - Beban progresif",
            "Cooldown Mobility" to "10 menit - Recovery"
        )
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailTopBar(
                title = title,
                onBack = onBack
            )
        }
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .clickable(onClick = onSummaryClick),
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFF14364A),
                                    Color(0xFF28576A),
                                    Color(0xFFF7C57E),
                                    Color(0xFFEDEFF5)
                                )
                            )
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .align(Alignment.Center)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(Color(0xFFFFF0C2), Color.Transparent)
                                )
                            )
                    )
                    Icon(
                        imageVector = if (isRunningProgram) {
                            Icons.AutoMirrored.Outlined.DirectionsRun
                        } else {
                            Icons.Outlined.FitnessCenter
                        },
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.22f),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(172.dp)
                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TinyMetricCard(
                    "Kkal Est.",
                    session.aiTargetCalories(recommendation).toString(),
                    Icons.Outlined.LocalFireDepartment,
                    Modifier.weight(1f),
                    onClick = { onMetricClick("Kkal Est.") }
                )
                TinyMetricCard(
                    "Target Durasi",
                    "${recommendation?.targetDurationMinutes ?: snapshot.primarySession().targetDurationMinutes} min",
                    Icons.Outlined.AccessTime,
                    Modifier.weight(1f),
                    onClick = { onMetricClick("Target Durasi") }
                )
            }
        }
        item {
            Text(
                text = "Rangkaian Latihan",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
        }
        items(routines) { routine ->
            ProgramRoutineRow(
                title = routine.first,
                subtitle = routine.second,
                onClick = { onRoutineClick(routine.first, routine.second) }
            )
        }
        item {
            PrimaryGradientButton(
                text = if (isRunningProgram && !isIntervalRun) "Simpan Hasil Lari" else "Mulai Latihan",
                onClick = onStartWorkout,
                leadingIcon = Icons.Outlined.PlayArrow,
                trailingIcon = null,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
internal fun GymWorkoutDetailScreen(
    snapshot: DashboardSnapshot,
    modifier: Modifier = Modifier,
    selectedLog: WorkoutLog? = null,
    exercises: List<GymExerciseUi>,
    onBack: () -> Unit,
    onMore: () -> Unit,
    onShare: () -> Unit,
    onSummaryClick: () -> Unit,
    onMetricClick: (String) -> Unit,
    onHeroClick: () -> Unit,
    onInsightClick: () -> Unit,
    onStartWorkout: () -> Unit,
    onToggleExercise: (Int) -> Unit,
    onExerciseRepsChange: (Int, Int, String) -> Unit,
    onExerciseWeightChange: (Int, Int, String) -> Unit,
    onExercisePrimaryAction: (Int) -> Unit,
    onExerciseTimerTick: (Int) -> Unit,
    onFinishSession: () -> Unit
) {
    val session = snapshot.sessionForFilter(ProgramFilter.GYM)
    val recommendation = snapshot.latestRecommendation
    val latestStrengthLog = selectedLog
    val hasLoggedStrength = latestStrengthLog != null
    fun iconForExercise(name: String): ImageVector = when {
        name.contains("squat", ignoreCase = true) -> Icons.Outlined.SportsGymnastics
        else -> Icons.Outlined.FitnessCenter
    }
    val displayExercises = when {
        latestStrengthLog?.exerciseEntries?.isNotEmpty() == true -> latestStrengthLog.exerciseEntries
            .groupBy { it.exerciseName }
            .map { (exerciseName, groupedEntries) ->
                val totalSets = groupedEntries.sumOf { it.actualSets }
                val totalReps = groupedEntries.sumOf { it.actualSets * it.actualReps }
                val averageReps = if (totalSets > 0) {
                    (totalReps.toDouble() / totalSets).roundToInt()
                } else {
                    0
                }
                val totalKg = groupedEntries.sumOf { entry ->
                    (entry.actualSets * entry.actualReps * entry.actualWeightKg).roundToInt()
                }
                val setEntries = groupedEntries.flatMapIndexed { entryIndex, entry ->
                    List(entry.actualSets.coerceAtLeast(1)) { offset ->
                        GymSetEntryUi(
                            setNumber = entryIndex + offset + 1,
                            repsCompleted = entry.actualReps.toString(),
                            weightKg = entry.actualWeightKg.toString().replace('.', ','),
                            completed = true
                        )
                    }
                }

                GymExerciseUi(
                    name = exerciseName,
                    subtitle = "$totalSets Sets • ${totalKg}kg total",
                    icon = iconForExercise(exerciseName),
                    defaultSets = totalSets,
                    defaultReps = averageReps,
                    defaultVolume = totalKg,
                    setEntries = setEntries,
                    activeSetIndex = (totalSets - 1).coerceAtLeast(0),
                    timerPhase = GymSetTimerPhase.FINISHED,
                    actualSets = totalSets.toString(),
                    actualReps = averageReps.toString(),
                    actualWeightKg = totalKg.toString(),
                    notes = groupedEntries.joinToString("\n") { it.notes }.trim(),
                    completed = true,
                    expanded = false
                )
            }
        else -> exercises.map { it.ensureSetEntries() }
    }
    val anyExpanded = exercises.any { it.expanded }
    val allCompleted = exercises.all { it.completed }
    val targetVolume = displayExercises.sumOf { it.defaultVolume }
    val totalVolume = latestStrengthLog?.loggedStrengthVolume() ?: 0
    val hasCompletedExerciseInput = exercises.any { exercise ->
        exercise.completedSetEntries().isNotEmpty() || exercise.completed
    }
    val primaryActionText = when {
        hasLoggedStrength -> "Bagikan Aktivitas"
        hasCompletedExerciseInput -> "Selesaikan Sesi"
        else -> "Mulai Latihan"
    }
    val primaryAction = when {
        hasLoggedStrength -> onShare
        hasCompletedExerciseInput -> onFinishSession
        else -> onStartWorkout
    }
    val primaryActionIcon = if (hasLoggedStrength && !anyExpanded && !allCompleted) Icons.Outlined.Share else null

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailTopBar(
                title = "Detail Latihan Gym",
                onBack = onBack,
                trailingIcon = null,
                onTrailingClick = onMore
            )
        }
        if (latestStrengthLog != null) {
            item {
                Text(
                    text = latestStrengthLog.activitySubtitle(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
        item {
            StatHeroCard(
                label = "TOTAL VOLUME",
                mainValue = "${(if (hasLoggedStrength) totalVolume else targetVolume).formatThousands()} kg",
                accent = OceanBlue,
                badgeText = if (hasLoggedStrength) "Data latihan tersimpan" else "Target program hari ini",
                onClick = onSummaryClick
            )
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                TinyMetricCard(
                    if (hasLoggedStrength) "Duration" else "Target Durasi",
                    if (hasLoggedStrength) "${latestStrengthLog?.actualDurationMinutes ?: 0} min" else "${recommendation?.targetDurationMinutes ?: session.targetDurationMinutes} min",
                    Icons.Outlined.AccessTime,
                    Modifier.weight(1f),
                    onClick = { onMetricClick("Duration") }
                )
                TinyMetricCard(
                    if (hasLoggedStrength) "Kcal" else "Estimasi Kkal",
                    if (hasLoggedStrength) ((latestStrengthLog?.actualDurationMinutes ?: 0) * 7).toString() else session.aiTargetCalories(recommendation).toString(),
                    Icons.Outlined.LocalFireDepartment,
                    Modifier.weight(1f),
                    onClick = { onMetricClick("Kcal") }
                )
            }
        }
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .clickable(onClick = onHeroClick),
                shape = RoundedCornerShape(20.dp),
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize(),
                        painter = painterResource(id = R.drawable.gym_focus_upper_body),
                        contentDescription = "Gym focus upper body",
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, Color(0x7A0C223A))
                                )
                            )
                    )
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(14.dp),
                        shape = RoundedCornerShape(14.dp),
                        color = SurfaceWhite.copy(alpha = 0.92f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.FitnessCenter,
                                contentDescription = null,
                                tint = OceanBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Focus: ${session.focusArea}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = OceanBlue
                            )
                        }
                    }
                }
            }
        }
        item {
            SectionHeader("Daftar Latihan")
        }
        items(displayExercises.size) { index ->
            val item = displayExercises[index]
            val isReadOnlyExercise = latestStrengthLog != null
            ExpandableExerciseCard(
                exercise = item,
                isReadOnly = isReadOnlyExercise,
                onToggle = {
                    if (!isReadOnlyExercise) {
                        onToggleExercise(index)
                    }
                },
                onRepsChange = { setIndex, value ->
                    if (!isReadOnlyExercise) {
                        onExerciseRepsChange(index, setIndex, value)
                    }
                },
                onWeightChange = { setIndex, value ->
                    if (!isReadOnlyExercise) {
                        onExerciseWeightChange(index, setIndex, value)
                    }
                },
                onPrimaryAction = {
                    if (!isReadOnlyExercise) {
                        onExercisePrimaryAction(index)
                    }
                },
                onTimerTick = {
                    if (!isReadOnlyExercise) {
                        onExerciseTimerTick(index)
                    }
                }
            )
        }
        if (hasLoggedStrength || hasCompletedExerciseInput || displayExercises.none { it.expanded }) {
            item {
            PrimaryGradientButton(
                text = primaryActionText,
                onClick = primaryAction,
                leadingIcon = primaryActionIcon,
                trailingIcon = null
            )
            }
        }
    }
}

@Composable
internal fun CompletionFeedbackScreen(
    exercises: List<GymExerciseUi>,
    onBackToHome: () -> Unit,
    onSeeProgram: () -> Unit,
    onOpenCoach: () -> Unit,
    onTotalClick: (String) -> Unit,
    onOptimizationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalSets = exercises.sumOf {
        if (it.setEntries.isNotEmpty()) {
            it.loggedSetCount()
        } else {
            it.actualSets.toIntOrNull() ?: 0
        }
    }
    val totalReps = exercises.sumOf {
        if (it.setEntries.isNotEmpty()) {
            it.loggedTotalReps()
        } else {
            (it.actualSets.toIntOrNull() ?: 0) * (it.actualReps.toIntOrNull() ?: 0)
        }
    }
    val sessionSummary = exercises.toSessionAiSummary()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailTopBar(
                title = "Selesai Latihan!",
                onBack = onBackToHome,
                trailingIcon = null,
                onTrailingClick = null
            )
        }
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(94.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(AquaBright, AquaTeal))),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.EmojiEvents,
                        contentDescription = null,
                        tint = OceanBlue,
                        modifier = Modifier.size(42.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Kerja Bagus!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = OceanBlue
                )
                Text(
                    text = "Kamu telah menyelesaikan sesi hari ini.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary
                )
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTotalClick("Total Sets") },
                    shape = RoundedCornerShape(22.dp),
                    color = SurfaceWhite,
                    border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "TOTAL SETS",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextSecondary
                        )
                        Text(
                            text = "$totalSets Sets",
                            style = MaterialTheme.typography.headlineMedium,
                            color = OceanBlue
                        )
                    }
                }
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTotalClick("Total Repetisi") },
                    shape = RoundedCornerShape(22.dp),
                    color = SurfaceWhite,
                    border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "TOTAL REPETISI",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextSecondary
                        )
                        Text(
                            text = "$totalReps Reps",
                            style = MaterialTheme.typography.headlineMedium,
                            color = OceanBlue
                        )
                    }
                }
            }
        }
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onOpenCoach),
                shape = RoundedCornerShape(24.dp),
                color = SurfaceWhite,
                border = BorderStroke(1.dp, AquaBright.copy(alpha = 0.55f))
            ) {
                Box(modifier = Modifier.padding(18.dp)) {
                    Surface(
                        modifier = Modifier.align(Alignment.TopEnd),
                        shape = RoundedCornerShape(50.dp),
                        color = AquaBright.copy(alpha = 0.76f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AutoAwesome,
                                contentDescription = null,
                                tint = AquaTeal,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "AI INSIGHT",
                                style = MaterialTheme.typography.labelSmall,
                                color = AquaTeal
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(Color(0xFF0D2037), Color(0xFF102A47), Color(0xFF1E4565))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AutoAwesome,
                                contentDescription = null,
                                tint = AquaBright,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "AI Coaching",
                                style = MaterialTheme.typography.headlineSmall,
                                color = OceanBlue
                            )
                            Text(
                                text = sessionSummary.body,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextPrimary
                            )
                            Text(
                                text = sessionSummary.breakdown,
                                style = MaterialTheme.typography.labelLarge,
                                color = OceanBlue
                            )
                        }
                    }
                }
            }
        }
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onOptimizationClick),
                shape = RoundedCornerShape(22.dp),
                color = OceanBlue
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.AutoAwesome,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Optimasi Program",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                    Text(
                        text = sessionSummary.optimization,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
        item {
            PrimaryGradientButton(text = "Lihat Program Baru", onClick = onSeeProgram)
        }
        item {
            SecondaryOutlineButton(text = "Kembali ke Beranda", onClick = onBackToHome)
        }
    }
}

@Composable
internal fun AiCoachChatScreen(
    messages: List<CoachMessage>,
    isSending: Boolean,
    input: String,
    onInputChange: (String) -> Unit,
    onBack: () -> Unit,
    onTopAction: () -> Unit,
    onSuggestion: (String) -> Unit,
    onApplyProposal: (Int) -> Unit,
    onCancelProposal: (Int) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceSoft)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Kembali",
                    tint = OceanBlue
                )
            }
            Text(
                text = "AI Coach\nConsultation",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.displaySmall.copy(fontSize = 30.sp, lineHeight = 34.sp),
                color = OceanBlue
            )
            Surface(
                shape = CircleShape,
                color = AquaBright.copy(alpha = 0.35f)
            ) {
                Box(
                    modifier = Modifier.size(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AutoAwesome,
                        contentDescription = "AI Coach",
                        tint = OceanBlue
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(20.dp),
            color = SurfaceMuted
        ) {
            Text(
                text = "Today",
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondary
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            items(messages.size) { index ->
                val message = messages[index]
                val showAvatar = message.fromCoach && (index == 0 || !messages[index - 1].fromCoach)
                CoachMessageRow(
                    message = message,
                    showAvatar = showAvatar,
                    onApplyProposal = { onApplyProposal(index) },
                    onCancelProposal = { onCancelProposal(index) }
                )
            }
            if (isSending) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(AquaBright)
                                    .clickable(onClick = onTopAction),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.AutoAwesome,
                                    contentDescription = null,
                                    tint = OceanBlue,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = AquaBright.copy(alpha = 0.55f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    repeat(3) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(OceanBlue.copy(alpha = 0.35f))
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = onInputChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Tanya soal latihan, gym, lari, atau recovery...") },
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                enabled = !isSending,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = SurfaceWhite,
                    unfocusedContainerColor = SurfaceWhite,
                    focusedBorderColor = BorderSoft,
                    unfocusedBorderColor = BorderSoft,
                    cursorColor = OceanBlue
                )
            )
            Surface(
                modifier = Modifier
                    .size(58.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .clickable(enabled = !isSending, onClick = onSend),
                color = if (isSending) OceanBlue.copy(alpha = 0.45f) else OceanBlue
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isSending) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                            contentDescription = "Send",
                            tint = Color.White
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
internal fun StatHeroCard(
    label: String,
    mainValue: String,
    accent: Color,
    badgeText: String? = "New Personal Record!",
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .let { base ->
                if (onClick != null) base.clickable(onClick = onClick) else base
            },
        shape = RoundedCornerShape(24.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft)
    ) {
        Box(modifier = Modifier.padding(18.dp)) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(AquaBright.copy(alpha = 0.14f))
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (label.isNotBlank()) {
                    Text(text = label, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                }
                Text(text = mainValue, style = MaterialTheme.typography.displaySmall, color = accent)
                if (badgeText != null) {
                    Surface(shape = RoundedCornerShape(18.dp), color = Color(0xFFD9FFF5)) {
                        Text(
                            text = badgeText,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = AquaTeal
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun TinyMetricCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = if (onClick != null) modifier.clickable(onClick = onClick) else modifier,
        shape = RoundedCornerShape(18.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = null, tint = AquaTeal, modifier = Modifier.size(18.dp))
            Text(text = title, style = MaterialTheme.typography.bodyMedium, color = TextSecondary, textAlign = TextAlign.Center)
            Text(text = value, style = MaterialTheme.typography.titleMedium, color = TextPrimary, textAlign = TextAlign.Center)
        }
    }
}

@Composable
internal fun SplitRow(
    title: String,
    pace: String,
    progress: Float,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .let { base ->
                if (onClick != null) base.clickable(onClick = onClick) else base
            },
        shape = RoundedCornerShape(18.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(SurfaceTint),
                contentAlignment = Alignment.Center
            ) {
                Text(title.takeLast(1), style = MaterialTheme.typography.labelMedium, color = OceanBlue)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(title, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            Column(horizontalAlignment = Alignment.End) {
                Text(pace, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(BorderSoft.copy(alpha = 0.65f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .height(4.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(AquaBright)
                    )
                }
            }
        }
    }
}

@Composable
internal fun InsightCard(
    title: String,
    body: String,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .let { base ->
                if (onClick != null) base.clickable(onClick = onClick) else base
            },
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF4FBFF),
        border = BorderStroke(1.dp, AquaTeal.copy(alpha = 0.35f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.labelLarge, color = AquaTeal)
            Text(text = body, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
internal fun AiTrackingCard(
    actionLabel: String,
    statOneLabel: String,
    statOneValue: String,
    statTwoLabel: String,
    statTwoValue: String,
    body: String,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .let { base ->
                if (onClick != null) base.clickable(onClick = onClick) else base
            },
        shape = RoundedCornerShape(22.dp),
        color = Color(0xFFF4FBFF),
        border = BorderStroke(1.dp, AquaTeal.copy(alpha = 0.35f))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = AquaTeal,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "AI Tracking",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = AquaBright.copy(alpha = 0.32f)
                ) {
                    Text(
                        text = actionLabel,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = OceanBlue
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                TinyMetricCard(
                    title = statOneLabel,
                    value = statOneValue,
                    icon = Icons.Outlined.AccessTime,
                    modifier = Modifier.weight(1f)
                )
                TinyMetricCard(
                    title = statTwoLabel,
                    value = statTwoValue,
                    icon = Icons.Outlined.BarChart,
                    modifier = Modifier.weight(1f)
                )
            }
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
    }
}

@Composable
internal fun HeroIllustration(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .let { base ->
                if (onClick != null) base.clickable(onClick = onClick) else base
            },
        shape = RoundedCornerShape(24.dp),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .background(Brush.linearGradient(listOf(Color(0xFF132C4B), OceanBlueDark, AquaTeal)))
                .padding(18.dp)
                .height(180.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.22f),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(92.dp)
            )
            Column(
                modifier = Modifier.align(Alignment.BottomStart),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(shape = RoundedCornerShape(16.dp), color = AquaBright.copy(alpha = 0.24f)) {
                    Text(
                        text = "OPTIMASI AI AKTIF",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
                Text(text = title, style = MaterialTheme.typography.titleLarge, color = Color.White)
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.86f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
internal fun ProgramRoutineRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceTint),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.PlayArrow, contentDescription = null, tint = OceanBlue)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text(text = subtitle, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
            }
            Icon(Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = null, tint = TextSecondary)
        }
    }
}

@Composable
internal fun ExpandableExerciseCard(
    exercise: GymExerciseUi,
    isReadOnly: Boolean = false,
    onToggle: () -> Unit,
    onRepsChange: (Int, String) -> Unit,
    onWeightChange: (Int, String) -> Unit,
    onPrimaryAction: () -> Unit,
    onTimerTick: () -> Unit
) {
    val showCompletedState = isReadOnly || exercise.completed
    val completedSetEntries = exercise.completedSetEntries()

    LaunchedEffect(exercise.timerPhase, exercise.workSecondsRemaining, exercise.restSecondsRemaining) {
        when (exercise.timerPhase) {
            GymSetTimerPhase.WORK,
            GymSetTimerPhase.REST -> {
                kotlinx.coroutines.delay(1000)
                onTimerTick()
            }
            else -> Unit
        }
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = if (showCompletedState) Color(0xFFF9FDFF) else SurfaceWhite,
        border = BorderStroke(1.dp, if (showCompletedState) AquaTeal.copy(alpha = 0.35f) else BorderSoft)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .let { base ->
                        if (!showCompletedState) base.clickable(onClick = onToggle) else base
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(if (showCompletedState) Color(0xFFD9FFF5) else SurfaceTint),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(exercise.icon, contentDescription = null, tint = OceanBlue)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = exercise.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                    Text(text = exercise.subtitle, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                }
                Icon(
                    imageVector = when {
                        showCompletedState -> Icons.Outlined.CheckCircle
                        exercise.expanded -> Icons.Outlined.KeyboardArrowUp
                        else -> Icons.Outlined.KeyboardArrowDown
                    },
                    contentDescription = null,
                    tint = if (showCompletedState) AquaTeal else TextSecondary
                )
            }
            if (exercise.expanded || showCompletedState) {
                HorizontalDivider(color = BorderSoft)
                if (showCompletedState) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        TinyMetricCard(
                            "Sets",
                            exercise.actualSets.ifBlank { "-" },
                            Icons.Outlined.CheckCircle,
                            Modifier.weight(1f)
                        )
                        TinyMetricCard(
                            "Total Kg",
                            exercise.actualWeightKg.ifBlank { "-" },
                            Icons.Outlined.FitnessCenter,
                            Modifier.weight(1f)
                        )
                    }
                    if (completedSetEntries.isNotEmpty()) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            completedSetEntries.forEach { setEntry ->
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0xFFF4FBFF),
                                    border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.65f))
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 14.dp, vertical = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Set ${setEntry.setNumber}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = TextPrimary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = "${setEntry.repsCompleted.ifBlank { "-" }} reps • ${setEntry.weightKg.ifBlank { "-" }} kg",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = OceanBlue
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = Color(0xFFF4FBFF),
                        border = BorderStroke(1.dp, AquaTeal.copy(alpha = 0.22f))
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = exercise.timerStatusLabel().orEmpty(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                            Text(
                                text = "Set aktif: ${exercise.activeSetIndex + 1} dari ${exercise.setEntries.size}",
                                style = MaterialTheme.typography.labelLarge,
                                color = OceanBlue
                            )
                        }
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        exercise.setEntries.forEachIndexed { setIndex, setEntry ->
                            val isCurrentSet = setIndex == exercise.activeSetIndex && !setEntry.completed
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = if (setEntry.completed) Color(0xFFF4FBFF) else SurfaceWhite,
                                border = BorderStroke(
                                    1.dp,
                                    when {
                                        setEntry.completed -> AquaTeal.copy(alpha = 0.4f)
                                        isCurrentSet -> OceanBlue.copy(alpha = 0.35f)
                                        else -> BorderSoft
                                    }
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Set ${setEntry.setNumber}",
                                            style = MaterialTheme.typography.titleSmall,
                                            color = TextPrimary
                                        )
                                        Text(
                                            text = when {
                                                setEntry.completed -> "Selesai"
                                                isCurrentSet -> "Aktif"
                                                else -> "Menunggu"
                                            },
                                            style = MaterialTheme.typography.labelLarge,
                                            color = if (setEntry.completed) AquaTeal else OceanBlue
                                        )
                                    }
                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        val isEditable = exercise.isSetEditable(setIndex)
                                        DetailInputField(
                                            label = "Reps selesai",
                                            value = setEntry.repsCompleted,
                                            onValueChange = { onRepsChange(setIndex, it) },
                                            modifier = Modifier.weight(1f),
                                            enabled = isEditable,
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                        )
                                        DetailInputField(
                                            label = "Beban per set (kg)",
                                            value = setEntry.weightKg,
                                            onValueChange = { onWeightChange(setIndex, it) },
                                            modifier = Modifier.weight(1f),
                                            enabled = isEditable,
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    PrimaryGradientButton(
                        text = exercise.primarySetActionLabel(),
                        onClick = onPrimaryAction,
                        enabled = exercise.primarySetActionEnabled()
                    )
                }
            }
        }
    }
}

@Composable
private fun ExerciseAiInsightCard(feedback: ExerciseAiFeedbackUi) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = feedback.background,
        border = BorderStroke(1.dp, feedback.accent.copy(alpha = 0.24f))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = feedback.accent,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = feedback.status.label,
                    style = MaterialTheme.typography.labelLarge,
                    color = feedback.accent
                )
            }
            Text(
                text = feedback.title,
                style = MaterialTheme.typography.titleSmall,
                color = TextPrimary
            )
            Text(
                text = feedback.body,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
    }
}

@Composable
internal fun DetailInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        label = { Text(label) },
        singleLine = singleLine,
        minLines = if (singleLine) 1 else 3,
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SurfaceWhite,
            unfocusedContainerColor = SurfaceWhite,
            disabledContainerColor = SurfaceTint.copy(alpha = 0.35f),
            focusedBorderColor = OceanBlue,
            unfocusedBorderColor = BorderSoft,
            disabledBorderColor = BorderSoft.copy(alpha = 0.7f),
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            disabledTextColor = TextSecondary,
            disabledLabelColor = TextSecondary
        )
    )
}

private fun String.parseFlexibleDecimal(): Double? = trim()
    .replace(',', '.')
    .toDoubleOrNull()

@Composable
internal fun RestTimerCard(
    title: String,
    restSeconds: Int
) {
    var remainingSeconds by rememberSaveable(restSeconds) { mutableIntStateOf(restSeconds.coerceAtLeast(0)) }
    var isRunning by rememberSaveable(restSeconds) { mutableStateOf(false) }

    LaunchedEffect(isRunning, remainingSeconds) {
        if (!isRunning || remainingSeconds <= 0) return@LaunchedEffect
        kotlinx.coroutines.delay(1000)
        remainingSeconds -= 1
        if (remainingSeconds <= 0) {
            isRunning = false
        }
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFF4FBFF),
        border = BorderStroke(1.dp, AquaTeal.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${remainingSeconds}s",
                    style = MaterialTheme.typography.headlineSmall,
                    color = OceanBlue
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SecondaryOutlineButton(
                        text = if (isRunning) "Pause" else "Mulai",
                        onClick = {
                            if (remainingSeconds <= 0) {
                                remainingSeconds = restSeconds.coerceAtLeast(0)
                            }
                            isRunning = !isRunning
                        }
                    )
                    SecondaryOutlineButton(
                        text = "Reset",
                        onClick = {
                            isRunning = false
                            remainingSeconds = restSeconds.coerceAtLeast(0)
                        }
                    )
                }
            }
        }
    }
}

@Composable
internal fun CoachMessageRow(
    message: CoachMessage,
    showAvatar: Boolean,
    onApplyProposal: () -> Unit = {},
    onCancelProposal: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.fromCoach) Arrangement.Start else Arrangement.End
    ) {
        if (message.fromCoach) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                if (showAvatar) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(AquaBright)
                            .border(1.dp, AquaTeal.copy(alpha = 0.35f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AutoAwesome,
                            contentDescription = null,
                            tint = OceanBlue,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(40.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.fillMaxWidth(0.84f)) {
                    Surface(
                        shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomEnd = 18.dp, bottomStart = 6.dp),
                        color = AquaBright.copy(alpha = 0.92f)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = message.text,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextPrimary
                            )
                            message.programProposal?.let { proposal ->
                                when (proposal.status) {
                                    CoachProgramProposalStatus.PENDING -> {
                                        Surface(
                                            shape = RoundedCornerShape(16.dp),
                                            color = SurfaceWhite,
                                            border = BorderStroke(1.dp, AquaTeal.copy(alpha = 0.28f))
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(14.dp),
                                                verticalArrangement = Arrangement.spacedBy(10.dp)
                                            ) {
                                                Text(
                                                    text = proposal.summaryLabel,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    color = OceanBlue
                                                )
                                                Row(
                                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                                ) {
                                                    Button(
                                                        onClick = onApplyProposal,
                                                        shape = RoundedCornerShape(14.dp),
                                                        colors = ButtonDefaults.buttonColors(
                                                            containerColor = OceanBlue,
                                                            contentColor = Color.White
                                                        )
                                                    ) {
                                                        Text("Terapkan")
                                                    }
                                                    TextButton(onClick = onCancelProposal) {
                                                        Text("Batal")
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    CoachProgramProposalStatus.APPLIED -> {
                                        Text(
                                            text = "Program baru sudah diterapkan.",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = AquaTeal
                                        )
                                    }

                                    CoachProgramProposalStatus.CANCELLED -> {
                                        Text(
                                            text = "Perubahan program dibatalkan.",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = TextSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Text(
                        text = message.timeLabel,
                        modifier = Modifier.padding(top = 6.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(0.82f),
                horizontalAlignment = Alignment.End
            ) {
                Surface(
                    shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomEnd = 6.dp, bottomStart = 18.dp),
                    color = OceanBlue
                ) {
                    Text(
                        text = message.text,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
                Text(
                    text = message.timeLabel,
                    modifier = Modifier.padding(top = 6.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
internal fun ChatBubble(message: CoachMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.fromCoach) Arrangement.Start else Arrangement.End
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.82f),
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = if (message.fromCoach) 6.dp else 20.dp,
                bottomEnd = if (message.fromCoach) 20.dp else 6.dp
            ),
            color = if (message.fromCoach) AquaBright.copy(alpha = 0.22f) else OceanBlue
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (message.fromCoach) MaterialTheme.colorScheme.onBackground else Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message.timeLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (message.fromCoach) TextSecondary else Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}

