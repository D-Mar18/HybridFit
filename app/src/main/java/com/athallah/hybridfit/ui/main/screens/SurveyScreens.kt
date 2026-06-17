@file:Suppress("UNUSED_PARAMETER")

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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.HelpOutline
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
import androidx.compose.material.icons.outlined.ShowChart
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
import androidx.compose.ui.text.input.KeyboardType
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

@Composable
internal fun SurveyGoalScreen(
    selectedGoal: GoalOption,
    onSelect: (GoalOption) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    SurveyContainer(
        modifier = modifier,
        progressLabelStart = "Langkah 1 dari 5",
        progress = 0.2f,
        title = "Apa tujuan utama\nAnda?",
        subtitle = "AI kami akan menyesuaikan volume dan intensitas latihan berdasarkan pilihan Anda.",
        onBack = onBack,
        onMenu = onMenu
    ) {
        GoalOption.entries.forEach { option ->
            SurveyOptionCard(
                selected = option == selectedGoal,
                title = option.title,
                subtitle = option.subtitle,
                icon = option.icon,
                showTrailingIndicator = false,
                onClick = { onSelect(option) }
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        PrimaryGradientButton(text = "Lanjut", onClick = onNext)
    }
}

@Composable
internal fun SurveyLevelScreen(
    selectedExperience: ExperienceOption,
    onSelect: (ExperienceOption) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    SurveyContainer(
        modifier = modifier,
        progressLabelStart = "Langkah 2 dari 5",
        progress = 0.4f,
        title = "Apa tingkat\npengalaman olahraga\nAnda?",
        subtitle = "Pilih yang paling sesuai dengan aktivitas fisik Anda dalam setahun terakhir untuk membantu OceanFit AI menyusun program yang lebih presisi.",
        onBack = onBack,
        onMenu = onMenu
    ) {
        ExperienceOption.entries.forEach { option ->
            SurveyOptionCard(
                selected = option == selectedExperience,
                title = option.title,
                subtitle = option.subtitle,
                icon = option.icon,
                onClick = { onSelect(option) }
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SecondaryOutlineButton(
                text = "Kembali",
                onClick = onBack,
                modifier = Modifier.weight(1f)
            )
            PrimaryGradientButton(
                text = "Lanjutkan",
                onClick = onNext,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
internal fun SurveyFrequencyScreen(
    selectedFrequency: FrequencyOption,
    onSelect: (FrequencyOption) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    SurveyContainer(
        modifier = modifier,
        progressLabelStart = "Langkah 3 dari 5",
        progress = 0.6f,
        title = "Berapa kali Anda ingin\nberlatih dalam\nseminggu?",
        subtitle = "Sesuaikan jadwal dengan gaya hidup Anda untuk hasil yang maksimal.",
        onBack = onBack,
        onMenu = onMenu
    ) {
        FrequencyOption.entries.forEach { option ->
            FrequencyCard(
                selected = option == selectedFrequency,
                title = "${option.days} Hari",
                subtitle = option.subtitle,
                onClick = { onSelect(option) }
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SecondaryOutlineButton(
                text = "Kembali",
                onClick = onBack,
                modifier = Modifier.weight(1f)
            )
            PrimaryGradientButton(
                text = "Lanjutkan",
                onClick = onNext,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
internal fun SurveyHealthScreen(
    selectedHealth: HealthOption,
    healthNotes: String,
    onSelect: (HealthOption) -> Unit,
    onNotesChange: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    SurveyContainer(
        modifier = modifier,
        progressLabelStart = "Langkah 4 dari 5",
        progress = 0.8f,
        title = "Status Kesehatan",
        subtitle = "Keamanan Anda adalah prioritas kami. Beritahu kami jika Anda memiliki kondisi kesehatan atau batasan fisik tertentu.",
        onBack = onBack,
        onMenu = onMenu
    ) {
        HealthOption.entries.forEach { option ->
            HealthOptionCard(
                selected = option == selectedHealth,
                title = option.title,
                subtitle = option.subtitle,
                isOther = option == HealthOption.OTHER,
                notes = if (option == HealthOption.OTHER) healthNotes else "",
                onNotesChange = onNotesChange,
                onClick = { onSelect(option) }
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Spacer(modifier = Modifier.height(18.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SecondaryOutlineButton(
                text = "Kembali",
                onClick = onBack,
                modifier = Modifier.weight(1f)
            )
            PrimaryGradientButton(
                text = "Lanjutkan",
                onClick = onNext,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
internal fun SurveyBodyMetricsScreen(
    age: String,
    weightKg: String,
    heightCm: String,
    onAgeChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onFinish: () -> Unit,
    onBack: () -> Unit,
    onMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    SurveyContainer(
        modifier = modifier,
        progressLabelStart = "Langkah 5 dari 5",
        progress = 1f,
        title = "Berapa umur, berat,\ndan tinggi Anda?",
        subtitle = "Data tubuh ini dipakai Hybrid Fit untuk menyusun beban awal, set, repetisi, serta durasi latihan yang lebih masuk akal untuk Anda.",
        onBack = onBack,
        onMenu = onMenu
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = SurfaceWhite,
            border = BorderStroke(1.dp, BorderSoft),
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SurveyMetricField(
                    label = "Umur",
                    value = age,
                    placeholder = "Contoh: 21 tahun",
                    keyboardType = KeyboardType.Number,
                    onValueChange = onAgeChange
                )
                SurveyMetricField(
                    label = "Berat Badan (kg)",
                    value = weightKg,
                    placeholder = "Contoh: 62,5 kg",
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = onWeightChange
                )
                SurveyMetricField(
                    label = "Tinggi Badan (cm)",
                    value = heightCm,
                    placeholder = "Contoh: 170 cm",
                    keyboardType = KeyboardType.Number,
                    onValueChange = onHeightChange
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SecondaryOutlineButton(
                text = "Kembali",
                onClick = onBack,
                modifier = Modifier.weight(1f)
            )
            PrimaryGradientButton(
                text = "Buat Program",
                onClick = onFinish,
                modifier = Modifier.weight(1f)
            )
        }
    }
}


@Composable
internal fun SurveyContainer(
    modifier: Modifier,
    progressLabelStart: String,
    progressLabelEnd: String? = null,
    progress: Float,
    title: String,
    subtitle: String,
    onBack: () -> Unit,
    onMenu: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(16.dp, 10.dp, 16.dp, 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CenterTopBar(
                title = "Hybrid Fit",
                onBack = onBack,
                onMore = onMenu
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = progressLabelStart,
                    style = MaterialTheme.typography.labelLarge,
                    color = OceanBlue
                )
                if (progressLabelEnd != null) {
                    Text(
                        text = progressLabelEnd,
                        style = MaterialTheme.typography.labelLarge,
                        color = TextSecondary
                    )
                }
            }
        }
        item {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(12.dp)),
                color = OceanBlue,
                trackColor = SurfaceTint
            )
        }
        item {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary
            )
        }
        item {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp), content = content)
        }
    }
}

@Composable
private fun SurveyMetricField(
    label: String,
    value: String,
    placeholder: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        placeholder = {
            Text(
                text = placeholder,
                color = TextSecondary.copy(alpha = 0.65f)
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SurfaceWhite,
            unfocusedContainerColor = SurfaceWhite,
            focusedBorderColor = OceanBlue,
            unfocusedBorderColor = BorderSoft,
            cursorColor = OceanBlue,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
        )
    )
}

@Composable
internal fun SurveyOptionCard(
    selected: Boolean,
    title: String,
    subtitle: String,
    icon: ImageVector,
    showTrailingIndicator: Boolean = true,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = if (selected) Color(0xFFEAFBFF) else SurfaceWhite,
        border = BorderStroke(1.dp, if (selected) AquaTeal else BorderSoft),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (selected) AquaBright.copy(alpha = 0.18f) else SurfaceTint),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = if (selected) AquaTeal else OceanBlue)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                Text(text = subtitle, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            if (showTrailingIndicator) {
                SelectionCircle(selected = selected)
            }
        }
    }
}

@Composable
internal fun FrequencyCard(
    selected: Boolean,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = if (selected) Color(0xFFEAFBFF) else SurfaceWhite,
        border = BorderStroke(1.dp, if (selected) AquaTeal else BorderSoft),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            }
            SelectionCheckCircle(selected = selected)
        }
    }
}

@Composable
internal fun SelectionCircle(selected: Boolean) {
    Box(
        modifier = Modifier
            .size(22.dp)
            .border(2.dp, if (selected) AquaTeal else BorderSoft, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(if (selected) AquaTeal.copy(alpha = 0.14f) else Color.Transparent)
        )
    }
}

@Composable
internal fun SelectionCheckCircle(selected: Boolean) {
    Box(
        modifier = Modifier
            .size(26.dp)
            .clip(CircleShape)
            .background(if (selected) AquaTeal else Color.Transparent)
            .border(2.dp, if (selected) AquaTeal else BorderSoft, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
internal fun HealthOptionCard(
    selected: Boolean,
    title: String,
    subtitle: String,
    isOther: Boolean,
    notes: String,
    onNotesChange: (String) -> Unit,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = if (selected) Color(0xFFEAFBFF) else SurfaceWhite,
        border = BorderStroke(1.dp, if (selected) AquaTeal else BorderSoft),
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = title, style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                    if (!isOther) {
                        Text(text = subtitle, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                    }
                }
                SelectionCircle(selected = selected)
            }
            if (isOther) {
                OutlinedTextField(
                    value = notes,
                    onValueChange = onNotesChange,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selected,
                    placeholder = {
                        Text(
                            text = subtitle,
                            color = TextSecondary.copy(alpha = 0.7f)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceWhite,
                        unfocusedContainerColor = SurfaceWhite,
                        disabledContainerColor = SurfaceWhite,
                        focusedBorderColor = BorderSoft,
                        unfocusedBorderColor = BorderSoft,
                        disabledBorderColor = BorderSoft.copy(alpha = 0.55f),
                        cursorColor = OceanBlue,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        disabledTextColor = TextPrimary
                    )
                )
            }
        }
    }
}

