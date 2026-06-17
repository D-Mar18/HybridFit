package com.athallah.hybridfit.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity
import com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity

data class WorkoutPlanWithSessions(
    @Embedded val plan: WorkoutPlanEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "planId"
    )
    val sessions: List<WorkoutSessionEntity>
)
