@file:Suppress("UNUSED_PARAMETER")

package com.athallah.hybridfit.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Edit
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
import androidx.compose.material.icons.outlined.PhotoCamera
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
internal fun HomeDashboardScreen(
    snapshot: DashboardSnapshot,
    displayName: String,
    hasProgram: Boolean,
    isProgramCreationInProgress: Boolean,
    onMetricClick: (String) -> Unit,
    onOpenHero: () -> Unit,
    onCreateProgram: () -> Unit,
    onMenu: () -> Unit,
    onAiHub: () -> Unit,
    onNotifications: () -> Unit,
    onAccount: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasMetricActivity = snapshot.recentLogs.isNotEmpty()
    val featuredProgramFilter = snapshot.homeFeaturedProgramFilter()
    val featuredSession = snapshot.sessionForFilter(featuredProgramFilter)
    val featuredTrainingTitle = snapshot.homeFeaturedTrainingTitle()
    val featuredTrainingDescription = snapshot.homeFeaturedTrainingDescription()
    val recentActivityLogs = snapshot.recentLogsForFilter(featuredProgramFilter)
    val featuredSessionCompletedToday = snapshot.recentLogs.any { log ->
        log.sessionId == featuredSession.id &&
            log.performedOn == LocalDate.now() &&
            (log.wasCompleted || log.completionPercent >= 100)
    }
    val metrics = listOf(
        DashboardMetric(
            title = "Langkah",
            value = snapshot.estimatedSteps().formatThousands(),
            icon = Icons.AutoMirrored.Outlined.DirectionsRun,
            color = OceanBlue,
            progress = if (hasMetricActivity) (snapshot.estimatedSteps() / 8000f).coerceIn(0f, 1f) else 0f
        ),
        DashboardMetric(
            title = "Kalori",
            value = snapshot.estimatedCalories().toString(),
            icon = Icons.Outlined.LocalFireDepartment,
            color = AquaTeal,
            progress = if (hasMetricActivity) (snapshot.estimatedCalories() / 600f).coerceIn(0f, 1f) else 0f
        ),
        DashboardMetric(
            title = "Skor",
            value = "${snapshot.sleepQualityScore()}%",
            icon = Icons.Outlined.CheckCircle,
            color = Color(0xFF1FB4D6),
            progress = if (hasMetricActivity) (snapshot.sleepQualityScore() / 100f).coerceIn(0f, 1f) else 0f
        )
    )
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = SurfaceWhite,
                    border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
                ) {
                    IconButton(onClick = onAiHub) {
                        Icon(
                            imageVector = Icons.Outlined.AutoAwesome,
                            contentDescription = "AI Coach",
                            tint = AquaTeal
                        )
                    }
                }
                Text(
                    text = "Hybrid Fit",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = OceanBlue
                )
                Surface(
                    shape = CircleShape,
                    color = SurfaceWhite,
                    border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
                ) {
                    IconButton(onClick = onMenu) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "Menu",
                            tint = OceanBlue
                        )
                    }
                }
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Halo, ${displayName.substringBefore(" ")}",
                    style = MaterialTheme.typography.displaySmall.copy(fontSize = 28.sp, lineHeight = 34.sp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Siap menaklukkan hari ini?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary
                )
            }
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(metrics) { metric ->
                    MetricCard(
                        metric = metric,
                        onClick = { onMetricClick(metric.title) }
                    )
                }
            }
        }
        item {
            SectionHeader("Latihan Hari Ini")
        }
        item {
            if (hasProgram) {
                FeaturedTrainingCard(
                    title = featuredTrainingTitle,
                    description = featuredTrainingDescription,
                    buttonText = if (featuredSessionCompletedToday) "Program Sudah Selesai" else "Mulai Sekarang",
                    icon = if (featuredProgramFilter == ProgramFilter.RUN) {
                        Icons.AutoMirrored.Outlined.DirectionsRun
                    } else {
                        Icons.Outlined.FitnessCenter
                    },
                    enabled = !featuredSessionCompletedToday,
                    onClick = onOpenHero
                )
            } else {
                EmptyTrainingCard(
                    title = "Belum Ada Program",
                    description = "Akun baru Anda masih kosong. Buat program pertama dulu agar latihan harian dan progress mulai aktif.",
                    buttonText = if (isProgramCreationInProgress) "Membuat Program..." else "Buat Program Pertama",
                    onClick = onCreateProgram
                )
            }
        }
        if (recentActivityLogs.isNotEmpty()) {
            item {
                SectionHeader("Aktivitas Terakhir")
            }
            items(recentActivityLogs.take(3)) { log ->
                ActivityRow(log = log)
            }
        }
    }
}

@Composable
internal fun ProgramsDashboardScreen(
    snapshot: DashboardSnapshot,
    selectedFilter: ProgramFilter,
    availableFilters: List<ProgramFilter>,
    hasProgram: Boolean,
    isProgramCreationInProgress: Boolean,
    onFilterSelected: (ProgramFilter) -> Unit,
    onEditProgram: () -> Unit,
    onOpenProgram: () -> Unit,
    onStartProgram: () -> Unit,
    modifier: Modifier = Modifier
) {
    val featureSession = snapshot.sessionForFilter(selectedFilter)
    val difficultyLabel = when (selectedFilter) {
        ProgramFilter.GYM -> "Mahir"
        ProgramFilter.RUN -> "Menengah"
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, 8.dp, 16.dp, 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CenterTopBar(
                title = "Hybrid Fit",
                onBack = {},
                showLeftIcon = false,
                onMore = onEditProgram,
                rightIcon = Icons.Outlined.Edit
            )
        }
        item {
            FilterRow(
                selected = selectedFilter,
                availableFilters = availableFilters,
                onSelected = onFilterSelected
            )
        }
        item {
            if (hasProgram) {
                ProgramPreviewCard(
                    snapshot = snapshot,
                    session = featureSession,
                    difficultyLabel = difficultyLabel,
                    onClick = onOpenProgram
                )
            } else {
                DashboardEmptyCard(
                    title = "Program Masih Kosong",
                    body = "Belum ada sesi gym atau lari untuk akun ini. Gunakan tombol di bawah untuk membuat program pertama."
                )
            }
        }
        if (!hasProgram || snapshot.sessionProgress(featureSession) < 1f) {
            item {
                PrimaryGradientButton(
                    text = if (hasProgram) {
                        "Mulai Sekarang"
                    } else if (isProgramCreationInProgress) {
                        "Membuat Program..."
                    } else {
                        "Buat Program Pertama"
                    },
                    onClick = onStartProgram
                )
            }
        }
    }
}

@Composable
internal fun StatsDashboardScreen(
    snapshot: DashboardSnapshot,
    selectedRange: StatsRange,
    onRangeSelected: (StatsRange) -> Unit,
    onBack: () -> Unit,
    onCalendar: () -> Unit,
    onShare: () -> Unit,
    onAchievement: () -> Unit,
    onViewAll: () -> Unit,
    onOverviewClick: () -> Unit,
    onComparisonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val achievements = snapshot.achievementCards()
    val hasStatsData = snapshot.recentLogs.isNotEmpty()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Analysis",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Surface(shape = CircleShape, color = SurfaceMuted) {
                    IconButton(onClick = onCalendar) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarToday,
                            contentDescription = "Calendar",
                            tint = OceanBlue
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Surface(shape = CircleShape, color = OceanBlue) {
                    IconButton(onClick = onShare) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Share",
                            tint = Color.White
                        )
                    }
                }
            }
        }
        item {
            TimeFilterRow(selected = selectedRange, onSelected = onRangeSelected)
        }
        item {
            Text(
                    text = "Ringkasan Aktivitas",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
        }
        item {
            OverviewStatCard(
                snapshot = snapshot,
                range = selectedRange,
                onClick = onOverviewClick,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item {
            SectionHeader(
                title = "Achievements",
                actionLabel = "Lihat Semua",
                onActionClick = onViewAll,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        if (achievements.isEmpty()) {
            item {
                DashboardEmptyCard(
                    title = "Belum ada achievement",
                    body = "Selesaikan latihan pertama Anda untuk mulai membuka badge dan milestone."
                )
            }
        } else {
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(achievements) { item ->
                        AchievementCard(item = item, onClick = onAchievement)
                    }
                }
            }
        }
        item {
            Text(
                text = "Perbandingan",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
        }
        item {
            ComparisonRow(
                icon = Icons.AutoMirrored.Outlined.DirectionsRun,
                title = "Lari",
                subtitle = "",
                value = if (hasStatsData) {
                    val delta = snapshot.runningComparison(selectedRange)
                    "${if (delta >= 0) "+" else ""}${"%.1f".format(delta)} km"
                } else {
                    "0.0 km"
                },
                onClick = null,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item {
            ComparisonRow(
                icon = Icons.Outlined.FitnessCenter,
                title = "Beban",
                subtitle = "",
                value = if (hasStatsData) {
                    val delta = snapshot.strengthComparison(selectedRange)
                    "${if (delta >= 0) "+" else ""}${delta.formatThousands()} kg"
                } else {
                    "0 kg"
                },
                onClick = null,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
internal fun ProfileDashboardScreen(
    snapshot: DashboardSnapshot,
    displayName: String,
    goalLabel: String,
    experienceLabel: String,
    avatarUrl: String?,
    onNotification: () -> Unit,
    onProfileHeroClick: () -> Unit,
    onProfileStatClick: (String) -> Unit,
    onSettingClick: (String) -> Unit,
    isLogoutInProgress: Boolean,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val settings = listOf("Akun", "Pengaturan")
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BrandHeader(icon = Icons.AutoMirrored.Outlined.ShowChart)
                IconButton(onClick = onNotification) {
                    Surface(
                        shape = CircleShape,
                        color = SurfaceWhite,
                        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.NotificationsNone,
                            contentDescription = "Notifikasi",
                            tint = TextSecondary,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }
        item {
            ProfileHero(
                name = displayName,
                goalLabel = goalLabel,
                experienceLabel = experienceLabel,
                avatarUrl = avatarUrl,
                onClick = onProfileHeroClick
            )
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CompactStatCard(
                    title = "Total sesi",
                    value = snapshot.progressSummary.totalSessionsLogged.toString(),
                    icon = Icons.Outlined.Schedule,
                    modifier = Modifier.weight(1f),
                    onClick = null
                )
                CompactStatCard(
                    title = "Streak hari",
                    value = snapshot.estimatedStreakDays().toString(),
                    icon = Icons.Outlined.LocalFireDepartment,
                    modifier = Modifier.weight(1f),
                    onClick = null
                )
            }
        }
        item {
            CompactStatCard(
                title = "Pencapaian",
                value = snapshot.achievementCards().size.toString(),
                icon = Icons.Outlined.EmojiEvents,
                modifier = Modifier.fillMaxWidth(),
                onClick = null
            )
        }
        item {
            Text(
                text = "PENGATURAN",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
        }
        item {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = SurfaceWhite,
                border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    settings.forEachIndexed { index, item ->
                        SettingsRow(title = item, onClick = { onSettingClick(item) })
                        if (index < settings.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = BorderSoft.copy(alpha = 0.45f)
                            )
                        }
                    }
                }
            }
        }
        item {
            SecondaryDangerButton(
                text = if (isLogoutInProgress) "Keluar..." else "Keluar",
                onClick = onLogout,
                enabled = !isLogoutInProgress,
                isLoading = isLogoutInProgress
            )
        }
    }
}


internal data class DashboardMetric(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val color: Color,
    val progress: Float
)

@Composable
internal fun MetricCard(
    metric: DashboardMetric,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(118.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f)),
        shadowElevation = 6.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CircularMetricIcon(icon = metric.icon, color = metric.color, progress = metric.progress)
            Text(
                text = metric.value,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp, lineHeight = 24.sp),
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = metric.title.uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
        }
    }
}

@Composable
internal fun CircularMetricIcon(
    icon: ImageVector,
    color: Color,
    progress: Float
) {
    Box(
        modifier = Modifier.size(52.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = BorderSoft.copy(alpha = 0.45f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 8f, cap = StrokeCap.Round)
            )
            if (progress > 0f) {
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = 360f * progress,
                    useCenter = false,
                    style = Stroke(width = 8f, cap = StrokeCap.Round)
                )
            }
        }
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
internal fun FeaturedTrainingCard(
    title: String,
    description: String,
    buttonText: String,
    icon: ImageVector,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = Color.Transparent,
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFF071521), Color(0xFF0A314F), Color(0xFF0B6C79))
                    )
                )
                .padding(18.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .width(132.dp)
                    .height(108.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF08131F), Color(0xFF174D67), Color(0xFF1C98A8))
                        )
                    )
                    .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(18.dp))
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.08f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.92f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Column(
                modifier = Modifier.height(210.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(text = title, style = MaterialTheme.typography.titleLarge, color = Color.White)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(16.dp))
                PrimaryGradientButton(text = buttonText, onClick = onClick, enabled = enabled)
            }
        }
    }
}

private data class DashboardQuickAction(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val tint: Color,
    val onClick: () -> Unit
)

@Composable
private fun DashboardQuickActionCard(action: DashboardQuickAction) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = action.onClick),
        shape = RoundedCornerShape(22.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f)),
        shadowElevation = 5.dp
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(action.tint.copy(alpha = 0.14f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(action.icon, contentDescription = null, tint = action.tint)
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = action.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Text(
                    text = action.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = TextSecondary
            )
        }
    }
}

@Composable
internal fun EmptyTrainingCard(
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = Color.Transparent,
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFF071521), Color(0xFF0A314F), Color(0xFF0B6C79))
                    )
                )
                .padding(18.dp)
        ) {
            Column(
                modifier = Modifier.height(210.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(text = title, style = MaterialTheme.typography.titleLarge, color = Color.White)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(16.dp))
                PrimaryGradientButton(text = buttonText, onClick = onClick)
            }
        }
    }
}

@Composable
internal fun DashboardEmptyCard(
    title: String,
    body: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
    }
}

@Composable
internal fun ActivityRow(
    log: WorkoutLog
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f)),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceTint),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (log.actualDistanceKm != null) Icons.AutoMirrored.Outlined.DirectionsRun else Icons.Outlined.FitnessCenter,
                    contentDescription = null,
                    tint = OceanBlue
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = log.sessionTitle, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text(text = log.activitySubtitle(), style = MaterialTheme.typography.labelMedium, color = TextSecondary)
            }
        }
    }
}

@Composable
internal fun FilterRow(
    selected: ProgramFilter,
    availableFilters: List<ProgramFilter>,
    onSelected: (ProgramFilter) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        availableFilters.forEach { filter ->
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = if (selected == filter) OceanBlue else SurfaceMuted.copy(alpha = 0.55f),
                shadowElevation = if (selected == filter) 6.dp else 0.dp
            ) {
                Text(
                    text = filter.label,
                    modifier = Modifier
                        .clickable { onSelected(filter) }
                        .padding(horizontal = 22.dp, vertical = 11.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = if (selected == filter) Color.White else TextSecondary
                )
            }
        }
    }
}

@Composable
internal fun ProgramPreviewCard(
    snapshot: DashboardSnapshot,
    session: WorkoutSession,
    difficultyLabel: String,
    onClick: () -> Unit
) {
    val progressValue = snapshot.sessionProgress(session)
    val progressCaption = snapshot.sessionProgressCaption(session)
    val badgeLabel = snapshot.sessionBadgeLabel(session)
    val canOpen = progressValue < 1f

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .let { base -> if (canOpen) base.clickable(onClick = onClick) else base },
        shape = RoundedCornerShape(18.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f)),
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF0A233A), OceanBlueDark, AquaTeal)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (session.category == WorkoutCategory.CARDIO) Icons.AutoMirrored.Outlined.DirectionsRun else Icons.Outlined.FitnessCenter,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White.copy(alpha = 0.96f),
                    shadowElevation = 2.dp
                ) {
                    Text(
                        text = badgeLabel,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = OceanBlue
                    )
                }
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = session.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text(
                    text = "${session.targetDurationMinutes} mnt - $difficultyLabel",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary
                )
                LinearProgressIndicator(
                    progress = { progressValue },
                    modifier = Modifier
                        .fillMaxWidth(0.86f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    color = OceanBlue,
                    trackColor = SurfaceTint
                )
                Text(
                    text = progressCaption,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }
            if (canOpen) {
                Icon(Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = null, tint = TextSecondary)
            }
        }
    }
}

@Composable
internal fun OverviewStatCard(
    snapshot: DashboardSnapshot,
    range: StatsRange,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val activityValue = snapshot.caloriesForRange(range)
    val hasData = snapshot.logsForRange(range).isNotEmpty()
    val growthValue = when (range) {
        StatsRange.DAY -> snapshot.runningComparison(range).roundToInt()
        StatsRange.WEEK -> snapshot.progressSummary.weeklyConsistencyPercent
        StatsRange.MONTH -> snapshot.runningComparison(range).roundToInt()
        StatsRange.YEAR -> snapshot.runningComparison(range).roundToInt()
    }
    val values = snapshot.overviewSeries(range)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .let { base ->
                if (onClick != null) base.clickable(onClick = onClick) else base
            },
        shape = RoundedCornerShape(22.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "${range.label} ACTIVITY",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                    Text(
                        text = "${activityValue.formatThousands()} kcal",
                        style = MaterialTheme.typography.displaySmall,
                        color = OceanBlue
                    )
                }
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (hasData) Color(0xFFDDFBEA) else SurfaceTint
                ) {
                    Text(
                        text = if (hasData) {
                            if (range == StatsRange.WEEK) "+${growthValue}%"
                            else "${if (growthValue >= 0) "+" else ""}$growthValue"
                        } else {
                            "0"
                        },
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (hasData) AquaTeal else TextSecondary
                    )
                }
            }
            androidx.compose.foundation.Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            ) {
                val points = listOf(0.08f, 0.2f, 0.34f, 0.48f, 0.62f, 0.78f, 0.92f)
                val offsets = points.zip(values).map {
                    Offset(size.width * it.first, size.height * (1f - it.second))
                }
                for (index in 0 until offsets.lastIndex) {
                    drawLine(
                        brush = Brush.horizontalGradient(listOf(OceanBlue, AquaTeal)),
                        start = offsets[index],
                        end = offsets[index + 1],
                        strokeWidth = 10f,
                        cap = StrokeCap.Round
                    )
                }
                drawLine(
                    color = BorderSoft,
                    start = Offset(0f, size.height * 0.84f),
                    end = Offset(size.width, size.height * 0.84f),
                    strokeWidth = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                )
            }
        }
    }
}

@Composable
internal fun TimeFilterRow(
    selected: StatsRange,
    onSelected: (StatsRange) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        StatsRange.entries.forEach { range ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = range.label,
                    modifier = Modifier.clickable { onSelected(range) },
                    style = MaterialTheme.typography.labelLarge,
                    color = if (range == selected) OceanBlue else TextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (range == selected) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(3.dp)
                            .background(OceanBlue, RoundedCornerShape(6.dp))
                    )
                }
            }
        }
    }
}

@Composable
internal fun AchievementCard(
    item: AchievementCardUi,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(128.dp)
            .height(132.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(item.background),
                contentAlignment = Alignment.Center
            ) {
                Icon(item.icon, contentDescription = null, tint = item.tint)
            }
            Text(text = item.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Text(text = item.subtitle, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
        }
    }
}

@Composable
internal fun ComparisonRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val isRunning = title == "Lari"
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .let { base ->
                if (onClick != null) base.clickable(onClick = onClick) else base
            },
        shape = RoundedCornerShape(18.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(if (isRunning) AquaTeal else OceanBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = Color.White)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                if (subtitle.isNotBlank()) {
                    Text(text = subtitle, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = value, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                Text(
                    text = if (isRunning) "MENINGKAT" else "STABIL",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isRunning) AquaTeal else OceanBlue
                )
            }
        }
    }
}

@Composable
internal fun ProfileHero(
    name: String,
    goalLabel: String,
    experienceLabel: String,
    avatarUrl: String? = null,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .let { base ->
                if (onClick != null) base.clickable(onClick = onClick) else base
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(112.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(AquaBright, OceanBlue)))
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(SurfaceWhite),
                contentAlignment = Alignment.Center
            ) {
                if (!avatarUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = avatarUrl,
                        contentDescription = "Foto profil",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = name.initials(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = OceanBlue
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(6.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(AquaBright)
                        .border(2.dp, SurfaceWhite, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PhotoCamera,
                        contentDescription = null,
                        tint = OceanBlue,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
        Text(text = name, style = MaterialTheme.typography.titleLarge)
        Text(
            text = "$goalLabel - $experienceLabel",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )
    }
}

@Composable
internal fun CompactStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = if (onClick != null) modifier.clickable(onClick = onClick) else modifier,
        shape = RoundedCornerShape(20.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = null, tint = OceanBlue)
            Text(text = value, style = MaterialTheme.typography.headlineMedium)
            Text(text = title.uppercase(Locale.getDefault()), style = MaterialTheme.typography.labelSmall, color = TextSecondary)
        }
    }
}

@Composable
internal fun SettingsRow(
    title: String,
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
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceTint),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (title) {
                        "Akun" -> Icons.Outlined.PersonOutline
                        "Pengaturan" -> Icons.Outlined.Settings
                        else -> Icons.AutoMirrored.Outlined.HelpOutline
                    },
                    contentDescription = null,
                    tint = OceanBlue
                )
            }
            Text(text = title, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            Icon(Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = null, tint = TextSecondary)
        }
    }
}

