package com.athallah.hybridfit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity
import com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity
import com.athallah.hybridfit.data.local.relation.WorkoutPlanWithSessions
import kotlinx.coroutines.flow.Flow
@Dao
interface WorkoutPlanDao {
    @Insert
    suspend fun insertPlan(plan: WorkoutPlanEntity): Long

    @Insert
    suspend fun insertSessions(sessions: List<WorkoutSessionEntity>): List<Long>

    @Transaction
    @Query(
        "SELECT * FROM workout_plans " +
            "WHERE status = 'ACTIVE' " +
            "ORDER BY createdAt DESC LIMIT 1"
    )
    fun observeActivePlanWithSessions(): Flow<WorkoutPlanWithSessions?>

    @Transaction
    @Query(
        "SELECT * FROM workout_plans " +
            "WHERE status = 'ACTIVE' " +
            "ORDER BY createdAt DESC LIMIT 1"
    )
    suspend fun getActivePlanWithSessions(): WorkoutPlanWithSessions?
}
