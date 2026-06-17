package com.athallah.hybridfit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
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

    @Insert
    suspend fun insertSession(session: WorkoutSessionEntity): Long

    @Update
    suspend fun updateSession(session: WorkoutSessionEntity)

    @Update
    suspend fun updatePlan(plan: WorkoutPlanEntity)

    @Query("DELETE FROM workout_sessions WHERE id = :sessionId")
    suspend fun deleteSession(sessionId: Long)

    @Query("SELECT * FROM workout_sessions WHERE id = :sessionId LIMIT 1")
    suspend fun getSessionById(sessionId: Long): WorkoutSessionEntity?

    @Transaction
    @Query(
        "SELECT * FROM workout_plans " +
            "WHERE userId = :userId AND " +
            "status = 'ACTIVE' " +
            "ORDER BY createdAt DESC LIMIT 1"
    )
    fun observeActivePlanWithSessions(userId: Long): Flow<WorkoutPlanWithSessions?>

    @Transaction
    @Query(
        "SELECT * FROM workout_plans " +
            "WHERE userId = :userId AND " +
            "status = 'ACTIVE' " +
            "ORDER BY createdAt DESC LIMIT 1"
    )
    suspend fun getActivePlanWithSessions(userId: Long): WorkoutPlanWithSessions?
}
