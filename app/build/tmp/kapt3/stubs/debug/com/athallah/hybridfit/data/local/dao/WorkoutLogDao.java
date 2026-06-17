package com.athallah.hybridfit.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.athallah.hybridfit.data.local.entity.WorkoutLogEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J \u0010\u000e\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u001c\u0010\u0013\u001a\u00020\b2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0015J\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u00172\u0006\u0010\r\u001a\u00020\u0005H'J\u0016\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0012\u00a8\u0006\u0019"}, d2 = {"Lcom/athallah/hybridfit/data/local/dao/WorkoutLogDao;", "", "countLogsForSession", "", "sessionId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteLog", "", "logId", "getAllLogs", "", "Lcom/athallah/hybridfit/data/local/entity/WorkoutLogEntity;", "userId", "getLogById", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertLog", "log", "(Lcom/athallah/hybridfit/data/local/entity/WorkoutLogEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertLogs", "logs", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAllLogs", "Lkotlinx/coroutines/flow/Flow;", "updateLog", "app_debug"})
@androidx.room.Dao()
public abstract interface WorkoutLogDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertLog(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.local.entity.WorkoutLogEntity log, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertLogs(@org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.data.local.entity.WorkoutLogEntity> logs, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateLog(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.local.entity.WorkoutLogEntity log, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM workout_logs WHERE id = :logId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteLog(long logId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT workout_logs.* FROM workout_logs INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId WHERE workout_plans.userId = :userId ORDER BY workout_logs.performedOn DESC, workout_logs.id DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.athallah.hybridfit.data.local.entity.WorkoutLogEntity>> observeAllLogs(long userId);
    
    @androidx.room.Query(value = "SELECT workout_logs.* FROM workout_logs INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId WHERE workout_plans.userId = :userId ORDER BY workout_logs.performedOn DESC, workout_logs.id DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllLogs(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.athallah.hybridfit.data.local.entity.WorkoutLogEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT workout_logs.* FROM workout_logs INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId WHERE workout_plans.userId = :userId AND workout_logs.id = :logId LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLogById(long userId, long logId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.athallah.hybridfit.data.local.entity.WorkoutLogEntity> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM workout_logs WHERE sessionId = :sessionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object countLogsForSession(long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}