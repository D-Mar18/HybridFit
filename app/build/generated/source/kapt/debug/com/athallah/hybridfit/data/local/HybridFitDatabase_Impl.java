package com.athallah.hybridfit.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.athallah.hybridfit.data.local.dao.RecommendationDao;
import com.athallah.hybridfit.data.local.dao.RecommendationDao_Impl;
import com.athallah.hybridfit.data.local.dao.RecommendationFeedbackDao;
import com.athallah.hybridfit.data.local.dao.RecommendationFeedbackDao_Impl;
import com.athallah.hybridfit.data.local.dao.RecoveryCheckInDao;
import com.athallah.hybridfit.data.local.dao.RecoveryCheckInDao_Impl;
import com.athallah.hybridfit.data.local.dao.StrengthExerciseEntryDao;
import com.athallah.hybridfit.data.local.dao.StrengthExerciseEntryDao_Impl;
import com.athallah.hybridfit.data.local.dao.UserProfileDao;
import com.athallah.hybridfit.data.local.dao.UserProfileDao_Impl;
import com.athallah.hybridfit.data.local.dao.WorkoutLogDao;
import com.athallah.hybridfit.data.local.dao.WorkoutLogDao_Impl;
import com.athallah.hybridfit.data.local.dao.WorkoutPlanDao;
import com.athallah.hybridfit.data.local.dao.WorkoutPlanDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class HybridFitDatabase_Impl extends HybridFitDatabase {
  private volatile UserProfileDao _userProfileDao;

  private volatile WorkoutPlanDao _workoutPlanDao;

  private volatile WorkoutLogDao _workoutLogDao;

  private volatile StrengthExerciseEntryDao _strengthExerciseEntryDao;

  private volatile RecoveryCheckInDao _recoveryCheckInDao;

  private volatile RecommendationDao _recommendationDao;

  private volatile RecommendationFeedbackDao _recommendationFeedbackDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_profiles` (`id` INTEGER NOT NULL, `fullName` TEXT NOT NULL, `age` INTEGER NOT NULL, `weightKg` REAL NOT NULL, `heightCm` INTEGER NOT NULL, `genderLabel` TEXT NOT NULL, `goal` TEXT NOT NULL, `fitnessLevel` TEXT NOT NULL, `preferredFocus` TEXT NOT NULL, `workoutDaysPerWeek` INTEGER NOT NULL, `sessionDurationMinutes` INTEGER NOT NULL, `experienceNotes` TEXT NOT NULL, `createdAt` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `workout_plans` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `title` TEXT NOT NULL, `goal` TEXT NOT NULL, `startDate` TEXT NOT NULL, `sessionsPerWeek` INTEGER NOT NULL, `status` TEXT NOT NULL, `createdAt` TEXT NOT NULL, FOREIGN KEY(`userId`) REFERENCES `user_profiles`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_workout_plans_userId` ON `workout_plans` (`userId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `workout_sessions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `planId` INTEGER NOT NULL, `dayOfWeek` TEXT NOT NULL, `title` TEXT NOT NULL, `category` TEXT NOT NULL, `focusArea` TEXT NOT NULL, `targetDurationMinutes` INTEGER NOT NULL, `targetDistanceKm` REAL, `targetSets` INTEGER, `targetReps` INTEGER, `restSeconds` INTEGER NOT NULL, `guidance` TEXT NOT NULL, `orderInDay` INTEGER NOT NULL, `plannerState` TEXT NOT NULL, `sessionNotes` TEXT NOT NULL, FOREIGN KEY(`planId`) REFERENCES `workout_plans`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_workout_sessions_planId` ON `workout_sessions` (`planId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `workout_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` INTEGER NOT NULL, `performedOn` TEXT NOT NULL, `actualDurationMinutes` INTEGER NOT NULL, `actualDistanceKm` REAL, `averagePaceSecondsPerKm` INTEGER, `completedSets` INTEGER, `completedReps` INTEGER, `completionPercent` INTEGER NOT NULL, `exertionScore` INTEGER NOT NULL, `notes` TEXT NOT NULL, `wasCompleted` INTEGER NOT NULL, FOREIGN KEY(`sessionId`) REFERENCES `workout_sessions`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_workout_logs_sessionId` ON `workout_logs` (`sessionId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `strength_exercise_entries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `workoutLogId` INTEGER NOT NULL, `exerciseName` TEXT NOT NULL, `actualSets` INTEGER NOT NULL, `actualReps` INTEGER NOT NULL, `actualWeightKg` REAL NOT NULL, `notes` TEXT NOT NULL, FOREIGN KEY(`workoutLogId`) REFERENCES `workout_logs`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_strength_exercise_entries_workoutLogId` ON `strength_exercise_entries` (`workoutLogId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `recovery_check_ins` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `recordedOn` TEXT NOT NULL, `sleepHours` REAL NOT NULL, `recoveryScore` INTEGER NOT NULL, `energyLevel` INTEGER NOT NULL, `sorenessLevel` INTEGER NOT NULL, `notes` TEXT NOT NULL)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_recovery_check_ins_userId` ON `recovery_check_ins` (`userId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `recommendations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `createdAt` TEXT NOT NULL, `title` TEXT NOT NULL, `summary` TEXT NOT NULL, `action` TEXT NOT NULL, `targetDurationMinutes` INTEGER NOT NULL, `targetDistanceKm` REAL, `targetSets` INTEGER, `targetReps` INTEGER, `restSeconds` INTEGER NOT NULL, `rationale` TEXT NOT NULL, FOREIGN KEY(`userId`) REFERENCES `user_profiles`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_recommendations_userId` ON `recommendations` (`userId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `recommendation_feedback` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `recommendationId` INTEGER NOT NULL, `wasHelpful` INTEGER NOT NULL, `rating` INTEGER NOT NULL, `notes` TEXT NOT NULL, `recordedAt` TEXT NOT NULL, FOREIGN KEY(`recommendationId`) REFERENCES `recommendations`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_recommendation_feedback_recommendationId` ON `recommendation_feedback` (`recommendationId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '21a218ddba13b9a94221cdc6da76003d')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `user_profiles`");
        db.execSQL("DROP TABLE IF EXISTS `workout_plans`");
        db.execSQL("DROP TABLE IF EXISTS `workout_sessions`");
        db.execSQL("DROP TABLE IF EXISTS `workout_logs`");
        db.execSQL("DROP TABLE IF EXISTS `strength_exercise_entries`");
        db.execSQL("DROP TABLE IF EXISTS `recovery_check_ins`");
        db.execSQL("DROP TABLE IF EXISTS `recommendations`");
        db.execSQL("DROP TABLE IF EXISTS `recommendation_feedback`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUserProfiles = new HashMap<String, TableInfo.Column>(13);
        _columnsUserProfiles.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("fullName", new TableInfo.Column("fullName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("weightKg", new TableInfo.Column("weightKg", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("heightCm", new TableInfo.Column("heightCm", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("genderLabel", new TableInfo.Column("genderLabel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("goal", new TableInfo.Column("goal", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("fitnessLevel", new TableInfo.Column("fitnessLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("preferredFocus", new TableInfo.Column("preferredFocus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("workoutDaysPerWeek", new TableInfo.Column("workoutDaysPerWeek", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("sessionDurationMinutes", new TableInfo.Column("sessionDurationMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("experienceNotes", new TableInfo.Column("experienceNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("createdAt", new TableInfo.Column("createdAt", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserProfiles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserProfiles = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserProfiles = new TableInfo("user_profiles", _columnsUserProfiles, _foreignKeysUserProfiles, _indicesUserProfiles);
        final TableInfo _existingUserProfiles = TableInfo.read(db, "user_profiles");
        if (!_infoUserProfiles.equals(_existingUserProfiles)) {
          return new RoomOpenHelper.ValidationResult(false, "user_profiles(com.athallah.hybridfit.data.local.entity.UserProfileEntity).\n"
                  + " Expected:\n" + _infoUserProfiles + "\n"
                  + " Found:\n" + _existingUserProfiles);
        }
        final HashMap<String, TableInfo.Column> _columnsWorkoutPlans = new HashMap<String, TableInfo.Column>(8);
        _columnsWorkoutPlans.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlans.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlans.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlans.put("goal", new TableInfo.Column("goal", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlans.put("startDate", new TableInfo.Column("startDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlans.put("sessionsPerWeek", new TableInfo.Column("sessionsPerWeek", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlans.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlans.put("createdAt", new TableInfo.Column("createdAt", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkoutPlans = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysWorkoutPlans.add(new TableInfo.ForeignKey("user_profiles", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesWorkoutPlans = new HashSet<TableInfo.Index>(1);
        _indicesWorkoutPlans.add(new TableInfo.Index("index_workout_plans_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        final TableInfo _infoWorkoutPlans = new TableInfo("workout_plans", _columnsWorkoutPlans, _foreignKeysWorkoutPlans, _indicesWorkoutPlans);
        final TableInfo _existingWorkoutPlans = TableInfo.read(db, "workout_plans");
        if (!_infoWorkoutPlans.equals(_existingWorkoutPlans)) {
          return new RoomOpenHelper.ValidationResult(false, "workout_plans(com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity).\n"
                  + " Expected:\n" + _infoWorkoutPlans + "\n"
                  + " Found:\n" + _existingWorkoutPlans);
        }
        final HashMap<String, TableInfo.Column> _columnsWorkoutSessions = new HashMap<String, TableInfo.Column>(15);
        _columnsWorkoutSessions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("planId", new TableInfo.Column("planId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("dayOfWeek", new TableInfo.Column("dayOfWeek", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("focusArea", new TableInfo.Column("focusArea", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("targetDurationMinutes", new TableInfo.Column("targetDurationMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("targetDistanceKm", new TableInfo.Column("targetDistanceKm", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("targetSets", new TableInfo.Column("targetSets", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("targetReps", new TableInfo.Column("targetReps", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("restSeconds", new TableInfo.Column("restSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("guidance", new TableInfo.Column("guidance", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("orderInDay", new TableInfo.Column("orderInDay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("plannerState", new TableInfo.Column("plannerState", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("sessionNotes", new TableInfo.Column("sessionNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkoutSessions = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysWorkoutSessions.add(new TableInfo.ForeignKey("workout_plans", "CASCADE", "NO ACTION", Arrays.asList("planId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesWorkoutSessions = new HashSet<TableInfo.Index>(1);
        _indicesWorkoutSessions.add(new TableInfo.Index("index_workout_sessions_planId", false, Arrays.asList("planId"), Arrays.asList("ASC")));
        final TableInfo _infoWorkoutSessions = new TableInfo("workout_sessions", _columnsWorkoutSessions, _foreignKeysWorkoutSessions, _indicesWorkoutSessions);
        final TableInfo _existingWorkoutSessions = TableInfo.read(db, "workout_sessions");
        if (!_infoWorkoutSessions.equals(_existingWorkoutSessions)) {
          return new RoomOpenHelper.ValidationResult(false, "workout_sessions(com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity).\n"
                  + " Expected:\n" + _infoWorkoutSessions + "\n"
                  + " Found:\n" + _existingWorkoutSessions);
        }
        final HashMap<String, TableInfo.Column> _columnsWorkoutLogs = new HashMap<String, TableInfo.Column>(12);
        _columnsWorkoutLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutLogs.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutLogs.put("performedOn", new TableInfo.Column("performedOn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutLogs.put("actualDurationMinutes", new TableInfo.Column("actualDurationMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutLogs.put("actualDistanceKm", new TableInfo.Column("actualDistanceKm", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutLogs.put("averagePaceSecondsPerKm", new TableInfo.Column("averagePaceSecondsPerKm", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutLogs.put("completedSets", new TableInfo.Column("completedSets", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutLogs.put("completedReps", new TableInfo.Column("completedReps", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutLogs.put("completionPercent", new TableInfo.Column("completionPercent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutLogs.put("exertionScore", new TableInfo.Column("exertionScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutLogs.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutLogs.put("wasCompleted", new TableInfo.Column("wasCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkoutLogs = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysWorkoutLogs.add(new TableInfo.ForeignKey("workout_sessions", "CASCADE", "NO ACTION", Arrays.asList("sessionId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesWorkoutLogs = new HashSet<TableInfo.Index>(1);
        _indicesWorkoutLogs.add(new TableInfo.Index("index_workout_logs_sessionId", false, Arrays.asList("sessionId"), Arrays.asList("ASC")));
        final TableInfo _infoWorkoutLogs = new TableInfo("workout_logs", _columnsWorkoutLogs, _foreignKeysWorkoutLogs, _indicesWorkoutLogs);
        final TableInfo _existingWorkoutLogs = TableInfo.read(db, "workout_logs");
        if (!_infoWorkoutLogs.equals(_existingWorkoutLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "workout_logs(com.athallah.hybridfit.data.local.entity.WorkoutLogEntity).\n"
                  + " Expected:\n" + _infoWorkoutLogs + "\n"
                  + " Found:\n" + _existingWorkoutLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsStrengthExerciseEntries = new HashMap<String, TableInfo.Column>(7);
        _columnsStrengthExerciseEntries.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStrengthExerciseEntries.put("workoutLogId", new TableInfo.Column("workoutLogId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStrengthExerciseEntries.put("exerciseName", new TableInfo.Column("exerciseName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStrengthExerciseEntries.put("actualSets", new TableInfo.Column("actualSets", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStrengthExerciseEntries.put("actualReps", new TableInfo.Column("actualReps", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStrengthExerciseEntries.put("actualWeightKg", new TableInfo.Column("actualWeightKg", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStrengthExerciseEntries.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStrengthExerciseEntries = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysStrengthExerciseEntries.add(new TableInfo.ForeignKey("workout_logs", "CASCADE", "NO ACTION", Arrays.asList("workoutLogId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesStrengthExerciseEntries = new HashSet<TableInfo.Index>(1);
        _indicesStrengthExerciseEntries.add(new TableInfo.Index("index_strength_exercise_entries_workoutLogId", false, Arrays.asList("workoutLogId"), Arrays.asList("ASC")));
        final TableInfo _infoStrengthExerciseEntries = new TableInfo("strength_exercise_entries", _columnsStrengthExerciseEntries, _foreignKeysStrengthExerciseEntries, _indicesStrengthExerciseEntries);
        final TableInfo _existingStrengthExerciseEntries = TableInfo.read(db, "strength_exercise_entries");
        if (!_infoStrengthExerciseEntries.equals(_existingStrengthExerciseEntries)) {
          return new RoomOpenHelper.ValidationResult(false, "strength_exercise_entries(com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity).\n"
                  + " Expected:\n" + _infoStrengthExerciseEntries + "\n"
                  + " Found:\n" + _existingStrengthExerciseEntries);
        }
        final HashMap<String, TableInfo.Column> _columnsRecoveryCheckIns = new HashMap<String, TableInfo.Column>(8);
        _columnsRecoveryCheckIns.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecoveryCheckIns.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecoveryCheckIns.put("recordedOn", new TableInfo.Column("recordedOn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecoveryCheckIns.put("sleepHours", new TableInfo.Column("sleepHours", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecoveryCheckIns.put("recoveryScore", new TableInfo.Column("recoveryScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecoveryCheckIns.put("energyLevel", new TableInfo.Column("energyLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecoveryCheckIns.put("sorenessLevel", new TableInfo.Column("sorenessLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecoveryCheckIns.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRecoveryCheckIns = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRecoveryCheckIns = new HashSet<TableInfo.Index>(1);
        _indicesRecoveryCheckIns.add(new TableInfo.Index("index_recovery_check_ins_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        final TableInfo _infoRecoveryCheckIns = new TableInfo("recovery_check_ins", _columnsRecoveryCheckIns, _foreignKeysRecoveryCheckIns, _indicesRecoveryCheckIns);
        final TableInfo _existingRecoveryCheckIns = TableInfo.read(db, "recovery_check_ins");
        if (!_infoRecoveryCheckIns.equals(_existingRecoveryCheckIns)) {
          return new RoomOpenHelper.ValidationResult(false, "recovery_check_ins(com.athallah.hybridfit.data.local.entity.RecoveryCheckInEntity).\n"
                  + " Expected:\n" + _infoRecoveryCheckIns + "\n"
                  + " Found:\n" + _existingRecoveryCheckIns);
        }
        final HashMap<String, TableInfo.Column> _columnsRecommendations = new HashMap<String, TableInfo.Column>(12);
        _columnsRecommendations.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendations.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendations.put("createdAt", new TableInfo.Column("createdAt", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendations.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendations.put("summary", new TableInfo.Column("summary", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendations.put("action", new TableInfo.Column("action", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendations.put("targetDurationMinutes", new TableInfo.Column("targetDurationMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendations.put("targetDistanceKm", new TableInfo.Column("targetDistanceKm", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendations.put("targetSets", new TableInfo.Column("targetSets", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendations.put("targetReps", new TableInfo.Column("targetReps", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendations.put("restSeconds", new TableInfo.Column("restSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendations.put("rationale", new TableInfo.Column("rationale", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRecommendations = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysRecommendations.add(new TableInfo.ForeignKey("user_profiles", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesRecommendations = new HashSet<TableInfo.Index>(1);
        _indicesRecommendations.add(new TableInfo.Index("index_recommendations_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        final TableInfo _infoRecommendations = new TableInfo("recommendations", _columnsRecommendations, _foreignKeysRecommendations, _indicesRecommendations);
        final TableInfo _existingRecommendations = TableInfo.read(db, "recommendations");
        if (!_infoRecommendations.equals(_existingRecommendations)) {
          return new RoomOpenHelper.ValidationResult(false, "recommendations(com.athallah.hybridfit.data.local.entity.RecommendationEntity).\n"
                  + " Expected:\n" + _infoRecommendations + "\n"
                  + " Found:\n" + _existingRecommendations);
        }
        final HashMap<String, TableInfo.Column> _columnsRecommendationFeedback = new HashMap<String, TableInfo.Column>(6);
        _columnsRecommendationFeedback.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendationFeedback.put("recommendationId", new TableInfo.Column("recommendationId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendationFeedback.put("wasHelpful", new TableInfo.Column("wasHelpful", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendationFeedback.put("rating", new TableInfo.Column("rating", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendationFeedback.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecommendationFeedback.put("recordedAt", new TableInfo.Column("recordedAt", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRecommendationFeedback = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysRecommendationFeedback.add(new TableInfo.ForeignKey("recommendations", "CASCADE", "NO ACTION", Arrays.asList("recommendationId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesRecommendationFeedback = new HashSet<TableInfo.Index>(1);
        _indicesRecommendationFeedback.add(new TableInfo.Index("index_recommendation_feedback_recommendationId", false, Arrays.asList("recommendationId"), Arrays.asList("ASC")));
        final TableInfo _infoRecommendationFeedback = new TableInfo("recommendation_feedback", _columnsRecommendationFeedback, _foreignKeysRecommendationFeedback, _indicesRecommendationFeedback);
        final TableInfo _existingRecommendationFeedback = TableInfo.read(db, "recommendation_feedback");
        if (!_infoRecommendationFeedback.equals(_existingRecommendationFeedback)) {
          return new RoomOpenHelper.ValidationResult(false, "recommendation_feedback(com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity).\n"
                  + " Expected:\n" + _infoRecommendationFeedback + "\n"
                  + " Found:\n" + _existingRecommendationFeedback);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "21a218ddba13b9a94221cdc6da76003d", "61a62a98f6b080f13d10e0e099960658");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "user_profiles","workout_plans","workout_sessions","workout_logs","strength_exercise_entries","recovery_check_ins","recommendations","recommendation_feedback");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `user_profiles`");
      _db.execSQL("DELETE FROM `workout_plans`");
      _db.execSQL("DELETE FROM `workout_sessions`");
      _db.execSQL("DELETE FROM `workout_logs`");
      _db.execSQL("DELETE FROM `strength_exercise_entries`");
      _db.execSQL("DELETE FROM `recovery_check_ins`");
      _db.execSQL("DELETE FROM `recommendations`");
      _db.execSQL("DELETE FROM `recommendation_feedback`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserProfileDao.class, UserProfileDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WorkoutPlanDao.class, WorkoutPlanDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WorkoutLogDao.class, WorkoutLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StrengthExerciseEntryDao.class, StrengthExerciseEntryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RecoveryCheckInDao.class, RecoveryCheckInDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RecommendationDao.class, RecommendationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RecommendationFeedbackDao.class, RecommendationFeedbackDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserProfileDao userProfileDao() {
    if (_userProfileDao != null) {
      return _userProfileDao;
    } else {
      synchronized(this) {
        if(_userProfileDao == null) {
          _userProfileDao = new UserProfileDao_Impl(this);
        }
        return _userProfileDao;
      }
    }
  }

  @Override
  public WorkoutPlanDao workoutPlanDao() {
    if (_workoutPlanDao != null) {
      return _workoutPlanDao;
    } else {
      synchronized(this) {
        if(_workoutPlanDao == null) {
          _workoutPlanDao = new WorkoutPlanDao_Impl(this);
        }
        return _workoutPlanDao;
      }
    }
  }

  @Override
  public WorkoutLogDao workoutLogDao() {
    if (_workoutLogDao != null) {
      return _workoutLogDao;
    } else {
      synchronized(this) {
        if(_workoutLogDao == null) {
          _workoutLogDao = new WorkoutLogDao_Impl(this);
        }
        return _workoutLogDao;
      }
    }
  }

  @Override
  public StrengthExerciseEntryDao strengthExerciseEntryDao() {
    if (_strengthExerciseEntryDao != null) {
      return _strengthExerciseEntryDao;
    } else {
      synchronized(this) {
        if(_strengthExerciseEntryDao == null) {
          _strengthExerciseEntryDao = new StrengthExerciseEntryDao_Impl(this);
        }
        return _strengthExerciseEntryDao;
      }
    }
  }

  @Override
  public RecoveryCheckInDao recoveryCheckInDao() {
    if (_recoveryCheckInDao != null) {
      return _recoveryCheckInDao;
    } else {
      synchronized(this) {
        if(_recoveryCheckInDao == null) {
          _recoveryCheckInDao = new RecoveryCheckInDao_Impl(this);
        }
        return _recoveryCheckInDao;
      }
    }
  }

  @Override
  public RecommendationDao recommendationDao() {
    if (_recommendationDao != null) {
      return _recommendationDao;
    } else {
      synchronized(this) {
        if(_recommendationDao == null) {
          _recommendationDao = new RecommendationDao_Impl(this);
        }
        return _recommendationDao;
      }
    }
  }

  @Override
  public RecommendationFeedbackDao recommendationFeedbackDao() {
    if (_recommendationFeedbackDao != null) {
      return _recommendationFeedbackDao;
    } else {
      synchronized(this) {
        if(_recommendationFeedbackDao == null) {
          _recommendationFeedbackDao = new RecommendationFeedbackDao_Impl(this);
        }
        return _recommendationFeedbackDao;
      }
    }
  }
}
