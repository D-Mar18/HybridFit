package com.athallah.hybridfit.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity;
import com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity;
import com.athallah.hybridfit.data.local.relation.WorkoutPlanWithSessions;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0012J\"\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u0018\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00182\u0006\u0010\t\u001a\u00020\u0005H'J\u0016\u0010\u0019\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0012\u00a8\u0006\u001b"}, d2 = {"Lcom/athallah/hybridfit/data/local/dao/WorkoutPlanDao;", "", "deleteSession", "", "sessionId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActivePlanWithSessions", "Lcom/athallah/hybridfit/data/local/relation/WorkoutPlanWithSessions;", "userId", "getSessionById", "Lcom/athallah/hybridfit/data/local/entity/WorkoutSessionEntity;", "insertPlan", "plan", "Lcom/athallah/hybridfit/data/local/entity/WorkoutPlanEntity;", "(Lcom/athallah/hybridfit/data/local/entity/WorkoutPlanEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSession", "session", "(Lcom/athallah/hybridfit/data/local/entity/WorkoutSessionEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSessions", "", "sessions", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeActivePlanWithSessions", "Lkotlinx/coroutines/flow/Flow;", "updatePlan", "updateSession", "app_debug"})
@androidx.room.Dao()
public abstract interface WorkoutPlanDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPlan(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity plan, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSessions(@org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity> sessions, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSession(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity session, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSession(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity session, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updatePlan(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity plan, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM workout_sessions WHERE id = :sessionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteSession(long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM workout_sessions WHERE id = :sessionId LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSessionById(long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity> $completion);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM workout_plans WHERE userId = :userId AND status = 'ACTIVE' ORDER BY createdAt DESC LIMIT 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.athallah.hybridfit.data.local.relation.WorkoutPlanWithSessions> observeActivePlanWithSessions(long userId);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM workout_plans WHERE userId = :userId AND status = 'ACTIVE' ORDER BY createdAt DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getActivePlanWithSessions(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.athallah.hybridfit.data.local.relation.WorkoutPlanWithSessions> $completion);
}