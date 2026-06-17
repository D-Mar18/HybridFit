package com.athallah.hybridfit.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\f\u001a\u00020\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00102\u0006\u0010\n\u001a\u00020\u0005H'\u00a8\u0006\u0011"}, d2 = {"Lcom/athallah/hybridfit/data/local/dao/StrengthExerciseEntryDao;", "", "deleteEntriesForLog", "", "workoutLogId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllEntries", "", "Lcom/athallah/hybridfit/data/local/entity/StrengthExerciseEntryEntity;", "userId", "getEntriesForLog", "insertEntries", "entries", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAllEntries", "Lkotlinx/coroutines/flow/Flow;", "app_debug"})
@androidx.room.Dao()
public abstract interface StrengthExerciseEntryDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertEntries(@org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity> entries, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM strength_exercise_entries WHERE workoutLogId = :workoutLogId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteEntriesForLog(long workoutLogId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM strength_exercise_entries WHERE workoutLogId = :workoutLogId ORDER BY id ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEntriesForLog(long workoutLogId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT strength_exercise_entries.* FROM strength_exercise_entries INNER JOIN workout_logs ON workout_logs.id = strength_exercise_entries.workoutLogId INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId WHERE workout_plans.userId = :userId ORDER BY strength_exercise_entries.workoutLogId DESC, strength_exercise_entries.id ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity>> observeAllEntries(long userId);
    
    @androidx.room.Query(value = "SELECT strength_exercise_entries.* FROM strength_exercise_entries INNER JOIN workout_logs ON workout_logs.id = strength_exercise_entries.workoutLogId INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId WHERE workout_plans.userId = :userId ORDER BY strength_exercise_entries.workoutLogId DESC, strength_exercise_entries.id ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllEntries(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity>> $completion);
}