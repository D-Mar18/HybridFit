package com.athallah.hybridfit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecommendationFeedbackDao {
    @Insert
    suspend fun insertFeedback(feedback: RecommendationFeedbackEntity): Long

    @Query("SELECT * FROM recommendation_feedback ORDER BY recordedAt DESC, id DESC")
    fun observeAllFeedback(): Flow<List<RecommendationFeedbackEntity>>

    @Query("SELECT * FROM recommendation_feedback ORDER BY recordedAt DESC, id DESC")
    suspend fun getAllFeedback(): List<RecommendationFeedbackEntity>
}
