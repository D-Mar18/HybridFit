package com.athallah.hybridfit.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Upsert;
import com.athallah.hybridfit.data.local.entity.UserProfileEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u000b2\u0006\u0010\u0007\u001a\u00020\bH'J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/athallah/hybridfit/data/local/dao/UserProfileDao;", "", "countProfiles", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProfile", "Lcom/athallah/hybridfit/data/local/entity/UserProfileEntity;", "userId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeProfile", "Lkotlinx/coroutines/flow/Flow;", "upsertProfile", "", "profile", "(Lcom/athallah/hybridfit/data/local/entity/UserProfileEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface UserProfileDao {
    
    @androidx.room.Query(value = "SELECT * FROM user_profiles WHERE id = :userId LIMIT 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.athallah.hybridfit.data.local.entity.UserProfileEntity> observeProfile(long userId);
    
    @androidx.room.Query(value = "SELECT * FROM user_profiles WHERE id = :userId LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getProfile(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.athallah.hybridfit.data.local.entity.UserProfileEntity> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM user_profiles")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object countProfiles(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Upsert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object upsertProfile(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.local.entity.UserProfileEntity profile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}