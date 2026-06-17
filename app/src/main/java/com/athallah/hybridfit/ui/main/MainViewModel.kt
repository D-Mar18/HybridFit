package com.athallah.hybridfit.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.athallah.hybridfit.auth.GoogleAccountProfile
import com.athallah.hybridfit.data.local.OnboardingPreferenceStore
import com.athallah.hybridfit.data.remote.AuthSession
import com.athallah.hybridfit.data.remote.AuthSessionStore
import com.athallah.hybridfit.data.remote.CoachChatMessage
import com.athallah.hybridfit.data.remote.CoachChatResponse
import com.athallah.hybridfit.data.remote.HybridFitAiApi
import com.athallah.hybridfit.data.remote.HybridFitAuthApi
import com.athallah.hybridfit.data.remote.RemoteAuthUser
import com.athallah.hybridfit.data.repository.HybridFitRepository
import com.athallah.hybridfit.domain.model.DashboardSnapshot
import com.athallah.hybridfit.domain.model.FitnessGoal
import com.athallah.hybridfit.domain.model.FitnessLevel
import com.athallah.hybridfit.domain.model.SessionPlannerState
import com.athallah.hybridfit.domain.model.StrengthExerciseDraft
import com.athallah.hybridfit.domain.model.WorkoutCategory
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.time.DayOfWeek
import java.time.Instant
import java.util.Locale

enum class AuthAction {
    NONE,
    LOGIN,
    REGISTER,
    GOOGLE_LOGIN,
    APPLE_LOGIN,
    LOGOUT
}

data class MainUiState(
    val isLoading: Boolean = true,
    val snapshot: DashboardSnapshot? = null,
    val isCredentialAuthInProgress: Boolean = false,
    val isGoogleAuthInProgress: Boolean = false,
    val isLogoutInProgress: Boolean = false,
    val isProgramCreationInProgress: Boolean = false,
    val isCoachChatSending: Boolean = false,
    val isAuthenticated: Boolean = false,
    val hasCompletedIntroSurvey: Boolean = false,
    val currentUser: RemoteAuthUser? = null,
    val authStatusMessage: String? = null,
    val authEventId: Long = 0L,
    val infoMessage: String? = null,
    val infoEventId: Long = 0L,
    val lastAuthAction: AuthAction = AuthAction.NONE
)

class MainViewModel(
    private val repository: HybridFitRepository,
    private val authApi: HybridFitAuthApi,
    private val aiApi: HybridFitAiApi,
    private val authSessionStore: AuthSessionStore,
    private val onboardingPreferenceStore: OnboardingPreferenceStore
) : ViewModel() {
    var uiState by mutableStateOf(MainUiState())
        private set
    private var dashboardObservationJob: Job? = null

    init {
        val storedSession = authSessionStore.getSession()
        uiState = uiState.copy(
            isAuthenticated = storedSession != null,
            hasCompletedIntroSurvey = resolveSurveyCompleted(storedSession?.user),
            currentUser = storedSession?.user
        )

        startDashboardObservation(storedSession?.user?.id?.toLong())
    }

    fun simulateWorkoutCompletion() {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            repository.recordQuickWorkout(currentUserId)
        }
    }

    fun refreshRecommendation() {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            repository.refreshRecommendation(currentUserId)
        }
    }

    fun submitFeedback(wasHelpful: Boolean) {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            repository.recordFeedback(currentUserId, wasHelpful)
        }
    }

    fun updateCurrentUserAvatar(avatarUrl: String) {
        val currentSession = authSessionStore.getSession() ?: return
        val updatedSession = currentSession.copy(
            user = currentSession.user.copy(avatarUrl = avatarUrl)
        )
        authSessionStore.saveSession(updatedSession)
        authSessionStore.saveAvatarForEmail(updatedSession.user.email, avatarUrl)
        uiState = uiState.copy(currentUser = updatedSession.user)
    }

    fun saveStrengthWorkout(
        sessionId: Long,
        actualDurationMinutes: Int,
        exertionScore: Int,
        notes: String,
        exercises: List<StrengthExerciseDraft>
    ) {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            runCatching {
                repository.saveStrengthWorkout(
                    userId = currentUserId,
                    sessionId = sessionId,
                    actualDurationMinutes = actualDurationMinutes,
                    exertionScore = exertionScore,
                    notes = notes,
                    exercises = exercises
                )
            }.onSuccess {
                uiState = uiState.copy(
                    infoMessage = "Log latihan gym berhasil disimpan.",
                    infoEventId = uiState.infoEventId + 1
                )
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    infoMessage = throwable.message ?: "Log latihan gym gagal disimpan.",
                    infoEventId = uiState.infoEventId + 1
                )
            }
        }
    }

    fun saveRunWorkout(
        sessionId: Long,
        distanceKm: Double,
        actualDurationMinutes: Int,
        exertionScore: Int,
        notes: String
    ) {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            runCatching {
                repository.saveRunWorkout(
                    userId = currentUserId,
                    sessionId = sessionId,
                    distanceKm = distanceKm,
                    actualDurationMinutes = actualDurationMinutes,
                    exertionScore = exertionScore,
                    notes = notes
                )
            }.onSuccess {
                uiState = uiState.copy(
                    infoMessage = "Log lari berhasil disimpan.",
                    infoEventId = uiState.infoEventId + 1
                )
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    infoMessage = throwable.message ?: "Log lari gagal disimpan.",
                    infoEventId = uiState.infoEventId + 1
                )
            }
        }
    }

    fun updateStrengthWorkout(
        logId: Long,
        actualDurationMinutes: Int,
        exertionScore: Int,
        notes: String,
        exercises: List<StrengthExerciseDraft>
    ) {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            runCatching {
                repository.updateStrengthWorkout(
                    userId = currentUserId,
                    logId = logId,
                    actualDurationMinutes = actualDurationMinutes,
                    exertionScore = exertionScore,
                    notes = notes,
                    exercises = exercises
                )
            }.onSuccess {
                uiState = uiState.copy(
                    infoMessage = "Log gym berhasil diperbarui.",
                    infoEventId = uiState.infoEventId + 1
                )
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    infoMessage = throwable.message ?: "Log gym gagal diperbarui.",
                    infoEventId = uiState.infoEventId + 1
                )
            }
        }
    }

    fun updateRunWorkout(
        logId: Long,
        distanceKm: Double,
        actualDurationMinutes: Int,
        exertionScore: Int,
        notes: String
    ) {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            runCatching {
                repository.updateRunWorkout(
                    userId = currentUserId,
                    logId = logId,
                    distanceKm = distanceKm,
                    actualDurationMinutes = actualDurationMinutes,
                    exertionScore = exertionScore,
                    notes = notes
                )
            }.onSuccess {
                uiState = uiState.copy(
                    infoMessage = "Log lari berhasil diperbarui.",
                    infoEventId = uiState.infoEventId + 1
                )
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    infoMessage = throwable.message ?: "Log lari gagal diperbarui.",
                    infoEventId = uiState.infoEventId + 1
                )
            }
        }
    }

    fun deleteWorkoutLog(logId: Long) {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            runCatching {
                repository.deleteWorkoutLog(currentUserId, logId)
            }.onSuccess {
                uiState = uiState.copy(
                    infoMessage = "Log latihan berhasil dihapus.",
                    infoEventId = uiState.infoEventId + 1
                )
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    infoMessage = throwable.message ?: "Log latihan gagal dihapus.",
                    infoEventId = uiState.infoEventId + 1
                )
            }
        }
    }

    fun saveRecoveryCheckIn(
        sleepHours: Double,
        recoveryScore: Int,
        energyLevel: Int,
        sorenessLevel: Int,
        notes: String
    ) {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            runCatching {
                repository.saveRecoveryCheckIn(
                    userId = currentUserId,
                    sleepHours = sleepHours,
                    recoveryScore = recoveryScore,
                    energyLevel = energyLevel,
                    sorenessLevel = sorenessLevel,
                    notes = notes
                )
            }.onSuccess {
                uiState = uiState.copy(
                    infoMessage = "Check-in tidur dan recovery berhasil disimpan.",
                    infoEventId = uiState.infoEventId + 1
                )
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    infoMessage = throwable.message ?: "Check-in recovery gagal disimpan.",
                    infoEventId = uiState.infoEventId + 1
                )
            }
        }
    }

    fun addPlannerSession(
        title: String,
        category: WorkoutCategory,
        focusArea: String,
        dayOfWeek: DayOfWeek,
        durationMinutes: Int,
        targetDistanceKm: Double?,
        targetSets: Int?,
        targetReps: Int?,
        restSeconds: Int,
        notes: String
    ) {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            runCatching {
                repository.addPlannerSession(
                    userId = currentUserId,
                    title = title,
                    category = category,
                    focusArea = focusArea,
                    dayOfWeek = dayOfWeek,
                    durationMinutes = durationMinutes,
                    targetDistanceKm = targetDistanceKm,
                    targetSets = targetSets,
                    targetReps = targetReps,
                    restSeconds = restSeconds,
                    notes = notes
                )
            }.onSuccess {
                uiState = uiState.copy(
                    infoMessage = "Sesi planner baru berhasil ditambahkan.",
                    infoEventId = uiState.infoEventId + 1
                )
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    infoMessage = throwable.message ?: "Sesi planner gagal ditambahkan.",
                    infoEventId = uiState.infoEventId + 1
                )
            }
        }
    }

    fun updatePlannerSession(
        sessionId: Long,
        title: String,
        category: WorkoutCategory,
        focusArea: String,
        dayOfWeek: DayOfWeek,
        durationMinutes: Int,
        targetDistanceKm: Double?,
        targetSets: Int?,
        targetReps: Int?,
        restSeconds: Int,
        notes: String
    ) {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            runCatching {
                repository.updatePlannerSession(
                    userId = currentUserId,
                    sessionId = sessionId,
                    title = title,
                    category = category,
                    focusArea = focusArea,
                    dayOfWeek = dayOfWeek,
                    durationMinutes = durationMinutes,
                    targetDistanceKm = targetDistanceKm,
                    targetSets = targetSets,
                    targetReps = targetReps,
                    restSeconds = restSeconds,
                    notes = notes
                )
            }.onSuccess {
                uiState = uiState.copy(
                    infoMessage = "Planner sesi berhasil diperbarui.",
                    infoEventId = uiState.infoEventId + 1
                )
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    infoMessage = throwable.message ?: "Planner sesi gagal diperbarui.",
                    infoEventId = uiState.infoEventId + 1
                )
            }
        }
    }

    fun movePlannerSessionToNextDay(sessionId: Long) {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            repository.movePlannerSessionToNextDay(currentUserId, sessionId)
            uiState = uiState.copy(
                infoMessage = "Sesi dipindahkan ke hari berikutnya.",
                infoEventId = uiState.infoEventId + 1
            )
        }
    }

    fun updatePlannerSessionState(sessionId: Long, plannerState: SessionPlannerState) {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            repository.updatePlannerSessionState(currentUserId, sessionId, plannerState)
            uiState = uiState.copy(
                infoMessage = when (plannerState) {
                    SessionPlannerState.PLANNED -> "Sesi dikembalikan ke status terjadwal."
                    SessionPlannerState.SKIPPED -> "Sesi ditandai dilewati."
                    SessionPlannerState.COMPLETED -> "Sesi ditandai selesai."
                },
                infoEventId = uiState.infoEventId + 1
            )
        }
    }

    fun deletePlannerSession(sessionId: Long) {
        val currentUserId = uiState.currentUser?.id?.toLong() ?: return
        viewModelScope.launch {
            repository.deletePlannerSession(currentUserId, sessionId)
            uiState = uiState.copy(
                infoMessage = "Sesi planner berhasil dihapus.",
                infoEventId = uiState.infoEventId + 1
            )
        }
    }

    fun completeIntroSurvey() {
        val currentUser = uiState.currentUser ?: return
        val currentUserId = currentUser.id.toLong()
        onboardingPreferenceStore.setCompletedIntroSurvey(currentUserId, true)
        val updatedUser = currentUser.copy(hasCompletedSurvey = true)
        authSessionStore.getSession()?.let { session ->
            authSessionStore.saveSession(session.copy(user = updatedUser))
        }
        uiState = uiState.copy(
            hasCompletedIntroSurvey = true,
            currentUser = updatedUser
        )
    }

    fun createStarterProgram(
        fullName: String,
        age: Int,
        weightKg: Double,
        heightCm: Int,
        goal: FitnessGoal,
        fitnessLevel: FitnessLevel,
        preferredFocus: WorkoutCategory,
        workoutDaysPerWeek: Int,
        sessionDurationMinutes: Int,
        experienceNotes: String
    ) {
        val currentUser = uiState.currentUser ?: run {
            uiState = uiState.copy(
                infoMessage = "Login terlebih dahulu untuk membuat program pertama.",
                infoEventId = uiState.infoEventId + 1
            )
            return
        }

        if (uiState.isProgramCreationInProgress) return

        viewModelScope.launch {
            val storedSession = authSessionStore.getSession()
            if (storedSession == null) {
                uiState = uiState.copy(
                    infoMessage = "Sesi login tidak ditemukan. Silakan login lagi.",
                    infoEventId = uiState.infoEventId + 1
                )
                return@launch
            }

            uiState = uiState.copy(
                isProgramCreationInProgress = true,
                infoMessage = null
            )

            var updatedUser = currentUser
            var infoMessage = "Survey dan program pertama berhasil dibuat."

            runCatching {
                updatedUser = if (storedSession.accessToken.startsWith("local-")) {
                    currentUser.copy(hasCompletedSurvey = true)
                } else {
                    authApi.submitSurvey(
                        accessToken = storedSession.accessToken,
                        fullName = fullName,
                        age = age,
                        weightKg = weightKg,
                        heightCm = heightCm,
                        goal = goal.name,
                        fitnessLevel = fitnessLevel.name,
                        preferredFocus = preferredFocus.name,
                        workoutDaysPerWeek = workoutDaysPerWeek,
                        sessionDurationMinutes = sessionDurationMinutes,
                        experienceNotes = experienceNotes
                    )
                }

                authSessionStore.saveSession(storedSession.copy(user = updatedUser))

                repository.createStarterProgram(
                    userId = currentUser.id.toLong(),
                    fullName = fullName,
                    age = age,
                    weightKg = weightKg,
                    heightCm = heightCm,
                    goal = goal,
                    fitnessLevel = fitnessLevel,
                    preferredFocus = preferredFocus,
                    workoutDaysPerWeek = workoutDaysPerWeek,
                    sessionDurationMinutes = sessionDurationMinutes,
                    experienceNotes = experienceNotes
                )
            }.onFailure { throwable ->
                if (updatedUser.hasCompletedSurvey) {
                    onboardingPreferenceStore.setCompletedIntroSurvey(updatedUser.id.toLong(), true)
                    uiState = uiState.copy(
                        isProgramCreationInProgress = false,
                        hasCompletedIntroSurvey = true,
                        currentUser = updatedUser,
                        infoMessage = throwable.message
                            ?: "Survey tersimpan di server, tetapi data lokal gagal disiapkan.",
                        infoEventId = uiState.infoEventId + 1
                    )
                    return@launch
                }

                uiState = uiState.copy(
                    isProgramCreationInProgress = false,
                    infoMessage = throwable.message ?: "Program pertama gagal dibuat.",
                    infoEventId = uiState.infoEventId + 1
                )
                return@launch
            }.onSuccess {
                onboardingPreferenceStore.setCompletedIntroSurvey(updatedUser.id.toLong(), true)
                uiState = uiState.copy(
                    isProgramCreationInProgress = false,
                    hasCompletedIntroSurvey = true,
                    currentUser = updatedUser,
                    infoMessage = infoMessage,
                    infoEventId = uiState.infoEventId + 1
                )
            }
        }
    }

    fun applyCoachProgramProposal(
        goal: FitnessGoal,
        preferredFocus: WorkoutCategory,
        workoutDaysPerWeek: Int,
        sessionDurationMinutes: Int,
        requestSummary: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val currentUserId = uiState.currentUser?.id?.toLong()
        if (currentUserId == null) {
            onFailure("Login terlebih dahulu sebelum mengubah program.")
            return
        }

        viewModelScope.launch {
            runCatching {
                repository.replaceProgramFromAi(
                    userId = currentUserId,
                    goal = goal,
                    preferredFocus = preferredFocus,
                    workoutDaysPerWeek = workoutDaysPerWeek,
                    sessionDurationMinutes = sessionDurationMinutes,
                    requestSummary = requestSummary
                )
            }.onSuccess {
                uiState = uiState.copy(
                    infoMessage = "Program latihan berhasil diperbarui dari AI Coach.",
                    infoEventId = uiState.infoEventId + 1
                )
                onSuccess()
            }.onFailure { throwable ->
                onFailure(throwable.message ?: "Program dari AI Coach gagal diterapkan.")
            }
        }
    }

    fun updateWeeklyTargets(
        workoutDaysPerWeek: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val currentUserId = uiState.currentUser?.id?.toLong()
        val profile = uiState.snapshot?.profile
        if (currentUserId == null || profile == null) {
            onFailure("Profil pengguna belum tersedia.")
            return
        }

        viewModelScope.launch {
            runCatching {
                repository.replaceProgramFromAi(
                    userId = currentUserId,
                    goal = profile.goal,
                    preferredFocus = profile.preferredFocus,
                    workoutDaysPerWeek = workoutDaysPerWeek,
                    sessionDurationMinutes = profile.sessionDurationMinutes,
                    requestSummary = profile.experienceNotes.ifBlank { "Penyesuaian target mingguan." }
                )
            }.onSuccess {
                uiState = uiState.copy(
                    infoMessage = "Target mingguan dan jadwal latihan berhasil diperbarui.",
                    infoEventId = uiState.infoEventId + 1
                )
                onSuccess()
            }.onFailure { throwable ->
                onFailure(throwable.message ?: "Target mingguan gagal diperbarui.")
            }
        }
    }

    fun registerWithEmail(fullName: String, email: String, password: String) {
        if (uiState.isCredentialAuthInProgress || uiState.isGoogleAuthInProgress || uiState.isLogoutInProgress) {
            return
        }

        uiState = uiState.copy(
            isCredentialAuthInProgress = true,
            authStatusMessage = null,
            lastAuthAction = AuthAction.REGISTER
        )

        viewModelScope.launch {
            runCatching {
                val session = runCatching {
                    authApi.register(fullName = fullName, email = email, password = password)
                }.getOrElse { throwable ->
                    if (throwable.isBackendConnectionError()) {
                        authSessionStore.createLocalEmailSession(
                            fullName = fullName,
                            email = email
                        )
                    } else {
                        throw throwable
                    }
                }
                authSessionStore.saveSession(session)
                authSessionStore.saveLocalEmailAccount(session, password)
                session
            }.onSuccess { session ->
                syncSurveyPreference(session.user)
                startDashboardObservation(session.user.id.toLong())
                uiState = uiState.copy(
                    isCredentialAuthInProgress = false,
                    isAuthenticated = true,
                    hasCompletedIntroSurvey = resolveSurveyCompleted(session.user),
                    currentUser = session.user,
                    authStatusMessage = "Registrasi berhasil. Lanjutkan survei personalisasi.",
                    authEventId = uiState.authEventId + 1,
                    lastAuthAction = AuthAction.REGISTER
                )
            }.onFailure { throwable ->
                val storedSession = authSessionStore.getSession()
                uiState = uiState.copy(
                    isCredentialAuthInProgress = false,
                    isAuthenticated = storedSession != null,
                    hasCompletedIntroSurvey = resolveSurveyCompleted(storedSession?.user),
                    currentUser = storedSession?.user,
                    authStatusMessage = throwable.toUserFriendlyAuthMessage("Registrasi gagal."),
                    authEventId = uiState.authEventId + 1,
                    lastAuthAction = AuthAction.REGISTER
                )
            }
        }
    }

    fun loginWithEmail(email: String, password: String) {
        if (uiState.isCredentialAuthInProgress || uiState.isGoogleAuthInProgress || uiState.isLogoutInProgress) {
            return
        }

        uiState = uiState.copy(
            isCredentialAuthInProgress = true,
            authStatusMessage = null,
            lastAuthAction = AuthAction.LOGIN
        )

        viewModelScope.launch {
            runCatching {
                val session = runCatching {
                    authApi.login(email = email, password = password)
                }.getOrElse { throwable ->
                    if (throwable.isBackendConnectionError()) {
                        authSessionStore.loginLocalEmail(email = email, password = password)
                    } else {
                        throw throwable
                    }
                }
                authSessionStore.saveSession(session)
                authSessionStore.saveLocalEmailAccount(session, password)
                session
            }.onSuccess { session ->
                syncSurveyPreference(session.user)
                startDashboardObservation(session.user.id.toLong())
                uiState = uiState.copy(
                    isCredentialAuthInProgress = false,
                    isAuthenticated = true,
                    hasCompletedIntroSurvey = resolveSurveyCompleted(session.user),
                    currentUser = session.user,
                    authStatusMessage = "Login berhasil. Selamat datang kembali, ${session.user.fullName}.",
                    authEventId = uiState.authEventId + 1,
                    lastAuthAction = AuthAction.LOGIN
                )
            }.onFailure { throwable ->
                val storedSession = authSessionStore.getSession()
                uiState = uiState.copy(
                    isCredentialAuthInProgress = false,
                    isAuthenticated = storedSession != null,
                    hasCompletedIntroSurvey = resolveSurveyCompleted(storedSession?.user),
                    currentUser = storedSession?.user,
                    authStatusMessage = throwable.toUserFriendlyAuthMessage("Login gagal."),
                    authEventId = uiState.authEventId + 1,
                    lastAuthAction = AuthAction.LOGIN
                )
            }
        }
    }

    fun beginGoogleAuth() {
        if (uiState.isCredentialAuthInProgress || uiState.isGoogleAuthInProgress || uiState.isLogoutInProgress) {
            return
        }

        uiState = uiState.copy(
            isGoogleAuthInProgress = true,
            authStatusMessage = null,
            lastAuthAction = AuthAction.GOOGLE_LOGIN
        )
    }

    fun loginWithAppleDemo() {
        if (uiState.isCredentialAuthInProgress || uiState.isGoogleAuthInProgress || uiState.isLogoutInProgress) {
            return
        }

        val session = createLocalProviderSession(
            id = "apple-demo",
            email = "apple.user@hybridfit.dev",
            fullName = "Apple Athlete",
            authProvider = "APPLE",
            avatarUrl = null
        )
        viewModelScope.launch {
            authSessionStore.saveSession(session)
            syncSurveyPreference(session.user)
            startDashboardObservation(session.user.id.toLong())
            uiState = uiState.copy(
                isAuthenticated = true,
                hasCompletedIntroSurvey = resolveSurveyCompleted(session.user),
                currentUser = session.user,
                authStatusMessage = "Login Apple berhasil sebagai ${session.user.fullName}.",
                authEventId = uiState.authEventId + 1,
                lastAuthAction = AuthAction.APPLE_LOGIN
            )
        }
    }

    fun completeGoogleAuth(accountProfile: GoogleAccountProfile) {
        viewModelScope.launch {
            runCatching {
                authApi.loginWithGoogle(accountProfile.idToken)
            }.onSuccess { session ->
                val cachedAvatar = authSessionStore.getAvatarForEmail(session.user.email)
                val resolvedSession = if (session.user.avatarUrl.isNullOrBlank() && !cachedAvatar.isNullOrBlank()) {
                    session.copy(user = session.user.copy(avatarUrl = cachedAvatar))
                } else {
                    session
                }
                authSessionStore.saveSession(resolvedSession)
                syncSurveyPreference(resolvedSession.user)
                startDashboardObservation(resolvedSession.user.id.toLong())
                uiState = uiState.copy(
                    isGoogleAuthInProgress = false,
                    isAuthenticated = true,
                    hasCompletedIntroSurvey = resolveSurveyCompleted(resolvedSession.user),
                    currentUser = resolvedSession.user,
                    authStatusMessage = "Login Google berhasil sebagai ${resolvedSession.user.fullName}.",
                    authEventId = uiState.authEventId + 1,
                    lastAuthAction = AuthAction.GOOGLE_LOGIN
                )
            }.onFailure { throwable ->
                val storedSession = authSessionStore.getSession()
                uiState = uiState.copy(
                    isGoogleAuthInProgress = false,
                    isAuthenticated = storedSession != null,
                    hasCompletedIntroSurvey = resolveSurveyCompleted(storedSession?.user),
                    currentUser = storedSession?.user,
                    authStatusMessage = throwable.toUserFriendlyAuthMessage(
                        "Login Google ke server HybridFit gagal."
                    ),
                    authEventId = uiState.authEventId + 1,
                    lastAuthAction = AuthAction.GOOGLE_LOGIN
                )
            }
        }
    }

    fun failGoogleAuth(message: String) {
        val storedSession = authSessionStore.getSession()
        uiState = uiState.copy(
            isGoogleAuthInProgress = false,
            isAuthenticated = storedSession != null,
            hasCompletedIntroSurvey = resolveSurveyCompleted(storedSession?.user),
            currentUser = storedSession?.user,
            authStatusMessage = message,
            authEventId = uiState.authEventId + 1,
            lastAuthAction = AuthAction.GOOGLE_LOGIN
        )
    }

    fun logout() {
        if (uiState.isLogoutInProgress) return

        viewModelScope.launch {
            val storedSession = authSessionStore.getSession()
            if (storedSession == null) {
                authSessionStore.clear()
                startDashboardObservation(null)
                uiState = uiState.copy(
                    isLogoutInProgress = false,
                    isAuthenticated = false,
                    hasCompletedIntroSurvey = false,
                    currentUser = null,
                    authStatusMessage = "Sesi login sudah tidak tersedia.",
                    authEventId = uiState.authEventId + 1,
                    lastAuthAction = AuthAction.LOGOUT
                )
                return@launch
            }

            uiState = uiState.copy(
                isLogoutInProgress = true,
                authStatusMessage = null,
                lastAuthAction = AuthAction.LOGOUT
            )

            if (storedSession.accessToken.startsWith("local-")) {
                authSessionStore.clear()
                startDashboardObservation(null)
                uiState = uiState.copy(
                    isLogoutInProgress = false,
                    isAuthenticated = false,
                    hasCompletedIntroSurvey = false,
                    currentUser = null,
                    authStatusMessage = "Logout berhasil.",
                    authEventId = uiState.authEventId + 1,
                    lastAuthAction = AuthAction.LOGOUT
                )
                return@launch
            }

            runCatching {
                authApi.logout(storedSession.accessToken)
            }.onSuccess { message ->
                authSessionStore.clear()
                startDashboardObservation(null)
                uiState = uiState.copy(
                    isLogoutInProgress = false,
                    isAuthenticated = false,
                    hasCompletedIntroSurvey = false,
                    currentUser = null,
                    authStatusMessage = message,
                    authEventId = uiState.authEventId + 1,
                    lastAuthAction = AuthAction.LOGOUT
                )
            }.onFailure { throwable ->
                authSessionStore.clear()
                startDashboardObservation(null)
                uiState = uiState.copy(
                    isLogoutInProgress = false,
                    isAuthenticated = false,
                    hasCompletedIntroSurvey = false,
                    currentUser = null,
                    authStatusMessage = throwable.toUserFriendlyAuthMessage(
                        "Logout lokal berhasil, tetapi server tidak merespons."
                    ),
                    authEventId = uiState.authEventId + 1,
                    lastAuthAction = AuthAction.LOGOUT
                )
            }
        }
    }

    internal fun sendCoachChatMessage(
        messages: List<CoachMessage>,
        onSuccess: (CoachChatResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (uiState.isCoachChatSending) return

        val normalizedMessages = messages
            .mapNotNull { message ->
                val text = message.text.trim()
                if (text.isBlank()) {
                    null
                } else {
                    CoachChatMessage(
                        role = if (message.fromCoach) "assistant" else "user",
                        text = text
                    )
                }
            }

        if (normalizedMessages.isEmpty()) {
            onFailure("Belum ada pesan untuk dikirim ke AI Coach.")
            return
        }

        val session = authSessionStore.getSession()
        if (session == null) {
            onFailure("Sesi login tidak ditemukan. Silakan login lagi.")
            return
        }

        if (session.accessToken.startsWith("local-")) {
            onSuccess(
                CoachChatResponse(
                    reply = buildLocalCoachReply(
                        latestPrompt = normalizedMessages.lastOrNull { it.role == "user" }?.text.orEmpty(),
                        snapshot = uiState.snapshot
                    ),
                    model = "local-fallback",
                    proposal = null
                )
            )
            return
        }

        uiState = uiState.copy(isCoachChatSending = true)

        viewModelScope.launch {
            runCatching {
                aiApi.sendCoachChat(
                    accessToken = session.accessToken,
                    messages = normalizedMessages
                )
            }.onSuccess { reply ->
                uiState = uiState.copy(isCoachChatSending = false)
                onSuccess(reply)
            }.onFailure { throwable ->
                uiState = uiState.copy(isCoachChatSending = false)
                if (
                    throwable is ConnectException ||
                    throwable is SocketTimeoutException ||
                    throwable is UnknownHostException ||
                    throwable.isTemporaryAiBusyError()
                ) {
                    if (throwable.isTemporaryAiBusyError()) {
                        uiState = uiState.copy(
                            infoMessage = "Gemini sedang sibuk. AI Coach memakai mode cadangan sementara.",
                            infoEventId = uiState.infoEventId + 1
                        )
                    }
                    onSuccess(
                        CoachChatResponse(
                            reply = buildLocalCoachReply(
                                latestPrompt = normalizedMessages.lastOrNull { it.role == "user" }?.text.orEmpty(),
                                snapshot = uiState.snapshot
                            ),
                            model = "local-fallback",
                            proposal = null
                        )
                    )
                } else {
                    onFailure(
                        throwable.toUserFriendlyAuthMessage("AI Coach belum bisa membalas sekarang.")
                    )
                }
            }
        }
    }

    fun consumeAuthStatusMessage() {
        if (uiState.authStatusMessage == null) return
        uiState = uiState.copy(authStatusMessage = null)
    }

    fun consumeInfoMessage() {
        if (uiState.infoMessage == null) return
        uiState = uiState.copy(infoMessage = null)
    }

    private fun resolveSurveyCompleted(user: RemoteAuthUser?): Boolean {
        val userId = user?.id?.toLong()
        return user?.hasCompletedSurvey == true ||
            onboardingPreferenceStore.hasCompletedIntroSurvey(userId)
    }

    private fun syncSurveyPreference(user: RemoteAuthUser?) {
        if (user != null) {
            onboardingPreferenceStore.setCompletedIntroSurvey(
                user.id.toLong(),
                user.hasCompletedSurvey
            )
        }
    }

    private fun startDashboardObservation(userId: Long?) {
        dashboardObservationJob?.cancel()
        uiState = uiState.copy(isLoading = true)
        dashboardObservationJob = viewModelScope.launch {
            repository.bootstrap()
            repository.observeDashboard(userId).collectLatest { snapshot ->
                val completedFromDatabase = snapshot.profile != null
                if (completedFromDatabase && userId != null) {
                    onboardingPreferenceStore.setCompletedIntroSurvey(userId, true)
                }
                val remoteSurveyCompleted = authSessionStore.getSession()
                    ?.user
                    ?.takeIf { it.id.toLong() == userId }
                    ?.hasCompletedSurvey == true
                uiState = uiState.copy(
                    isLoading = false,
                    snapshot = snapshot,
                    hasCompletedIntroSurvey = completedFromDatabase ||
                        remoteSurveyCompleted ||
                        onboardingPreferenceStore.hasCompletedIntroSurvey(userId)
                )
            }
        }
    }

    private fun createLocalProviderSession(
        id: String,
        email: String,
        fullName: String,
        authProvider: String,
        avatarUrl: String?
    ): AuthSession {
        val normalizedEmail = email.trim().lowercase()
        val localUserId = normalizedEmail.hashCode().let { hash ->
            if (hash == Int.MIN_VALUE) Int.MAX_VALUE else kotlin.math.abs(hash)
        }.coerceAtLeast(1)
        val now = Instant.now().toString()
        val safeId = id.trim().ifBlank { normalizedEmail }
            .replace(" ", "-")
            .lowercase()

        return AuthSession(
            user = RemoteAuthUser(
                id = localUserId,
                email = normalizedEmail,
                fullName = fullName,
                authProvider = authProvider,
                avatarUrl = avatarUrl ?: authSessionStore.getAvatarForEmail(normalizedEmail),
                createdAt = now,
                hasCompletedSurvey = onboardingPreferenceStore.hasCompletedIntroSurvey(
                    localUserId.toLong()
                )
            ),
            accessToken = "local-${authProvider.lowercase()}-access-$safeId",
            refreshToken = "local-${authProvider.lowercase()}-refresh-$safeId"
        )
    }

    private fun Throwable.toUserFriendlyAuthMessage(fallback: String): String = when (this) {
        is ConnectException,
        is SocketTimeoutException -> {
            "Koneksi backend belum tersedia. Mode akun lokal aktif di perangkat ini."
        }
        is UnknownHostException -> {
            "Alamat backend tidak ditemukan. Mode akun lokal aktif di perangkat ini."
        }
        else -> message ?: fallback
    }

    private fun Throwable.isBackendConnectionError(): Boolean =
        this is ConnectException ||
            this is SocketTimeoutException ||
            this is UnknownHostException

    private fun Throwable.isTemporaryAiBusyError(): Boolean {
        val normalized = (message ?: "")
            .lowercase(Locale.ROOT)
        return normalized.contains("high demand") ||
            normalized.contains("try again later") ||
            normalized.contains("currently unavailable") ||
            normalized.contains("service unavailable") ||
            normalized.contains("gemini sedang sibuk") ||
            normalized.contains("resource exhausted") ||
            normalized.contains("quota") ||
            normalized.contains("429") ||
            normalized.contains("503")
    }

    private fun buildLocalCoachReply(
        latestPrompt: String,
        snapshot: DashboardSnapshot?
    ): String {
        val prompt = latestPrompt.trim()
        if (prompt.isBlank()) {
            return "Saya siap bantu soal olahraga dan kebugaran. Coba kirim pertanyaan yang lebih spesifik, misalnya teknik squat, recovery setelah lari, atau cara atur program latihan."
        }

        val normalized = prompt.lowercase(Locale.ROOT)
        val profile = snapshot?.profile
        val recoveryScore = snapshot?.latestRecovery?.recoveryScore
        val latestRecommendation = snapshot?.latestRecommendation
        val recentLogCount = snapshot?.recentLogs?.size ?: 0
        val planSessions = snapshot?.activePlan?.sessions?.size ?: 0

        fun contextualClose(): String = buildString {
            if (profile != null) {
                append(" Tujuan Anda saat ini ${profile.goal.label.lowercase(Locale("id", "ID"))}")
                append(" dengan level ${profile.fitnessLevel.label.lowercase(Locale("id", "ID"))}.")
            }
            if (recoveryScore != null) {
                append(" Recovery terakhir Anda ${recoveryScore}%, jadi sesuaikan intensitas bila tubuh masih terasa berat.")
            }
            if (latestRecommendation != null) {
                append(" Rekomendasi aktif HybridFit saat ini: ${latestRecommendation.summary}")
            }
        }

        val irrelevantKeywords = listOf("cuaca", "film", "musik", "politik", "game", "tugas kuliah", "kode program")
        if (irrelevantKeywords.any { normalized.contains(it) }) {
            return "Saya fokus membantu topik olahraga dan kebugaran. Coba tanyakan soal teknik latihan, gym, lari, recovery, progres, atau penyesuaian program Anda."
        }

        return when {
            normalized.contains("squat") -> buildString {
                append("Untuk improve squat, fokus dulu ke 4 hal: posisi kaki selebar bahu, bracing perut sebelum turun, lutut mengikuti arah ujung kaki, dan dorong naik dari mid-foot sampai tumit. ")
                append("Mulai dengan beban yang masih rapi, lalu naikkan bertahap sambil jaga depth dan tempo. ")
                append("Tambahkan drill seperti goblet squat, pause squat 2-3 detik di bawah, dan latihan ankle/hip mobility kalau terasa kaku.")
                append(contextualClose())
            }

            normalized.contains("lari") || normalized.contains("run") || normalized.contains("pace") -> buildString {
                append("Untuk lari, pastikan intensitas sesi sesuai tujuan hari itu. Easy run seharusnya masih nyaman buat ngobrol singkat, sedangkan interval fokus ke pace stabil per segmen. ")
                append("Kalau pace mudah naik-turun, kurangi start terlalu cepat dan jaga cadence tetap rapi dari awal.")
                append(contextualClose())
            }

            normalized.contains("capek") || normalized.contains("lelah") || normalized.contains("recovery") || normalized.contains("pemulihan") -> buildString {
                append("Kalau tubuh terasa capek, prioritaskan pemulihan dulu: turunkan volume 10-20%, tambah jeda istirahat, tidur cukup, dan jaga hidrasi. ")
                append("Kalau nyeri tajam atau performa turun beberapa sesi berturut-turut, sebaiknya ambil sesi ringan atau recovery day.")
                append(contextualClose())
            }

            normalized.contains("program") || normalized.contains("jadwal") || normalized.contains("latihan hari ini") -> buildString {
                append("Untuk atur program, pastikan urutannya realistis: sesi utama, jeda recovery, lalu progres bertahap. ")
                append("Jangan naikkan volume semua sekaligus; pilih salah satu dulu antara durasi, beban, atau repetisi.")
                if (planSessions > 0) {
                    append(" Saat ini Anda punya $planSessions sesi aktif di program, jadi lebih baik evaluasi konsistensi dulu sebelum menambah beban latihan.")
                }
                append(contextualClose())
            }

            normalized.contains("gym") || normalized.contains("beban") || normalized.contains("set") || normalized.contains("rep") -> buildString {
                append("Untuk latihan beban, usahakan setiap set selesai dengan teknik yang konsisten dulu baru mengejar beban lebih besar. ")
                append("Kalau target rep belum tercapai, tahan beban yang sama 1-2 sesi sampai gerakan stabil, lalu naikkan perlahan.")
                append(contextualClose())
            }

            else -> buildString {
                append("Saya bisa bantu dari sisi teknik, program, recovery, gym, lari, dan progres latihan. ")
                append("Kalau Anda mau, kirim pertanyaan yang lebih spesifik seperti: 'cara improve squat', 'kenapa saya capek setelah lari', atau 'bagaimana atur program gym 4 hari'.")
                if (recentLogCount > 0) {
                    append(" Saya juga melihat sudah ada $recentLogCount log latihan yang bisa dijadikan acuan progres Anda.")
                }
            }
        }
    }

    class Factory(
        private val repository: HybridFitRepository,
        private val authApi: HybridFitAuthApi,
        private val aiApi: HybridFitAiApi,
        private val authSessionStore: AuthSessionStore,
        private val onboardingPreferenceStore: OnboardingPreferenceStore
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    repository = repository,
                    authApi = authApi,
                    aiApi = aiApi,
                    authSessionStore = authSessionStore,
                    onboardingPreferenceStore = onboardingPreferenceStore
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
