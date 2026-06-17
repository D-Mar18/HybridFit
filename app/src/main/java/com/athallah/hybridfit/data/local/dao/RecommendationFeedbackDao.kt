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

    @Query(
        "SELECT recommendation_feedback.* FROM recommendation_feedback " +
            "INNER JOIN recommendations ON recommendations.id = recommendation_feedback.recommendationId " +
            "WHERE recommendations.userId = :userId " +
            "ORDER BY recommendation_feedback.recordedAt DESC, recommendation_feedback.id DESC"
    )
    fun observeAllFeedback(userId: Long): Flow<List<RecommendationFeedbackEntity>>

    @Query(
        "SELECT recommendation_feedback.* FROM recommendation_feedback " +
            "INNER JOIN recommendations ON recommendations.id = recommendation_feedback.recommendationId " +
            "WHERE recommendations.userId = :userId " +
            "ORDER BY recommendation_feedback.recordedAt DESC, recommendation_feedback.id DESC"
    )
    suspend fun getAllFeedback(userId: Long): List<RecommendationFeedbackEntity>
}
