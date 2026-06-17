package com.athallah.hybridfit.ui.main

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.athallah.hybridfit.domain.model.DashboardSnapshot
import com.athallah.hybridfit.domain.model.ProgressTrend
import com.athallah.hybridfit.domain.model.Recommendation
import com.athallah.hybridfit.domain.model.RecommendationAction
import com.athallah.hybridfit.domain.model.UserProfile
import com.athallah.hybridfit.domain.model.WorkoutCategory
import com.athallah.hybridfit.domain.model.WorkoutLog
import com.athallah.hybridfit.domain.model.WorkoutSession
import com.athallah.hybridfit.ui.theme.AquaBright
import com.athallah.hybridfit.ui.theme.AquaTeal
import com.athallah.hybridfit.ui.theme.BorderSoft
import com.athallah.hybridfit.ui.theme.OceanBlue
import com.athallah.hybridfit.ui.theme.OceanBlueDark
import com.athallah.hybridfit.ui.theme.SurfaceTint
import com.athallah.hybridfit.ui.theme.SurfaceWhite
import com.athallah.hybridfit.ui.theme.SuccessMint
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.roundToInt
import androidx.compose.foundation.BorderStroke

private enum class MainTab(
    val label: String,
    val icon: ImageVector
) {
    HOME("Home", Icons.Outlined.Home),
    PROGRAMS("Programs", Icons.Outlined.FitnessCenter),
    STATS("Stats", Icons.Outlined.BarChart),
    PROFILE("Profile", Icons.Outlined.PersonOutline)
}

private enum class ProgramFilter(val label: String) {
    GYM("Gym"),
    RUN("Lari")
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState = viewModel.uiState
    var selectedTab by rememberSaveable { mutableStateOf(MainTab.HOME.name) }
    var selectedProgramFilter by rememberSaveable { mutableStateOf(ProgramFilter.GYM.name) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (uiState.snapshot != null) {
                BottomTabBar(
                    selected = MainTab.valueOf(selectedTab),
                    onSelect = { selectedTab = it.name }
                )
            }
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> LoadingScreen(innerPadding)
            uiState.snapshot != null -> {
                val snapshot = uiState.snapshot ?: return@Scaffold
                when (MainTab.valueOf(selectedTab)) {
                    MainTab.HOME -> HomeTab(
                        snapshot = snapshot,
                        onStartWorkout = viewModel::simulateWorkoutCompletion,
                        onRefreshRecommendation = viewModel::refreshRecommendation,
                        modifier = Modifier.padding(innerPadding)
                    )

                    MainTab.PROGRAMS -> ProgramsTab(
                        snapshot = snapshot,
                        selectedFilter = ProgramFilter.valueOf(selectedProgramFilter),
                        onFilterChange = { selectedProgramFilter = it.name },
                        onStartWorkout = viewModel::simulateWorkoutCompletion,
                        onHelpfulFeedback = { viewModel.submitFeedback(true) },
                        onNotHelpfulFeedback = { viewModel.submitFeedback(false) },
                        onRefreshRecommendation = viewModel::refreshRecommendation,
                        modifier = Modifier.padding(innerPadding)
                    )

                    MainTab.STATS -> StatsTab(
                        snapshot = snapshot,
                        modifier = Modifier.padding(innerPadding)
                    )

                    MainTab.PROFILE -> ProfileTab(
                        snapshot = snapshot,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingScreen(innerPadding: PaddingValues) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            Text(
                text = "Menyiapkan dashboard Hybrid Fit...",
                modifier = Modifier.padding(top = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun BottomTabBar(
    selected: MainTab,
    onSelect: (MainTab) -> Unit
) {
    NavigationBar(
        containerColor = SurfaceWhite,
        tonalElevation = 10.dp
    ) {
        MainTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = selected == tab,
                onClick = { onSelect(tab) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = OceanBlue,
                    selectedTextColor = OceanBlue,
                    indicatorColor = SurfaceTint,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                icon = { Icon(imageVector = tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) }
            )
        }
    }
}

@Composable
private fun HomeTab(
    snapshot: DashboardSnapshot,
    onStartWorkout: () -> Unit,
    onRefreshRecommendation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val todaySession = snapshot.primarySession()
    val metrics = listOf(
        DashboardMetric(
            title = "Langkah",
            value = snapshot.estimatedSteps().formatThousands(),
            icon = Icons.Outlined.DirectionsRun,
            tint = OceanBlue
        ),
        DashboardMetric(
            title = "Kalori",
            value = snapshot.estimatedCalories().toString(),
            icon = Icons.Outlined.LocalFireDepartment,
            tint = AquaTeal
        ),
        DashboardMetric(
            title = "Skor",
            value = "${snapshot.progressSummary.averageCompletionPercent}%",
            icon = Icons.Outlined.ShowChart,
            tint = AquaBright
        )
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            ScreenHeader(
                title = "Hybrid Fit",
                subtitle = "Halo, ${snapshot.profile?.firstNameOrFallback() ?: "Pejuang Fit"}!",
                trailing = {
                    IconButton(onClick = onRefreshRecommendation) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Halo, Pejuang Fit!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Siap menaklukkan latihan hari ini?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(metrics) { metric ->
                    MetricCard(metric = metric)
                }
            }
        }

        item {
            SectionLabel(title = "Latihan Hari Ini")
        }

        item {
            HeroWorkoutCard(
                session = todaySession,
                recommendation = snapshot.latestRecommendation,
                onStartWorkout = onStartWorkout,
                onRefreshRecommendation = onRefreshRecommendation
            )
        }

        item {
            SectionLabel(title = "Aktivitas Terakhir")
        }

        items(snapshot.recentLogs.take(4)) { log ->
            ActivityCard(log = log)
        }
    }
}

@Composable
private fun ProgramsTab(
    snapshot: DashboardSnapshot,
    selectedFilter: ProgramFilter,
    onFilterChange: (ProgramFilter) -> Unit,
    onStartWorkout: () -> Unit,
    onHelpfulFeedback: () -> Unit,
    onNotHelpfulFeedback: () -> Unit,
    onRefreshRecommendation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sessions = snapshot.filteredSessions(selectedFilter)
    val featuredSession = sessions.firstOrNull() ?: snapshot.primarySession()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CenterHeader(
                title = "Hybrid Fit",
                onBack = null,
                trailing = {
                    IconButton(onClick = onRefreshRecommendation) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "Opsi"
                        )
                    }
                }
            )
        }

        item {
            FilterPills(
                selected = selectedFilter,
                onChange = onFilterChange
            )
        }

        item {
            InsightStrip(
                title = "AI session aktif",
                subtitle = snapshot.latestRecommendation?.summary
                    ?: "Sistem sedang menyesuaikan beban dan waktu istirahat."
            )
        }

        item {
            ProgramFeatureCard(
                session = featuredSession,
                recommendation = snapshot.latestRecommendation,
                onStartWorkout = onStartWorkout
            )
        }

        item {
            TargetsRow(
                recommendation = snapshot.latestRecommendation,
                session = featuredSession
            )
        }

        item {
            SectionLabel(title = "Rangkaian Latihan")
        }

        items(sessions.ifEmpty { listOf(featuredSession) }) { session ->
            SessionListCard(session = session)
        }

        item {
            RecommendationFeedbackCard(
                recommendation = snapshot.latestRecommendation,
                onHelpfulFeedback = onHelpfulFeedback,
                onNotHelpfulFeedback = onNotHelpfulFeedback
            )
        }
    }
}

@Composable
private fun StatsTab(
    snapshot: DashboardSnapshot,
    modifier: Modifier = Modifier
) {
    val weeklyCalories = snapshot.estimatedCalories()
    val distanceTotal = snapshot.totalDistanceKm()
    val strengthVolume = snapshot.totalStrengthVolume()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CenterHeader(
                title = "Analysis",
                onBack = {},
                trailing = {
                    Row {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Outlined.CalendarToday,
                                contentDescription = "Kalender"
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Outlined.Share,
                                contentDescription = "Bagikan"
                            )
                        }
                    }
                }
            )
        }

        item {
            TimeRangeRow(selected = "WEEK")
        }

        item {
            OverviewCard(
                weeklyCalories = weeklyCalories,
                consistency = snapshot.progressSummary.weeklyConsistencyPercent
            )
        }

        item {
            SectionLabel(title = "Achievements", actionLabel = "View All")
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(snapshot.buildAchievements()) { achievement ->
                    AchievementCard(achievement = achievement)
                }
            }
        }

        item {
            SectionLabel(title = "Comparison")
        }

        item {
            ComparisonCard(
                icon = Icons.Outlined.DirectionsRun,
                title = "Running",
                subtitle = "Perbandingan pekan ini",
                value = "+${"%.1f".format(distanceTotal)} km",
                tone = AquaTeal
            )
        }

        item {
            ComparisonCard(
                icon = Icons.Outlined.FitnessCenter,
                title = "Strength",
                subtitle = "Akumulasi volume latihan",
                value = "+${strengthVolume.formatThousands()} kg",
                tone = OceanBlue
            )
        }

        item {
            ProgressInsightCard(trend = snapshot.progressSummary.trend, highlight = snapshot.progressSummary.bestHighlight)
        }
    }
}

@Composable
private fun ProfileTab(
    snapshot: DashboardSnapshot,
    modifier: Modifier = Modifier
) {
    val profile = snapshot.profile

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BrandMark()
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.NotificationsNone,
                        contentDescription = "Notifikasi"
                    )
                }
            }
        }

        item {
            ProfileHeader(profile = profile, snapshot = snapshot)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CompactProfileStat(
                    title = "Total sesi",
                    value = snapshot.progressSummary.totalSessionsLogged.toString(),
                    modifier = Modifier.weight(1f)
                )
                CompactProfileStat(
                    title = "Streak hari",
                    value = snapshot.estimatedStreakDays().toString(),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            CompactProfileStat(
                title = "Pencapaian",
                value = snapshot.buildAchievements().size.toString(),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            SettingsGroup(
                title = "Pengaturan",
                items = listOf(
                    SettingItem("Akun", Icons.Outlined.PersonOutline),
                    SettingItem("Target Mingguan", Icons.Outlined.EmojiEvents),
                    SettingItem("Pengaturan", Icons.Outlined.Settings),
                    SettingItem("Bantuan", Icons.Outlined.TipsAndUpdates)
                )
            )
        }

        item {
            LogoutCard()
        }
    }
}

@Composable
private fun ScreenHeader(
    title: String,
    subtitle: String,
    trailing: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BrandMark()
        trailing()
    }
}

@Composable
private fun CenterHeader(
    title: String,
    onBack: (() -> Unit)?,
    trailing: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBack != null) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Kembali"
                )
            }
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = OceanBlue
        )
        trailing()
    }
}

@Composable
private fun BrandMark() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    brush = Brush.linearGradient(listOf(OceanBlue, AquaTeal))
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(15.dp)
            )
        }
        Text(
            text = "hybrid fit",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun MetricCard(metric: DashboardMetric) {
    Surface(
        modifier = Modifier.width(132.dp),
        shape = RoundedCornerShape(20.dp),
        color = SurfaceWhite,
        shadowElevation = 6.dp,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(3.dp, metric.tint, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = metric.icon,
                    contentDescription = metric.title,
                    tint = metric.tint,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = metric.value,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = metric.title.uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun HeroWorkoutCard(
    session: WorkoutSession,
    recommendation: Recommendation?,
    onStartWorkout: () -> Unit,
    onRefreshRecommendation: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0xFF06162D), OceanBlueDark, AquaTeal)
                )
            )
            .drawBehind {
                drawCircle(
                    color = AquaBright.copy(alpha = 0.18f),
                    radius = size.minDimension * 0.38f,
                    center = Offset(size.width * 0.8f, size.height * 0.22f)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.08f),
                    radius = size.minDimension * 0.32f,
                    center = Offset(size.width * 0.18f, size.height * 0.78f)
                )
            }
    ) {
        Icon(
            imageVector = if (session.category == WorkoutCategory.CARDIO) {
                Icons.Outlined.DirectionsRun
            } else {
                Icons.Outlined.FitnessCenter
            },
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.17f),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 20.dp, end = 20.dp)
                .size(110.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ChipLabel(
                text = if (recommendation != null) "AI SESSION AKTIF" else "PROGRAM HARI INI",
                background = AquaBright.copy(alpha = 0.18f),
                textColor = Color.White
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = session.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = recommendation?.summary ?: session.guidance,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                GradientActionButton(
                    text = "Mulai Sekarang",
                    icon = Icons.Outlined.PlayArrow,
                    onClick = onStartWorkout
                )
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier = Modifier
                            .clickable(onClick = onRefreshRecommendation)
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Sync,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Sinkronkan AI",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ActivityCard(log: WorkoutLog) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = SurfaceWhite,
        shadowElevation = 3.dp,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceTint),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (log.actualDistanceKm != null) {
                        Icons.Outlined.DirectionsRun
                    } else {
                        Icons.Outlined.FitnessCenter
                    },
                    contentDescription = null,
                    tint = OceanBlue,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = log.sessionTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = log.activitySubtitle(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun FilterPills(
    selected: ProgramFilter,
    onChange: (ProgramFilter) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        ProgramFilter.entries.forEach { filter ->
            val isSelected = filter == selected
            Surface(
                shape = RoundedCornerShape(30.dp),
                color = if (isSelected) OceanBlue else SurfaceTint,
                shadowElevation = if (isSelected) 6.dp else 0.dp
            ) {
                Text(
                    text = filter.label,
                    modifier = Modifier
                        .clickable { onChange(filter) }
                        .padding(horizontal = 18.dp, vertical = 10.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun InsightStrip(
    title: String,
    subtitle: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = SurfaceTint
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = null,
                tint = OceanBlue,
                modifier = Modifier.size(18.dp)
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun ProgramFeatureCard(
    session: WorkoutSession,
    recommendation: Recommendation?,
    onStartWorkout: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = SurfaceWhite,
        shadowElevation = 5.dp,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(136.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        brush = Brush.linearGradient(
                            listOf(Color(0xFF11274C), Color(0xFF184DA8), Color(0xFF0D7E8C))
                        )
                    )
            ) {
                Icon(
                    imageVector = if (session.category == WorkoutCategory.CARDIO) {
                        Icons.Outlined.DirectionsRun
                    } else {
                        Icons.Outlined.FitnessCenter
                    },
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.16f),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 18.dp)
                        .size(82.dp)
                )
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White.copy(alpha = 0.92f)
                ) {
                    Text(
                        text = "Fokus: ${session.focusArea}",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = OceanBlue
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = session.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "${session.targetDurationMinutes} menit  •  ${session.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("id", "ID"))}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    LinearProgressIndicator(
                        progress = { (recommendation?.targetDurationMinutes ?: session.targetDurationMinutes) / 60f },
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(6.dp)
                            .clip(CircleShape),
                        color = OceanBlue,
                        trackColor = SurfaceTint
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            GradientActionButton(
                text = "Mulai Sekarang",
                icon = Icons.Outlined.PlayArrow,
                onClick = onStartWorkout
            )
        }
    }
}

@Composable
private fun TargetsRow(
    recommendation: Recommendation?,
    session: WorkoutSession
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        SmallTargetCard(
            title = "Kalori Est.",
            value = ((recommendation?.targetDurationMinutes ?: session.targetDurationMinutes) * 8).toString(),
            icon = Icons.Outlined.LocalFireDepartment,
            modifier = Modifier.weight(1f)
        )
        SmallTargetCard(
            title = "Target Durasi",
            value = "${recommendation?.targetDurationMinutes ?: session.targetDurationMinutes} min",
            icon = Icons.Outlined.AccessTime,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SmallTargetCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = SurfaceWhite,
        shadowElevation = 3.dp,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AquaTeal,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = title.uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SessionListCard(session: WorkoutSession) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = SurfaceWhite,
        shadowElevation = 2.dp,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 13.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceTint),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (session.category == WorkoutCategory.CARDIO) {
                        Icons.Outlined.DirectionsRun
                    } else {
                        Icons.Outlined.FitnessCenter
                    },
                    contentDescription = null,
                    tint = OceanBlue
                )
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    text = session.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = session.sessionMetaLine(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun RecommendationFeedbackCard(
    recommendation: Recommendation?,
    onHelpfulFeedback: () -> Unit,
    onNotHelpfulFeedback: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = Color(0xFFF2FAFF),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.TipsAndUpdates,
                    contentDescription = null,
                    tint = AquaTeal
                )
                Text(
                    text = "Hybrid Insight",
                    style = MaterialTheme.typography.labelLarge,
                    color = AquaTeal
                )
            }
            Text(
                text = recommendation?.rationale ?: "Belum ada insight baru.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onHelpfulFeedback,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = OceanBlue)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cocok")
                }
                Button(
                    onClick = onNotHelpfulFeedback,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = AquaTeal)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Sync,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Revisi")
                }
            }
        }
    }
}

@Composable
private fun TimeRangeRow(selected: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        listOf("DAY", "WEEK", "MONTH", "YEAR").forEach { item ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (item == selected) OceanBlue else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (item == selected) {
                    Box(
                        modifier = Modifier
                            .width(42.dp)
                            .height(3.dp)
                            .clip(CircleShape)
                            .background(OceanBlue)
                    )
                } else {
                    Spacer(modifier = Modifier.height(3.dp))
                }
            }
        }
    }
}

@Composable
private fun OverviewCard(
    weeklyCalories: Int,
    consistency: Int
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = SurfaceWhite,
        shadowElevation = 4.dp,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "WEEKLY ACTIVITY",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${weeklyCalories.formatThousands()} kcal",
                        style = MaterialTheme.typography.displaySmall,
                        color = OceanBlue
                    )
                }
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = SuccessMint
                ) {
                    Text(
                        text = "↗ +$consistency%",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = AquaTeal
                    )
                }
            }
            ActivityTrendGraphic()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("M", "T", "W", "T", "F", "S", "S").forEach { day ->
                    Text(
                        text = day,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ActivityTrendGraphic() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        val points = listOf(0.08f, 0.18f, 0.34f, 0.42f, 0.56f, 0.74f, 0.9f)
        val values = listOf(0.35f, 0.46f, 0.64f, 0.58f, 0.73f, 0.7f, 0.9f)
        val pathPoints = points.zip(values).map {
            Offset(size.width * it.first, size.height * (1f - it.second))
        }

        for (i in 0..3) {
            val y = size.height * (i / 4f)
            drawLine(
                color = BorderSoft,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 12f))
            )
        }

        for (index in 0 until pathPoints.lastIndex) {
            drawLine(
                brush = Brush.horizontalGradient(listOf(OceanBlue, AquaTeal)),
                start = pathPoints[index],
                end = pathPoints[index + 1],
                strokeWidth = 10f,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
private fun AchievementCard(achievement: AchievementUiModel) {
    Surface(
        modifier = Modifier.width(122.dp),
        shape = RoundedCornerShape(20.dp),
        color = SurfaceWhite,
        shadowElevation = 2.dp,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(achievement.background),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = achievement.icon,
                    contentDescription = null,
                    tint = achievement.tint
                )
            }
            Text(
                text = achievement.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = achievement.subtitle,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ComparisonCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    value: String,
    tone: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = SurfaceWhite,
        shadowElevation = 2.dp,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(tone.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tone
                )
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = tone
            )
        }
    }
}

@Composable
private fun ProgressInsightCard(
    trend: ProgressTrend,
    highlight: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = Color(0xFFF5FAFF),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Hybrid Insight",
                style = MaterialTheme.typography.labelLarge,
                color = OceanBlue
            )
            Text(
                text = when (trend) {
                    ProgressTrend.IMPROVING -> "Performa meningkat dengan ritme yang sehat."
                    ProgressTrend.STABLE -> "Progres sedang stabil, terus jaga konsistensi."
                    ProgressTrend.NEEDS_ATTENTION -> "Sistem mendeteksi perlunya penyesuaian ritme latihan."
                },
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = highlight,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ProfileHeader(
    profile: UserProfile?,
    snapshot: DashboardSnapshot
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(listOf(AquaBright, OceanBlue))
                )
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
                Text(
                    text = profile?.initials() ?: "HF",
                    style = MaterialTheme.typography.headlineMedium,
                    color = OceanBlue
                )
            }
        }
        Text(
            text = profile?.fullName ?: "Hybrid User",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "${profile?.goal?.label ?: "Program aktif"} • ${snapshot.progressSummary.trend.label}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CompactProfileStat(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = SurfaceWhite,
        shadowElevation = 2.dp,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = title.uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SettingsGroup(
    title: String,
    items: List<SettingItem>
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = title.uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            color = SurfaceWhite,
            shadowElevation = 2.dp,
            border = CardDefaults.outlinedCardBorder()
        ) {
            Column {
                items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceTint),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null,
                                tint = OceanBlue,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = item.label,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (index != items.lastIndex) {
                        HorizontalDivider(color = BorderSoft)
                    }
                }
            }
        }
    }
}

@Composable
private fun LogoutCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = SurfaceWhite,
        border = BorderStroke(
            width = 1.dp,
            brush = Brush.horizontalGradient(
                listOf(Color(0xFFFFE2E2), Color(0xFFFFD5D5))
            )
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Logout,
                contentDescription = null,
                tint = Color(0xFFC53535)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Keluar",
                style = MaterialTheme.typography.labelLarge,
                color = Color(0xFFC53535)
            )
        }
    }
}

@Composable
private fun GradientActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .clip(RoundedCornerShape(18.dp))
                .background(Brush.horizontalGradient(listOf(OceanBlue, AquaTeal)))
                .clickable(onClick = onClick)
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun ChipLabel(
    text: String,
    background: Color,
    textColor: Color
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = background
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
    }
}

@Composable
private fun SectionLabel(
    title: String,
    actionLabel: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (actionLabel != null) {
            Text(
                text = actionLabel,
                style = MaterialTheme.typography.labelLarge,
                color = OceanBlue
            )
        }
    }
}

private data class DashboardMetric(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val tint: Color
)

private data class AchievementUiModel(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val tint: Color,
    val background: Color
)

private data class SettingItem(
    val label: String,
    val icon: ImageVector
)

private fun DashboardSnapshot.primarySession(): WorkoutSession {
    return activePlan?.sessions?.firstOrNull() ?: WorkoutSession(
        id = 0,
        planId = 0,
        dayOfWeek = java.time.DayOfWeek.MONDAY,
        title = "Hybrid Starter Session",
        category = WorkoutCategory.STRENGTH,
        focusArea = "Fondasi kebugaran",
        targetDurationMinutes = 40,
        targetDistanceKm = null,
        targetSets = 3,
        targetReps = 10,
        restSeconds = 60,
        guidance = "Bangun ritme latihan bertahap dan konsisten.",
        orderInDay = 1
    )
}

private fun DashboardSnapshot.filteredSessions(filter: ProgramFilter): List<WorkoutSession> {
    val sessions = activePlan?.sessions.orEmpty()
    val desiredCategory = when (filter) {
        ProgramFilter.GYM -> WorkoutCategory.STRENGTH
        ProgramFilter.RUN -> WorkoutCategory.CARDIO
    }
    return sessions.filter { it.category == desiredCategory }.ifEmpty { sessions }
}

private fun DashboardSnapshot.estimatedSteps(): Int {
    val distanceBased = recentLogs.sumOf { log ->
        val distance = log.actualDistanceKm ?: 0.0
        (distance * 1312).roundToInt()
    }
    return distanceBased.coerceAtLeast(progressSummary.totalSessionsLogged * 2100 + 2400)
}

private fun DashboardSnapshot.estimatedCalories(): Int {
    return recentLogs.sumOf { log ->
        val cardioCalories = ((log.actualDistanceKm ?: 0.0) * 78).roundToInt()
        val durationCalories = log.actualDurationMinutes * 6
        cardioCalories + durationCalories
    }.coerceAtLeast(progressSummary.totalSessionsLogged * 140 + 120)
}

private fun DashboardSnapshot.totalDistanceKm(): Double {
    return recentLogs.sumOf { it.actualDistanceKm ?: 0.0 }
}

private fun DashboardSnapshot.totalStrengthVolume(): Int {
    return recentLogs.sumOf { log ->
        val sets = log.completedSets ?: 0
        val reps = log.completedReps ?: 0
        sets * reps * 10
    }.coerceAtLeast(450)
}

private fun DashboardSnapshot.estimatedStreakDays(): Int {
    if (recentLogs.isEmpty()) return 0
    return recentLogs
        .sortedByDescending { it.performedOn }
        .fold(Pair(LocalDate.now().plusDays(1), 0)) { acc, log ->
            val expected = acc.first.minusDays(1)
            if (log.performedOn == expected) {
                Pair(log.performedOn, acc.second + 1)
            } else {
                acc
            }
        }.second
        .coerceAtLeast(2)
}

private fun DashboardSnapshot.buildAchievements(): List<AchievementUiModel> {
    val streak = estimatedStreakDays()
    return listOf(
        AchievementUiModel(
            title = "$streak Day Streak",
            subtitle = "Konsisten",
            icon = Icons.Outlined.EmojiEvents,
            tint = AquaTeal,
            background = SuccessMint
        ),
        AchievementUiModel(
            title = "Goal Crusher",
            subtitle = progressSummary.trend.label,
            icon = Icons.Outlined.LocalFireDepartment,
            tint = OceanBlue,
            background = SurfaceTint
        ),
        AchievementUiModel(
            title = "AI Ready",
            subtitle = "$feedbackCount feedback",
            icon = Icons.Outlined.AutoAwesome,
            tint = AquaBright,
            background = Color(0xFFEFF9FF)
        )
    )
}

private fun UserProfile.firstNameOrFallback(): String {
    return fullName.substringBefore(" ").ifBlank { "Pejuang Fit" }
}

private fun UserProfile.initials(): String {
    return fullName
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercaseChar().toString() }
        .ifBlank { "HF" }
}

private fun WorkoutSession.sessionMetaLine(): String {
    val intensity = when (category) {
        WorkoutCategory.STRENGTH -> "Fokus otot"
        WorkoutCategory.CARDIO -> "Intensitas sedang"
        WorkoutCategory.MOBILITY -> "Mobilitas"
        WorkoutCategory.RECOVERY -> "Recovery"
    }
    return "${targetDurationMinutes} menit • $intensity"
}

private fun WorkoutLog.activitySubtitle(): String {
    val dayLabel = when (ChronoUnit.DAYS.between(performedOn, LocalDate.now()).toInt()) {
        0 -> "Hari ini"
        1 -> "Kemarin"
        else -> "${ChronoUnit.DAYS.between(performedOn, LocalDate.now())} hari lalu"
    }
    val detail = actualDistanceKm?.let { "$it km" } ?: "${actualDurationMinutes} menit"
    return "$dayLabel • $detail"
}

private fun Int.formatThousands(): String {
    return "%,d".format(this).replace(',', '.')
}
