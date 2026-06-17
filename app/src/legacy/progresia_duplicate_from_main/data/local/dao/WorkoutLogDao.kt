package com.athallah.hybridfit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.athallah.hybridfit.data.local.entity.WorkoutLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutLogDao {
    @Insert
    suspend fun insertLog(log: WorkoutLogEntity): Long

    @Insert
    suspend fun insertLogs(logs: List<WorkoutLogEntity>)

    @Query("SELECT * FROM workout_logs ORDER BY performedOn DESC, id DESC")
    fun observeAllLogs(): Flow<List<WorkoutLogEntity>>

    @Query("SELECT * FROM workout_logs ORDER BY performedOn DESC, id DESC")
    suspend fun getAllLogs(): List<WorkoutLogEntity>
}
