@file:Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")

package com.athallah.hybridfit.ui.main
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.MarkEmailRead
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import com.athallah.hybridfit.domain.model.DashboardSnapshot
import com.athallah.hybridfit.domain.model.SessionPlannerState
import com.athallah.hybridfit.domain.model.WorkoutCategory
import com.athallah.hybridfit.domain.model.WorkoutSession
import com.athallah.hybridfit.ui.theme.AquaBright
import com.athallah.hybridfit.ui.theme.AquaTeal
import com.athallah.hybridfit.ui.theme.BorderSoft
import com.athallah.hybridfit.ui.theme.OceanBlue
import com.athallah.hybridfit.ui.theme.SurfaceTint
import com.athallah.hybridfit.ui.theme.SurfaceWhite
import com.athallah.hybridfit.ui.theme.TextPrimary
import com.athallah.hybridfit.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import java.io.File
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
internal fun QuickActionsScreen(
    isAuthenticated: Boolean,
    onBack: () -> Unit,
    onAiHub: () -> Unit,
    onNotifications: () -> Unit,
    onAccount: () -> Unit,
    onWeeklyTarget: () -> Unit,
    onSettings: () -> Unit,
    onHelp: () -> Unit,
    onAchievements: () -> Unit,
    onForgotPassword: () -> Unit,
    onRegister: () -> Unit,
    onGoogleLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val title = if (isAuthenticated) "Akses Cepat" else "Mulai Lebih Cepat"
    val subtitle = if (isAuthenticated) {
        "Pilih pintasan fitur Hybrid Fit yang ingin Anda buka."
    } else {
        "Gunakan pintasan untuk masuk, membuat akun, atau meminta bantuan."
    }

    val actions = if (isAuthenticated) {
        listOf(
            QuickActionCardUi("AI Hub", "Buka pusat AI, IRS, tracking, dan AI Coach.", Icons.Outlined.AutoAwesome, Color(0xFF0E8A5F), onAiHub),
            QuickActionCardUi("Notifikasi", "Lihat update jadwal dan insight terbaru.", Icons.Outlined.NotificationsNone, AquaTeal, onNotifications),
            QuickActionCardUi("Akun", "Kelola profil, email, dan fokus latihan.", Icons.Outlined.PersonOutline, OceanBlue, onAccount),
            QuickActionCardUi("Pengaturan", "Atur reminder, sinkronisasi, dan preferensi.", Icons.Outlined.Settings, Color(0xFF29436B), onSettings),
            QuickActionCardUi("Achievements", "Lihat badge, streak, dan milestone Anda.", Icons.Outlined.EmojiEvents, Color(0xFF0E8A5F), onAchievements)
        )
    } else {
        listOf(
            QuickActionCardUi("Login Google", "Masuk cepat menggunakan akun Google Anda.", Icons.Outlined.AutoAwesome, AquaTeal, onGoogleLogin),
            QuickActionCardUi("Daftar Akun", "Buat akun Hybrid Fit baru dan lanjutkan survei.", Icons.Outlined.PersonOutline, OceanBlue, onRegister),
            QuickActionCardUi("Lupa Password", "Ajukan reset kata sandi secara aman.", Icons.Outlined.Email, Color(0xFF3366CC), onForgotPassword)
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7FBFF)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            UtilityTopBar(
                title = title,
                onBack = onBack
            )
        }
        item {
            Surface(
                shape = RoundedCornerShape(26.dp),
                color = Color.Transparent,
                shadowElevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFFE8F4FF), Color(0xFFF5FDFF))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineMedium,
                            color = OceanBlue
                        )
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondary
                        )
                    }
                }
            }
        }
        items(actions) { action ->
            QuickActionCard(action = action)
        }
    }
}

@Composable
internal fun ForgotPasswordScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    onBack: () -> Unit,
    onSubmit: () -> Unit,
    onUseGoogle: () -> Unit,
    modifier: Modifier = Modifier
) {
    AuthContainer(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Kembali",
                    tint = OceanBlue
                )
            }
            BrandHeader(icon = Icons.Outlined.Email)
            Spacer(modifier = Modifier.width(48.dp))
        }
        Spacer(modifier = Modifier.height(34.dp))
        Text(
            text = "Reset Kata Sandi",
            style = MaterialTheme.typography.displaySmall,
            color = OceanBlue
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Masukkan email akun Anda. Kami akan menyiapkan langkah pemulihan yang aman.",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary
        )
        Spacer(modifier = Modifier.height(24.dp))
        CardSurface {
            FormTextField(
                value = email,
                onValueChange = onEmailChange,
                label = "Email Akun",
                icon = Icons.Outlined.Email
            )
            PrimaryGradientButton(
                text = "Kirim Instruksi",
                onClick = onSubmit
            )
            SecondaryOutlineButton(
                text = "Gunakan Login Google",
                onClick = onUseGoogle
            )
            Text(
                text = "Tips: untuk mode demo lokal, tombol ini akan menampilkan konfirmasi pemulihan tanpa mengirim email sungguhan.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
    }
}

@Composable
internal fun NotificationsScreen(
    items: List<NotificationItemUi>,
    onBack: () -> Unit,
    onMarkAllRead: () -> Unit,
    onOpenNotification: (NotificationItemUi) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            DetailTopBar(
                title = "Notifikasi",
                onBack = onBack,
                trailingIcon = Icons.Outlined.MarkEmailRead,
                onTrailingClick = onMarkAllRead
            )
        }
        item {
            Text(
                text = "Update terbaru tentang latihan, rekomendasi AI, dan progres mingguan Anda.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )
        }
        if (items.isEmpty()) {
            item {
                EmptyStateCard(
                    icon = Icons.Outlined.NotificationsNone,
                    title = "Semua notifikasi sudah dibaca",
                    body = "Saat ada update baru, Hybrid Fit akan menampilkannya di sini."
                )
            }
        } else {
            items(items) { item ->
                NotificationCard(item = item, onClick = { onOpenNotification(item) })
            }
        }
    }
}

@Composable
internal fun AccountScreen(
    displayName: String,
    email: String,
    goalLabel: String,
    experienceLabel: String,
    notes: String,
    avatarUrl: String?,
    onDisplayNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onAvatarChange: (String) -> Unit,
    onBack: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val savedAvatarPath = copyAvatarToInternalStorage(context, uri)
            if (savedAvatarPath != null) {
                onAvatarChange(savedAvatarPath)
            }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailTopBar(
                title = "Akun Saya",
                onBack = onBack
            )
        }
        item {
            ProfileHero(
                name = displayName,
                goalLabel = goalLabel,
                experienceLabel = experienceLabel,
                avatarUrl = avatarUrl,
                onClick = { photoPickerLauncher.launch("image/*") }
            )
        }
        item {
            CardSurface {
                FormTextField(
                    value = displayName,
                    onValueChange = onDisplayNameChange,
                    label = "Nama Lengkap",
                    icon = Icons.Outlined.PersonOutline
                )
                FormTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = "Email",
                    icon = Icons.Outlined.Email
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Catatan Profil",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextSecondary
                    )
                    OutlinedTextField(
                        value = notes,
                        onValueChange = onNotesChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        placeholder = {
                            Text("Tambahkan catatan singkat tentang fokus latihan atau preferensi Anda.")
                        },
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceWhite,
                            unfocusedContainerColor = SurfaceWhite,
                            focusedBorderColor = OceanBlue,
                            unfocusedBorderColor = BorderSoft,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )
                }
                PrimaryGradientButton(text = "Simpan Perubahan", onClick = onSave)
            }
        }
    }
}

private fun copyAvatarToInternalStorage(
    context: Context,
    uri: Uri
): String? {
    return runCatching {
        val avatarDirectory = File(context.filesDir, "profile_avatars").apply { mkdirs() }
        val extension = when (context.contentResolver.getType(uri)?.substringAfterLast('/')) {
            "png" -> "png"
            "webp" -> "webp"
            else -> "jpg"
        }
        val destination = File(avatarDirectory, "avatar_${System.currentTimeMillis()}.$extension")
        context.contentResolver.openInputStream(uri)?.use { input ->
            destination.outputStream().use { output ->
                input.copyTo(output)
            }
        } ?: return null
        destination.absolutePath
    }.getOrNull()
}

@Composable
internal fun WeeklyTargetScreen(
    snapshot: DashboardSnapshot,
    selectedDays: Int,
    stepGoal: String,
    sleepGoal: String,
    plannerEditor: PlannerEditorUi,
    recoveryDraft: RecoveryDraftUi,
    onSelectDays: (Int) -> Unit,
    onStepGoalChange: (String) -> Unit,
    onSleepGoalChange: (String) -> Unit,
    onPlannerEditorChange: (PlannerEditorUi) -> Unit,
    onPlannerSave: () -> Unit,
    onPlannerReset: () -> Unit,
    onPlannerEdit: (WorkoutSession) -> Unit,
    onPlannerMove: (WorkoutSession) -> Unit,
    onPlannerSkip: (WorkoutSession) -> Unit,
    onPlannerComplete: (WorkoutSession) -> Unit,
    onPlannerDelete: (WorkoutSession) -> Unit,
    onRecoveryDraftChange: (RecoveryDraftUi) -> Unit,
    onRecoverySave: () -> Unit,
    onBack: () -> Unit,
    onSaveTargets: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sessions = snapshot.activePlan?.sessions.orEmpty()
    val monthSessions = remember(sessions) {
        sessions.sortedWith(compareBy<WorkoutSession> { it.dayOfWeek.value }.thenBy { it.orderInDay })
    }
    var editorMode by rememberSaveable(plannerEditor.sessionId) {
        mutableStateOf(if (plannerEditor.sessionId == null) "Tambah Sesi" else "Edit Sesi")
    }
    var editProgramExpanded by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(plannerEditor.sessionId) {
        if (plannerEditor.sessionId != null) {
            editorMode = "Edit Sesi"
        }
    }
    val frequencyRows = remember { FrequencyOption.entries.chunked(3) }
    val variantOptions = when (plannerEditor.category) {
        WorkoutCategory.STRENGTH -> listOf("Upper Body", "Lower Body", "Full Body")
        WorkoutCategory.CARDIO -> listOf("Easy Run", "Tempo Run", "Interval Run", "Long Run")
        WorkoutCategory.MOBILITY -> listOf("Mobility")
        WorkoutCategory.RECOVERY -> listOf("Recovery")
    }
    val formIsValid = when (plannerEditor.category) {
        WorkoutCategory.STRENGTH ->
            plannerEditor.title.isNotBlank() &&
            plannerEditor.durationMinutes.isNotBlank() &&
            plannerEditor.restSeconds.isNotBlank() &&
            plannerEditor.targetSets.isNotBlank() &&
            plannerEditor.targetReps.isNotBlank() &&
            plannerEditor.programVariant.isNotBlank() &&
                (editorMode == "Tambah Sesi" || plannerEditor.sessionId != null)
        WorkoutCategory.CARDIO ->
            plannerEditor.title.isNotBlank() &&
            plannerEditor.durationMinutes.isNotBlank() &&
            plannerEditor.targetDistanceKm.isNotBlank() &&
            plannerEditor.programVariant.isNotBlank() &&
                (editorMode == "Tambah Sesi" || plannerEditor.sessionId != null)
        WorkoutCategory.MOBILITY,
        WorkoutCategory.RECOVERY ->
            plannerEditor.title.isNotBlank() &&
            plannerEditor.durationMinutes.isNotBlank() &&
            plannerEditor.programVariant.isNotBlank() &&
                (editorMode == "Tambah Sesi" || plannerEditor.sessionId != null)
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailTopBar(
                title = "Edit Latihan",
                onBack = onBack
            )
        }
        item {
            Text(
                text = "Atur frekuensi dan susun sesi latihan langsung dari editor program Hybrid Fit.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )
        }
        item {
            SectionHeader("Frekuensi Latihan")
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                frequencyRows.forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        row.forEach { option ->
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onSelectDays(option.days) },
                                shape = RoundedCornerShape(18.dp),
                                color = if (option.days == selectedDays) AquaBright.copy(alpha = 0.28f) else SurfaceWhite,
                                border = BorderStroke(
                                    1.dp,
                                    if (option.days == selectedDays) AquaTeal else BorderSoft.copy(alpha = 0.45f)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "${option.days} Hari",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = option.subtitle,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextSecondary,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        repeat(3 - row.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
        item {
            PrimaryGradientButton(
                text = "Simpan Frekuensi Latihan",
                onClick = onSaveTargets
            )
        }
        item {
            SectionHeader("Mode Editor")
        }
        item {
            CardSurface {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    listOf("Tambah Sesi", "Edit Sesi").forEach { option ->
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    editorMode = option
                                    if (option == "Tambah Sesi") {
                                        onPlannerReset()
                                    }
                                },
                            shape = RoundedCornerShape(16.dp),
                            color = if (editorMode == option) OceanBlue else SurfaceWhite,
                            border = BorderStroke(1.dp, if (editorMode == option) OceanBlue else BorderSoft)
                        ) {
                            Text(
                                text = option,
                                modifier = Modifier.padding(vertical = 14.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleSmall,
                                color = if (editorMode == option) Color.White else TextPrimary
                            )
                        }
                    }
                }
            }
        }
        item {
            SectionHeader(if (editorMode == "Tambah Sesi") "Tambah / Susun Sesi" else "Edit Sesi Program")
        }
        item {
            CardSurface {
                if (editorMode == "Edit Sesi") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Program Bulan Ini",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextSecondary
                        )
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = monthSessions.isNotEmpty()) {
                                    editProgramExpanded = !editProgramExpanded
                                },
                            shape = RoundedCornerShape(16.dp),
                            color = SurfaceWhite,
                            border = BorderStroke(1.dp, BorderSoft)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = monthSessions.firstOrNull { it.id == plannerEditor.sessionId }?.let {
                                        "${it.title} - ${it.dayOfWeek.name.take(3)}"
                                    } ?: if (monthSessions.isEmpty()) "Belum ada sesi di bulan ini" else "Pilih program yang ingin diedit",
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = if (plannerEditor.sessionId != null) TextPrimary else TextSecondary
                                )
                                if (monthSessions.isNotEmpty()) {
                                    Icon(
                                        imageVector = if (editProgramExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = OceanBlue
                                    )
                                }
                            }
                        }
                        if (editProgramExpanded) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                monthSessions.forEach { session ->
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                onPlannerEdit(session)
                                                editProgramExpanded = false
                                            },
                                        shape = RoundedCornerShape(14.dp),
                                        color = if (plannerEditor.sessionId == session.id) AquaBright.copy(alpha = 0.24f) else SurfaceTint.copy(alpha = 0.18f),
                                        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.35f))
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(14.dp),
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text(
                                                text = session.title,
                                                style = MaterialTheme.typography.titleSmall,
                                                color = TextPrimary
                                            )
                                            Text(
                                                text = "${session.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }} • ${session.targetDurationMinutes} mnt",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = TextSecondary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                val showEditorForm = editorMode == "Tambah Sesi" || plannerEditor.sessionId != null
                if (showEditorForm) {
                    DetailInputField(
                        label = "Judul Sesi",
                        value = plannerEditor.title,
                        onValueChange = { onPlannerEditorChange(plannerEditor.copy(title = it)) }
                    )
                    SessionCategorySelector(
                        selectedCategory = plannerEditor.category,
                        onSelected = { category ->
                            onPlannerEditorChange(
                                plannerEditor.copy(
                                    category = category,
                                    programVariant = when (category) {
                                        WorkoutCategory.STRENGTH -> "Full Body"
                                        WorkoutCategory.CARDIO -> "Easy Run"
                                        WorkoutCategory.MOBILITY -> "Mobility"
                                        WorkoutCategory.RECOVERY -> "Recovery"
                                    },
                                    targetDistanceKm = "",
                                    targetSets = "",
                                    targetReps = ""
                                )
                            )
                        }
                    )
                    ProgramVariantSelector(
                        label = if (plannerEditor.category == WorkoutCategory.STRENGTH) "Program Gym" else "Program Lari",
                        options = variantOptions,
                        selectedOption = plannerEditor.programVariant.ifBlank { variantOptions.first() },
                        onSelected = { onPlannerEditorChange(plannerEditor.copy(programVariant = it)) }
                    )
                    PlannerDaySelector(
                        selectedDay = plannerEditor.dayOfWeek,
                        onSelected = { onPlannerEditorChange(plannerEditor.copy(dayOfWeek = it)) }
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        DetailInputField(
                            label = "Durasi (mnt)",
                            value = plannerEditor.durationMinutes,
                            onValueChange = { onPlannerEditorChange(plannerEditor.copy(durationMinutes = it)) },
                            modifier = Modifier.weight(1f)
                        )
                        if (plannerEditor.category == WorkoutCategory.STRENGTH || plannerEditor.programVariant == "Interval Run") {
                            DetailInputField(
                                label = "Rest (dtk)",
                                value = plannerEditor.restSeconds,
                                onValueChange = { onPlannerEditorChange(plannerEditor.copy(restSeconds = it)) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    when (plannerEditor.category) {
                        WorkoutCategory.STRENGTH -> {
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                DetailInputField(
                                    label = "Set Target",
                                    value = plannerEditor.targetSets,
                                    onValueChange = { onPlannerEditorChange(plannerEditor.copy(targetSets = it)) },
                                    modifier = Modifier.weight(1f)
                                )
                                DetailInputField(
                                    label = "Repetisi Target",
                                    value = plannerEditor.targetReps,
                                    onValueChange = { onPlannerEditorChange(plannerEditor.copy(targetReps = it)) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        WorkoutCategory.CARDIO -> {
                            if (plannerEditor.programVariant == "Interval Run") {
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    DetailInputField(
                                        label = "Jumlah Set",
                                        value = plannerEditor.targetSets,
                                        onValueChange = { onPlannerEditorChange(plannerEditor.copy(targetSets = it.filter(Char::isDigit))) },
                                        modifier = Modifier.weight(1f)
                                    )
                                    DetailInputField(
                                        label = "Jarak / Set (meter)",
                                        value = plannerEditor.targetDistanceKm,
                                        onValueChange = { onPlannerEditorChange(plannerEditor.copy(targetDistanceKm = it.filter { char -> char.isDigit() || char == '.' })) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            } else {
                                DetailInputField(
                                    label = "Jarak (km)",
                                    value = plannerEditor.targetDistanceKm,
                                    onValueChange = { onPlannerEditorChange(plannerEditor.copy(targetDistanceKm = it)) }
                                )
                            }
                        }
                        WorkoutCategory.MOBILITY,
                        WorkoutCategory.RECOVERY -> Unit
                    }
                    DetailInputField(
                        label = "Catatan Planner",
                        value = plannerEditor.notes,
                        onValueChange = { onPlannerEditorChange(plannerEditor.copy(notes = it)) },
                        singleLine = false
                    )
                    PrimaryGradientButton(
                        text = if (editorMode == "Tambah Sesi") "Tambah Sesi" else "Simpan Edit",
                        onClick = onPlannerSave,
                        enabled = formIsValid
                    )
                    SecondaryOutlineButton(
                        text = "Reset Form",
                        onClick = onPlannerReset
                    )
                }
            }
        }
    }
}

@Composable
internal fun AppSettingsScreen(
    reminderEnabled: Boolean,
    smartRecommendationEnabled: Boolean,
    sleepInsightEnabled: Boolean,
    dataSaverEnabled: Boolean,
    onReminderChange: (Boolean) -> Unit,
    onSmartRecommendationChange: (Boolean) -> Unit,
    onSleepInsightChange: (Boolean) -> Unit,
    onDataSaverChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailTopBar(
                title = "Pengaturan",
                onBack = onBack
            )
        }
        item {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = SurfaceWhite,
                border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    ToggleSettingRow(
                        title = "Reminder Latihan",
                        subtitle = "Aktifkan pengingat sesi harian dan weekly check-in.",
                        icon = Icons.Outlined.NotificationsNone,
                        checked = reminderEnabled,
                        onCheckedChange = onReminderChange
                    )
                    HorizontalDivider(color = BorderSoft.copy(alpha = 0.45f))
                    ToggleSettingRow(
                        title = "Smart Recommendation",
                        subtitle = "Biarkan AI menyesuaikan intensitas dan waktu jeda.",
                        icon = Icons.Outlined.AutoAwesome,
                        checked = smartRecommendationEnabled,
                        onCheckedChange = onSmartRecommendationChange
                    )
                    HorizontalDivider(color = BorderSoft.copy(alpha = 0.45f))
                    ToggleSettingRow(
                        title = "Sleep Insight",
                        subtitle = "Gunakan skor tidur untuk menyusun volume latihan.",
                        icon = Icons.Outlined.Tune,
                        checked = sleepInsightEnabled,
                        onCheckedChange = onSleepInsightChange
                    )
                    HorizontalDivider(color = BorderSoft.copy(alpha = 0.45f))
                    ToggleSettingRow(
                        title = "Mode Hemat Data",
                        subtitle = "Kurangi sinkronisasi berat saat jaringan seluler aktif.",
                        icon = Icons.Outlined.Sync,
                        checked = dataSaverEnabled,
                        onCheckedChange = onDataSaverChange
                    )
                }
            }
        }
        item {
            PrimaryGradientButton(text = "Simpan Pengaturan", onClick = onSave)
        }
    }
}

@Composable
internal fun HelpCenterScreen(
    supportMessage: String,
    onSupportMessageChange: (String) -> Unit,
    onBack: () -> Unit,
    onOpenCoach: () -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier
) {
    val faqs = listOf(
        "Bagaimana cara menyesuaikan intensitas latihan?" to "Gunakan Edit Latihan di tab Programs lalu lihat rekomendasi AI pada tab Programs.",
        "Apakah saya bisa mengubah tujuan olahraga?" to "Bisa. Anda dapat memperbarui target dari halaman akun dan survei lanjutan.",
        "Bagaimana jika tubuh terasa terlalu lelah?" to "Buka AI Coach atau tandai feedback sesi agar rekomendasi berikutnya lebih ringan."
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailTopBar(
                title = "Bantuan",
                onBack = onBack,
                trailingIcon = Icons.AutoMirrored.Outlined.HelpOutline,
                onTrailingClick = onOpenCoach
            )
        }
        item {
            InsightCard(
                title = "Pusat Bantuan Hybrid Fit",
                body = "Temukan jawaban cepat, kirim pertanyaan, atau lanjutkan konsultasi dengan AI Coach untuk penyesuaian program.",
                onClick = onOpenCoach
            )
        }
        item {
            SectionHeader("Pertanyaan Umum")
        }
        items(faqs) { item ->
            FaqCard(question = item.first, answer = item.second)
        }
        item {
            CardSurface {
                Text(
                    text = "Masih butuh bantuan?",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                OutlinedTextField(
                    value = supportMessage,
                    onValueChange = onSupportMessageChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    placeholder = {
                        Text("Tuliskan pertanyaan atau kendala Anda di sini.")
                    },
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceWhite,
                        unfocusedContainerColor = SurfaceWhite,
                        focusedBorderColor = OceanBlue,
                        unfocusedBorderColor = BorderSoft,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
                PrimaryGradientButton(text = "Kirim Pertanyaan", onClick = onSend)
                SecondaryOutlineButton(text = "Buka AI Coach", onClick = onOpenCoach)
            }
        }
    }
}

@Composable
internal fun AiHubScreen(
    snapshot: DashboardSnapshot,
    selectedProgramFilter: ProgramFilter,
    onBack: () -> Unit,
    onOpenCoach: () -> Unit,
    onOpenProgram: () -> Unit,
    modifier: Modifier = Modifier
) {
    val recommendation = snapshot.latestRecommendation
    val session = snapshot.sessionForFilter(selectedProgramFilter)
    val plannerSessions = snapshot.activePlan?.sessions.orEmpty()
    val completedSessions = plannerSessions.count { it.plannerState == SessionPlannerState.COMPLETED }
    val skippedSessions = plannerSessions.count { it.plannerState == SessionPlannerState.SKIPPED }
    val latestRecovery = snapshot.latestRecovery
    val aiSignals = listOf(
        Triple(
            "Profil & Tujuan",
            "AI memakai tujuan ${snapshot.profile?.goal?.label ?: "yang dipilih user"} serta fokus ${selectedProgramFilter.label.lowercase()} sebagai dasar penyusunan program.",
            Icons.Outlined.PersonOutline
        ),
        Triple(
            "Riwayat Latihan",
            if (snapshot.recentLogs.isNotEmpty()) {
                "Tercatat ${snapshot.recentLogs.size} log latihan. Data ini dipakai untuk menilai completion rate dan kesiapan menaikkan beban."
            } else {
                "Belum ada log latihan. Saat ini AI masih berada pada mode baseline awal."
            },
            Icons.Outlined.BarChart
        ),
        Triple(
            "Feedback & Recovery",
            if (latestRecovery != null || snapshot.feedbackCount > 0) {
                "Feedback ${snapshot.feedbackCount} dan recovery ${latestRecovery?.recoveryScore ?: 0}% dipakai AI untuk menyesuaikan rest time, intensitas, dan kapasitas pemulihan user."
            } else {
                "Belum ada feedback atau recovery check-in. Setelah keduanya diisi, AI akan lebih akurat menyesuaikan rekomendasi."
            },
            Icons.Outlined.Tune
        ),
        Triple(
            "Planner Mingguan",
            if (plannerSessions.isNotEmpty()) {
                "Planner berisi ${plannerSessions.size} sesi, dengan $completedSessions selesai dan $skippedSessions dilewati. Status ini membantu AI membaca konsistensi nyata."
            } else {
                "Belum ada sesi planner aktif. AI baru membaca struktur dasar program."
            },
            Icons.Outlined.CalendarToday
        )
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailTopBar(
                title = "AI HybridFit",
                onBack = onBack
            )
        }
        item {
            HeroIllustration(
                title = "AI HybridFit Coach",
                subtitle = "Pusat bantuan AI untuk membaca profil, tujuan latihan, recovery, dan planner mingguan Anda.",
                icon = Icons.Outlined.AutoAwesome,
                onClick = onOpenProgram
            )
        }
        item {
            SectionHeader("Sinyal Yang Dibaca AI")
        }
        items(aiSignals) { signal ->
            AiSignalCard(
                title = signal.first,
                body = signal.second,
                icon = signal.third
            )
        }
        item {
            PrimaryGradientButton(
                text = "Buka AI Coach",
                onClick = onOpenCoach
            )
        }
        item {
            SecondaryOutlineButton(
                text = "Lihat Program Saat Ini",
                onClick = onOpenProgram
            )
        }
    }
}

@Composable
internal fun AchievementsScreen(
    snapshot: DashboardSnapshot,
    onBack: () -> Unit,
    onTopAction: () -> Unit,
    onOpenAchievement: (AchievementCardUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val achievements = snapshot.achievementCards()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailTopBar(
                title = "Achievements",
                onBack = onBack,
                trailingIcon = Icons.Outlined.EmojiEvents,
                onTrailingClick = onTopAction
            )
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CompactStatCard(
                    title = "Streak",
                    value = "${snapshot.progressSummary.weeklyConsistencyPercent}%",
                    icon = Icons.Outlined.CalendarToday,
                    modifier = Modifier.weight(1f)
                )
                CompactStatCard(
                    title = "Feedback",
                    value = snapshot.feedbackCount.toString(),
                    icon = Icons.Outlined.CheckCircle,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        item {
            Text(
                text = snapshot.progressSummary.bestHighlight,
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )
        }
        if (achievements.isEmpty()) {
            item {
                DashboardEmptyCard(
                    title = "Belum ada achievement",
                    body = "Selesaikan program dan catat latihan pertama Anda untuk mulai membuka badge, streak, dan milestone."
                )
            }
        } else {
            items(achievements) { item ->
                AchievementDetailRow(item = item, onClick = null)
            }
        }
    }
}

@Composable
private fun AiSignalCard(
    title: String,
    body: String,
    icon: ImageVector
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(AquaBright.copy(alpha = 0.3f), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = OceanBlue
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
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
}

@Composable
internal fun FeatureInfoScreen(
    info: FeatureInfoUi,
    workoutDates: Set<LocalDate>,
    plannedSessions: List<WorkoutSession>,
    runLogDraft: RunLogDraftUi,
    showSimpleRunInput: Boolean,
    onRunDistanceChange: (String) -> Unit,
    onRunDurationChange: (String) -> Unit,
    onRunDurationSecondsChange: (String) -> Unit,
    onRunNotesChange: (String) -> Unit,
    onBack: () -> Unit,
    onPrimaryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isAnalysisCalendar = info.title == "Rentang Analisis"
    val hasRoutineSteps = info.routineSteps.isNotEmpty()
    val hasIntervalSegments = info.intervalSegments.isNotEmpty()
    var remainingSeconds by rememberSaveable(info.title, info.countdownSeconds) {
        mutableIntStateOf(info.countdownSeconds.coerceAtLeast(0))
    }
    var activeStepIndex by rememberSaveable(info.title) { mutableIntStateOf(0) }
    var activeStepRemaining by rememberSaveable(info.title) {
        mutableIntStateOf(info.routineSteps.firstOrNull()?.durationSeconds ?: 0)
    }
    var completedSteps by rememberSaveable(info.title) { mutableStateOf(emptySet<Int>()) }
    var isRoutineRunning by rememberSaveable(info.title) { mutableStateOf(false) }
    val intervalSegments = remember(info.title) {
        mutableStateListOf<RunSegmentUi>().apply {
            addAll(info.intervalSegments)
        }
    }
    var activeIntervalIndex by rememberSaveable(info.title) { mutableIntStateOf(0) }

    LaunchedEffect(info.countdownSeconds, remainingSeconds, hasRoutineSteps) {
        if (hasRoutineSteps || info.countdownSeconds <= 0 || remainingSeconds <= 0) return@LaunchedEffect
        delay(1000)
        remainingSeconds -= 1
    }

    LaunchedEffect(hasRoutineSteps, isRoutineRunning, activeStepRemaining, activeStepIndex) {
        if (!hasRoutineSteps || !isRoutineRunning || activeStepRemaining <= 0) return@LaunchedEffect
        delay(1000)
        activeStepRemaining -= 1
        if (activeStepRemaining <= 0) {
            isRoutineRunning = false
        }
    }

    val activeIntervalSegment = intervalSegments.getOrNull(activeIntervalIndex)

    LaunchedEffect(
        hasIntervalSegments,
        activeIntervalIndex,
        activeIntervalSegment?.isResting,
        activeIntervalSegment?.restSecondsRemaining
    ) {
        if (!hasIntervalSegments) return@LaunchedEffect
        val segment = intervalSegments.getOrNull(activeIntervalIndex) ?: return@LaunchedEffect
        if (!segment.isResting || segment.restSecondsRemaining <= 0) return@LaunchedEffect

        delay(1000)
        val updatedRemaining = (segment.restSecondsRemaining - 1).coerceAtLeast(0)
        intervalSegments[activeIntervalIndex] = segment.copy(restSecondsRemaining = updatedRemaining)

        if (updatedRemaining == 0) {
            intervalSegments[activeIntervalIndex] = intervalSegments[activeIntervalIndex].copy(
                completed = true,
                isResting = false,
                restSecondsRemaining = 0
            )
            if (activeIntervalIndex < intervalSegments.lastIndex) {
                activeIntervalIndex += 1
            }
        }
    }

    val routineRemainingSeconds = if (hasRoutineSteps) {
        info.routineSteps.mapIndexed { index, step ->
            when {
                index < activeStepIndex -> 0
                index == activeStepIndex -> activeStepRemaining
                else -> step.durationSeconds
            }
        }.sum()
    } else {
        remainingSeconds
    }
    val routineButtonLabel = when {
        showSimpleRunInput -> "Simpan Data Lari"
        hasIntervalSegments -> info.primaryActionLabel
        !hasRoutineSteps -> info.primaryActionLabel
        completedSteps.size >= info.routineSteps.size -> info.primaryActionLabel
        isRoutineRunning -> "Langkah Berjalan"
        activeStepRemaining <= 0 && activeStepIndex !in completedSteps -> "Selesaikan Langkah ${activeStepIndex + 1}"
        activeStepIndex in completedSteps -> "Lanjut ke Langkah ${activeStepIndex + 2}"
        else -> "Mulai Langkah ${activeStepIndex + 1}"
    }
    val routineButtonEnabled = !hasRoutineSteps || !isRoutineRunning

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailTopBar(
                title = if (isAnalysisCalendar) "Kalender Latihan" else info.title,
                onBack = onBack
            )
        }
        if (!isAnalysisCalendar && info.showHeroCard) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    color = Color.Transparent,
                    shadowElevation = 8.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(info.accent.copy(alpha = 0.18f), Color.White)
                                )
                            )
                            .padding(22.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(88.dp)
                                    .background(
                                        brush = Brush.linearGradient(listOf(info.accent, AquaTeal)),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = info.icon,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                            Text(
                                text = info.subtitle,
                                style = MaterialTheme.typography.titleMedium,
                                color = OceanBlue,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = info.body,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            )
                            if (info.countdownSeconds > 0 && !hasIntervalSegments) {
                                Surface(
                                    shape = RoundedCornerShape(22.dp),
                                    color = info.accent.copy(alpha = 0.1f),
                                    border = BorderStroke(1.dp, info.accent.copy(alpha = 0.2f))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.AccessTime,
                                                contentDescription = null,
                                                tint = info.accent
                                            )
                                            Text(
                                                text = "Countdown Aktif",
                                                style = MaterialTheme.typography.labelLarge,
                                                color = info.accent
                                            )
                                        }
                                        Text(
                                            text = routineRemainingSeconds.toCountdownLabel(),
                                            style = MaterialTheme.typography.displaySmall,
                                            color = OceanBlue,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (isAnalysisCalendar) {
            item {
                WorkoutCalendarCard(
                    workoutDates = workoutDates,
                    plannedSessions = plannedSessions
                )
            }
        }
        if (hasIntervalSegments) {
            items(intervalSegments.size) { index ->
                val segment = intervalSegments[index]
                val isCompleted = segment.completed
                val isActive = index == activeIntervalIndex && !isCompleted
                val isResting = isActive && segment.isResting
                val isEditable = isActive && !isResting
                val isValid = segment.distanceMeters.toIntOrNull()?.let { it > 0 } == true &&
                    (segment.minutes.toIntOrNull() ?: -1) >= 0 &&
                    (segment.seconds.toIntOrNull() ?: -1) in 0..59
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = if (isCompleted) Color(0xFFF4FBFF) else SurfaceWhite,
                    border = BorderStroke(
                        1.dp,
                        when {
                            isCompleted -> AquaTeal.copy(alpha = 0.35f)
                            isActive -> info.accent.copy(alpha = 0.35f)
                            else -> BorderSoft.copy(alpha = 0.45f)
                        }
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Set ${segment.segmentNumber}",
                                style = MaterialTheme.typography.titleSmall,
                                color = TextPrimary
                            )
                            Text(
                                text = when {
                                    isCompleted -> "Selesai"
                                    isResting -> "Istirahat"
                                    isActive -> "Aktif"
                                    else -> "Menunggu"
                                },
                                style = MaterialTheme.typography.labelLarge,
                                color = if (isCompleted) AquaTeal else info.accent
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            DetailInputField(
                                label = "Jarak",
                                value = segment.distanceMeters,
                                onValueChange = {
                                    val sanitized = it.filter(Char::isDigit)
                                    intervalSegments[index] = intervalSegments[index].copy(distanceMeters = sanitized)
                                },
                                modifier = Modifier.weight(1f),
                                enabled = isEditable,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            DetailInputField(
                                label = "Menit",
                                value = segment.minutes,
                                onValueChange = {
                                    val sanitized = it.filter(Char::isDigit)
                                    intervalSegments[index] = intervalSegments[index].copy(minutes = sanitized)
                                },
                                modifier = Modifier.weight(1f),
                                enabled = isEditable,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            DetailInputField(
                                label = "Detik",
                                value = segment.seconds,
                                onValueChange = {
                                    val sanitized = it.filter(Char::isDigit).take(2)
                                    intervalSegments[index] = intervalSegments[index].copy(seconds = sanitized)
                                },
                                modifier = Modifier.weight(1f),
                                enabled = isEditable,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                        TinyMetricCard(
                            title = "Pace Segmen",
                            value = segment.paceLabel(),
                            icon = Icons.AutoMirrored.Outlined.ShowChart,
                            modifier = Modifier.fillMaxWidth()
                        )
                        PrimaryGradientButton(
                            text = when {
                                isCompleted -> "Selesai"
                                isResting -> "Istirahat ${segment.restSecondsRemaining.toCountdownLabel()}"
                                isActive -> "Selesai"
                                else -> "Menunggu"
                            },
                            onClick = {
                                if (isActive && isValid) {
                                    intervalSegments[index] = intervalSegments[index].copy(
                                        isResting = true,
                                        restSecondsRemaining = 240
                                    )
                                }
                            },
                            enabled = isActive && !isResting && isValid
                        )
                    }
                }
            }
        } else if (showSimpleRunInput && !isAnalysisCalendar) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    color = SurfaceWhite,
                    border = BorderStroke(1.dp, BorderSoft)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Input Hasil Lari",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            DetailInputField(
                                label = "Jarak (km)",
                                value = runLogDraft.distanceKm,
                                onValueChange = onRunDistanceChange,
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                            )
                            DetailInputField(
                                label = "Menit",
                                value = runLogDraft.durationMinutes,
                                onValueChange = onRunDurationChange,
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            DetailInputField(
                                label = "Detik",
                                value = runLogDraft.durationSeconds,
                                onValueChange = onRunDurationSecondsChange,
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                        TinyMetricCard(
                            title = "Pace",
                            value = runLogDraft.manualPaceLabel(),
                            icon = Icons.AutoMirrored.Outlined.ShowChart,
                            modifier = Modifier.fillMaxWidth()
                        )
                        DetailInputField(
                            label = "Catatan Lari",
                            value = runLogDraft.notes,
                            onValueChange = onRunNotesChange,
                            singleLine = false
                        )
                    }
                }
            }
        } else if (hasRoutineSteps) {
            items(info.routineSteps.size) { index ->
                val step = info.routineSteps[index]
                val isCompleted = index in completedSteps
                val isActive = index == activeStepIndex
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = if (isCompleted) Color(0xFFF4FBFF) else SurfaceWhite,
                    border = BorderStroke(
                        1.dp,
                        when {
                            isCompleted -> AquaTeal.copy(alpha = 0.35f)
                            isActive -> info.accent.copy(alpha = 0.35f)
                            else -> BorderSoft.copy(alpha = 0.45f)
                        }
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${index + 1}. ${step.title}",
                                style = MaterialTheme.typography.titleSmall,
                                color = TextPrimary
                            )
                            Text(
                                text = when {
                                    isCompleted -> "Selesai"
                                    isActive && isRoutineRunning -> activeStepRemaining.toCountdownLabel()
                                    isActive -> "Aktif"
                                    else -> "Menunggu"
                                },
                                style = MaterialTheme.typography.labelLarge,
                                color = if (isCompleted) AquaTeal else info.accent
                            )
                        }
                        Text(
                            text = step.subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }
            }
        } else if (!isAnalysisCalendar) {
            items(info.highlights) { highlight ->
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = SurfaceWhite,
                    border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(info.accent.copy(alpha = 0.14f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = info.accent,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = highlight,
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextPrimary
                        )
                    }
                }
            }
        }
        if (!isAnalysisCalendar && (!hasIntervalSegments || intervalSegments.all { it.completed })) {
            item {
                PrimaryGradientButton(
                    text = routineButtonLabel,
                    onClick = {
                        if (!hasRoutineSteps) {
                            onPrimaryAction()
                        } else if (completedSteps.size >= info.routineSteps.size) {
                            onPrimaryAction()
                        } else if (activeStepRemaining <= 0 && activeStepIndex !in completedSteps) {
                            completedSteps = completedSteps + activeStepIndex
                            if (activeStepIndex < info.routineSteps.lastIndex) {
                                activeStepIndex += 1
                                activeStepRemaining = info.routineSteps[activeStepIndex].durationSeconds
                            }
                        } else if (activeStepIndex in completedSteps) {
                            val nextIndex = (activeStepIndex + 1).coerceAtMost(info.routineSteps.lastIndex)
                            activeStepIndex = nextIndex
                            activeStepRemaining = info.routineSteps[nextIndex].durationSeconds
                        } else {
                            if (!isRoutineRunning) {
                                isRoutineRunning = true
                            }
                        }
                    },
                    enabled = routineButtonEnabled
                )
            }
        } else if (!isAnalysisCalendar) {
            item {
                Text(
                    text = "Selesaikan set aktif dan tunggu istirahat selesai sebelum melanjutkan ke tahap berikutnya.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun WorkoutCalendarCard(
    workoutDates: Set<LocalDate>,
    plannedSessions: List<WorkoutSession>,
    modifier: Modifier = Modifier
) {
    val currentMonth = remember { YearMonth.now() }
    val firstDay = currentMonth.atDay(1)
    val daysInMonth = currentMonth.lengthOfMonth()
    val leadingEmptyCells = ((firstDay.dayOfWeek.value - DayOfWeek.MONDAY.value) + 7) % 7
    val totalCells = leadingEmptyCells + daysInMonth
    val rowCount = (totalCells + 6) / 7
    val monthLabel = buildString {
        append(
            currentMonth.month.getDisplayName(
                TextStyle.FULL,
                Locale("id", "ID")
            ).replaceFirstChar { char ->
                if (char.isLowerCase()) char.titlecase(Locale("id", "ID")) else char.toString()
            }
        )
        append(" ")
        append(currentMonth.year)
    }
    val workoutDaysThisMonth = workoutDates
        .filter { YearMonth.from(it) == currentMonth }
        .toSet()
    val plannedEntries = remember(currentMonth, plannedSessions) {
        val monthStart = currentMonth.atDay(1)
        val monthEnd = currentMonth.atEndOfMonth()
        generateSequence(monthStart) { current ->
            current.plusDays(1).takeIf { it <= monthEnd }
        }.flatMap { date ->
            plannedSessions
                .filter { it.dayOfWeek == date.dayOfWeek }
                .sortedBy { it.orderInDay }
                .map { session -> date to session }
        }.toList()
    }
    val plannedWorkoutDays = plannedEntries.map { it.first }.toSet()
    val highlightedDays = workoutDaysThisMonth + plannedWorkoutDays
    val completedWorkoutDays = workoutDaysThisMonth
    val today = LocalDate.now()
    val dayHeaders = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f)),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Aktivitas Latihan",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                    Text(
                        text = monthLabel,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(AquaTeal, CircleShape)
                        )
                        Text(
                            text = "Ada latihan",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextSecondary
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(OceanBlue, CircleShape)
                        )
                        Text(
                            text = "Sudah latihan",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextSecondary
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dayHeaders.forEach { label ->
                    Text(
                        text = label,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium,
                        color = TextSecondary
                    )
                }
            }

            repeat(rowCount) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(7) { columnIndex ->
                        val cellIndex = rowIndex * 7 + columnIndex
                        val dayNumber = cellIndex - leadingEmptyCells + 1
                        if (dayNumber !in 1..daysInMonth) {
                            Spacer(modifier = Modifier.weight(1f))
                        } else {
                            val date = currentMonth.atDay(dayNumber)
                            val hasWorkout = date in highlightedDays
                            val hasCompletedWorkout = date in completedWorkoutDays
                            val isToday = date == today

                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                color = when {
                                    hasCompletedWorkout -> OceanBlue.copy(alpha = 0.14f)
                                    hasWorkout -> infoCardHighlight()
                                    else -> SurfaceTint.copy(alpha = 0.35f)
                                },
                                border = BorderStroke(
                                    width = if (isToday) 1.5.dp else 1.dp,
                                    color = when {
                                        hasCompletedWorkout -> OceanBlue.copy(alpha = 0.75f)
                                        hasWorkout -> AquaTeal.copy(alpha = 0.55f)
                                        isToday -> OceanBlue.copy(alpha = 0.45f)
                                        else -> BorderSoft.copy(alpha = 0.35f)
                                    }
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = dayNumber.toString(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = when {
                                            hasCompletedWorkout -> OceanBlue
                                            hasWorkout -> OceanBlue
                                            else -> TextPrimary
                                        },
                                        fontWeight = if (hasWorkout || hasCompletedWorkout || isToday) FontWeight.Bold else FontWeight.Medium
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(
                                                color = when {
                                                    hasCompletedWorkout -> OceanBlue
                                                    hasWorkout -> AquaTeal
                                                    else -> Color.Transparent
                                                },
                                                shape = CircleShape
                                            )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (plannedEntries.isNotEmpty()) {
                HorizontalDivider(color = BorderSoft.copy(alpha = 0.45f))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "List Latihan",
                        style = MaterialTheme.typography.titleMedium,
                        color = OceanBlue,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    plannedEntries.forEach { (date, session) ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            color = SurfaceTint.copy(alpha = 0.22f),
                            border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.35f))
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = date.toCalendarLabel(),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = OceanBlue,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = session.toCalendarNote(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun infoCardHighlight(): Color = AquaBright.copy(alpha = 0.18f)

private fun LocalDate.toCalendarLabel(): String {
    val monthLabel = month.getDisplayName(TextStyle.FULL, Locale("id", "ID"))
        .replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase(Locale("id", "ID")) else char.toString()
        }
    return "$dayOfMonth $monthLabel"
}

private fun WorkoutSession.toCalendarNote(): String {
    val distanceLabel = targetDistanceKm?.let { " ${"%.0f".format(it)} km" }.orEmpty()
    val strengthLabel = if (targetSets != null && targetReps != null) {
        " ${targetSets} set x ${targetReps} rep"
    } else {
        ""
    }
    return when (category) {
        WorkoutCategory.CARDIO -> "Latihan ${focusArea.lowercase()} - $title$distanceLabel"
        WorkoutCategory.STRENGTH -> "Latihan ${focusArea.lowercase()} - $title$strengthLabel"
        WorkoutCategory.MOBILITY -> "Latihan mobilitas - $title"
        WorkoutCategory.RECOVERY -> "Sesi pemulihan - $title"
    }
}

private fun Int.toCountdownLabel(): String {
    val totalSeconds = coerceAtLeast(0)
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return if (hours > 0) {
        "%d:%02d:%02d".format(hours, minutes, seconds)
    } else {
        "%d:%02d".format(minutes, seconds)
    }
}

private data class QuickActionCardUi(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val tint: Color,
    val onClick: () -> Unit
)

@Composable
private fun QuickActionCard(action: QuickActionCardUi) {
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
private fun NotificationCard(
    item: NotificationItemUi,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(item.background, RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(item.icon, contentDescription = null, tint = item.tint)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                    Text(
                        text = item.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
                Text(
                    text = item.timeLabel,
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary
                )
            }
            Text(
                text = item.body,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary
            )
        }
    }
}

@Composable
private fun TargetFrequencyCard(
    option: FrequencyOption,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = if (selected) AquaBright.copy(alpha = 0.18f) else SurfaceWhite,
        border = BorderStroke(1.dp, if (selected) AquaTeal else BorderSoft.copy(alpha = 0.45f))
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "${option.days} Hari",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
                Text(
                    text = option.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .background(
                        color = if (selected) AquaTeal else Color.Transparent,
                        shape = CircleShape
                    )
                    .padding(5.dp),
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
    }
}

@Composable
private fun PlannerSessionCard(
    session: WorkoutSession,
    onEdit: () -> Unit,
    onMove: () -> Unit,
    onSkip: () -> Unit,
    onComplete: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = session.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                    Text(
                        text = "${session.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }} - ${session.targetDurationMinutes} mnt - ${session.category.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    if (session.sessionNotes.isNotBlank()) {
                        Text(
                            text = session.sessionNotes,
                            style = MaterialTheme.typography.labelMedium,
                            color = TextSecondary
                        )
                    }
                }
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = when (session.plannerState) {
                        SessionPlannerState.COMPLETED -> Color(0xFFDFFFF7)
                        SessionPlannerState.SKIPPED -> Color(0xFFFFF1E8)
                        SessionPlannerState.PLANNED -> SurfaceTint
                    }
                ) {
                    Text(
                        text = when (session.plannerState) {
                            SessionPlannerState.COMPLETED -> "Selesai"
                            SessionPlannerState.SKIPPED -> "Skip"
                            SessionPlannerState.PLANNED -> "Planned"
                        },
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = when (session.plannerState) {
                            SessionPlannerState.COMPLETED -> AquaTeal
                            SessionPlannerState.SKIPPED -> Color(0xFFCC6A00)
                            SessionPlannerState.PLANNED -> OceanBlue
                        }
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SecondaryOutlineButton(text = "Edit", onClick = onEdit)
                SecondaryOutlineButton(text = "Geser Hari", onClick = onMove)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SecondaryOutlineButton(text = "Lewati", onClick = onSkip)
                SecondaryOutlineButton(text = "Selesai", onClick = onComplete)
                SecondaryOutlineButton(text = "Hapus", onClick = onDelete)
            }
        }
    }
}

@Composable
private fun SessionCategorySelector(
    selectedCategory: WorkoutCategory,
    onSelected: (WorkoutCategory) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Kategori Sesi",
            style = MaterialTheme.typography.labelLarge,
            color = TextSecondary
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(
                WorkoutCategory.STRENGTH to "Gym",
                WorkoutCategory.CARDIO to "Lari",
                WorkoutCategory.MOBILITY to "Mobility",
                WorkoutCategory.RECOVERY to "Recovery"
            ).forEach { (category, label) ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (selectedCategory == category) OceanBlue else SurfaceTint,
                    modifier = Modifier.clickable { onSelected(category) }
                ) {
                    Text(
                        text = label,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selectedCategory == category) Color.White else TextSecondary
                    )
                }
            }
        }
    }
}

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
private fun ProgramVariantSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onSelected: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = TextSecondary
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (selectedOption == option) AquaBright.copy(alpha = 0.28f) else SurfaceTint,
                    border = BorderStroke(
                        1.dp,
                        if (selectedOption == option) AquaTeal else Color.Transparent
                    ),
                    modifier = Modifier.clickable { onSelected(option) }
                ) {
                    Text(
                        text = option,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selectedOption == option) OceanBlue else TextPrimary
                    )
                }
            }
        }
    }
}

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
private fun PlannerDaySelector(
    selectedDay: java.time.DayOfWeek,
    onSelected: (java.time.DayOfWeek) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Hari Latihan",
            style = MaterialTheme.typography.labelLarge,
            color = TextSecondary
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            java.time.DayOfWeek.values().take(7).forEach { day ->
                val label = day.name.take(3)
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (selectedDay == day) AquaTeal else SurfaceTint,
                    modifier = Modifier.clickable { onSelected(day) }
                ) {
                    Text(
                        text = label,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = if (selectedDay == day) Color.White else TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun ToggleSettingRow(
    title: String,
    subtitle: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(SurfaceTint, RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = OceanBlue)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
            Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = AquaTeal,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = BorderSoft
            )
        )
    }
}

@Composable
private fun FaqCard(
    question: String,
    answer: String
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
            Text(
                text = answer,
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )
        }
    }
}

@Composable
private fun AchievementDetailRow(
    item: AchievementCardUi,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .let { base -> if (onClick != null) base.clickable(onClick = onClick) else base },
        shape = RoundedCornerShape(22.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(item.background, RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(item.icon, contentDescription = null, tint = item.tint)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
            if (onClick != null) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = null,
                    tint = TextSecondary
                )
            }
        }
    }
}

@Composable
private fun EmptyStateCard(
    icon: ImageVector,
    title: String,
    body: String
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(SurfaceTint, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = OceanBlue, modifier = Modifier.size(34.dp))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun UtilityTopBar(
    title: String,
    onBack: () -> Unit
) {
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
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
            color = OceanBlue,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(48.dp))
    }
}
