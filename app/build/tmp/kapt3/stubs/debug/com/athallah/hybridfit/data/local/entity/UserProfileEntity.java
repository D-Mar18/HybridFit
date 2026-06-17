package com.athallah.hybridfit.data.local.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.athallah.hybridfit.domain.model.FitnessGoal;
import com.athallah.hybridfit.domain.model.FitnessLevel;
import com.athallah.hybridfit.domain.model.PlanStatus;
import com.athallah.hybridfit.domain.model.RecommendationAction;
import com.athallah.hybridfit.domain.model.SessionPlannerState;
import com.athallah.hybridfit.domain.model.WorkoutCategory;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001Bo\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0007\u0012\u0006\u0010\u0013\u001a\u00020\u0007\u0012\u0006\u0010\u0014\u001a\u00020\u0005\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\u0002\u0010\u0017J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0007H\u00c6\u0003J\t\u0010/\u001a\u00020\u0007H\u00c6\u0003J\t\u00100\u001a\u00020\u0005H\u00c6\u0003J\t\u00101\u001a\u00020\u0016H\u00c6\u0003J\t\u00102\u001a\u00020\u0005H\u00c6\u0003J\t\u00103\u001a\u00020\u0007H\u00c6\u0003J\t\u00104\u001a\u00020\tH\u00c6\u0003J\t\u00105\u001a\u00020\u0007H\u00c6\u0003J\t\u00106\u001a\u00020\u0005H\u00c6\u0003J\t\u00107\u001a\u00020\rH\u00c6\u0003J\t\u00108\u001a\u00020\u000fH\u00c6\u0003J\t\u00109\u001a\u00020\u0011H\u00c6\u0003J\u008b\u0001\u0010:\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00072\b\b\u0002\u0010\u0013\u001a\u00020\u00072\b\b\u0002\u0010\u0014\u001a\u00020\u00052\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u00c6\u0001J\u0013\u0010;\u001a\u00020<2\b\u0010=\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010>\u001a\u00020\u0007H\u00d6\u0001J\t\u0010?\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0014\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0019R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0011\u0010\u0013\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0019R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u0012\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0019\u00a8\u0006@"}, d2 = {"Lcom/athallah/hybridfit/data/local/entity/UserProfileEntity;", "", "id", "", "fullName", "", "age", "", "weightKg", "", "heightCm", "genderLabel", "goal", "Lcom/athallah/hybridfit/domain/model/FitnessGoal;", "fitnessLevel", "Lcom/athallah/hybridfit/domain/model/FitnessLevel;", "preferredFocus", "Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "workoutDaysPerWeek", "sessionDurationMinutes", "experienceNotes", "createdAt", "Ljava/time/Instant;", "(JLjava/lang/String;IDILjava/lang/String;Lcom/athallah/hybridfit/domain/model/FitnessGoal;Lcom/athallah/hybridfit/domain/model/FitnessLevel;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;IILjava/lang/String;Ljava/time/Instant;)V", "getAge", "()I", "getCreatedAt", "()Ljava/time/Instant;", "getExperienceNotes", "()Ljava/lang/String;", "getFitnessLevel", "()Lcom/athallah/hybridfit/domain/model/FitnessLevel;", "getFullName", "getGenderLabel", "getGoal", "()Lcom/athallah/hybridfit/domain/model/FitnessGoal;", "getHeightCm", "getId", "()J", "getPreferredFocus", "()Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "getSessionDurationMinutes", "getWeightKg", "()D", "getWorkoutDaysPerWeek", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "user_profiles")
public final class UserProfileEntity {
    @androidx.room.PrimaryKey()
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String fullName = null;
    private final int age = 0;
    private final double weightKg = 0.0;
    private final int heightCm = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String genderLabel = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.model.FitnessGoal goal = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.model.FitnessLevel fitnessLevel = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.model.WorkoutCategory preferredFocus = null;
    private final int workoutDaysPerWeek = 0;
    private final int sessionDurationMinutes = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String experienceNotes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant createdAt = null;
    
    public UserProfileEntity(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String fullName, int age, double weightKg, int heightCm, @org.jetbrains.annotations.NotNull()
    java.lang.String genderLabel, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessGoal goal, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessLevel fitnessLevel, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory preferredFocus, int workoutDaysPerWeek, int sessionDurationMinutes, @org.jetbrains.annotations.NotNull()
    java.lang.String experienceNotes, @org.jetbrains.annotations.NotNull()
    java.time.Instant createdAt) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFullName() {
        return null;
    }
    
    public final int getAge() {
        return 0;
    }
    
    public final double getWeightKg() {
        return 0.0;
    }
    
    public final int getHeightCm() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGenderLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.FitnessGoal getGoal() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.FitnessLevel getFitnessLevel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.WorkoutCategory getPreferredFocus() {
        return null;
    }
    
    public final int getWorkoutDaysPerWeek() {
        return 0;
    }
    
    public final int getSessionDurationMinutes() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getExperienceNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getCreatedAt() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final int component11() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.FitnessGoal component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.FitnessLevel component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.domain.model.WorkoutCategory component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.data.local.entity.UserProfileEntity copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String fullName, int age, double weightKg, int heightCm, @org.jetbrains.annotations.NotNull()
    java.lang.String genderLabel, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessGoal goal, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessLevel fitnessLevel, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory preferredFocus, int workoutDaysPerWeek, int sessionDurationMinutes, @org.jetbrains.annotations.NotNull()
    java.lang.String experienceNotes, @org.jetbrains.annotations.NotNull()
    java.time.Instant createdAt) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}