package com.athallah.hybridfit.ui.main

import androidx.compose.foundation.BorderStroke
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.FitnessCenter
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onGoogleLoginRequested: () -> Unit
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var currentScene by rememberSaveable { mutableStateOf(AppScene.REGISTER.name) }
    var hasResolvedStartScene by rememberSaveable { mutableStateOf(false) }
    var selectedTab by rememberSaveable { mutableStateOf(MainTab.HOME.name) }
    var selectedProgramFilter by rememberSaveable { mutableStateOf(ProgramFilter.GYM.name) }
    var selectedStatsRange by rememberSaveable { mutableStateOf(StatsRange.WEEK.name) }
    var detailReturnTab by rememberSaveable { mutableStateOf(MainTab.HOME.name) }
    var selectedActivityLogId by rememberSaveable { mutableStateOf<Long?>(null) }
    var chatReturnScene by rememberSaveable { mutableStateOf(AppScene.COMPLETION.name) }
    var utilityReturnScene by rememberSaveable { mutableStateOf(AppScene.REGISTER.name) }

    var registerName by rememberSaveable { mutableStateOf("") }
    var registerEmail by rememberSaveable { mutableStateOf("") }
    var registerPassword by rememberSaveable { mutableStateOf("") }
    var registerConfirmPassword by rememberSaveable { mutableStateOf("") }
    var showRegisterPassword by rememberSaveable { mutableStateOf(false) }
    var showRegisterConfirmPassword by rememberSaveable { mutableStateOf(false) }

    var loginEmail by rememberSaveable { mutableStateOf("") }
    var loginPassword by rememberSaveable { mutableStateOf("") }
    var showLoginPassword by rememberSaveable { mutableStateOf(false) }

    var selectedGoal by rememberSaveable { mutableStateOf(GoalOption.HYBRID.name) }
    var selectedExperience by rememberSaveable { mutableStateOf(ExperienceOption.BEGINNER.name) }
    var selectedFrequency by rememberSaveable { mutableStateOf(FrequencyOption.FOUR.name) }
    var selectedHealth by rememberSaveable { mutableStateOf(HealthOption.HEALTHY.name) }
    var healthNotes by rememberSaveable { mutableStateOf("") }
    var surveyAge by rememberSaveable { mutableStateOf("") }
    var surveyWeightKg by rememberSaveable { mutableStateOf("") }
    var surveyHeightCm by rememberSaveable { mutableStateOf("") }
    var accountDisplayName by rememberSaveable { mutableStateOf("") }
    var accountEmail by rememberSaveable { mutableStateOf("") }
    var accountNotes by rememberSaveable {
        mutableStateOf("Menjaga konsistensi latihan hybrid sambil meningkatkan kualitas pemulihan.")
    }
    var weeklyStepGoal by rememberSaveable { mutableStateOf("8000") }
    var weeklySleepGoal by rememberSaveable { mutableStateOf("7.5") }
    val appSettingsPreferences = remember {
        context.getSharedPreferences("hybridfit_app_settings", Context.MODE_PRIVATE)
    }
    var reminderEnabled by rememberSaveable {
        mutableStateOf(appSettingsPreferences.getBoolean("reminder_enabled", true))
    }
    var smartRecommendationEnabled by rememberSaveable {
        mutableStateOf(appSettingsPreferences.getBoolean("smart_recommendation_enabled", true))
    }
    var sleepInsightEnabled by rememberSaveable {
        mutableStateOf(appSettingsPreferences.getBoolean("sleep_insight_enabled", true))
    }
    var dataSaverEnabled by rememberSaveable {
        mutableStateOf(appSettingsPreferences.getBoolean("data_saver_enabled", false))
    }
    var forgotPasswordEmail by rememberSaveable { mutableStateOf("") }
    var supportMessage by rememberSaveable { mutableStateOf("") }
    var plannerEditor by remember {
        mutableStateOf(
            PlannerEditorUi(
                durationMinutes = "35",
                restSeconds = "60"
            )
        )
    }
    var recoveryDraft by remember { mutableStateOf(RecoveryDraftUi()) }
    var runLogDraft by remember { mutableStateOf(RunLogDraftUi()) }
    var strengthDurationMinutes by remember { mutableStateOf("45") }
    var strengthEffortScore by remember { mutableStateOf("7") }
    var strengthSessionNotes by remember { mutableStateOf("") }
    var editingStrengthLogId by rememberSaveable { mutableStateOf<Long?>(null) }

    fun defaultGymExercises() =
        buildStarterGymExercises(
            profile = uiState.snapshot?.profile,
            fallbackGoal = GoalOption.valueOf(selectedGoal),
            fallbackExperience = ExperienceOption.valueOf(selectedExperience),
            fallbackAge = surveyAge.toIntOrNull(),
            fallbackWeightKg = surveyWeightKg.trim().replace(',', '.').toDoubleOrNull(),
            fallbackHeightCm = surveyHeightCm.toIntOrNull()
        ).map { it.ensureSetEntries() }

    val gymExercises = remember {
        mutableStateListOf<GymExerciseUi>().apply {
            addAll(defaultGymExercises())
        }
    }

    val coachMessages = remember {
        mutableStateListOf(
            CoachMessage(
                text = "Halo, saya AI Coach HybridFit. Saya khusus membantu topik olahraga dan kebugaran seperti program latihan, gym, lari, recovery, progres, dan target latihan Anda.",
                fromCoach = true,
                timeLabel = "Sekarang"
            )
        )
    }
    var coachInput by rememberSaveable { mutableStateOf("") }
    var featureInfo by remember {
        mutableStateOf(
            FeatureInfoUi(
                title = "Hybrid Fit",
                subtitle = "Insight fitur",
                body = "",
                highlights = emptyList()
            )
        )
    }
    val notifications = remember {
        mutableStateListOf(
            NotificationItemUi(
                title = "Akun Siap Digunakan",
                subtitle = "Lanjutkan survei dan susun program pertama.",
                body = "Setelah login, Anda bisa menentukan tujuan, frekuensi latihan, dan status kesehatan sebelum Hybrid Fit menyusun program awal.",
                timeLabel = "Baru saja",
                icon = Icons.Outlined.Schedule,
                tint = OceanBlue,
                background = SurfaceTint
            ),
            NotificationItemUi(
                title = "Recovery Insight",
                subtitle = "Skor recovery akan aktif setelah ada data latihan.",
                body = "Begitu aktivitas, durasi latihan, dan feedback mulai tercatat, sistem akan menampilkan insight pemulihan yang lebih personal.",
                timeLabel = "08:15",
                icon = Icons.Outlined.CheckCircle,
                tint = AquaTeal,
                background = AquaBright.copy(alpha = 0.24f)
            ),
            NotificationItemUi(
                title = "Feedback Diterima",
                subtitle = "Sistem akan menyesuaikan rekomendasi.",
                body = "Setelah Anda mengirim feedback sesi latihan, Hybrid Fit dapat memakai masukan tersebut untuk menyesuaikan program berikutnya.",
                timeLabel = "Kemarin",
                icon = Icons.Outlined.TipsAndUpdates,
                tint = Color(0xFF2F6BD6),
                background = Color(0xFFEAF1FF)
            )
        )
    }

    fun resetStrengthDrafts() {
        gymExercises.clear()
        gymExercises.addAll(defaultGymExercises())
        strengthDurationMinutes = "45"
        strengthEffortScore = "7"
        strengthSessionNotes = ""
        editingStrengthLogId = null
    }

    fun resetPlannerDraft() {
        plannerEditor = PlannerEditorUi(
            category = WorkoutCategory.STRENGTH,
            programVariant = "Full Body",
            durationMinutes = "35",
            restSeconds = "60"
        )
    }

    fun resetRecoveryDraft() {
        recoveryDraft = RecoveryDraftUi()
    }

    fun resetRunDraft() {
        runLogDraft = RunLogDraftUi()
    }

    fun showMessage(message: String) {
        scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    fun currentTimeLabel(): String = LocalTime.now()
        .format(DateTimeFormatter.ofPattern("HH:mm"))

    fun updateCoachProposalStatus(
        index: Int,
        status: CoachProgramProposalStatus
    ) {
        val currentMessage = coachMessages.getOrNull(index) ?: return
        val proposal = currentMessage.programProposal ?: return
        coachMessages[index] = currentMessage.copy(
            programProposal = proposal.copy(status = status)
        )
    }

    fun mapCoachProposal(
        goal: String,
        preferredFocus: String,
        workoutDaysPerWeek: Int,
        sessionDurationMinutes: Int,
        programLabel: String,
        summaryLabel: String
    ): CoachProgramProposalUi? {
        val resolvedGoal = runCatching { FitnessGoal.valueOf(goal) }.getOrNull() ?: return null
        val resolvedFocus = runCatching { WorkoutCategory.valueOf(preferredFocus) }.getOrNull() ?: return null
        if (workoutDaysPerWeek !in 1..7 || sessionDurationMinutes !in 10..240) return null
        if (programLabel.isBlank() || summaryLabel.isBlank()) return null

        return CoachProgramProposalUi(
            goal = resolvedGoal,
            preferredFocus = resolvedFocus,
            workoutDaysPerWeek = workoutDaysPerWeek,
            sessionDurationMinutes = sessionDurationMinutes,
            programLabel = programLabel,
            summaryLabel = summaryLabel
        )
    }

    fun applyCoachProgramProposal(index: Int) {
        val proposal = coachMessages.getOrNull(index)?.programProposal ?: return
        if (proposal.status != CoachProgramProposalStatus.PENDING) return

        viewModel.applyCoachProgramProposal(
            goal = proposal.goal,
            preferredFocus = proposal.preferredFocus,
            workoutDaysPerWeek = proposal.workoutDaysPerWeek,
            sessionDurationMinutes = proposal.sessionDurationMinutes,
            requestSummary = proposal.summaryLabel,
            onSuccess = {
                updateCoachProposalStatus(index, CoachProgramProposalStatus.APPLIED)
                selectedProgramFilter = when (proposal.preferredFocus) {
                    WorkoutCategory.CARDIO -> ProgramFilter.RUN.name
                    else -> ProgramFilter.GYM.name
                }
                selectedTab = MainTab.PROGRAMS.name
                currentScene = AppScene.DASHBOARD.name
                showMessage("Program ${proposal.programLabel} berhasil diterapkan.")
            },
            onFailure = { message ->
                showMessage(message)
            }
        )
    }

    fun cancelCoachProgramProposal(index: Int) {
        val proposal = coachMessages.getOrNull(index)?.programProposal ?: return
        if (proposal.status != CoachProgramProposalStatus.PENDING) return
        updateCoachProposalStatus(index, CoachProgramProposalStatus.CANCELLED)
        showMessage("Perubahan program dibatalkan.")
    }

    fun parseFlexibleDecimal(value: String): Double? = value
        .trim()
        .replace(',', '.')
        .toDoubleOrNull()

    fun parseRoutineCountdownSeconds(subtitle: String): Int {
        val normalized = subtitle.lowercase(Locale.getDefault())
        val minutes = Regex("(\\d+)\\s*menit").find(normalized)
            ?.groupValues
            ?.getOrNull(1)
            ?.toIntOrNull()
            ?: 0
        val seconds = Regex("(\\d+)\\s*(detik|dtk)").find(normalized)
            ?.groupValues
            ?.getOrNull(1)
            ?.toIntOrNull()
            ?: 0
        return (minutes * 60) + seconds
    }

    fun buildRoutineSteps(title: String, totalSeconds: Int): List<RoutineStepUi> {
        val catalog = when {
            title.contains("pemanasan", ignoreCase = true) -> listOf(
                "March in Place" to "Bangun ritme napas dan langkah.",
                "Arm Circle" to "Longgarkan bahu dan lengan.",
                "Torso Twist" to "Aktifkan core dan pinggang.",
                "Leg Swing Kanan" to "Buka fleksor pinggul kanan.",
                "Leg Swing Kiri" to "Buka fleksor pinggul kiri.",
                "High Knees" to "Siapkan dorongan lutut.",
                "Butt Kicks" to "Aktifkan paha belakang.",
                "Walking Lunge" to "Buka glute dan quadriceps.",
                "Ankle Bounce" to "Aktifkan betis dan pergelangan kaki.",
                "Hip Opener" to "Siapkan langkah lebih panjang."
            )
            title.contains("pendinginan", ignoreCase = true) || title.contains("regangan", ignoreCase = true) -> listOf(
                "Slow Walk" to "Turunkan denyut nadi secara perlahan.",
                "Hamstring Stretch" to "Regangkan paha belakang.",
                "Quad Stretch Kanan" to "Buka paha depan kanan.",
                "Quad Stretch Kiri" to "Buka paha depan kiri.",
                "Calf Stretch Kanan" to "Lepaskan ketegangan betis kanan.",
                "Calf Stretch Kiri" to "Lepaskan ketegangan betis kiri.",
                "Figure Four Stretch" to "Rilekskan glute dan pinggul.",
                "Child Pose" to "Longgarkan punggung bawah.",
                "Shoulder Stretch" to "Turunkan tegang bahu dan dada.",
                "Deep Breathing" to "Kembalikan ritme napas."
            )
            else -> listOf(
                "Fokus Teknik" to "Jaga postur dan ritme gerak.",
                "Kontrol Napas" to "Tarik dan buang napas teratur.",
                "Stabilitas" to "Pastikan transisi gerakan rapi."
            )
        }

        val stepCount = (totalSeconds / 30).coerceAtLeast(1)
        return List(stepCount) { index ->
            val source = catalog[index % catalog.size]
            RoutineStepUi(
                title = source.first,
                subtitle = source.second,
                durationSeconds = 30
            )
        }
    }

    fun buildIntervalSegments(totalSeconds: Int): List<RunSegmentUi> {
        val segmentCount = (totalSeconds / (5 * 60)).coerceIn(2, 8)
        return List(segmentCount) { index ->
            RunSegmentUi(
                segmentNumber = index + 1,
                distanceMeters = "400"
            )
        }
    }

    fun formatWeightInput(value: Double): String {
        val normalized = if (value % 1.0 == 0.0) {
            value.roundToInt().toString()
        } else {
            value.toString()
        }
        return normalized.replace('.', ',')
    }

    fun mapStrengthEntriesToExercises(
        entries: List<com.athallah.hybridfit.domain.model.StrengthExerciseEntry>,
        defaultByName: Map<String, GymExerciseUi>
    ): List<GymExerciseUi> {
        return entries
            .groupBy { it.exerciseName }
            .map { (exerciseName, groupedEntries) ->
                val totalSets = groupedEntries.sumOf { it.actualSets }
                val totalReps = groupedEntries.sumOf { it.actualSets * it.actualReps }
                val averageReps = if (totalSets > 0) {
                    (totalReps.toDouble() / totalSets).roundToInt()
                } else {
                    0
                }
                val totalVolume = groupedEntries.sumOf { entry ->
                    (entry.actualSets * entry.actualReps * entry.actualWeightKg).roundToInt()
                }
                val fallback = defaultByName[exerciseName] ?: GymExerciseUi(
                    name = exerciseName,
                    subtitle = "Latihan tersimpan",
                    icon = Icons.Outlined.FitnessCenter,
                    defaultSets = totalSets.coerceAtLeast(1),
                    defaultReps = averageReps.coerceAtLeast(1),
                    defaultVolume = totalVolume.coerceAtLeast(0)
                )
                var setCounter = 1
                val setEntries = groupedEntries.flatMap { entry ->
                    List(entry.actualSets.coerceAtLeast(1)) {
                        GymSetEntryUi(
                            setNumber = setCounter++,
                            repsCompleted = entry.actualReps.toString(),
                            weightKg = formatWeightInput(entry.actualWeightKg),
                            completed = true
                        )
                    }
                }

                fallback.copy(
                    subtitle = "${setEntries.size} Sets • ${totalVolume}kg total",
                    setEntries = setEntries,
                    activeSetIndex = (setEntries.size - 1).coerceAtLeast(0),
                    timerPhase = GymSetTimerPhase.FINISHED,
                    actualSets = setEntries.size.toString(),
                    actualReps = averageReps.toString(),
                    actualWeightKg = totalVolume.toString(),
                    notes = groupedEntries.joinToString("\n") { it.notes }.trim(),
                    expanded = true,
                    completed = true
                ).withDerivedMetrics()
            }
    }

    fun updateGymExercise(index: Int, transform: (GymExerciseUi) -> GymExerciseUi) {
        val current = gymExercises[index].ensureSetEntries()
        gymExercises[index] = transform(current)
            .ensureSetEntries()
            .withDerivedMetrics()
    }

    fun openDashboard(tab: MainTab = MainTab.HOME) {
        selectedTab = tab.name
        currentScene = AppScene.DASHBOARD.name
    }

    fun openDetail(scene: AppScene, returnTab: MainTab) {
        detailReturnTab = returnTab.name
        currentScene = scene.name
    }

    fun prepareRunLogEdit(log: com.athallah.hybridfit.domain.model.WorkoutLog) {
        selectedActivityLogId = log.id
        runLogDraft = RunLogDraftUi(
            logId = log.id,
            distanceKm = log.actualDistanceKm?.toString()?.replace('.', ',').orEmpty(),
            durationMinutes = log.actualDurationMinutes.toString(),
            durationSeconds = "",
            effortScore = log.exertionScore.toString(),
            notes = log.notes
        )
        selectedProgramFilter = ProgramFilter.RUN.name
        openDetail(AppScene.AI_DETAIL, MainTab.HOME)
    }

    fun prepareStrengthLogEdit(log: com.athallah.hybridfit.domain.model.WorkoutLog) {
        selectedActivityLogId = log.id
        resetStrengthDrafts()
        editingStrengthLogId = log.id
        strengthDurationMinutes = log.actualDurationMinutes.toString()
        strengthEffortScore = log.exertionScore.toString()
        strengthSessionNotes = log.notes

        val defaultByName = defaultGymExercises().associateBy { it.name }
        val merged = if (log.exerciseEntries.isNotEmpty()) {
            mapStrengthEntriesToExercises(log.exerciseEntries, defaultByName)
        } else {
            defaultGymExercises()
        }

        gymExercises.clear()
        gymExercises.addAll(merged)
        openDetail(AppScene.GYM_DETAIL, MainTab.HOME)
    }

    fun deleteSelectedActivity(log: WorkoutLog) {
        viewModel.deleteWorkoutLog(log.id)
        if (selectedActivityLogId == log.id) {
            selectedActivityLogId = null
        }
        if (runLogDraft.logId == log.id) {
            resetRunDraft()
        }
        if (editingStrengthLogId == log.id) {
            resetStrengthDrafts()
        }
        openDashboard(MainTab.HOME)
    }

    fun completeWorkoutAndShowFeedback() {
        viewModel.simulateWorkoutCompletion()
        currentScene = AppScene.COMPLETION.name
    }

    fun openCoachChat(returnScene: AppScene) {
        chatReturnScene = returnScene.name
        currentScene = AppScene.AI_CHAT.name
    }

    fun submitCoachPrompt(rawText: String) {
        val sentText = rawText.trim()
        if (sentText.isBlank()) {
            showMessage("Tulis pesan terlebih dahulu.")
            return
        }

        coachMessages.add(
            CoachMessage(
                text = sentText,
                fromCoach = false,
                timeLabel = currentTimeLabel()
            )
        )
        coachInput = ""

        viewModel.sendCoachChatMessage(
            messages = coachMessages.toList(),
            onSuccess = { result ->
                val proposal = result.proposal?.let {
                    mapCoachProposal(
                        goal = it.goal,
                        preferredFocus = it.preferredFocus,
                        workoutDaysPerWeek = it.workoutDaysPerWeek,
                        sessionDurationMinutes = it.sessionDurationMinutes,
                        programLabel = it.programLabel,
                        summaryLabel = it.summaryLabel
                    )
                }
                coachMessages.add(
                    CoachMessage(
                        text = result.reply,
                        fromCoach = true,
                        timeLabel = currentTimeLabel(),
                        programProposal = proposal
                    )
                )
            },
            onFailure = { message ->
                showMessage(message)
            }
        )
    }

    val displayName = accountDisplayName.takeIf { it.isNotBlank() }
        ?: uiState.currentUser?.fullName?.takeIf { it.isNotBlank() }
        ?: uiState.snapshot?.profile?.fullName?.takeIf { it.isNotBlank() }
        ?: registerName.takeIf { it.isNotBlank() }
        ?: "Pengguna Baru"
    val resolvedEmail = accountEmail.takeIf { it.isNotBlank() }
        ?: uiState.currentUser?.email?.takeIf { it.isNotBlank() }
        ?: loginEmail.takeIf { it.isNotBlank() }
        ?: registerEmail.takeIf { it.isNotBlank() }
        ?: ""
    val avatarUrl = uiState.currentUser?.avatarUrl
    val displayGoal = GoalOption.valueOf(selectedGoal)
    val displayExperience = ExperienceOption.valueOf(selectedExperience)
    val displayHealth = HealthOption.valueOf(selectedHealth)
    val isRegisterInProgress =
        uiState.isCredentialAuthInProgress && uiState.lastAuthAction == AuthAction.REGISTER
    val isCredentialLoginInProgress =
        uiState.isCredentialAuthInProgress && uiState.lastAuthAction == AuthAction.LOGIN

    fun resolveSurveyBodyMetrics(): Triple<Int, Double, Int>? {
        val storedProfile = uiState.snapshot?.profile
        val canUseSafeFallback = uiState.hasCompletedIntroSurvey && storedProfile == null

        val age = storedProfile?.age?.takeIf { it > 0 }
            ?: surveyAge.toIntOrNull()
            ?: if (canUseSafeFallback) 22 else null
        if (age == null || age !in 10..100) {
            showMessage("Masukkan umur yang valid antara 10 sampai 100 tahun.")
            return null
        }

        val weightKg = storedProfile?.weightKg?.takeIf { it > 0.0 }
            ?: parseFlexibleDecimal(surveyWeightKg)
            ?: if (canUseSafeFallback) 60.0 else null
        if (weightKg == null || weightKg !in 30.0..250.0) {
            showMessage("Masukkan berat badan yang valid antara 30 sampai 250 kg.")
            return null
        }

        val heightCm = storedProfile?.heightCm?.takeIf { it > 0 }
            ?: surveyHeightCm.toIntOrNull()
            ?: if (canUseSafeFallback) 170 else null
        if (heightCm == null || heightCm !in 120..230) {
            showMessage("Masukkan tinggi badan yang valid antara 120 sampai 230 cm.")
            return null
        }

        return Triple(age, weightKg, heightCm)
    }

    fun requestStarterProgram() {
        val (age, weightKg, heightCm) = resolveSurveyBodyMetrics() ?: return
        viewModel.createStarterProgram(
            fullName = displayName,
            age = age,
            weightKg = weightKg,
            heightCm = heightCm,
            goal = displayGoal.mappedGoal,
            fitnessLevel = displayExperience.mappedLevel,
            preferredFocus = when (displayGoal) {
                GoalOption.RUNNING,
                GoalOption.WEIGHT_LOSS -> WorkoutCategory.CARDIO
                GoalOption.BUILD_MUSCLE -> WorkoutCategory.STRENGTH
                GoalOption.HYBRID -> WorkoutCategory.STRENGTH
            },
            workoutDaysPerWeek = FrequencyOption.valueOf(selectedFrequency).days,
            sessionDurationMinutes = when (displayExperience) {
                ExperienceOption.BEGINNER -> 30
                ExperienceOption.INTERMEDIATE -> 40
                ExperienceOption.ADVANCED -> 50
            },
            experienceNotes = buildString {
                append(displayExperience.subtitle)
                append(" | Umur: $age tahun")
                append(" | Berat: ${"%.1f".format(Locale.US, weightKg)} kg")
                append(" | Tinggi: $heightCm cm")
                if (healthNotes.isNotBlank()) {
                    append(" ")
                    append(healthNotes.trim())
                }
            }
        )
    }

    fun completeSurveyAndCreateProgram() {
        selectedProgramFilter = when (displayGoal) {
            GoalOption.BUILD_MUSCLE -> ProgramFilter.GYM.name
            GoalOption.WEIGHT_LOSS -> ProgramFilter.RUN.name
            GoalOption.RUNNING -> ProgramFilter.RUN.name
            GoalOption.HYBRID -> ProgramFilter.GYM.name
        }

        if (!uiState.isAuthenticated) {
            currentScene = AppScene.REGISTER.name
            showMessage("Daftar akun terlebih dahulu, lalu lanjutkan mengisi survey.")
            return
        }

        requestStarterProgram()
    }

    fun openUtility(scene: AppScene) {
        utilityReturnScene = currentScene
        currentScene = scene.name
    }

    fun returnFromUtility() {
        currentScene = utilityReturnScene
    }

    fun openFeatureInfoScreen(info: FeatureInfoUi) {
        featureInfo = info
        openUtility(AppScene.FEATURE_INFO)
    }

    fun openFeatureInfo(
        title: String,
        subtitle: String,
        body: String,
        highlights: List<String>,
        icon: ImageVector = Icons.Outlined.AutoAwesome,
        accent: Color = OceanBlue,
        showHeroCard: Boolean = true,
        primaryActionLabel: String = "Mengerti",
        countdownSeconds: Int = 0,
        routineSteps: List<RoutineStepUi> = emptyList(),
        intervalSegments: List<RunSegmentUi> = emptyList()
    ) {
        openFeatureInfoScreen(
            FeatureInfoUi(
                title = title,
                subtitle = subtitle,
                body = body,
                highlights = highlights,
                icon = icon,
                accent = accent,
                showHeroCard = showHeroCard,
                primaryActionLabel = primaryActionLabel,
                countdownSeconds = countdownSeconds,
                routineSteps = routineSteps,
                intervalSegments = intervalSegments
            )
        )
    }

    fun openQuickActions() = openUtility(AppScene.QUICK_ACTIONS)

    fun openNotifications() = openUtility(AppScene.NOTIFICATIONS)

    fun openAccountScreen() {
        accountDisplayName = displayName
        accountEmail = resolvedEmail
        openUtility(AppScene.ACCOUNT)
    }

    fun inferProgramVariant(session: WorkoutSession): String {
        val source = "${session.title} ${session.focusArea}".lowercase()
        return when (session.category) {
            WorkoutCategory.STRENGTH -> when {
                source.contains("upper") -> "Upper Body"
                source.contains("lower") -> "Lower Body"
                else -> "Full Body"
            }
            WorkoutCategory.CARDIO -> when {
                source.contains("interval") -> "Interval Run"
                source.contains("tempo") -> "Tempo Run"
                source.contains("long") -> "Long Run"
                else -> "Easy Run"
            }
            WorkoutCategory.MOBILITY -> "Mobility"
            WorkoutCategory.RECOVERY -> "Recovery"
        }
    }

    fun openWeeklyTargetScreen() = openUtility(AppScene.WEEKLY_TARGET)

    fun openAppSettingsScreen() = openUtility(AppScene.APP_SETTINGS)

    fun openAiHub() = openUtility(AppScene.AI_HUB)

    fun openHelpCenter() = openUtility(AppScene.HELP_CENTER)

    fun openAchievementsScreen() = openUtility(AppScene.ACHIEVEMENTS)

    fun sharePlainText(title: String, text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(intent, title))
    }

    fun openForgotPasswordScreen() {
        forgotPasswordEmail = loginEmail.ifBlank { resolvedEmail }
        openUtility(AppScene.FORGOT_PASSWORD)
    }

    val showBottomBar = currentScene == AppScene.DASHBOARD.name && uiState.snapshot != null

    LaunchedEffect(uiState.authEventId) {
        val message = uiState.authStatusMessage ?: return@LaunchedEffect
        snackbarHostState.showSnackbar(message)
        when (uiState.lastAuthAction) {
            AuthAction.REGISTER -> {
                if (uiState.isAuthenticated) {
                    currentScene = AppScene.SURVEY_GOAL.name
                } else {
                    currentScene = AppScene.REGISTER.name
                }
            }

            AuthAction.LOGIN,
            AuthAction.GOOGLE_LOGIN,
            AuthAction.APPLE_LOGIN -> {
                if (uiState.isAuthenticated) {
                    if (uiState.hasCompletedIntroSurvey) {
                        openDashboard(MainTab.HOME)
                    } else {
                        currentScene = AppScene.SURVEY_GOAL.name
                    }
                } else {
                    currentScene = AppScene.LOGIN.name
                    selectedTab = MainTab.HOME.name
                }
            }

            AuthAction.LOGOUT -> {
                if (!uiState.isAuthenticated) {
                    currentScene = AppScene.LOGIN.name
                    selectedProgramFilter = ProgramFilter.GYM.name
                    selectedStatsRange = StatsRange.WEEK.name
                    selectedTab = MainTab.HOME.name
                }
            }

            AuthAction.NONE -> {
                if (!uiState.isAuthenticated) {
                    currentScene = AppScene.REGISTER.name
                    selectedTab = MainTab.HOME.name
                }
            }
        }
        viewModel.consumeAuthStatusMessage()
    }

    LaunchedEffect(uiState.infoEventId) {
        val message = uiState.infoMessage ?: return@LaunchedEffect
        snackbarHostState.showSnackbar(message)
        viewModel.consumeInfoMessage()
    }

    LaunchedEffect(uiState.isLoading, uiState.isAuthenticated, uiState.hasCompletedIntroSurvey) {
        if (uiState.isLoading || hasResolvedStartScene) return@LaunchedEffect

        currentScene = when {
            uiState.isAuthenticated && uiState.hasCompletedIntroSurvey -> AppScene.DASHBOARD.name
            uiState.isAuthenticated -> AppScene.SURVEY_GOAL.name
            else -> AppScene.REGISTER.name
        }
        selectedTab = MainTab.HOME.name
        hasResolvedStartScene = true
    }

    LaunchedEffect(uiState.isAuthenticated, uiState.lastAuthAction, uiState.hasCompletedIntroSurvey) {
        if (
            uiState.isAuthenticated &&
            (
                currentScene == AppScene.WELCOME.name ||
                    currentScene == AppScene.LOGIN.name ||
                currentScene == AppScene.REGISTER.name ||
                    currentScene == AppScene.SURVEY_GOAL.name ||
                    currentScene == AppScene.SURVEY_LEVEL.name ||
                    currentScene == AppScene.SURVEY_FREQUENCY.name ||
                    currentScene == AppScene.SURVEY_HEALTH.name ||
                    currentScene == AppScene.SURVEY_BODY.name
                )
        ) {
            if (uiState.hasCompletedIntroSurvey) {
                openDashboard(MainTab.HOME)
            } else {
                currentScene = AppScene.SURVEY_GOAL.name
            }
        }
    }

    LaunchedEffect(uiState.isAuthenticated, uiState.hasCompletedIntroSurvey, currentScene) {
        if (currentScene == AppScene.WELCOME.name) {
            currentScene = when {
                uiState.isAuthenticated && uiState.hasCompletedIntroSurvey -> AppScene.DASHBOARD.name
                uiState.isAuthenticated -> AppScene.SURVEY_GOAL.name
                else -> AppScene.REGISTER.name
            }
        }
    }

    LaunchedEffect(uiState.currentUser?.id) {
        selectedActivityLogId = null
        selectedGoal = GoalOption.HYBRID.name
        selectedExperience = ExperienceOption.BEGINNER.name
        selectedFrequency = FrequencyOption.FOUR.name
        selectedHealth = HealthOption.HEALTHY.name
        healthNotes = ""
        surveyAge = ""
        surveyWeightKg = ""
        surveyHeightCm = ""
        resetStrengthDrafts()
        resetPlannerDraft()
        resetRecoveryDraft()
        resetRunDraft()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    selected = MainTab.valueOf(selectedTab),
                    onSelected = { selectedTab = it.name }
                )
            }
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            LoadingScreen(innerPadding)
            return@Scaffold
        }

        val snapshot = uiState.snapshot ?: return@Scaffold
        val availableProgramFilters = snapshot.availableProgramFilters()
        val resolvedProgramFilter = availableProgramFilters.firstOrNull { filter ->
            filter.name == selectedProgramFilter
        } ?: availableProgramFilters.firstOrNull() ?: ProgramFilter.GYM
        val selectedActivityLog = snapshot.recentLogs.firstOrNull { it.id == selectedActivityLogId }
        val selectedRunLog = selectedActivityLog?.takeIf { it.actualDistanceKm != null }
        val selectedStrengthLog = selectedActivityLog?.takeIf { it.actualDistanceKm == null }

        fun savePlannerSession() {
            val duration = plannerEditor.durationMinutes.toIntOrNull()
            val needsRest = plannerEditor.category == WorkoutCategory.STRENGTH || plannerEditor.programVariant == "Interval Run"
            val rest = if (needsRest) plannerEditor.restSeconds.toIntOrNull() else 0
            val resolvedTitle = plannerEditor.title.ifBlank { plannerEditor.programVariant }
            val resolvedFocusArea = plannerEditor.programVariant.ifBlank {
                when (plannerEditor.category) {
                    WorkoutCategory.STRENGTH -> "Full Body"
                    WorkoutCategory.CARDIO -> "Easy Run"
                    WorkoutCategory.MOBILITY -> "Mobility"
                    WorkoutCategory.RECOVERY -> "Recovery"
                }
            }
            val distance = plannerEditor.targetDistanceKm.toDoubleOrNull()
            val sets = plannerEditor.targetSets.toIntOrNull()
            val reps = plannerEditor.targetReps.toIntOrNull()
            val isValid = when (plannerEditor.category) {
                WorkoutCategory.STRENGTH -> resolvedTitle.isNotBlank() && duration != null && rest != null && sets != null && reps != null
                WorkoutCategory.CARDIO -> resolvedTitle.isNotBlank() && duration != null && distance != null
                WorkoutCategory.MOBILITY,
                WorkoutCategory.RECOVERY -> resolvedTitle.isNotBlank() && duration != null
            }
            if (!isValid) {
                showMessage("Lengkapi data sesi terlebih dahulu sebelum disimpan.")
                return
            }

            val safeDuration = duration ?: return
            val safeRest = rest ?: 0

            val editingSessionId = plannerEditor.sessionId
            if (editingSessionId == null) {
                viewModel.addPlannerSession(
                    title = resolvedTitle.trim(),
                    category = plannerEditor.category,
                    focusArea = resolvedFocusArea,
                    dayOfWeek = plannerEditor.dayOfWeek,
                    durationMinutes = safeDuration,
                    targetDistanceKm = distance,
                    targetSets = sets,
                    targetReps = reps,
                    restSeconds = safeRest,
                    notes = plannerEditor.notes.trim()
                )
            } else {
                viewModel.updatePlannerSession(
                    sessionId = editingSessionId,
                    title = resolvedTitle.trim(),
                    category = plannerEditor.category,
                    focusArea = resolvedFocusArea,
                    dayOfWeek = plannerEditor.dayOfWeek,
                    durationMinutes = safeDuration,
                    targetDistanceKm = distance,
                    targetSets = sets,
                    targetReps = reps,
                    restSeconds = safeRest,
                    notes = plannerEditor.notes.trim()
                )
            }
            resetPlannerDraft()
        }

        fun editPlannerSession(session: WorkoutSession) {
            plannerEditor = PlannerEditorUi(
                sessionId = session.id,
                title = session.title,
                category = session.category,
                programVariant = inferProgramVariant(session),
                dayOfWeek = session.dayOfWeek,
                durationMinutes = session.targetDurationMinutes.toString(),
                targetDistanceKm = session.targetDistanceKm?.toString().orEmpty(),
                targetSets = session.targetSets?.toString().orEmpty(),
                targetReps = session.targetReps?.toString().orEmpty(),
                restSeconds = session.restSeconds.toString(),
                notes = session.sessionNotes
            )
        }

        fun saveRecoveryCheckIn() {
            val sleepHours = recoveryDraft.sleepHours.toDoubleOrNull()
            val recoveryScore = recoveryDraft.recoveryScore.toIntOrNull()
            val energyLevel = recoveryDraft.energyLevel.toIntOrNull()
            val sorenessLevel = recoveryDraft.sorenessLevel.toIntOrNull()
            if (sleepHours == null || recoveryScore == null || energyLevel == null || sorenessLevel == null) {
                showMessage("Isi jam tidur, recovery score, energi, dan soreness terlebih dahulu.")
                return
            }
            viewModel.saveRecoveryCheckIn(
                sleepHours = sleepHours,
                recoveryScore = recoveryScore,
                energyLevel = energyLevel,
                sorenessLevel = sorenessLevel,
                notes = recoveryDraft.notes.trim()
            )
            resetRecoveryDraft()
        }

        fun saveStrengthSession() {
            val session = snapshot.sessionForFilter(ProgramFilter.GYM)
            val duration = strengthDurationMinutes.toIntOrNull()
            val effort = strengthEffortScore.toIntOrNull()
            val validExercises = gymExercises.flatMap { exercise ->
                exercise.ensureSetEntries().completedSetEntries().mapNotNull { setEntry ->
                    val reps = setEntry.repsCompleted.toIntOrNull()
                    val weight = parseFlexibleDecimal(setEntry.weightKg)
                    if (reps == null || reps <= 0 || weight == null || weight <= 0.0) {
                        null
                    } else {
                        com.athallah.hybridfit.domain.model.StrengthExerciseDraft(
                            exerciseName = exercise.name,
                            actualSets = 1,
                            actualReps = reps,
                            actualWeightKg = weight,
                            notes = "Set ${setEntry.setNumber}"
                        )
                    }
                }
            }

            if (duration == null || effort == null || validExercises.isEmpty()) {
                showMessage("Selesaikan minimal satu set latihan terlebih dahulu.")
                return
            }

            val editingLogId = editingStrengthLogId
            if (editingLogId == null) {
                viewModel.saveStrengthWorkout(
                    sessionId = session.id,
                    actualDurationMinutes = duration,
                    exertionScore = effort,
                    notes = strengthSessionNotes.trim(),
                    exercises = validExercises
                )
                currentScene = AppScene.COMPLETION.name
            } else {
                viewModel.updateStrengthWorkout(
                    logId = editingLogId,
                    actualDurationMinutes = duration,
                    exertionScore = effort,
                    notes = strengthSessionNotes.trim(),
                    exercises = validExercises
                )
                selectedActivityLogId = editingLogId
                resetStrengthDrafts()
                currentScene = AppScene.GYM_DETAIL.name
            }
        }

        fun saveRunSession() {
            val session = snapshot.sessionForFilter(ProgramFilter.RUN)
            val isIntervalRun = session.isIntervalRun()
            val draftWithSegments = if (isIntervalRun) {
                runLogDraft.ensureSegmentEntries(session.targetDistanceKm)
            } else {
                runLogDraft
            }
            val completedSegments = if (isIntervalRun) draftWithSegments.completedSegments() else emptyList()
            val distance = if (completedSegments.isNotEmpty()) {
                draftWithSegments.totalDistanceKmFromSegments()
            } else {
                parseFlexibleDecimal(runLogDraft.distanceKm)
            }
            val duration = if (completedSegments.isNotEmpty()) {
                ((draftWithSegments.totalDurationSecondsFromSegments() / 60.0).roundToInt()).coerceAtLeast(1)
            } else {
                runLogDraft.totalManualDurationSeconds()?.let { seconds ->
                    (seconds / 60.0).roundToInt().coerceAtLeast(1)
                }
            }
            val effort = runLogDraft.effortScore.toIntOrNull() ?: 6
            if (distance == null || duration == null) {
                showMessage(
                    if (isIntervalRun) {
                        "Lengkapi minimal satu segmen lari atau isi jarak dan durasi terlebih dahulu."
                    } else {
                        "Lengkapi jarak, menit, dan detik lari terlebih dahulu."
                    }
                )
                return
            }

            val editingLogId = runLogDraft.logId
            if (editingLogId == null) {
                viewModel.saveRunWorkout(
                    sessionId = session.id,
                    distanceKm = distance,
                    actualDurationMinutes = duration,
                    exertionScore = effort,
                    notes = runLogDraft.notes.trim()
                )
            } else {
                viewModel.updateRunWorkout(
                    logId = editingLogId,
                    distanceKm = distance,
                    actualDurationMinutes = duration,
                    exertionScore = effort,
                    notes = runLogDraft.notes.trim()
                )
                selectedActivityLogId = editingLogId
            }
            currentScene = AppScene.RUN_DETAIL.name
            resetRunDraft()
        }

        when (AppScene.valueOf(currentScene)) {
            AppScene.WELCOME -> WelcomeScreen(
                primaryActionLabel = "Mulai",
                onPrimaryAction = { currentScene = AppScene.REGISTER.name },
                onLogin = { currentScene = AppScene.LOGIN.name },
                onGoogleLogin = onGoogleLoginRequested,
                onQuickAccess = ::openQuickActions,
                showDirectAuthActions = false,
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.REGISTER -> RegisterScreen(
                name = registerName,
                onNameChange = { registerName = it },
                email = registerEmail,
                onEmailChange = { registerEmail = it },
                password = registerPassword,
                onPasswordChange = { registerPassword = it },
                confirmPassword = registerConfirmPassword,
                onConfirmPasswordChange = { registerConfirmPassword = it },
                showPassword = showRegisterPassword,
                onTogglePassword = { showRegisterPassword = !showRegisterPassword },
                showConfirmPassword = showRegisterConfirmPassword,
                onToggleConfirmPassword = { showRegisterConfirmPassword = !showRegisterConfirmPassword },
                isRegisterInProgress = isRegisterInProgress,
                onRegister = {
                    when {
                        registerName.isBlank() || registerEmail.isBlank() || registerPassword.isBlank() || registerConfirmPassword.isBlank() ->
                            showMessage("Lengkapi semua data registrasi terlebih dahulu.")

                        registerPassword != registerConfirmPassword ->
                            showMessage("Konfirmasi kata sandi belum cocok.")

                        else -> viewModel.registerWithEmail(
                            fullName = registerName.trim(),
                            email = registerEmail.trim(),
                            password = registerPassword
                        )
                    }
                },
                onLoginInstead = { currentScene = AppScene.LOGIN.name },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.FORGOT_PASSWORD -> ForgotPasswordScreen(
                email = forgotPasswordEmail,
                onEmailChange = { forgotPasswordEmail = it },
                onBack = ::returnFromUtility,
                onSubmit = {
                    if (forgotPasswordEmail.isBlank()) {
                        showMessage("Masukkan email akun terlebih dahulu.")
                    } else {
                        openFeatureInfo(
                            title = "Reset Kata Sandi",
                            subtitle = "Instruksi pemulihan siap",
                            body = "Untuk mode demo saat ini, instruksi reset belum dikirim ke email sungguhan. Namun alur pemulihan akun sudah disiapkan di dalam aplikasi.",
                            highlights = listOf(
                                "Email tujuan: ${forgotPasswordEmail.trim()}",
                                "Gunakan login Google jika akun Anda tersambung ke Google.",
                                "Nanti endpoint reset password bisa dihubungkan ke backend saat siap."
                            ),
                            icon = Icons.Outlined.Email,
                            accent = Color(0xFF2F6BD6),
                            primaryActionLabel = "Kembali ke Login"
                        )
                    }
                },
                onUseGoogle = onGoogleLoginRequested,
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.LOGIN -> LoginScreen(
                email = loginEmail,
                onEmailChange = { loginEmail = it },
                password = loginPassword,
                onPasswordChange = { loginPassword = it },
                showPassword = showLoginPassword,
                onTogglePassword = { showLoginPassword = !showLoginPassword },
                isCredentialLoginInProgress = isCredentialLoginInProgress,
                isGoogleLoginInProgress = uiState.isGoogleAuthInProgress,
                onQuickAccess = ::openQuickActions,
                onLogin = {
                    if (loginEmail.isBlank() || loginPassword.isBlank()) {
                        showMessage("Email dan password wajib diisi.")
                    } else {
                        viewModel.loginWithEmail(
                            email = loginEmail.trim(),
                            password = loginPassword
                        )
                    }
                },
                onForgotPassword = ::openForgotPasswordScreen,
                onGoogleLogin = onGoogleLoginRequested,
                onAppleLogin = viewModel::loginWithAppleDemo,
                onCreateAccount = { currentScene = AppScene.REGISTER.name },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.SURVEY_GOAL -> SurveyGoalScreen(
                selectedGoal = GoalOption.valueOf(selectedGoal),
                onSelect = { selectedGoal = it.name },
                onNext = { currentScene = AppScene.SURVEY_LEVEL.name },
                onBack = {
                    currentScene = if (uiState.lastAuthAction == AuthAction.REGISTER) {
                        AppScene.REGISTER.name
                    } else {
                        AppScene.LOGIN.name
                    }
                },
                onMenu = {
                    openFeatureInfo(
                        title = "Panduan Tujuan Latihan",
                        subtitle = "Pilih tujuan utama Anda",
                        body = "Pilihan tujuan akan memengaruhi jenis program, porsi latihan, dan fokus rekomendasi AI yang disusun Hybrid Fit.",
                        highlights = listOf(
                            "Build Muscle akan memprioritaskan latihan beban dan progres volume.",
                            "Weight Loss akan menyeimbangkan cardio dan defisit intensitas.",
                            "Hybrid Program menggabungkan strength dan running dalam satu rencana."
                        ),
                        icon = Icons.Outlined.Schedule,
                        accent = OceanBlue
                    )
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.SURVEY_LEVEL -> SurveyLevelScreen(
                selectedExperience = ExperienceOption.valueOf(selectedExperience),
                onSelect = { selectedExperience = it.name },
                onNext = { currentScene = AppScene.SURVEY_FREQUENCY.name },
                onBack = { currentScene = AppScene.SURVEY_GOAL.name },
                onMenu = {
                    openFeatureInfo(
                        title = "Level Pengalaman",
                        subtitle = "Kenali titik awal Anda",
                        body = "Hybrid Fit menyesuaikan volume dan kompleksitas program berdasarkan pengalaman latihan Anda saat ini.",
                        highlights = listOf(
                            "Pemula mendapatkan struktur yang lebih aman dan progresif.",
                            "Menengah menerima variasi latihan dan intensitas yang lebih seimbang.",
                            "Mahir diarahkan ke tantangan volume dan performa yang lebih tinggi."
                        ),
                        icon = Icons.AutoMirrored.Outlined.ShowChart,
                        accent = Color(0xFF29436B)
                    )
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.SURVEY_FREQUENCY -> SurveyFrequencyScreen(
                selectedFrequency = FrequencyOption.valueOf(selectedFrequency),
                onSelect = { selectedFrequency = it.name },
                onNext = { currentScene = AppScene.SURVEY_HEALTH.name },
                onBack = { currentScene = AppScene.SURVEY_LEVEL.name },
                onMenu = {
                    openFeatureInfo(
                        title = "Frekuensi Latihan",
                        subtitle = "Target mingguan yang realistis",
                        body = "Jumlah hari latihan per minggu akan memengaruhi pembagian beban, sesi recovery, dan target progres mingguan.",
                        highlights = listOf(
                            "4 hari cocok untuk peningkatan performa yang stabil.",
                            "5-6 hari cocok bila Anda siap menjaga tidur dan pemulihan.",
                            "Target ini nanti tetap bisa diubah dari menu Target Mingguan."
                        ),
                        icon = Icons.Outlined.CalendarToday,
                        accent = AquaTeal
                    )
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.SURVEY_HEALTH -> SurveyHealthScreen(
                selectedHealth = displayHealth,
                healthNotes = healthNotes,
                onSelect = { selectedHealth = it.name },
                onNotesChange = { healthNotes = it },
                onNext = { currentScene = AppScene.SURVEY_BODY.name },
                onBack = { currentScene = AppScene.SURVEY_FREQUENCY.name },
                onMenu = {
                    openFeatureInfo(
                        title = "Status Kesehatan",
                        subtitle = "Keamanan latihan adalah prioritas",
                        body = "Informasi kondisi fisik membantu sistem menjaga latihan tetap menantang tetapi tidak berisiko berlebihan.",
                        highlights = listOf(
                            "Kondisi sendi, jantung, atau pernapasan akan menurunkan intensitas awal.",
                            "Catatan kesehatan akan dipakai lagi saat AI menilai hasil feedback sesi.",
                            "Anda dapat memperbarui status ini kapan saja dari profil akun."
                        ),
                        icon = Icons.Outlined.CheckCircle,
                        accent = Color(0xFF0E8A5F)
                    )
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.SURVEY_BODY -> SurveyBodyMetricsScreen(
                age = surveyAge,
                weightKg = surveyWeightKg,
                heightCm = surveyHeightCm,
                onAgeChange = { surveyAge = it.filter { char -> char.isDigit() } },
                onWeightChange = { input ->
                    surveyWeightKg = input.filter { char ->
                        char.isDigit() || char == ',' || char == '.'
                    }
                },
                onHeightChange = { surveyHeightCm = it.filter { char -> char.isDigit() } },
                onFinish = { completeSurveyAndCreateProgram() },
                onBack = { currentScene = AppScene.SURVEY_HEALTH.name },
                onMenu = {
                    openFeatureInfo(
                        title = "Data Tubuh",
                        subtitle = "Dasar rekomendasi beban awal",
                        body = "Umur, berat badan, dan tinggi badan membantu Hybrid Fit menyusun volume, beban awal, serta durasi latihan yang terasa lebih realistis untuk kondisi Anda saat ini.",
                        highlights = listOf(
                            "Berat badan dipakai untuk mengira volume awal latihan gym dan target energi latihan.",
                            "Tinggi badan dan umur membantu sistem menjaga progres awal tetap aman dan bertahap.",
                            "Data ini bisa diperbarui lagi nanti dari profil bila kondisi Anda berubah."
                        ),
                        icon = Icons.Outlined.AccountCircle,
                        accent = OceanBlue
                    )
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.DASHBOARD -> when (MainTab.valueOf(selectedTab)) {
                MainTab.HOME -> HomeDashboardScreen(
                    snapshot = snapshot,
                    displayName = displayName,
                    hasProgram = snapshot.hasProgram(),
                    isProgramCreationInProgress = uiState.isProgramCreationInProgress,
                    onMetricClick = { label ->
                        val info = when (label) {
                            "Langkah" -> FeatureInfoUi(
                                title = "Langkah Harian",
                                subtitle = "Aktivitas bergerak Anda hari ini",
                                body = "Jumlah langkah membantu sistem membaca tingkat aktivitas umum di luar sesi latihan utama.",
                                highlights = listOf(
                                    "Target saat ini: $weeklyStepGoal langkah per hari.",
                                    "Aktivitas berjalan membantu pemulihan aktif dan pembakaran kalori.",
                                    "Data ini dapat dipakai untuk menyesuaikan volume cardio."
                                ),
                                icon = Icons.AutoMirrored.Outlined.DirectionsRun,
                                accent = OceanBlue
                            )

                            "Kalori" -> FeatureInfoUi(
                                title = "Kalori Harian",
                                subtitle = "Estimasi energi yang telah digunakan",
                                body = "Ringkasan kalori memberi gambaran apakah beban aktivitas Anda masih seimbang dengan jadwal latihan dan recovery.",
                                highlights = listOf(
                                    "Kalori lebih tinggi biasanya sejalan dengan volume latihan yang meningkat.",
                                    "Sistem dapat menurunkan intensitas jika aktivitas non-latihan terlalu padat.",
                                    "Perhatikan asupan dan hidrasi untuk menjaga performa."
                                ),
                                icon = Icons.Outlined.LocalFireDepartment,
                                accent = AquaTeal
                            )

                            else -> FeatureInfoUi(
                                title = "Kualitas Tidur",
                                subtitle = "Skor recovery Anda malam tadi",
                                body = "Skor tidur membantu Hybrid Fit menentukan apakah sesi hari ini aman untuk ditingkatkan atau perlu dibuat lebih ringan.",
                                highlights = listOf(
                                    "Target saat ini: $weeklySleepGoal jam tidur berkualitas.",
                                    "Tidur yang baik membuat rekomendasi AI lebih agresif dengan aman.",
                                    "Skor rendah akan memprioritaskan pemulihan dan rest time lebih panjang."
                                ),
                                icon = Icons.Outlined.CheckCircle,
                                accent = Color(0xFF29436B)
                            )
                        }
                        openFeatureInfoScreen(info)
                    },
                    onOpenHero = {
                        if (!snapshot.hasProgram()) {
                            requestStarterProgram()
                        } else {
                            val featuredFilter = snapshot.homeFeaturedProgramFilter()
                            resetRunDraft()
                            resetStrengthDrafts()
                            selectedActivityLogId = null
                            selectedProgramFilter = featuredFilter.name
                            openDetail(
                                scene = if (featuredFilter == ProgramFilter.RUN) {
                                    AppScene.AI_DETAIL
                                } else {
                                    AppScene.GYM_DETAIL
                                },
                                returnTab = MainTab.HOME
                            )
                        }
                    },
                    onCreateProgram = ::requestStarterProgram,
                    onMenu = ::openQuickActions,
                    onAiHub = { openCoachChat(AppScene.DASHBOARD) },
                    onNotifications = ::openNotifications,
                    onAccount = ::openAccountScreen,
                    modifier = Modifier.padding(innerPadding)
                )

                MainTab.PROGRAMS -> ProgramsDashboardScreen(
                    snapshot = snapshot,
                    selectedFilter = resolvedProgramFilter,
                    availableFilters = availableProgramFilters,
                    hasProgram = snapshot.hasProgram(),
                    isProgramCreationInProgress = uiState.isProgramCreationInProgress,
                    onFilterSelected = { selectedProgramFilter = it.name },
                    onEditProgram = ::openWeeklyTargetScreen,
                    onOpenProgram = {
                        if (!snapshot.hasProgram()) {
                            requestStarterProgram()
                        } else {
                            val targetSession = snapshot.sessionForFilter(resolvedProgramFilter)
                            if (snapshot.sessionProgress(targetSession) >= 1f) {
                                showMessage("Program ini sudah selesai dan tidak bisa dibuka lagi dari daftar program.")
                            } else {
                            resetRunDraft()
                            resetStrengthDrafts()
                            selectedActivityLogId = null
                            openDetail(
                                scene = if (resolvedProgramFilter == ProgramFilter.RUN) {
                                    AppScene.AI_DETAIL
                                } else {
                                    AppScene.GYM_DETAIL
                                },
                                returnTab = MainTab.PROGRAMS
                            )
                            }
                        }
                    },
                    onStartProgram = {
                        if (!snapshot.hasProgram()) {
                            requestStarterProgram()
                        } else {
                            val targetSession = snapshot.sessionForFilter(resolvedProgramFilter)
                            if (snapshot.sessionProgress(targetSession) >= 1f) {
                                showMessage("Program ini sudah selesai.")
                            } else {
                            resetRunDraft()
                            resetStrengthDrafts()
                            selectedActivityLogId = null
                            openDetail(
                                scene = if (resolvedProgramFilter == ProgramFilter.RUN) {
                                    AppScene.AI_DETAIL
                                } else {
                                    AppScene.GYM_DETAIL
                                },
                                returnTab = MainTab.PROGRAMS
                            )
                            }
                        }
                    },
                    modifier = Modifier.padding(innerPadding)
                )

                MainTab.STATS -> StatsDashboardScreen(
                    snapshot = snapshot,
                    selectedRange = StatsRange.valueOf(selectedStatsRange),
                    onRangeSelected = { selectedStatsRange = it.name },
                    onBack = { openDashboard(MainTab.HOME) },
                    onCalendar = {
                        openFeatureInfo(
                            title = "Rentang Analisis",
                            subtitle = "Filter hari, minggu, bulan, atau tahun",
                            body = "Pemilihan rentang membantu Anda membaca perubahan performa dari jangka pendek sampai tren yang lebih panjang.",
                            highlights = listOf(
                                "DAY untuk melihat performa hari ini.",
                                "WEEK cocok memantau konsistensi dan beban mingguan.",
                                "MONTH dan YEAR membantu mengevaluasi progres lebih besar."
                            ),
                            icon = Icons.Outlined.CalendarToday,
                            accent = OceanBlue
                        )
                    },
                    onShare = {
                        val range = StatsRange.valueOf(selectedStatsRange)
                        val rangeLabel = when (range) {
                            StatsRange.DAY -> "hari ini"
                            StatsRange.WEEK -> "minggu ini"
                            StatsRange.MONTH -> "bulan ini"
                            StatsRange.YEAR -> "tahun ini"
                        }
                        val burnedCalories = snapshot.caloriesForRange(range)
                        sharePlainText(
                            title = "Progress Kalori Hybrid Fit",
                            text = "Hybrid Fit Progress\nKalori yang terbakar $rangeLabel: ${burnedCalories.formatThousands()} kcal"
                        )
                    },
                    onAchievement = ::openAchievementsScreen,
                    onViewAll = ::openAchievementsScreen,
                    onOverviewClick = {
                        val range = StatsRange.valueOf(selectedStatsRange)
                        val logsInRange = snapshot.logsForRange(range)
                        val rangeLabel = when (range) {
                            StatsRange.DAY -> "hari ini"
                            StatsRange.WEEK -> "minggu ini"
                            StatsRange.MONTH -> "bulan ini"
                            StatsRange.YEAR -> "tahun ini"
                        }
                        val runningDelta = snapshot.runningComparison(range)
                        val strengthDelta = snapshot.strengthComparison(range)
                        openFeatureInfo(
                            title = "${range.label} Activity",
                            subtitle = "Ringkasan aktivitas $rangeLabel",
                            body = if (logsInRange.isEmpty()) {
                                "Belum ada aktivitas yang tercatat untuk $rangeLabel. Setelah latihan selesai disimpan, ringkasan ini akan menampilkan kalori, sesi, dan perubahan performa."
                            } else {
                                "Dalam rentang $rangeLabel, Anda sudah mencatat ${logsInRange.size} sesi dengan estimasi ${snapshot.caloriesForRange(range).formatThousands()} kcal terbakar."
                            },
                            highlights = listOf(
                                "Sesi tercatat: ${logsInRange.size}",
                                "Kalori: ${snapshot.caloriesForRange(range).formatThousands()} kcal",
                                "Lari: ${if (runningDelta >= 0) "+" else ""}${"%.1f".format(runningDelta)} km",
                                "Beban: ${if (strengthDelta >= 0) "+" else ""}${strengthDelta.formatThousands()} kg"
                            ),
                            icon = Icons.Outlined.BarChart,
                            accent = OceanBlue
                        )
                    },
                    onComparisonClick = {},
                    modifier = Modifier.padding(innerPadding)
                )

                MainTab.PROFILE -> ProfileDashboardScreen(
                    snapshot = snapshot,
                    displayName = displayName,
                    goalLabel = displayGoal.title,
                    experienceLabel = displayExperience.title,
                    avatarUrl = avatarUrl,
                    onNotification = ::openNotifications,
                    onProfileHeroClick = ::openAccountScreen,
                    onProfileStatClick = {},
                    onSettingClick = { label ->
                        when (label) {
                            "Akun" -> openAccountScreen()
                            "Pengaturan" -> openAppSettingsScreen()
                            else -> openHelpCenter()
                        }
                    },
                    isLogoutInProgress = uiState.isLogoutInProgress,
                    onLogout = viewModel::logout,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            AppScene.QUICK_ACTIONS -> QuickActionsScreen(
                isAuthenticated = uiState.isAuthenticated,
                onBack = { openDashboard(MainTab.HOME) },
                onAiHub = ::openAiHub,
                onNotifications = ::openNotifications,
                onAccount = ::openAccountScreen,
                onWeeklyTarget = ::openWeeklyTargetScreen,
                onSettings = ::openAppSettingsScreen,
                onHelp = ::openHelpCenter,
                onAchievements = ::openAchievementsScreen,
                onForgotPassword = ::openForgotPasswordScreen,
                onRegister = { currentScene = AppScene.REGISTER.name },
                onGoogleLogin = onGoogleLoginRequested,
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.AI_HUB -> AiHubScreen(
                snapshot = snapshot,
                selectedProgramFilter = resolvedProgramFilter,
                onBack = ::returnFromUtility,
                onOpenCoach = { openCoachChat(AppScene.AI_HUB) },
                onOpenProgram = { openDashboard(MainTab.PROGRAMS) },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.NOTIFICATIONS -> NotificationsScreen(
                items = notifications,
                onBack = ::returnFromUtility,
                onMarkAllRead = {
                    notifications.clear()
                    showMessage("Semua notifikasi ditandai sudah dibaca.")
                },
                onOpenNotification = { item ->
                    openFeatureInfo(
                        title = item.title,
                        subtitle = item.subtitle,
                        body = item.body,
                        highlights = listOf(
                            "Waktu notifikasi: ${item.timeLabel}",
                            "Kategori update ini akan membantu personalisasi program berikutnya.",
                            "Notifikasi serupa akan muncul lagi saat ada perubahan penting."
                        ),
                        icon = item.icon,
                        accent = item.tint
                    )
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.ACCOUNT -> AccountScreen(
                displayName = accountDisplayName.ifBlank { displayName },
                email = accountEmail.ifBlank { resolvedEmail },
                goalLabel = displayGoal.title,
                experienceLabel = displayExperience.title,
                notes = accountNotes,
                avatarUrl = avatarUrl,
                onDisplayNameChange = { accountDisplayName = it },
                onEmailChange = { accountEmail = it },
                onNotesChange = { accountNotes = it },
                onAvatarChange = { viewModel.updateCurrentUserAvatar(it) },
                onBack = ::returnFromUtility,
                onSave = {
                    if (accountDisplayName.isBlank() || accountEmail.isBlank()) {
                        showMessage("Nama dan email akun tidak boleh kosong.")
                    } else {
                        showMessage("Profil akun diperbarui secara lokal.")
                        returnFromUtility()
                    }
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.WEEKLY_TARGET -> WeeklyTargetScreen(
                snapshot = snapshot,
                selectedDays = FrequencyOption.valueOf(selectedFrequency).days,
                stepGoal = weeklyStepGoal,
                sleepGoal = weeklySleepGoal,
                plannerEditor = plannerEditor,
                recoveryDraft = recoveryDraft,
                onSelectDays = { days ->
                    selectedFrequency = FrequencyOption.entries.first { it.days == days }.name
                },
                onStepGoalChange = { weeklyStepGoal = it },
                onSleepGoalChange = { weeklySleepGoal = it },
                onPlannerEditorChange = { plannerEditor = it },
                onPlannerSave = ::savePlannerSession,
                onPlannerReset = ::resetPlannerDraft,
                onPlannerEdit = ::editPlannerSession,
                onPlannerMove = { session -> viewModel.movePlannerSessionToNextDay(session.id) },
                onPlannerSkip = { session ->
                    viewModel.updatePlannerSessionState(session.id, com.athallah.hybridfit.domain.model.SessionPlannerState.SKIPPED)
                },
                onPlannerComplete = { session ->
                    viewModel.updatePlannerSessionState(session.id, com.athallah.hybridfit.domain.model.SessionPlannerState.COMPLETED)
                },
                onPlannerDelete = { session -> viewModel.deletePlannerSession(session.id) },
                onRecoveryDraftChange = { recoveryDraft = it },
                onRecoverySave = ::saveRecoveryCheckIn,
                onBack = ::returnFromUtility,
                onSaveTargets = {
                    viewModel.updateWeeklyTargets(
                        workoutDaysPerWeek = FrequencyOption.valueOf(selectedFrequency).days,
                        onSuccess = { returnFromUtility() },
                        onFailure = ::showMessage
                    )
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.APP_SETTINGS -> AppSettingsScreen(
                reminderEnabled = reminderEnabled,
                smartRecommendationEnabled = smartRecommendationEnabled,
                sleepInsightEnabled = sleepInsightEnabled,
                dataSaverEnabled = dataSaverEnabled,
                onReminderChange = { reminderEnabled = it },
                onSmartRecommendationChange = { smartRecommendationEnabled = it },
                onSleepInsightChange = { sleepInsightEnabled = it },
                onDataSaverChange = { dataSaverEnabled = it },
                onBack = ::returnFromUtility,
                onSave = {
                    appSettingsPreferences.edit()
                        .putBoolean("reminder_enabled", reminderEnabled)
                        .putBoolean("smart_recommendation_enabled", smartRecommendationEnabled)
                        .putBoolean("sleep_insight_enabled", sleepInsightEnabled)
                        .putBoolean("data_saver_enabled", dataSaverEnabled)
                        .apply()
                    showMessage("Pengaturan aplikasi diperbarui.")
                    returnFromUtility()
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.HELP_CENTER -> HelpCenterScreen(
                supportMessage = supportMessage,
                onSupportMessageChange = { supportMessage = it },
                onBack = ::returnFromUtility,
                onOpenCoach = { openCoachChat(AppScene.HELP_CENTER) },
                onSend = {
                    if (supportMessage.isBlank()) {
                        showMessage("Tulis pertanyaan Anda terlebih dahulu.")
                    } else {
                        openFeatureInfo(
                            title = "Pertanyaan Terkirim",
                            subtitle = "Bantuan sedang diproses",
                            body = "Pesan bantuan Anda sudah tercatat pada mode demo lokal. Nanti fitur ini bisa diteruskan ke backend atau email dukungan.",
                            highlights = listOf(
                                "Pesan: ${supportMessage.take(90)}",
                                "Anda juga bisa membuka AI Coach untuk respons lebih cepat.",
                                "Riwayat bantuan dapat ditambahkan sebagai fitur berikutnya."
                            ),
                            icon = Icons.AutoMirrored.Outlined.HelpOutline,
                            accent = AquaTeal
                        )
                    }
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.ACHIEVEMENTS -> AchievementsScreen(
                snapshot = snapshot,
                onBack = { openDashboard(MainTab.STATS) },
                onTopAction = {},
                onOpenAchievement = {},
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.FEATURE_INFO -> FeatureInfoScreen(
                info = featureInfo,
                workoutDates = snapshot.recentLogs.map { it.performedOn }.toSet(),
                plannedSessions = snapshot.activePlan?.sessions.orEmpty(),
                runLogDraft = runLogDraft,
                showSimpleRunInput =
                    resolvedProgramFilter == ProgramFilter.RUN &&
                        !snapshot.sessionForFilter(ProgramFilter.RUN).isIntervalRun() &&
                        featureInfo.title == snapshot.sessionForFilter(ProgramFilter.RUN).title,
                onRunDistanceChange = { runLogDraft = runLogDraft.copy(distanceKm = it) },
                onRunDurationChange = {
                    runLogDraft = runLogDraft.copy(durationMinutes = it.filter(Char::isDigit))
                },
                onRunDurationSecondsChange = {
                    runLogDraft = runLogDraft.copy(durationSeconds = it.filter(Char::isDigit).take(2))
                },
                onRunNotesChange = { runLogDraft = runLogDraft.copy(notes = it) },
                onBack = ::returnFromUtility,
                onPrimaryAction = {
                    if (
                        resolvedProgramFilter == ProgramFilter.RUN &&
                        !snapshot.sessionForFilter(ProgramFilter.RUN).isIntervalRun() &&
                        featureInfo.title == snapshot.sessionForFilter(ProgramFilter.RUN).title
                    ) {
                        saveRunSession()
                    } else if (featureInfo.primaryActionLabel == "Kembali ke Login") {
                        currentScene = AppScene.LOGIN.name
                    } else {
                        returnFromUtility()
                    }
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.RUN_DETAIL -> RunDetailScreen(
                snapshot = snapshot,
                selectedLog = selectedRunLog,
                onBack = { openDashboard(MainTab.valueOf(detailReturnTab)) },
                onShare = {
                    val detailLog = selectedRunLog ?: snapshot.primaryRunLog()
                    if (detailLog == null) {
                        showMessage("Belum ada hasil lari untuk dibagikan.")
                    } else {
                        sharePlainText(
                            title = "Bagikan Aktivitas Lari",
                            text = buildString {
                                appendLine("Hybrid Fit Run")
                                appendLine("Sesi: ${detailLog.sessionTitle}")
                                appendLine("Tanggal: ${detailLog.performedOn}")
                                appendLine("Jarak: ${"%.2f".format(detailLog.actualDistanceKm ?: 0.0)} km")
                                appendLine("Durasi: ${detailLog.actualDurationMinutes} menit")
                                appendLine("Pace: ${detailLog.paceLabel()}")
                            }
                        )
                    }
                },
                onSummaryClick = {
                    val detailLog = selectedRunLog ?: snapshot.primaryRunLog()
                    openFeatureInfo(
                        title = "Ringkasan Jarak",
                        subtitle = "Performa lari sesi pagi",
                        body = "Jarak tempuh dan durasi sesi dipakai untuk menilai kapasitas kardio dan bahan rekomendasi sesi berikutnya.",
                        highlights = listOf(
                            "Jarak aktual: ${"%.2f".format(detailLog?.actualDistanceKm ?: 0.0)} km",
                            "Durasi aktual: ${detailLog?.actualDurationMinutes ?: 0} menit.",
                            "Peningkatan jarak dapat dilakukan bertahap pada minggu depan."
                        ),
                        icon = Icons.AutoMirrored.Outlined.DirectionsRun,
                        accent = AquaTeal
                    )
                },
                onMetricClick = { label ->
                    openFeatureInfo(
                        title = label,
                        subtitle = "Detail metrik lari",
                        body = "Metrik ini membantu melihat efisiensi dan kualitas eksekusi sesi lari Anda secara lebih rinci.",
                        highlights = listOf(
                            "Metrik dibaca bersama split dan feedback latihan.",
                            "AI memakai data ini untuk mengubah durasi maupun pace target.",
                            "Catatan yang konsisten membuat rekomendasi lebih akurat."
                        ),
                        icon = when (label) {
                            "Duration" -> Icons.Outlined.AccessTime
                            "Calories" -> Icons.Outlined.LocalFireDepartment
                            else -> Icons.AutoMirrored.Outlined.ShowChart
                        },
                        accent = OceanBlue
                    )
                },
                onSplitClick = { label ->
                    openFeatureInfo(
                        title = label,
                        subtitle = "Analisis split lari",
                        body = "Split membantu melihat apakah pace Anda stabil, terlalu agresif di awal, atau masih menyimpan tenaga di akhir sesi.",
                        highlights = listOf(
                            "Perhatikan perubahan pace antar kilometer.",
                            "Split stabil biasanya menandakan distribusi energi yang baik.",
                            "AI dapat menggunakan pola split untuk menyusun interval berikutnya."
                        ),
                        icon = Icons.AutoMirrored.Outlined.ShowChart,
                        accent = AquaTeal
                    )
                },
                onEditLog = {
                    val targetLog = selectedRunLog ?: snapshot.primaryRunLog()
                    if (targetLog == null) {
                        showMessage("Belum ada log lari untuk diedit.")
                    } else {
                        prepareRunLogEdit(targetLog)
                    }
                },
                onDeleteLog = {
                    val targetLog = selectedRunLog ?: snapshot.primaryRunLog()
                    if (targetLog == null) {
                        showMessage("Belum ada log lari untuk dihapus.")
                    } else {
                        deleteSelectedActivity(targetLog)
                    }
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.AI_DETAIL -> AiWorkoutDetailScreen(
                snapshot = snapshot,
                selectedProgramFilter = resolvedProgramFilter,
                onBack = { openDashboard(MainTab.valueOf(detailReturnTab)) },
                onOpenCoach = { openCoachChat(AppScene.AI_DETAIL) },
                onSummaryClick = {
                    openFeatureInfo(
                        title = "Panduan Cerdas AI",
                        subtitle = "Mengapa sesi ini disarankan",
                        body = "Sistem merekomendasikan sesi ini berdasarkan tujuan, kualitas pemulihan, dan respons latihan terakhir Anda.",
                        highlights = listOf(
                            "Volume disesuaikan agar tetap menantang namun realistis.",
                            "Jeda istirahat dipilih untuk menjaga performa tanpa risiko berlebih.",
                            "Feedback sesi hari ini akan dipakai untuk rekomendasi berikutnya."
                        ),
                        icon = Icons.Outlined.AutoAwesome,
                        accent = AquaTeal
                    )
                },
                onMetricClick = { label ->
                    openFeatureInfo(
                        title = label,
                        subtitle = "Detail target sesi adaptif",
                        body = "Target sesi adaptif menjelaskan seberapa lama, seberapa jauh, atau seberapa padat latihan yang direkomendasikan AI hari ini.",
                        highlights = listOf(
                            "Target dibuat sesuai program ${resolvedProgramFilter.label}.",
                            "Recovery dan konsistensi latihan ikut memengaruhi target.",
                            "Jika sesi terasa terlalu berat, gunakan feedback setelah selesai."
                        ),
                        icon = when (label) {
                            "Kkal Est." -> Icons.Outlined.LocalFireDepartment
                            "Target Durasi" -> Icons.Outlined.AccessTime
                            else -> Icons.Outlined.AutoAwesome
                        },
                        accent = OceanBlue
                    )
                },
                onRoutineClick = { title, subtitle ->
                    val routineSeconds = parseRoutineCountdownSeconds(subtitle)
                    val isIntervalRoutine = resolvedProgramFilter == ProgramFilter.RUN &&
                        snapshot.sessionForFilter(ProgramFilter.RUN).isIntervalRun() &&
                        title.contains("interval", ignoreCase = true)
                    val isRunRoutineWithoutHero = resolvedProgramFilter == ProgramFilter.RUN &&
                        !title.contains("pemanasan", ignoreCase = true) &&
                        !title.contains("pendinginan", ignoreCase = true) &&
                        !title.contains("regangan", ignoreCase = true)
                    openFeatureInfo(
                        title = title,
                        subtitle = subtitle,
                        body = "Bagian ini adalah langkah latihan di dalam sesi Anda. Ikuti urutannya supaya tubuh siap, inti latihan berjalan stabil, lalu recovery tetap aman di akhir sesi.",
                        highlights = emptyList(),
                        icon = Icons.Outlined.PlayArrow,
                        accent = AquaTeal,
                        showHeroCard = !isRunRoutineWithoutHero,
                        primaryActionLabel = "Selesai",
                        countdownSeconds = routineSeconds,
                        routineSteps = if (isIntervalRoutine) emptyList() else buildRoutineSteps(title, routineSeconds),
                        intervalSegments = if (isIntervalRoutine) buildIntervalSegments(routineSeconds) else emptyList()
                    )
                },
                onStartWorkout = {
                    if (resolvedProgramFilter == ProgramFilter.RUN) {
                        saveRunSession()
                    } else {
                        completeWorkoutAndShowFeedback()
                    }
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.GYM_DETAIL -> GymWorkoutDetailScreen(
                snapshot = snapshot,
                selectedLog = selectedStrengthLog,
                exercises = gymExercises,
                onBack = { openDashboard(MainTab.valueOf(detailReturnTab)) },
                onMore = ::openQuickActions,
                onShare = {
                    val detailLog = selectedStrengthLog ?: snapshot.latestStrengthLog()
                    val shareText = if (detailLog != null) {
                        buildString {
                            appendLine("Hybrid Fit - Aktivitas Gym")
                            appendLine("Sesi: ${detailLog.sessionTitle}")
                            appendLine("Volume: ${detailLog.loggedStrengthVolume().formatThousands()} kg")
                            appendLine("Durasi: ${detailLog.actualDurationMinutes} menit")
                            appendLine("Keterangan: ${detailLog.activitySubtitle()}")
                        }
                    } else {
                        "Hybrid Fit - Belum ada data aktivitas gym yang tersimpan untuk dibagikan."
                    }
                    sharePlainText("Bagikan Aktivitas Gym", shareText)
                },
                onSummaryClick = {
                    val detailLog = selectedStrengthLog ?: snapshot.latestStrengthLog()
                    openFeatureInfo(
                        title = "Total Volume",
                        subtitle = "Ringkasan performa strength",
                        body = if (detailLog != null) {
                            "Kartu volume sekarang menampilkan hasil aktual dari log strength yang dipilih, sehingga progres dan beban total bisa dibaca dengan lebih realistis."
                        } else {
                            "Jika sesi belum dijalankan, angka pada kartu volume dibaca sebagai target awal program. Setelah latihan selesai, kartu ini berubah menjadi hasil aktual."
                        },
                        highlights = listOf(
                            if (detailLog != null) {
                                "Volume aktual: ${detailLog.loggedStrengthVolume().formatThousands()} kg."
                            } else {
                                "Target awal dipakai sebagai baseline latihan."
                            },
                            if (detailLog != null) {
                                "Completion: ${detailLog.completionPercent}%."
                            } else {
                                "Hasil aktual baru tercatat sesudah sesi selesai."
                            },
                            "AI dapat menyesuaikan set dan repetisi berdasarkan data tersebut."
                        ),
                        icon = Icons.Outlined.FitnessCenter,
                        accent = OceanBlue
                    )
                },
                onMetricClick = { label ->
                    openFeatureInfo(
                        title = label,
                        subtitle = "Detail metrik sesi gym",
                        body = "Metrik gym membantu melihat seberapa efisien sesi kekuatan berlangsung, dari durasi hingga kebutuhan recovery.",
                        highlights = listOf(
                            "Durasi dan kalori mendukung evaluasi kepadatan latihan.",
                            "Metrik ini dipakai untuk mengatur waktu jeda dan volume.",
                            "Data tercatat membantu membaca beban mingguan secara akurat."
                        ),
                        icon = if (label.contains("Duration", true)) Icons.Outlined.AccessTime else Icons.Outlined.LocalFireDepartment,
                        accent = AquaTeal
                    )
                },
                onHeroClick = {
                    openFeatureInfo(
                        title = "Focus: Upper Body",
                        subtitle = "Prioritas sesi strength hari ini",
                        body = "Fokus sesi dipilih dari tujuan program dan rekomendasi awal AI, lalu akan terus disesuaikan setelah sistem menerima data latihan Anda.",
                        highlights = listOf(
                            "Fokus area mengikuti struktur program yang sedang dipilih.",
                            "Latihan utama dipakai sebagai baseline evaluasi AI.",
                            "Volume dapat berubah setelah ada progres nyata."
                        ),
                        icon = Icons.Outlined.FitnessCenter,
                        accent = OceanBlue
                    )
                },
                onInsightClick = {
                    openAiHub()
                },
                onStartWorkout = {
                    selectedActivityLogId = null
                    val firstIncompleteIndex = gymExercises.indexOfFirst { !it.completed }
                    if (firstIncompleteIndex >= 0) {
                        gymExercises.forEachIndexed { exerciseIndex, exercise ->
                            gymExercises[exerciseIndex] = exercise.ensureSetEntries().copy(
                                expanded = exerciseIndex == firstIncompleteIndex
                            )
                        }
                        val firstExercise = gymExercises[firstIncompleteIndex]
                        showMessage("Mulai dari ${firstExercise.name}.")
                    } else {
                        completeWorkoutAndShowFeedback()
                    }
                },
                onToggleExercise = { index ->
                    val firstIncompleteIndex = gymExercises.indexOfFirst { !it.completed }
                    val canOpen = firstIncompleteIndex == -1 ||
                        index <= firstIncompleteIndex ||
                        gymExercises[index].completed

                    if (!canOpen) {
                        val currentExerciseName = gymExercises[firstIncompleteIndex].name
                        showMessage("Selesaikan $currentExerciseName terlebih dahulu. Latihan berikutnya masih menunggu.")
                    } else {
                        updateGymExercise(index) { item ->
                            item.copy(expanded = !item.expanded)
                        }
                    }
                },
                onExerciseRepsChange = { exerciseIndex, setIndex, value ->
                    updateGymExercise(exerciseIndex) { item ->
                        val sanitized = value.filter { char -> char.isDigit() }
                        val updatedEntries = item.setEntries.toMutableList()
                        val currentEntry = updatedEntries[setIndex]
                        updatedEntries[setIndex] = currentEntry.copy(
                            repsCompleted = sanitized
                        )
                        item.copy(
                            setEntries = updatedEntries,
                            completed = false
                        )
                    }
                },
                onExerciseWeightChange = { exerciseIndex, setIndex, value ->
                    updateGymExercise(exerciseIndex) { item ->
                        val sanitized = value.filter { char ->
                            char.isDigit() || char == ',' || char == '.'
                        }
                        val updatedEntries = item.setEntries.toMutableList()
                        val currentEntry = updatedEntries[setIndex]
                        updatedEntries[setIndex] = currentEntry.copy(
                            weightKg = sanitized
                        )
                        item.copy(
                            setEntries = updatedEntries,
                            completed = false
                        )
                    }
                },
                onExercisePrimaryAction = { index ->
                    val item = gymExercises[index].ensureSetEntries()
                    val activeTimerIndex = gymExercises.indexOfFirst { exercise ->
                        exercise.timerPhase == GymSetTimerPhase.WORK ||
                            exercise.timerPhase == GymSetTimerPhase.REST ||
                            exercise.timerPhase == GymSetTimerPhase.READY_FOR_REST
                    }
                    if (activeTimerIndex >= 0 && activeTimerIndex != index) {
                        showMessage("Selesaikan timer exercise yang sedang aktif terlebih dahulu.")
                    } else {
                        when (item.timerPhase) {
                            GymSetTimerPhase.IDLE -> {
                                updateGymExercise(index) { exercise ->
                                    exercise.copy(
                                        timerPhase = GymSetTimerPhase.WORK,
                                        workSecondsRemaining = 60,
                                        restSecondsRemaining = 180,
                                        expanded = true
                                    )
                                }
                                showMessage("Timer kerja set ${item.activeSetIndex + 1} dimulai.")
                            }

                            GymSetTimerPhase.READY_FOR_REST -> {
                                val activeEntry = item.setEntries.getOrNull(item.activeSetIndex)
                                val reps = activeEntry?.repsCompleted?.toIntOrNull()
                                val weight = activeEntry?.weightKg?.let(::parseFlexibleDecimal)
                                if (reps == null || reps <= 0 || weight == null || weight <= 0.0) {
                                    showMessage("Isi reps dan beban set ${item.activeSetIndex + 1} sebelum istirahat.")
                                } else {
                                    updateGymExercise(index) { exercise ->
                                        val updatedEntries = exercise.setEntries.toMutableList()
                                        updatedEntries[exercise.activeSetIndex] = updatedEntries[exercise.activeSetIndex].copy(
                                            completed = true
                                        )
                                        exercise.copy(
                                            setEntries = updatedEntries,
                                            timerPhase = GymSetTimerPhase.REST,
                                            restSecondsRemaining = 180
                                        )
                                    }
                                    showMessage("Timer istirahat 3 menit dimulai.")
                                }
                            }

                            GymSetTimerPhase.FINISHED,
                            GymSetTimerPhase.WORK,
                            GymSetTimerPhase.REST -> Unit
                        }
                    }
                },
                onExerciseTimerTick = { index ->
                    val item = gymExercises[index].ensureSetEntries()
                    when (item.timerPhase) {
                        GymSetTimerPhase.WORK -> {
                            if (item.workSecondsRemaining > 1) {
                                updateGymExercise(index) { exercise ->
                                    exercise.copy(workSecondsRemaining = exercise.workSecondsRemaining - 1)
                                }
                            } else {
                                updateGymExercise(index) { exercise ->
                                    exercise.copy(
                                        timerPhase = GymSetTimerPhase.READY_FOR_REST,
                                        workSecondsRemaining = 0
                                    )
                                }
                                showMessage("Set ${item.activeSetIndex + 1} selesai. Isi hasil set lalu tekan Istirahat.")
                            }
                        }

                        GymSetTimerPhase.REST -> {
                            if (item.restSecondsRemaining > 1) {
                                updateGymExercise(index) { exercise ->
                                    exercise.copy(restSecondsRemaining = exercise.restSecondsRemaining - 1)
                                }
                            } else {
                                val isLastSet = item.activeSetIndex >= item.setEntries.lastIndex
                                if (isLastSet) {
                                    updateGymExercise(index) { exercise ->
                                        exercise.copy(
                                            timerPhase = GymSetTimerPhase.FINISHED,
                                            workSecondsRemaining = 60,
                                            restSecondsRemaining = 180,
                                            expanded = false,
                                            completed = true
                                        )
                                    }
                                    showMessage("${item.name} selesai. Semua set sudah tercatat.")
                                } else {
                                    updateGymExercise(index) { exercise ->
                                        exercise.copy(
                                            activeSetIndex = exercise.activeSetIndex + 1,
                                            timerPhase = GymSetTimerPhase.IDLE,
                                            workSecondsRemaining = 60,
                                            restSecondsRemaining = 180
                                        )
                                    }
                                    showMessage("Lanjut ke set ${item.activeSetIndex + 2} untuk ${item.name}.")
                                }
                            }
                        }

                        GymSetTimerPhase.IDLE,
                        GymSetTimerPhase.READY_FOR_REST,
                        GymSetTimerPhase.FINISHED -> Unit
                    }
                },
                onFinishSession = { saveStrengthSession() },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.COMPLETION -> CompletionFeedbackScreen(
                exercises = gymExercises,
                onBackToHome = {
                    openDashboard(MainTab.HOME)
                    resetStrengthDrafts()
                },
                onSeeProgram = {
                    viewModel.submitFeedback(true)
                    viewModel.refreshRecommendation()
                    openDashboard(MainTab.PROGRAMS)
                    resetStrengthDrafts()
                    showMessage("Program baru telah disegarkan.")
                },
                onOpenCoach = { openCoachChat(AppScene.COMPLETION) },
                onTotalClick = { label ->
                    openFeatureInfo(
                        title = label,
                        subtitle = "Ringkasan hasil sesi",
                        body = "Data penyelesaian ini dipakai untuk membaca kualitas eksekusi latihan dan menentukan apakah sesi berikutnya perlu dinaikkan atau dipulihkan.",
                        highlights = listOf(
                            "Set dan repetisi tercatat sebagai evaluasi utama.",
                            "Semakin lengkap hasil sesi, semakin akurat rekomendasi AI.",
                            "Ringkasan ini bisa menjadi indikator progres mingguan."
                        ),
                        icon = Icons.Outlined.CheckCircle,
                        accent = OceanBlue
                    )
                },
                onOptimizationClick = {
                    openAiHub()
                },
                modifier = Modifier.padding(innerPadding)
            )

            AppScene.AI_CHAT -> AiCoachChatScreen(
                messages = coachMessages,
                isSending = uiState.isCoachChatSending,
                input = coachInput,
                onInputChange = { coachInput = it },
                onBack = { currentScene = chatReturnScene },
                onTopAction = {
                    openFeatureInfo(
                        title = "AI Coach",
                        subtitle = "Pendamping adaptif untuk program Anda",
                        body = "AI Coach membantu membaca keluhan, progres, dan kebutuhan penyesuaian program secara lebih percakapan.",
                        highlights = listOf(
                            "Gunakan AI Coach ketika latihan terasa terlalu berat atau terlalu ringan.",
                            "Pesan Anda dapat dipakai sebagai input evaluasi rekomendasi.",
                            "Fitur ini cocok untuk klarifikasi cepat sebelum sesi berikutnya."
                        ),
                        icon = Icons.AutoMirrored.Outlined.HelpOutline,
                        accent = AquaTeal
                    )
                },
                onSuggestion = { suggestion ->
                    submitCoachPrompt(suggestion)
                },
                onApplyProposal = ::applyCoachProgramProposal,
                onCancelProposal = ::cancelCoachProgramProposal,
                onSend = {
                    submitCoachPrompt(coachInput)
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
internal fun LoadingScreen(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = OceanBlue)
            Text(
                text = "Memuat Hybrid Fit...",
                modifier = Modifier.padding(top = 12.dp),
                color = TextSecondary
            )
        }
    }
}

@Composable
internal fun BottomBar(
    selected: MainTab,
    onSelected: (MainTab) -> Unit
) {
    NavigationBar(
        containerColor = SurfaceWhite,
        tonalElevation = 0.dp
    ) {
        MainTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = selected == tab,
                onClick = { onSelected(tab) },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = {
                    Text(
                        text = tab.label.uppercase(Locale.getDefault()),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = OceanBlue,
                    selectedTextColor = OceanBlue,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )
        }
    }
}

