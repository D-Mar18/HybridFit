package com.athallah.hybridfit.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.athallah.hybridfit.data.local.entity.RecoveryCheckInEntity;
import kotlinx.coroutines.flow.Flow;
import java.time.LocalDate;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\bg\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00110\u00102\u0006\u0010\u0004\u001a\u00020\u0005H'\u00a8\u0006\u0012"}, d2 = {"Lcom/athallah/hybridfit/data/local/dao/RecoveryCheckInDao;", "", "deleteByDate", "", "userId", "", "recordedOn", "Ljava/time/LocalDate;", "(JLjava/time/LocalDate;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLatestRecovery", "Lcom/athallah/hybridfit/data/local/entity/RecoveryCheckInEntity;", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertRecovery", "checkIn", "(Lcom/athallah/hybridfit/data/local/entity/RecoveryCheckInEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAllRecovery", "Lkotlinx/coroutines/flow/Flow;", "", "app_debug"})
@androidx.room.Dao()
public abstract interface RecoveryCheckInDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertRecovery(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.local.entity.RecoveryCheckInEntity checkIn, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "DELETE FROM recovery_check_ins WHERE userId = :userId AND recordedOn = :recordedOn")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteByDate(long userId, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate recordedOn, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM recovery_check_ins WHERE userId = :userId ORDER BY recordedOn DESC, id DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.athallah.hybridfit.data.local.entity.RecoveryCheckInEntity>> observeAllRecovery(long userId);
    
    @androidx.room.Query(value = "SELECT * FROM recovery_check_ins WHERE userId = :userId ORDER BY recordedOn DESC, id DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLatestRecovery(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.athallah.hybridfit.data.local.entity.RecoveryCheckInEntity> $completion);
}