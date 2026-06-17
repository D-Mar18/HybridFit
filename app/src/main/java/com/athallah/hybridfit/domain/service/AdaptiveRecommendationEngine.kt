package com.athallah.hybridfit.domain.service

import com.athallah.hybridfit.domain.model.Recommendation
import com.athallah.hybridfit.domain.model.RecommendationAction
import com.athallah.hybridfit.domain.model.RecommendationFeedback
import com.athallah.hybridfit.domain.model.RecoveryCheckIn
import com.athallah.hybridfit.domain.model.SessionPlannerState
import com.athallah.hybridfit.domain.model.UserProfile
import com.athallah.hybridfit.domain.model.WorkoutLog
import com.athallah.hybridfit.domain.model.WorkoutSession
import java.time.Instant
import kotlin.math.roundToInt

class AdaptiveRecommendationEngine {
    fun generate(
        profile: UserProfile,
        sessions: List<WorkoutSession>,
        recentLogs: List<WorkoutLog>,
        feedback: List<RecommendationFeedback>,
        latestRecovery: RecoveryCheckIn?
    ): Recommendation {
        val anchorSession = sessions.firstOrNull() ?: fallbackSession(profile)
        val evaluationWindow = recentLogs.take(5)
        val averageCompletion = evaluationWindow.map { it.completionPercent }.average().ifNaN { 75.0 }
        val averageExertion = evaluationWindow.map { it.exertionScore }.average().ifNaN { 7.0 }
        val helpfulRatio = feedback.take(3).map { if (it.wasHelpful) 1.0 else 0.0 }.average().ifNaN { 1.0 }
        val recoveryScore = latestRecovery?.recoveryScore ?: 72

        val action = when {
            averageCompletion >= 85.0 &&
                averageExertion <= 7.0 &&
                helpfulRatio >= 0.5 &&
                recoveryScore >= 70 ->
                RecommendationAction.INCREASE

            averageCompletion < 65.0 ||
                averageExertion >= 8.5 ||
                helpfulRatio < 0.35 ||
                recoveryScore < 45 ->
                RecommendationAction.RECOVER

            else -> RecommendationAction.MAINTAIN
        }

        val durationShift = when (action) {
            RecommendationAction.INCREASE -> 5
            RecommendationAction.MAINTAIN -> 0
            RecommendationAction.RECOVER -> -5
        }
        val setShift = when (action) {
            RecommendationAction.INCREASE -> 1
            RecommendationAction.MAINTAIN -> 0
            RecommendationAction.RECOVER -> -1
        }
        val repShift = when (action) {
            RecommendationAction.INCREASE -> 2
            RecommendationAction.MAINTAIN -> 0
            RecommendationAction.RECOVER -> -2
        }
        val restShift = when (action) {
            RecommendationAction.INCREASE -> -10
            RecommendationAction.MAINTAIN -> 0
            RecommendationAction.RECOVER -> 15
        }
        val distanceShift = when (action) {
            RecommendationAction.INCREASE -> 0.4
            RecommendationAction.MAINTAIN -> 0.0
            RecommendationAction.RECOVER -> -0.4
        }

        val targetDuration = (anchorSession.targetDurationMinutes + durationShift).coerceAtLeast(20)
        val targetSets = anchorSession.targetSets?.let { (it + setShift).coerceAtLeast(2) }
        val targetReps = anchorSession.targetReps?.let { (it + repShift).coerceAtLeast(6) }
        val targetDistance = anchorSession.targetDistanceKm?.let {
            ((it + distanceShift) * 10.0).roundToInt() / 10.0
        }?.coerceAtLeast(1.5)
        val restSeconds = (anchorSession.restSeconds + restShift).coerceIn(30, 180)

        val summary = when (action) {
            RecommendationAction.INCREASE ->
                "Performa kamu cukup stabil. Sistem menyarankan peningkatan volume kecil agar progres tetap berjalan."

            RecommendationAction.MAINTAIN ->
                "Latihan sudah berada di zona yang sehat. Sistem menjaga beban tetap stabil sambil melihat konsistensi pekan berikutnya."

            RecommendationAction.RECOVER ->
                "Tanda kelelahan mulai terlihat. Sistem menyarankan sesi yang lebih ringan agar pemulihan tetap terjaga."
        }

        val rationale = buildString {
            append("Dasar rekomendasi: penyelesaian rata-rata ")
            append("${averageCompletion.roundToInt()}%")
            append(", skor usaha ")
            append("${averageExertion.roundToInt()}/10")
            append(", dan umpan balik positif ")
            append("${(helpfulRatio * 100).roundToInt()}%.")
            append(" Recovery terakhir ${recoveryScore.coerceIn(0, 100)}%.")
        }

        return Recommendation(
            id = 0,
            userId = profile.id,
            createdAt = Instant.now(),
            title = "IRS Minggu Ini",
            summary = summary,
            action = action,
            targetDurationMinutes = targetDuration,
            targetDistanceKm = targetDistance,
            targetSets = targetSets,
            targetReps = targetReps,
            restSeconds = restSeconds,
            rationale = rationale
        )
    }

    private fun fallbackSession(profile: UserProfile): WorkoutSession = WorkoutSession(
        id = 0,
        planId = 0,
        dayOfWeek = java.time.DayOfWeek.MONDAY,
        title = "Sesi Dasar",
        category = profile.preferredFocus,
        focusArea = "Fondasi kebugaran",
        targetDurationMinutes = profile.sessionDurationMinutes,
        targetDistanceKm = if (profile.preferredFocus == com.athallah.hybridfit.domain.model.WorkoutCategory.CARDIO) 3.0 else null,
        targetSets = if (profile.preferredFocus == com.athallah.hybridfit.domain.model.WorkoutCategory.STRENGTH) 3 else null,
        targetReps = if (profile.preferredFocus == com.athallah.hybridfit.domain.model.WorkoutCategory.STRENGTH) 10 else null,
        restSeconds = 60,
        guidance = "Gunakan sesi ini sebagai baseline awal.",
        orderInDay = 1,
        plannerState = SessionPlannerState.PLANNED,
        sessionNotes = ""
    )

    private fun Double.ifNaN(fallback: () -> Double): Double = if (isNaN()) fallback() else this

    private fun Double.ifNaN(value: Double): Double = if (isNaN()) value else this
}
