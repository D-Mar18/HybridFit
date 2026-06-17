package com.athallah.hybridfit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.athallah.hybridfit.data.local.entity.RecoveryCheckInEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface RecoveryCheckInDao {
    @Insert
    suspend fun insertRecovery(checkIn: RecoveryCheckInEntity): Long

    @Query("DELETE FROM recovery_check_ins WHERE userId = :userId AND recordedOn = :recordedOn")
    suspend fun deleteByDate(userId: Long, recordedOn: LocalDate)

    @Query(
        "SELECT * FROM recovery_check_ins " +
            "WHERE userId = :userId " +
            "ORDER BY recordedOn DESC, id DESC"
    )
    fun observeAllRecovery(userId: Long): Flow<List<RecoveryCheckInEntity>>

    @Query(
        "SELECT * FROM recovery_check_ins " +
            "WHERE userId = :userId " +
            "ORDER BY recordedOn DESC, id DESC " +
            "LIMIT 1"
    )
    suspend fun getLatestRecovery(userId: Long): RecoveryCheckInEntity?
}
