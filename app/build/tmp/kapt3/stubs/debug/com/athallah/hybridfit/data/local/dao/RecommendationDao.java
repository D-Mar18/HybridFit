package com.athallah.hybridfit.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.athallah.hybridfit.data.local.entity.RecommendationEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H'\u00a8\u0006\f"}, d2 = {"Lcom/athallah/hybridfit/data/local/dao/RecommendationDao;", "", "getLatestRecommendation", "Lcom/athallah/hybridfit/data/local/entity/RecommendationEntity;", "userId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertRecommendation", "recommendation", "(Lcom/athallah/hybridfit/data/local/entity/RecommendationEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeLatestRecommendation", "Lkotlinx/coroutines/flow/Flow;", "app_debug"})
@androidx.room.Dao()
public abstract interface RecommendationDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertRecommendation(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.local.entity.RecommendationEntity recommendation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM recommendations WHERE userId = :userId ORDER BY createdAt DESC, id DESC LIMIT 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.athallah.hybridfit.data.local.entity.RecommendationEntity> observeLatestRecommendation(long userId);
    
    @androidx.room.Query(value = "SELECT * FROM recommendations WHERE userId = :userId ORDER BY createdAt DESC, id DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLatestRecommendation(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.athallah.hybridfit.data.local.entity.RecommendationEntity> $completion);
}