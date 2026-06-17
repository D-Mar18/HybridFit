package com.athallah.hybridfit.domain.service

import com.athallah.hybridfit.domain.model.ProgressSummary
import com.athallah.hybridfit.domain.model.ProgressTrend
import com.athallah.hybridfit.domain.model.RecoveryCheckIn
import com.athallah.hybridfit.domain.model.WorkoutLog
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

class ProgressAnalyzer {
    fun analyze(
        plannedSessionsPerWeek: Int,
        logs: List<WorkoutLog>,
        latestRecovery: RecoveryCheckIn?
    ): ProgressSummary {
        if (logs.isEmpty()) return ProgressSummary.empty()

        val today = LocalDate.now()
        val sortedLogs = logs.sortedByDescending { it.performedOn }
        val recentWeek = logs.filter { it.performedOn >= today.minusDays(7) }
        val completedWeek = recentWeek.count { it.wasCompleted }
        val weeklyConsistency = if (plannedSessionsPerWeek <= 0) {
            0
        } else {
            ((completedWeek.toDouble() / plannedSessionsPerWeek) * 100)
                .roundToInt()
                .coerceIn(0, 100)
        }

        val averageCompletion = logs.map { it.completionPercent }.average().roundToInt()
        val averageExertion = logs.map { it.exertionScore }.average().roundToInt()
        val maxDistance = logs.mapNotNull { it.actualDistanceKm }.maxOrNull()
        val maxVolume = logs.maxOfOrNull { log ->
            log.exerciseEntries.sumOf { entry ->
                (entry.actualSets * entry.actualReps * entry.actualWeightKg).roundToInt()
            }
        }
        val highlight = when {
            maxDistance != null -> "Jarak terbaik: $maxDistance km"
            maxVolume != null && maxVolume > 0 -> "Volume terbaik: ${maxVolume} kg"
            latestRecovery != null -> "Recovery terakhir: ${latestRecovery.recoveryScore}%"

            else -> "Progres konsisten sedang dibangun."
        }

        val daysSinceLastWorkout = ChronoUnit.DAYS.between(
            sortedLogs.first().performedOn,
            today
        )
        val breakToleranceDays = when (plannedSessionsPerWeek.coerceIn(1, 7)) {
            1, 2 -> 10L
            3, 4 -> 5L
            else -> 3L
        }
        val trend = when {
            logs.size < 3 -> ProgressTrend.BUILDING

            daysSinceLastWorkout > breakToleranceDays -> ProgressTrend.NEEDS_CONSISTENCY

            averageCompletion >= 85 &&
                averageExertion <= 7 &&
                (latestRecovery?.recoveryScore ?: 70) >= 70 -> ProgressTrend.IMPROVING

            averageCompletion < 65 ||
                averageExertion >= 9 ||
                (latestRecovery?.recoveryScore ?: 100) < 45 -> ProgressTrend.NEEDS_ATTENTION

            else -> ProgressTrend.STABLE
        }

        return ProgressSummary(
            totalSessionsLogged = logs.size,
            weeklyConsistencyPercent = weeklyConsistency,
            averageCompletionPercent = averageCompletion,
            averageExertionScore = averageExertion,
            bestHighlight = highlight,
            trend = trend
        )
    }
}
