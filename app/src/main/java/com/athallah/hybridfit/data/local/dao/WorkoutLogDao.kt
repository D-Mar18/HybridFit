package com.athallah.hybridfit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.athallah.hybridfit.data.local.entity.WorkoutLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutLogDao {
    @Insert
    suspend fun insertLog(log: WorkoutLogEntity): Long

    @Insert
    suspend fun insertLogs(logs: List<WorkoutLogEntity>)

    @Update
    suspend fun updateLog(log: WorkoutLogEntity)

    @Query("DELETE FROM workout_logs WHERE id = :logId")
    suspend fun deleteLog(logId: Long)

    @Query(
        "SELECT workout_logs.* FROM workout_logs " +
            "INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId " +
            "INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId " +
            "WHERE workout_plans.userId = :userId " +
            "ORDER BY workout_logs.performedOn DESC, workout_logs.id DESC"
    )
    fun observeAllLogs(userId: Long): Flow<List<WorkoutLogEntity>>

    @Query(
        "SELECT workout_logs.* FROM workout_logs " +
            "INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId " +
            "INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId " +
            "WHERE workout_plans.userId = :userId " +
            "ORDER BY workout_logs.performedOn DESC, workout_logs.id DESC"
    )
    suspend fun getAllLogs(userId: Long): List<WorkoutLogEntity>

    @Query(
        "SELECT workout_logs.* FROM workout_logs " +
            "INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId " +
            "INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId " +
            "WHERE workout_plans.userId = :userId AND workout_logs.id = :logId " +
            "LIMIT 1"
    )
    suspend fun getLogById(userId: Long, logId: Long): WorkoutLogEntity?

    @Query("SELECT COUNT(*) FROM workout_logs WHERE sessionId = :sessionId")
    suspend fun countLogsForSession(sessionId: Long): Int
}
