package com.athallah.hybridfit.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\f2\u0006\u0010\u0005\u001a\u00020\u0006H'\u00a8\u0006\r"}, d2 = {"Lcom/athallah/hybridfit/data/local/dao/RecommendationFeedbackDao;", "", "getAllFeedback", "", "Lcom/athallah/hybridfit/data/local/entity/RecommendationFeedbackEntity;", "userId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertFeedback", "feedback", "(Lcom/athallah/hybridfit/data/local/entity/RecommendationFeedbackEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAllFeedback", "Lkotlinx/coroutines/flow/Flow;", "app_debug"})
@androidx.room.Dao()
public abstract interface RecommendationFeedbackDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertFeedback(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity feedback, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT recommendation_feedback.* FROM recommendation_feedback INNER JOIN recommendations ON recommendations.id = recommendation_feedback.recommendationId WHERE recommendations.userId = :userId ORDER BY recommendation_feedback.recordedAt DESC, recommendation_feedback.id DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity>> observeAllFeedback(long userId);
    
    @androidx.room.Query(value = "SELECT recommendation_feedback.* FROM recommendation_feedback INNER JOIN recommendations ON recommendations.id = recommendation_feedback.recommendationId WHERE recommendations.userId = :userId ORDER BY recommendation_feedback.recordedAt DESC, recommendation_feedback.id DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllFeedback(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity>> $completion);
}