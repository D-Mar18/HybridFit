package com.athallah.hybridfit.data.repository;

import com.athallah.hybridfit.data.local.HybridFitDatabase;
import com.athallah.hybridfit.data.local.entity.RecoveryCheckInEntity;
import com.athallah.hybridfit.data.local.entity.RecommendationEntity;
import com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity;
import com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity;
import com.athallah.hybridfit.data.local.entity.UserProfileEntity;
import com.athallah.hybridfit.data.local.entity.WorkoutLogEntity;
import com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity;
import com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity;
import com.athallah.hybridfit.data.local.relation.WorkoutPlanWithSessions;
import com.athallah.hybridfit.domain.model.DashboardSnapshot;
import com.athallah.hybridfit.domain.model.FitnessGoal;
import com.athallah.hybridfit.domain.model.FitnessLevel;
import com.athallah.hybridfit.domain.model.PlanStatus;
import com.athallah.hybridfit.domain.model.ProgressSummary;
import com.athallah.hybridfit.domain.model.RecoveryCheckIn;
import com.athallah.hybridfit.domain.model.Recommendation;
import com.athallah.hybridfit.domain.model.RecommendationFeedback;
import com.athallah.hybridfit.domain.model.SessionPlannerState;
import com.athallah.hybridfit.domain.model.StrengthExerciseDraft;
import com.athallah.hybridfit.domain.model.StrengthExerciseEntry;
import com.athallah.hybridfit.domain.model.UserProfile;
import com.athallah.hybridfit.domain.model.WorkoutCategory;
import com.athallah.hybridfit.domain.model.WorkoutLog;
import com.athallah.hybridfit.domain.model.WorkoutPlan;
import com.athallah.hybridfit.domain.model.WorkoutSession;
import com.athallah.hybridfit.domain.service.AdaptiveRecommendationEngine;
import com.athallah.hybridfit.domain.service.ProgressAnalyzer;
import com.athallah.hybridfit.domain.service.SessionBlueprint;
import com.athallah.hybridfit.domain.service.StarterPlanBuilder;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0086\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJl\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010'2\b\u0010(\u001a\u0004\u0018\u00010%2\b\u0010)\u001a\u0004\u0018\u00010%2\u0006\u0010*\u001a\u00020%2\u0006\u0010+\u001a\u00020\u001eH\u0086@\u00a2\u0006\u0002\u0010,J\u000e\u0010-\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010.J\u000e\u0010/\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010.Jf\u00100\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u00101\u001a\u00020\u001e2\u0006\u00102\u001a\u00020%2\u0006\u00103\u001a\u00020'2\u0006\u00104\u001a\u00020%2\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u00020 2\u0006\u0010:\u001a\u00020%2\u0006\u0010;\u001a\u00020%2\u0006\u0010<\u001a\u00020\u001eH\u0086@\u00a2\u0006\u0002\u0010=J\u001e\u0010>\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010?\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010@J\u001e\u0010A\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010B\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010@J\b\u0010C\u001a\u00020DH\u0002J \u0010E\u001a\u0004\u0018\u00010F2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010?\u001a\u00020\u001cH\u0082@\u00a2\u0006\u0002\u0010@J\u001e\u0010G\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010?\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010@J\u001b\u0010H\u001a\b\u0012\u0004\u0012\u00020D0I2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c\u00a2\u0006\u0002\u0010JJ\u001e\u0010K\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010L\u001a\u00020MH\u0086@\u00a2\u0006\u0002\u0010NJ\u0016\u0010O\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010PJ\u0016\u0010Q\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010PJ>\u0010R\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u00105\u001a\u0002062\u0006\u00109\u001a\u00020 2\u0006\u0010:\u001a\u00020%2\u0006\u0010;\u001a\u00020%2\u0006\u0010S\u001a\u00020\u001eH\u0086@\u00a2\u0006\u0002\u0010TJ \u0010U\u001a\u00020%2\u0006\u0010V\u001a\u00020F2\u0006\u0010W\u001a\u00020'2\u0006\u0010$\u001a\u00020%H\u0002J>\u0010X\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010Y\u001a\u00020'2\u0006\u0010Z\u001a\u00020%2\u0006\u0010[\u001a\u00020%2\u0006\u0010\\\u001a\u00020%2\u0006\u0010+\u001a\u00020\u001eH\u0086@\u00a2\u0006\u0002\u0010]J>\u0010^\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010?\u001a\u00020\u001c2\u0006\u0010W\u001a\u00020'2\u0006\u0010_\u001a\u00020%2\u0006\u0010`\u001a\u00020%2\u0006\u0010+\u001a\u00020\u001eH\u0086@\u00a2\u0006\u0002\u0010aJD\u0010b\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010?\u001a\u00020\u001c2\u0006\u0010_\u001a\u00020%2\u0006\u0010`\u001a\u00020%2\u0006\u0010+\u001a\u00020\u001e2\f\u0010c\u001a\b\u0012\u0004\u0012\u00020e0dH\u0086@\u00a2\u0006\u0002\u0010fJ\u001e\u0010g\u001a\u00020%2\u0006\u0010V\u001a\u00020F2\f\u0010c\u001a\b\u0012\u0004\u0012\u00020e0dH\u0002Jt\u0010h\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010?\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010'2\b\u0010(\u001a\u0004\u0018\u00010%2\b\u0010)\u001a\u0004\u0018\u00010%2\u0006\u0010*\u001a\u00020%2\u0006\u0010+\u001a\u00020\u001eH\u0086@\u00a2\u0006\u0002\u0010iJ&\u0010j\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010?\u001a\u00020\u001c2\u0006\u0010k\u001a\u00020lH\u0086@\u00a2\u0006\u0002\u0010mJ>\u0010n\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010B\u001a\u00020\u001c2\u0006\u0010W\u001a\u00020'2\u0006\u0010_\u001a\u00020%2\u0006\u0010`\u001a\u00020%2\u0006\u0010+\u001a\u00020\u001eH\u0086@\u00a2\u0006\u0002\u0010aJ\u001e\u0010o\u001a\u00020\u001a2\u0006\u0010?\u001a\u00020\u001c2\u0006\u0010k\u001a\u00020lH\u0082@\u00a2\u0006\u0002\u0010pJD\u0010q\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010B\u001a\u00020\u001c2\u0006\u0010_\u001a\u00020%2\u0006\u0010`\u001a\u00020%2\u0006\u0010+\u001a\u00020\u001e2\f\u0010c\u001a\b\u0012\u0004\u0012\u00020e0dH\u0086@\u00a2\u0006\u0002\u0010fJ\f\u0010r\u001a\u00020#*\u00020#H\u0002J\f\u0010s\u001a\u00020t*\u00020uH\u0002J\f\u0010s\u001a\u00020v*\u00020wH\u0002J\f\u0010s\u001a\u00020x*\u00020yH\u0002J\f\u0010s\u001a\u00020z*\u00020{H\u0002J\f\u0010s\u001a\u00020|*\u00020}H\u0002J$\u0010s\u001a\u00020~*\u00020\u007f2\u0007\u0010\u0080\u0001\u001a\u00020\u001e2\r\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u00020z0dH\u0002J\r\u0010s\u001a\u00030\u0082\u0001*\u00020FH\u0002J\u000e\u0010s\u001a\u00030\u0083\u0001*\u00030\u0084\u0001H\u0002J\r\u0010\u0085\u0001\u001a\u00020u*\u00020tH\u0002J\u0017\u0010\u0085\u0001\u001a\u00020F*\u00030\u0086\u00012\u0007\u0010\u0087\u0001\u001a\u00020\u001cH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0088\u0001"}, d2 = {"Lcom/athallah/hybridfit/data/repository/HybridFitRepository;", "", "database", "Lcom/athallah/hybridfit/data/local/HybridFitDatabase;", "starterPlanBuilder", "Lcom/athallah/hybridfit/domain/service/StarterPlanBuilder;", "recommendationEngine", "Lcom/athallah/hybridfit/domain/service/AdaptiveRecommendationEngine;", "progressAnalyzer", "Lcom/athallah/hybridfit/domain/service/ProgressAnalyzer;", "(Lcom/athallah/hybridfit/data/local/HybridFitDatabase;Lcom/athallah/hybridfit/domain/service/StarterPlanBuilder;Lcom/athallah/hybridfit/domain/service/AdaptiveRecommendationEngine;Lcom/athallah/hybridfit/domain/service/ProgressAnalyzer;)V", "feedbackDao", "Lcom/athallah/hybridfit/data/local/dao/RecommendationFeedbackDao;", "recommendationDao", "Lcom/athallah/hybridfit/data/local/dao/RecommendationDao;", "recoveryCheckInDao", "Lcom/athallah/hybridfit/data/local/dao/RecoveryCheckInDao;", "strengthExerciseEntryDao", "Lcom/athallah/hybridfit/data/local/dao/StrengthExerciseEntryDao;", "userProfileDao", "Lcom/athallah/hybridfit/data/local/dao/UserProfileDao;", "workoutLogDao", "Lcom/athallah/hybridfit/data/local/dao/WorkoutLogDao;", "workoutPlanDao", "Lcom/athallah/hybridfit/data/local/dao/WorkoutPlanDao;", "addPlannerSession", "", "userId", "", "title", "", "category", "Lcom/athallah/hybridfit/domain/model/WorkoutCategory;", "focusArea", "dayOfWeek", "Ljava/time/DayOfWeek;", "durationMinutes", "", "targetDistanceKm", "", "targetSets", "targetReps", "restSeconds", "notes", "(JLjava/lang/String;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;Ljava/lang/String;Ljava/time/DayOfWeek;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "bootstrap", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearFitnessWorkspace", "createStarterProgram", "fullName", "age", "weightKg", "heightCm", "goal", "Lcom/athallah/hybridfit/domain/model/FitnessGoal;", "fitnessLevel", "Lcom/athallah/hybridfit/domain/model/FitnessLevel;", "preferredFocus", "workoutDaysPerWeek", "sessionDurationMinutes", "experienceNotes", "(JLjava/lang/String;IDILcom/athallah/hybridfit/domain/model/FitnessGoal;Lcom/athallah/hybridfit/domain/model/FitnessLevel;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;IILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deletePlannerSession", "sessionId", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteWorkoutLog", "logId", "emptyDashboardSnapshot", "Lcom/athallah/hybridfit/domain/model/DashboardSnapshot;", "findUserSession", "Lcom/athallah/hybridfit/data/local/entity/WorkoutSessionEntity;", "movePlannerSessionToNextDay", "observeDashboard", "Lkotlinx/coroutines/flow/Flow;", "(Ljava/lang/Long;)Lkotlinx/coroutines/flow/Flow;", "recordFeedback", "wasHelpful", "", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recordQuickWorkout", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshRecommendation", "replaceProgramFromAi", "requestSummary", "(JLcom/athallah/hybridfit/domain/model/FitnessGoal;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;IILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runCompletionPercent", "session", "distanceKm", "saveRecoveryCheckIn", "sleepHours", "recoveryScore", "energyLevel", "sorenessLevel", "(JDIIILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveRunWorkout", "actualDurationMinutes", "exertionScore", "(JJDIILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveStrengthWorkout", "exercises", "", "Lcom/athallah/hybridfit/domain/model/StrengthExerciseDraft;", "(JJIILjava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "strengthCompletionPercent", "updatePlannerSession", "(JJLjava/lang/String;Lcom/athallah/hybridfit/domain/model/WorkoutCategory;Ljava/lang/String;Ljava/time/DayOfWeek;ILjava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePlannerSessionState", "plannerState", "Lcom/athallah/hybridfit/domain/model/SessionPlannerState;", "(JJLcom/athallah/hybridfit/domain/model/SessionPlannerState;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateRunWorkout", "updateSessionStateInternal", "(JLcom/athallah/hybridfit/domain/model/SessionPlannerState;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateStrengthWorkout", "next", "toDomain", "Lcom/athallah/hybridfit/domain/model/Recommendation;", "Lcom/athallah/hybridfit/data/local/entity/RecommendationEntity;", "Lcom/athallah/hybridfit/domain/model/RecommendationFeedback;", "Lcom/athallah/hybridfit/data/local/entity/RecommendationFeedbackEntity;", "Lcom/athallah/hybridfit/domain/model/RecoveryCheckIn;", "Lcom/athallah/hybridfit/data/local/entity/RecoveryCheckInEntity;", "Lcom/athallah/hybridfit/domain/model/StrengthExerciseEntry;", "Lcom/athallah/hybridfit/data/local/entity/StrengthExerciseEntryEntity;", "Lcom/athallah/hybridfit/domain/model/UserProfile;", "Lcom/athallah/hybridfit/data/local/entity/UserProfileEntity;", "Lcom/athallah/hybridfit/domain/model/WorkoutLog;", "Lcom/athallah/hybridfit/data/local/entity/WorkoutLogEntity;", "sessionTitle", "exerciseEntries", "Lcom/athallah/hybridfit/domain/model/WorkoutSession;", "Lcom/athallah/hybridfit/domain/model/WorkoutPlan;", "Lcom/athallah/hybridfit/data/local/relation/WorkoutPlanWithSessions;", "toEntity", "Lcom/athallah/hybridfit/domain/service/SessionBlueprint;", "planId", "app_debug"})
public final class HybridFitRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.local.HybridFitDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.service.StarterPlanBuilder starterPlanBuilder = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.service.AdaptiveRecommendationEngine recommendationEngine = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.domain.service.ProgressAnalyzer progressAnalyzer = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.local.dao.UserProfileDao userProfileDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.local.dao.WorkoutPlanDao workoutPlanDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.local.dao.WorkoutLogDao workoutLogDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.local.dao.StrengthExerciseEntryDao strengthExerciseEntryDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.local.dao.RecoveryCheckInDao recoveryCheckInDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.local.dao.RecommendationDao recommendationDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.local.dao.RecommendationFeedbackDao feedbackDao = null;
    
    public HybridFitRepository(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.local.HybridFitDatabase database, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.service.StarterPlanBuilder starterPlanBuilder, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.service.AdaptiveRecommendationEngine recommendationEngine, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.service.ProgressAnalyzer progressAnalyzer) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.athallah.hybridfit.domain.model.DashboardSnapshot> observeDashboard(@org.jetbrains.annotations.Nullable()
    java.lang.Long userId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object bootstrap(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearFitnessWorkspace(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createStarterProgram(long userId, @org.jetbrains.annotations.NotNull()
    java.lang.String fullName, int age, double weightKg, int heightCm, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessGoal goal, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessLevel fitnessLevel, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory preferredFocus, int workoutDaysPerWeek, int sessionDurationMinutes, @org.jetbrains.annotations.NotNull()
    java.lang.String experienceNotes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object replaceProgramFromAi(long userId, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.FitnessGoal goal, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory preferredFocus, int workoutDaysPerWeek, int sessionDurationMinutes, @org.jetbrains.annotations.NotNull()
    java.lang.String requestSummary, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object refreshRecommendation(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object recordQuickWorkout(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveStrengthWorkout(long userId, long sessionId, int actualDurationMinutes, int exertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.StrengthExerciseDraft> exercises, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveRunWorkout(long userId, long sessionId, double distanceKm, int actualDurationMinutes, int exertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateStrengthWorkout(long userId, long logId, int actualDurationMinutes, int exertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.domain.model.StrengthExerciseDraft> exercises, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateRunWorkout(long userId, long logId, double distanceKm, int actualDurationMinutes, int exertionScore, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteWorkoutLog(long userId, long logId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveRecoveryCheckIn(long userId, double sleepHours, int recoveryScore, int energyLevel, int sorenessLevel, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addPlannerSession(long userId, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory category, @org.jetbrains.annotations.NotNull()
    java.lang.String focusArea, @org.jetbrains.annotations.NotNull()
    java.time.DayOfWeek dayOfWeek, int durationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetDistanceKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetSets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetReps, int restSeconds, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updatePlannerSession(long userId, long sessionId, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.WorkoutCategory category, @org.jetbrains.annotations.NotNull()
    java.lang.String focusArea, @org.jetbrains.annotations.NotNull()
    java.time.DayOfWeek dayOfWeek, int durationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetDistanceKm, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetSets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetReps, int restSeconds, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object movePlannerSessionToNextDay(long userId, long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updatePlannerSessionState(long userId, long sessionId, @org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.domain.model.SessionPlannerState plannerState, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deletePlannerSession(long userId, long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object recordFeedback(long userId, boolean wasHelpful, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object findUserSession(long userId, long sessionId, kotlin.coroutines.Continuation<? super com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity> $completion) {
        return null;
    }
    
    private final java.lang.Object updateSessionStateInternal(long sessionId, com.athallah.hybridfit.domain.model.SessionPlannerState plannerState, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final int strengthCompletionPercent(com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity session, java.util.List<com.athallah.hybridfit.domain.model.StrengthExerciseDraft> exercises) {
        return 0;
    }
    
    private final int runCompletionPercent(com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity session, double distanceKm, int durationMinutes) {
        return 0;
    }
    
    private final com.athallah.hybridfit.domain.model.DashboardSnapshot emptyDashboardSnapshot() {
        return null;
    }
    
    private final com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity toEntity(com.athallah.hybridfit.domain.service.SessionBlueprint $this$toEntity, long planId) {
        return null;
    }
    
    private final com.athallah.hybridfit.domain.model.UserProfile toDomain(com.athallah.hybridfit.data.local.entity.UserProfileEntity $this$toDomain) {
        return null;
    }
    
    private final com.athallah.hybridfit.domain.model.WorkoutPlan toDomain(com.athallah.hybridfit.data.local.relation.WorkoutPlanWithSessions $this$toDomain) {
        return null;
    }
    
    private final com.athallah.hybridfit.domain.model.WorkoutSession toDomain(com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity $this$toDomain) {
        return null;
    }
    
    private final com.athallah.hybridfit.domain.model.WorkoutLog toDomain(com.athallah.hybridfit.data.local.entity.WorkoutLogEntity $this$toDomain, java.lang.String sessionTitle, java.util.List<com.athallah.hybridfit.domain.model.StrengthExerciseEntry> exerciseEntries) {
        return null;
    }
    
    private final com.athallah.hybridfit.domain.model.StrengthExerciseEntry toDomain(com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity $this$toDomain) {
        return null;
    }
    
    private final com.athallah.hybridfit.domain.model.RecoveryCheckIn toDomain(com.athallah.hybridfit.data.local.entity.RecoveryCheckInEntity $this$toDomain) {
        return null;
    }
    
    private final com.athallah.hybridfit.domain.model.Recommendation toDomain(com.athallah.hybridfit.data.local.entity.RecommendationEntity $this$toDomain) {
        return null;
    }
    
    private final com.athallah.hybridfit.domain.model.RecommendationFeedback toDomain(com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity $this$toDomain) {
        return null;
    }
    
    private final com.athallah.hybridfit.data.local.entity.RecommendationEntity toEntity(com.athallah.hybridfit.domain.model.Recommendation $this$toEntity) {
        return null;
    }
    
    private final java.time.DayOfWeek next(java.time.DayOfWeek $this$next) {
        return null;
    }
}