package com.athallah.hybridfit.ui.main

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CheckCircle
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
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.roundToInt

internal enum class AppScene {
    WELCOME,
    REGISTER,
    LOGIN,
    FORGOT_PASSWORD,
    SURVEY_GOAL,
    SURVEY_LEVEL,
    SURVEY_FREQUENCY,
    SURVEY_HEALTH,
    SURVEY_BODY,
    QUICK_ACTIONS,
    NOTIFICATIONS,
    ACCOUNT,
    WEEKLY_TARGET,
    APP_SETTINGS,
    AI_HUB,
    HELP_CENTER,
    ACHIEVEMENTS,
    FEATURE_INFO,
    DASHBOARD,
    RUN_DETAIL,
    AI_DETAIL,
    GYM_DETAIL,
    COMPLETION,
    AI_CHAT
}

internal enum class MainTab(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Outlined.Home),
    PROGRAMS("Programs", Icons.Outlined.FitnessCenter),
    STATS("Stats", Icons.Outlined.BarChart),
    PROFILE("Profile", Icons.Outlined.PersonOutline)
}

internal enum class ProgramFilter(val label: String) {
    GYM("Gym"),
    RUN("Lari")
}

internal enum class StatsRange(val label: String) {
    DAY("DAY"),
    WEEK("WEEK"),
    MONTH("MONTH"),
    YEAR("YEAR")
}

internal enum class GoalOption(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val mappedGoal: FitnessGoal
) {
    BUILD_MUSCLE(
        "Build Muscle",
        "Fokus pada hipertrofi dan kekuatan.",
        Icons.Outlined.FitnessCenter,
        FitnessGoal.MUSCLE_GAIN
    ),
    WEIGHT_LOSS(
        "Weight Loss",
        "Kombinasi cardio dan pembakaran lemak.",
        Icons.Outlined.LocalFireDepartment,
        FitnessGoal.WEIGHT_LOSS
    ),
    RUNNING(
        "Stamina / Running",
        "Peningkatan daya tahan kardiovaskular.",
        Icons.AutoMirrored.Outlined.DirectionsRun,
        FitnessGoal.ENDURANCE
    ),
    HYBRID(
        "Hybrid Program",
        "Kombinasi optimal antara latihan beban dan lari.",
        Icons.Outlined.AutoAwesome,
        FitnessGoal.GENERAL_FITNESS
    )
}

internal enum class ExperienceOption(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val mappedLevel: FitnessLevel
) {
    BEGINNER(
        "Pemula",
        "Baru memulai atau kembali setelah istirahat cukup lama.",
        Icons.Outlined.LocalFireDepartment,
        FitnessLevel.BEGINNER
    ),
    INTERMEDIATE(
        "Menengah",
        "Sudah rutin berlatih selama 6-12 bulan dengan intensitas sedang.",
        Icons.AutoMirrored.Outlined.ShowChart,
        FitnessLevel.INTERMEDIATE
    ),
    ADVANCED(
        "Mahir",
        "Berlatih konsisten lebih dari 1 tahun dengan teknik yang baik.",
        Icons.Outlined.EmojiEvents,
        FitnessLevel.ADVANCED
    )
}

internal enum class FrequencyOption(val days: Int, val subtitle: String) {
    ONE(1, "Pemeliharaan ringan"),
    TWO(2, "Kesehatan & keseimbangan"),
    THREE(3, "Program standar"),
    FOUR(4, "Peningkatan performa"),
    FIVE(5, "Transformasi intensif"),
    SIX(6, "Target atlet profesional")
}

internal enum class HealthOption(val title: String, val subtitle: String) {
    HEALTHY("Tidak ada (Sehat)", "Saya siap melakukan aktivitas fisik intensitas tinggi."),
    JOINT("Cedera Sendi/Otot", "Cedera pada bahu, lutut, punggung, atau pergelangan kaki."),
    HEART("Kondisi Jantung", "Riwayat hipertensi atau gangguan ritme jantung."),
    BREATH("Asma/Pernapasan", "Sesak napas saat beraktivitas atau riwayat asma bronkial."),
    OTHER("Lainnya (Tuliskan)", "Masukkan kondisi kesehatan Anda di sini.")
}

internal enum class GymSetTimerPhase {
    IDLE,
    WORK,
    READY_FOR_REST,
    REST,
    FINISHED
}

internal data class GymSetEntryUi(
    val setNumber: Int,
    val repsCompleted: String = "",
    val weightKg: String = "",
    val completed: Boolean = false
)

internal data class GymExerciseUi(
    val name: String,
    val subtitle: String,
    val icon: ImageVector,
    val defaultSets: Int,
    val defaultReps: Int,
    val defaultVolume: Int,
    val setEntries: List<GymSetEntryUi> = emptyList(),
    val activeSetIndex: Int = 0,
    val timerPhase: GymSetTimerPhase = GymSetTimerPhase.IDLE,
    val workSecondsRemaining: Int = 60,
    val restSecondsRemaining: Int = 180,
    val actualSets: String = "",
    val actualReps: String = "",
    val actualWeightKg: String = "",
    val notes: String = "",
    val completed: Boolean = false,
    val expanded: Boolean = false
)

private fun Double.toInputString(): String {
    val normalized = if (this % 1.0 == 0.0) {
        roundToInt().toString()
    } else {
        toString()
    }
    return normalized.replace('.', ',')
}

internal fun GymExerciseUi.suggestedWeightPerSetKg(): Double {
    val denominator = (defaultSets * defaultReps).coerceAtLeast(1)
    return (defaultVolume.toDouble() / denominator).coerceAtLeast(0.0)
}

internal fun GymExerciseUi.ensureSetEntries(): GymExerciseUi {
    if (setEntries.size == defaultSets && setEntries.isNotEmpty()) return this

    val targetSetCount = defaultSets.coerceAtLeast(1)
    val suggestedReps = defaultReps.coerceAtLeast(0).toString()
    val suggestedWeight = suggestedWeightPerSetKg().toInputString()
    val safeEntries = List(targetSetCount) { index ->
        setEntries.getOrNull(index)?.copy(setNumber = index + 1) ?: GymSetEntryUi(
            setNumber = index + 1,
            repsCompleted = suggestedReps,
            weightKg = suggestedWeight
        )
    }
    return copy(setEntries = safeEntries)
}

private fun String.parseSetWeight(): Double? = trim()
    .replace(',', '.')
    .toDoubleOrNull()

internal fun GymExerciseUi.completedSetEntries(): List<GymSetEntryUi> =
    setEntries.filter { entry ->
        entry.completed &&
            (entry.repsCompleted.toIntOrNull() ?: 0) > 0 &&
            (entry.weightKg.parseSetWeight() ?: 0.0) > 0.0
    }

internal fun GymExerciseUi.loggedSetCount(): Int {
    return if (setEntries.isNotEmpty()) {
        completedSetEntries().size
    } else {
        actualSets.toIntOrNull() ?: 0
    }
}

internal fun GymExerciseUi.loggedTotalReps(): Int {
    return if (setEntries.isNotEmpty()) {
        completedSetEntries().sumOf { it.repsCompleted.toIntOrNull() ?: 0 }
    } else {
        (actualSets.toIntOrNull() ?: 0) * (actualReps.toIntOrNull() ?: 0)
    }
}

internal fun GymExerciseUi.loggedAverageReps(): Int {
    return if (setEntries.isNotEmpty()) {
        val completedSets = completedSetEntries()
        if (completedSets.isEmpty()) {
            0
        } else {
            (completedSets.sumOf { it.repsCompleted.toIntOrNull() ?: 0 }.toDouble() / completedSets.size)
                .roundToInt()
                .coerceAtLeast(0)
        }
    } else {
        actualReps.toIntOrNull() ?: 0
    }
}

internal fun GymExerciseUi.loggedTotalVolume(): Int {
    return if (setEntries.isNotEmpty()) {
        completedSetEntries().sumOf { entry ->
            val reps = entry.repsCompleted.toIntOrNull() ?: 0
            val weight = entry.weightKg.parseSetWeight() ?: 0.0
            (reps * weight).roundToInt()
        }
    } else {
        actualWeightKg.parseSetWeight()?.roundToInt() ?: 0
    }
}

internal fun GymExerciseUi.hasExerciseDraft(): Boolean {
    return setEntries.any { it.repsCompleted.isNotBlank() || it.weightKg.isNotBlank() || it.completed } ||
        actualSets.isNotBlank() ||
        actualWeightKg.isNotBlank() ||
        completed
}

internal fun GymExerciseUi.withDerivedMetrics(): GymExerciseUi {
    if (setEntries.isEmpty()) return this

    val completedSets = completedSetEntries()
    if (completedSets.isEmpty()) {
        return copy(
            actualSets = "",
            actualReps = "",
            actualWeightKg = ""
        )
    }

    val totalSets = completedSets.size
    val totalReps = completedSets.sumOf { it.repsCompleted.toIntOrNull() ?: 0 }
    val averageReps = (totalReps.toDouble() / totalSets).roundToInt().coerceAtLeast(0)
    val totalVolume = completedSets.sumOf { entry ->
        val reps = entry.repsCompleted.toIntOrNull() ?: 0
        val weight = entry.weightKg.parseSetWeight() ?: 0.0
        (reps * weight).roundToInt()
    }

    return copy(
        actualSets = totalSets.toString(),
        actualReps = averageReps.toString(),
        actualWeightKg = totalVolume.toString()
    )
}

private fun Int.toTimerLabel(): String = "%02d:%02d".format(this / 60, this % 60)

internal fun GymExerciseUi.primarySetActionLabel(): String = when (timerPhase) {
    GymSetTimerPhase.IDLE -> {
        val nextSet = (activeSetIndex + 1).coerceAtLeast(1)
        if (nextSet <= 1) "Mulai" else "Mulai Set $nextSet"
    }
    GymSetTimerPhase.WORK -> "Latihan ${workSecondsRemaining.coerceAtLeast(0).toTimerLabel()}"
    GymSetTimerPhase.READY_FOR_REST -> "Istirahat"
    GymSetTimerPhase.REST -> "Istirahat ${restSecondsRemaining.coerceAtLeast(0).toTimerLabel()}"
    GymSetTimerPhase.FINISHED -> "Selesai"
}

internal fun GymExerciseUi.primarySetActionEnabled(): Boolean {
    return !completed && timerPhase != GymSetTimerPhase.WORK && timerPhase != GymSetTimerPhase.REST
}

internal fun GymExerciseUi.timerStatusLabel(): String? = when {
    completed -> "Semua set untuk $name sudah selesai."
    timerPhase == GymSetTimerPhase.WORK -> "Set ${activeSetIndex + 1} sedang berjalan • ${workSecondsRemaining.coerceAtLeast(0).toTimerLabel()}"
    timerPhase == GymSetTimerPhase.READY_FOR_REST -> "Set ${activeSetIndex + 1} selesai. Isi hasil set lalu tekan Istirahat."
    timerPhase == GymSetTimerPhase.REST -> "Recovery set ${activeSetIndex + 1} • ${restSecondsRemaining.coerceAtLeast(0).toTimerLabel()}"
    else -> "Isi hasil tiap set, lalu mulai timer kerja 1 menit untuk set berikutnya."
}

internal fun GymExerciseUi.isSetEditable(setIndex: Int): Boolean {
    if (completed) return false
    if (setIndex != activeSetIndex) return false
    return when (timerPhase) {
        GymSetTimerPhase.IDLE,
        GymSetTimerPhase.READY_FOR_REST -> true
        GymSetTimerPhase.WORK,
        GymSetTimerPhase.REST,
        GymSetTimerPhase.FINISHED -> false
    }
}

internal enum class ExerciseAiStatus(val label: String) {
    BELOW_TARGET("Belum Capai Goal"),
    ON_TARGET("Goal Tercapai"),
    ABOVE_TARGET("Melebihi Goal")
}

internal data class ExerciseAiFeedbackUi(
    val status: ExerciseAiStatus,
    val title: String,
    val body: String,
    val accent: Color,
    val background: Color
)

internal data class SessionAiSummaryUi(
    val title: String,
    val body: String,
    val breakdown: String,
    val optimization: String,
    val nextStep: String
)

internal fun GymExerciseUi.toAiFeedback(): ExerciseAiFeedbackUi? {
    val actualSetsValue = loggedSetCount().takeIf { it > 0 } ?: actualSets.toIntOrNull() ?: return null
    val actualRepsValue = loggedAverageReps().takeIf { it > 0 } ?: actualReps.toIntOrNull() ?: return null
    val actualWork = loggedTotalReps().takeIf { it > 0 } ?: (actualSetsValue * actualRepsValue)
    if (actualSetsValue <= 0 || actualRepsValue <= 0 || actualWork <= 0) return null

    val targetWork = (defaultSets * defaultReps).coerceAtLeast(1)

    return when {
        actualWork < targetWork -> ExerciseAiFeedbackUi(
            status = ExerciseAiStatus.BELOW_TARGET,
            title = "AI melihat volume masih di bawah goal.",
            body = "Target latihan ini ${defaultSets} set x ${defaultReps} reps, sedangkan hasil Anda baru $actualSetsValue set x $actualRepsValue reps. Coba tambah jeda istirahat atau turunkan beban sedikit agar target berikutnya lebih realistis.",
            accent = Color(0xFF0E7D8D),
            background = AquaBright.copy(alpha = 0.22f)
        )
        actualWork == targetWork -> ExerciseAiFeedbackUi(
            status = ExerciseAiStatus.ON_TARGET,
            title = "AI menilai target berhasil diselesaikan.",
            body = "Volume $actualSetsValue set x $actualRepsValue reps sudah pas dengan goal latihan ini. Pertahankan tempo dan teknik karena beban saat ini terlihat cukup ideal untuk progres stabil.",
            accent = OceanBlue,
            background = Color(0xFFEAF1FF)
        )
        else -> ExerciseAiFeedbackUi(
            status = ExerciseAiStatus.ABOVE_TARGET,
            title = "AI melihat Anda melewati goal latihan.",
            body = "Hasil $actualSetsValue set x $actualRepsValue reps sudah melampaui target ${defaultSets} x ${defaultReps}. Jika teknik tetap rapi dan recovery bagus, sesi berikutnya bisa naikkan beban 2.5-5% atau tambah 1 set kecil.",
            accent = Color(0xFF0A8673),
            background = Color(0xFFE8FFF7)
        )
    }
}

internal fun List<GymExerciseUi>.toSessionAiSummary(): SessionAiSummaryUi {
    val feedbackItems = mapNotNull { it.toAiFeedback() }
    if (feedbackItems.isEmpty()) {
        return SessionAiSummaryUi(
            title = "AI menunggu data sesi yang lebih lengkap.",
            body = "Isi set dan repetisi pada latihan utama agar Hybrid Fit bisa menilai apakah performa Anda berada di bawah target, sesuai goal, atau sudah melampauinya.",
            breakdown = "Belum ada evaluasi per exercise.",
            optimization = "Rekomendasi berikutnya akan aktif setelah data latihan lengkap.",
            nextStep = "Lengkapi logging exercise"
        )
    }

    val belowCount = feedbackItems.count { it.status == ExerciseAiStatus.BELOW_TARGET }
    val onCount = feedbackItems.count { it.status == ExerciseAiStatus.ON_TARGET }
    val aboveCount = feedbackItems.count { it.status == ExerciseAiStatus.ABOVE_TARGET }
    val totalCount = feedbackItems.size

    return when {
        aboveCount >= belowCount && aboveCount > onCount -> SessionAiSummaryUi(
            title = "AI melihat performa Anda melampaui goal.",
            body = "Ada $aboveCount dari $totalCount exercise yang berhasil melewati target. Ini sinyal bagus, tetapi kualitas teknik dan recovery tetap perlu dijaga agar progres agresif tetap aman.",
            breakdown = "$belowCount di bawah goal • $onCount sesuai goal • $aboveCount di atas goal",
            optimization = "Rekomendasi besok: bila recovery baik, naikkan beban 2.5-5% pada latihan utama atau tambah 1 set kecil.",
            nextStep = "Naik bertahap dan cek recovery"
        )
        belowCount > onCount -> SessionAiSummaryUi(
            title = "AI melihat beberapa goal belum tercapai.",
            body = "Ada $belowCount dari $totalCount exercise yang masih di bawah target. Tidak apa-apa, fokus pada teknik, napas, dan pemulihan agar volume berikutnya lebih realistis dicapai.",
            breakdown = "$belowCount di bawah goal • $onCount sesuai goal • $aboveCount di atas goal",
            optimization = "Rekomendasi besok: pertahankan pola gerak, tetapi kurangi beban 5-10% atau tambah jeda istirahat sebelum mengejar volume yang sama.",
            nextStep = "Pulihkan lalu ulang dengan lebih rapi"
        )
        else -> SessionAiSummaryUi(
            title = "AI menilai sesi Anda sesuai goal.",
            body = "Mayoritas exercise sudah pas dengan target latihan hari ini. Ini tanda volume, beban, dan ritme kerja berada di zona yang sehat untuk progres yang stabil.",
            breakdown = "$belowCount di bawah goal • $onCount sesuai goal • $aboveCount di atas goal",
            optimization = "Rekomendasi besok: pertahankan beban dan ulang volume yang sama untuk mengunci konsistensi sebelum menambah intensitas.",
            nextStep = "Pertahankan volume saat ini"
        )
    }
}

internal data class PlannerEditorUi(
    val sessionId: Long? = null,
    val title: String = "",
    val category: WorkoutCategory = WorkoutCategory.STRENGTH,
    val programVariant: String = "",
    val dayOfWeek: java.time.DayOfWeek = java.time.DayOfWeek.MONDAY,
    val durationMinutes: String = "",
    val targetDistanceKm: String = "",
    val targetSets: String = "",
    val targetReps: String = "",
    val restSeconds: String = "",
    val notes: String = ""
)

internal data class RecoveryDraftUi(
    val sleepHours: String = "",
    val recoveryScore: String = "",
    val energyLevel: String = "",
    val sorenessLevel: String = "",
    val notes: String = ""
)

internal data class RunSegmentUi(
    val segmentNumber: Int,
    val distanceMeters: String = "400",
    val minutes: String = "",
    val seconds: String = "",
    val completed: Boolean = false,
    val isResting: Boolean = false,
    val restSecondsRemaining: Int = 240
)

internal data class RunLogDraftUi(
    val logId: Long? = null,
    val distanceKm: String = "",
    val durationMinutes: String = "",
    val durationSeconds: String = "",
    val effortScore: String = "",
    val notes: String = "",
    val segmentEntries: List<RunSegmentUi> = emptyList(),
    val activeSegmentIndex: Int = 0
)

internal fun RunLogDraftUi.ensureSegmentEntries(targetDistanceKm: Double?): RunLogDraftUi {
    if (segmentEntries.isNotEmpty()) return this

    val resolvedDistanceKm = distanceKm.trim()
        .replace(',', '.')
        .toDoubleOrNull()
        ?: targetDistanceKm
        ?: 4.0
    val segmentCount = ((resolvedDistanceKm * 1000.0) / 400.0)
        .roundToInt()
        .coerceAtLeast(1)

    return copy(
        segmentEntries = List(segmentCount) { index ->
            RunSegmentUi(segmentNumber = index + 1)
        }
    )
}

internal fun RunLogDraftUi.completedSegments(): List<RunSegmentUi> =
    segmentEntries.filter { segment ->
        segment.completed &&
            (segment.minutes.toIntOrNull() ?: -1) >= 0 &&
            (segment.seconds.toIntOrNull() ?: -1) in 0..59
    }

internal fun RunLogDraftUi.isSegmentEditable(segmentIndex: Int): Boolean =
    segmentIndex == activeSegmentIndex && segmentIndex < segmentEntries.size

internal fun RunLogDraftUi.totalDistanceKmFromSegments(): Double =
    completedSegments().sumOf { it.distanceMeters.toIntOrNull() ?: 0 } / 1000.0

internal fun RunLogDraftUi.totalDurationSecondsFromSegments(): Int =
    completedSegments().sumOf { segment ->
        ((segment.minutes.toIntOrNull() ?: 0) * 60) + (segment.seconds.toIntOrNull() ?: 0)
    }

internal fun RunLogDraftUi.averagePaceLabelFromSegments(): String {
    val distanceKm = totalDistanceKmFromSegments()
    val totalSeconds = totalDurationSecondsFromSegments()
    if (distanceKm <= 0.0 || totalSeconds <= 0) return "-"
    val paceSeconds = (totalSeconds / distanceKm).roundToInt()
    return "%d'%02d\"".format(paceSeconds / 60, paceSeconds % 60)
}

internal fun RunLogDraftUi.totalManualDurationSeconds(): Int? {
    val minutesValue = durationMinutes.toIntOrNull() ?: return null
    val secondsValue = durationSeconds.toIntOrNull() ?: 0
    if (secondsValue !in 0..59) return null
    return (minutesValue * 60) + secondsValue
}

internal fun RunLogDraftUi.manualPaceLabel(): String {
    val distance = distanceKm.trim()
        .replace(',', '.')
        .toDoubleOrNull()
        ?: return "-"
    val totalSeconds = totalManualDurationSeconds() ?: return "-"
    if (distance <= 0.0 || totalSeconds <= 0) return "-"
    val paceSeconds = (totalSeconds / distance).roundToInt()
    return "%d'%02d\"".format(paceSeconds / 60, paceSeconds % 60)
}

internal fun RunLogDraftUi.segmentSummaryText(): String {
    val completedCount = completedSegments().size
    return when {
        segmentEntries.isEmpty() -> "Belum ada segmen disiapkan."
        completedCount >= segmentEntries.size -> "Semua segmen selesai."
        else -> "Segmen ${activeSegmentIndex + 1} aktif • ${segmentEntries.size - completedCount} segmen tersisa."
    }
}

internal fun RunSegmentUi.paceLabel(): String {
    val totalSeconds = ((minutes.toIntOrNull() ?: 0) * 60) + (seconds.toIntOrNull() ?: 0)
    val distanceValue = distanceMeters.toIntOrNull() ?: 0
    if (distanceValue <= 0 || totalSeconds <= 0) return "-"
    val paceSeconds = ((totalSeconds * 1000.0) / distanceValue).roundToInt()
    return "%d'%02d\"".format(paceSeconds / 60, paceSeconds % 60)
}

internal enum class CoachProgramProposalStatus {
    PENDING,
    APPLIED,
    CANCELLED
}

internal data class CoachProgramProposalUi(
    val goal: FitnessGoal,
    val preferredFocus: WorkoutCategory,
    val workoutDaysPerWeek: Int,
    val sessionDurationMinutes: Int,
    val programLabel: String,
    val summaryLabel: String,
    val status: CoachProgramProposalStatus = CoachProgramProposalStatus.PENDING
)

internal data class CoachMessage(
    val text: String,
    val fromCoach: Boolean,
    val timeLabel: String,
    val programProposal: CoachProgramProposalUi? = null
)

internal data class AchievementCardUi(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val tint: Color,
    val background: Color
)

internal data class NotificationItemUi(
    val title: String,
    val subtitle: String,
    val body: String,
    val timeLabel: String,
    val icon: ImageVector,
    val tint: Color,
    val background: Color
)

internal data class FeatureInfoUi(
    val title: String = "",
    val subtitle: String = "",
    val body: String = "",
    val highlights: List<String> = emptyList(),
    val icon: ImageVector = Icons.Outlined.AutoAwesome,
    val accent: Color = OceanBlue,
    val showHeroCard: Boolean = true,
    val primaryActionLabel: String = "Mengerti",
    val countdownSeconds: Int = 0,
    val routineSteps: List<RoutineStepUi> = emptyList(),
    val intervalSegments: List<RunSegmentUi> = emptyList()
)

internal data class RoutineStepUi(
    val title: String,
    val subtitle: String,
    val durationSeconds: Int = 30
)

