package com.athallah.hybridfit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.athallah.hybridfit.data.local.entity.RecommendationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecommendationDao {
    @Insert
    suspend fun insertRecommendation(recommendation: RecommendationEntity): Long

    @Query("SELECT * FROM recommendations WHERE userId = :userId ORDER BY createdAt DESC, id DESC LIMIT 1")
    fun observeLatestRecommendation(userId: Long): Flow<RecommendationEntity?>

    @Query("SELECT * FROM recommendations WHERE userId = :userId ORDER BY createdAt DESC, id DESC LIMIT 1")
    suspend fun getLatestRecommendation(userId: Long): RecommendationEntity?
}
