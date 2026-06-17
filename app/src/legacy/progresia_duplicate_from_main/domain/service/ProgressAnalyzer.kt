package com.athallah.hybridfit.domain.service

import com.athallah.hybridfit.domain.model.ProgressSummary
import com.athallah.hybridfit.domain.model.ProgressTrend
import com.athallah.hybridfit.domain.model.WorkoutLog
import java.time.LocalDate
import kotlin.math.roundToInt

class ProgressAnalyzer {
    fun analyze(plannedSessionsPerWeek: Int, logs: List<WorkoutLog>): ProgressSummary {
        if (logs.isEmpty()) return ProgressSummary.empty()

        val recentWeek = logs.filter { it.performedOn >= LocalDate.now().minusDays(7) }
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
        val maxVolumeLog = logs.maxByOrNull { (it.completedSets ?: 0) * (it.completedReps ?: 0) }
        val highlight = when {
            maxDistance != null -> "Jarak terbaik: $maxDistance km"
            maxVolumeLog != null && maxVolumeLog.completedSets != null && maxVolumeLog.completedReps != null ->
                "Volume terbaik: ${maxVolumeLog.completedSets} x ${maxVolumeLog.completedReps}"

            else -> "Progres konsisten sedang dibangun."
        }

        val trend = when {
            averageCompletion >= 85 && averageExertion <= 7 -> ProgressTrend.IMPROVING
            averageCompletion < 65 || averageExertion >= 9 -> ProgressTrend.NEEDS_ATTENTION
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
