package com.athallah.hybridfit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StrengthExerciseEntryDao {
    @Insert
    suspend fun insertEntries(entries: List<StrengthExerciseEntryEntity>)

    @Query("DELETE FROM strength_exercise_entries WHERE workoutLogId = :workoutLogId")
    suspend fun deleteEntriesForLog(workoutLogId: Long)

    @Query("SELECT * FROM strength_exercise_entries WHERE workoutLogId = :workoutLogId ORDER BY id ASC")
    suspend fun getEntriesForLog(workoutLogId: Long): List<StrengthExerciseEntryEntity>

    @Query(
        "SELECT strength_exercise_entries.* FROM strength_exercise_entries " +
            "INNER JOIN workout_logs ON workout_logs.id = strength_exercise_entries.workoutLogId " +
            "INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId " +
            "INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId " +
            "WHERE workout_plans.userId = :userId " +
            "ORDER BY strength_exercise_entries.workoutLogId DESC, strength_exercise_entries.id ASC"
    )
    fun observeAllEntries(userId: Long): Flow<List<StrengthExerciseEntryEntity>>

    @Query(
        "SELECT strength_exercise_entries.* FROM strength_exercise_entries " +
            "INNER JOIN workout_logs ON workout_logs.id = strength_exercise_entries.workoutLogId " +
            "INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId " +
            "INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId " +
            "WHERE workout_plans.userId = :userId " +
            "ORDER BY strength_exercise_entries.workoutLogId DESC, strength_exercise_entries.id ASC"
    )
    suspend fun getAllEntries(userId: Long): List<StrengthExerciseEntryEntity>
}
