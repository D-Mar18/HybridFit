package com.athallah.hybridfit.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.athallah.hybridfit.data.local.converter.DbConverters;
import com.athallah.hybridfit.data.local.entity.WorkoutPlanEntity;
import com.athallah.hybridfit.data.local.entity.WorkoutSessionEntity;
import com.athallah.hybridfit.data.local.relation.WorkoutPlanWithSessions;
import com.athallah.hybridfit.domain.model.FitnessGoal;
import com.athallah.hybridfit.domain.model.PlanStatus;
import com.athallah.hybridfit.domain.model.SessionPlannerState;
import com.athallah.hybridfit.domain.model.WorkoutCategory;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WorkoutPlanDao_Impl implements WorkoutPlanDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WorkoutPlanEntity> __insertionAdapterOfWorkoutPlanEntity;

  private final DbConverters __dbConverters = new DbConverters();

  private final EntityInsertionAdapter<WorkoutSessionEntity> __insertionAdapterOfWorkoutSessionEntity;

  private final EntityDeletionOrUpdateAdapter<WorkoutSessionEntity> __updateAdapterOfWorkoutSessionEntity;

  private final EntityDeletionOrUpdateAdapter<WorkoutPlanEntity> __updateAdapterOfWorkoutPlanEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSession;

  public WorkoutPlanDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorkoutPlanEntity = new EntityInsertionAdapter<WorkoutPlanEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `workout_plans` (`id`,`userId`,`title`,`goal`,`startDate`,`sessionsPerWeek`,`status`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutPlanEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        if (entity.getTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitle());
        }
        final String _tmp = __dbConverters.fromFitnessGoal(entity.getGoal());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        final String _tmp_1 = __dbConverters.fromLocalDate(entity.getStartDate());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        statement.bindLong(6, entity.getSessionsPerWeek());
        final String _tmp_2 = __dbConverters.fromPlanStatus(entity.getStatus());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_2);
        }
        final String _tmp_3 = __dbConverters.fromInstant(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_3);
        }
      }
    };
    this.__insertionAdapterOfWorkoutSessionEntity = new EntityInsertionAdapter<WorkoutSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `workout_sessions` (`id`,`planId`,`dayOfWeek`,`title`,`category`,`focusArea`,`targetDurationMinutes`,`targetDistanceKm`,`targetSets`,`targetReps`,`restSeconds`,`guidance`,`orderInDay`,`plannerState`,`sessionNotes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutSessionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPlanId());
        final String _tmp = __dbConverters.fromDayOfWeek(entity.getDayOfWeek());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTitle());
        }
        final String _tmp_1 = __dbConverters.fromWorkoutCategory(entity.getCategory());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        if (entity.getFocusArea() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getFocusArea());
        }
        statement.bindLong(7, entity.getTargetDurationMinutes());
        if (entity.getTargetDistanceKm() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getTargetDistanceKm());
        }
        if (entity.getTargetSets() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getTargetSets());
        }
        if (entity.getTargetReps() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getTargetReps());
        }
        statement.bindLong(11, entity.getRestSeconds());
        if (entity.getGuidance() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getGuidance());
        }
        statement.bindLong(13, entity.getOrderInDay());
        final String _tmp_2 = __dbConverters.fromSessionPlannerState(entity.getPlannerState());
        if (_tmp_2 == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, _tmp_2);
        }
        if (entity.getSessionNotes() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getSessionNotes());
        }
      }
    };
    this.__updateAdapterOfWorkoutSessionEntity = new EntityDeletionOrUpdateAdapter<WorkoutSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `workout_sessions` SET `id` = ?,`planId` = ?,`dayOfWeek` = ?,`title` = ?,`category` = ?,`focusArea` = ?,`targetDurationMinutes` = ?,`targetDistanceKm` = ?,`targetSets` = ?,`targetReps` = ?,`restSeconds` = ?,`guidance` = ?,`orderInDay` = ?,`plannerState` = ?,`sessionNotes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutSessionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPlanId());
        final String _tmp = __dbConverters.fromDayOfWeek(entity.getDayOfWeek());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTitle());
        }
        final String _tmp_1 = __dbConverters.fromWorkoutCategory(entity.getCategory());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        if (entity.getFocusArea() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getFocusArea());
        }
        statement.bindLong(7, entity.getTargetDurationMinutes());
        if (entity.getTargetDistanceKm() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getTargetDistanceKm());
        }
        if (entity.getTargetSets() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getTargetSets());
        }
        if (entity.getTargetReps() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getTargetReps());
        }
        statement.bindLong(11, entity.getRestSeconds());
        if (entity.getGuidance() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getGuidance());
        }
        statement.bindLong(13, entity.getOrderInDay());
        final String _tmp_2 = __dbConverters.fromSessionPlannerState(entity.getPlannerState());
        if (_tmp_2 == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, _tmp_2);
        }
        if (entity.getSessionNotes() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getSessionNotes());
        }
        statement.bindLong(16, entity.getId());
      }
    };
    this.__updateAdapterOfWorkoutPlanEntity = new EntityDeletionOrUpdateAdapter<WorkoutPlanEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `workout_plans` SET `id` = ?,`userId` = ?,`title` = ?,`goal` = ?,`startDate` = ?,`sessionsPerWeek` = ?,`status` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutPlanEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        if (entity.getTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitle());
        }
        final String _tmp = __dbConverters.fromFitnessGoal(entity.getGoal());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        final String _tmp_1 = __dbConverters.fromLocalDate(entity.getStartDate());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        statement.bindLong(6, entity.getSessionsPerWeek());
        final String _tmp_2 = __dbConverters.fromPlanStatus(entity.getStatus());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_2);
        }
        final String _tmp_3 = __dbConverters.fromInstant(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_3);
        }
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteSession = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM workout_sessions WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertPlan(final WorkoutPlanEntity plan,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWorkoutPlanEntity.insertAndReturnId(plan);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertSessions(final List<WorkoutSessionEntity> sessions,
      final Continuation<? super List<Long>> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<List<Long>>() {
      @Override
      @NonNull
      public List<Long> call() throws Exception {
        __db.beginTransaction();
        try {
          final List<Long> _result = __insertionAdapterOfWorkoutSessionEntity.insertAndReturnIdsList(sessions);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertSession(final WorkoutSessionEntity session,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWorkoutSessionEntity.insertAndReturnId(session);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSession(final WorkoutSessionEntity session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkoutSessionEntity.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePlan(final WorkoutPlanEntity plan,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkoutPlanEntity.handle(plan);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSession(final long sessionId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSession.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteSession.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getSessionById(final long sessionId,
      final Continuation<? super WorkoutSessionEntity> $completion) {
    final String _sql = "SELECT * FROM workout_sessions WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WorkoutSessionEntity>() {
      @Override
      @Nullable
      public WorkoutSessionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlanId = CursorUtil.getColumnIndexOrThrow(_cursor, "planId");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfFocusArea = CursorUtil.getColumnIndexOrThrow(_cursor, "focusArea");
          final int _cursorIndexOfTargetDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDurationMinutes");
          final int _cursorIndexOfTargetDistanceKm = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDistanceKm");
          final int _cursorIndexOfTargetSets = CursorUtil.getColumnIndexOrThrow(_cursor, "targetSets");
          final int _cursorIndexOfTargetReps = CursorUtil.getColumnIndexOrThrow(_cursor, "targetReps");
          final int _cursorIndexOfRestSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "restSeconds");
          final int _cursorIndexOfGuidance = CursorUtil.getColumnIndexOrThrow(_cursor, "guidance");
          final int _cursorIndexOfOrderInDay = CursorUtil.getColumnIndexOrThrow(_cursor, "orderInDay");
          final int _cursorIndexOfPlannerState = CursorUtil.getColumnIndexOrThrow(_cursor, "plannerState");
          final int _cursorIndexOfSessionNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionNotes");
          final WorkoutSessionEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPlanId;
            _tmpPlanId = _cursor.getLong(_cursorIndexOfPlanId);
            final DayOfWeek _tmpDayOfWeek;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDayOfWeek)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDayOfWeek);
            }
            _tmpDayOfWeek = __dbConverters.toDayOfWeek(_tmp);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final WorkoutCategory _tmpCategory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __dbConverters.toWorkoutCategory(_tmp_1);
            final String _tmpFocusArea;
            if (_cursor.isNull(_cursorIndexOfFocusArea)) {
              _tmpFocusArea = null;
            } else {
              _tmpFocusArea = _cursor.getString(_cursorIndexOfFocusArea);
            }
            final int _tmpTargetDurationMinutes;
            _tmpTargetDurationMinutes = _cursor.getInt(_cursorIndexOfTargetDurationMinutes);
            final Double _tmpTargetDistanceKm;
            if (_cursor.isNull(_cursorIndexOfTargetDistanceKm)) {
              _tmpTargetDistanceKm = null;
            } else {
              _tmpTargetDistanceKm = _cursor.getDouble(_cursorIndexOfTargetDistanceKm);
            }
            final Integer _tmpTargetSets;
            if (_cursor.isNull(_cursorIndexOfTargetSets)) {
              _tmpTargetSets = null;
            } else {
              _tmpTargetSets = _cursor.getInt(_cursorIndexOfTargetSets);
            }
            final Integer _tmpTargetReps;
            if (_cursor.isNull(_cursorIndexOfTargetReps)) {
              _tmpTargetReps = null;
            } else {
              _tmpTargetReps = _cursor.getInt(_cursorIndexOfTargetReps);
            }
            final int _tmpRestSeconds;
            _tmpRestSeconds = _cursor.getInt(_cursorIndexOfRestSeconds);
            final String _tmpGuidance;
            if (_cursor.isNull(_cursorIndexOfGuidance)) {
              _tmpGuidance = null;
            } else {
              _tmpGuidance = _cursor.getString(_cursorIndexOfGuidance);
            }
            final int _tmpOrderInDay;
            _tmpOrderInDay = _cursor.getInt(_cursorIndexOfOrderInDay);
            final SessionPlannerState _tmpPlannerState;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPlannerState)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPlannerState);
            }
            _tmpPlannerState = __dbConverters.toSessionPlannerState(_tmp_2);
            final String _tmpSessionNotes;
            if (_cursor.isNull(_cursorIndexOfSessionNotes)) {
              _tmpSessionNotes = null;
            } else {
              _tmpSessionNotes = _cursor.getString(_cursorIndexOfSessionNotes);
            }
            _result = new WorkoutSessionEntity(_tmpId,_tmpPlanId,_tmpDayOfWeek,_tmpTitle,_tmpCategory,_tmpFocusArea,_tmpTargetDurationMinutes,_tmpTargetDistanceKm,_tmpTargetSets,_tmpTargetReps,_tmpRestSeconds,_tmpGuidance,_tmpOrderInDay,_tmpPlannerState,_tmpSessionNotes);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<WorkoutPlanWithSessions> observeActivePlanWithSessions(final long userId) {
    final String _sql = "SELECT * FROM workout_plans WHERE userId = ? AND status = 'ACTIVE' ORDER BY createdAt DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"workout_sessions",
        "workout_plans"}, new Callable<WorkoutPlanWithSessions>() {
      @Override
      @Nullable
      public WorkoutPlanWithSessions call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "goal");
            final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
            final int _cursorIndexOfSessionsPerWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionsPerWeek");
            final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final LongSparseArray<ArrayList<WorkoutSessionEntity>> _collectionSessions = new LongSparseArray<ArrayList<WorkoutSessionEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfId);
              if (!_collectionSessions.containsKey(_tmpKey)) {
                _collectionSessions.put(_tmpKey, new ArrayList<WorkoutSessionEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipworkoutSessionsAscomAthallahHybridfitDataLocalEntityWorkoutSessionEntity(_collectionSessions);
            final WorkoutPlanWithSessions _result;
            if (_cursor.moveToFirst()) {
              final WorkoutPlanEntity _tmpPlan;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final long _tmpUserId;
              _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final FitnessGoal _tmpGoal;
              final String _tmp;
              if (_cursor.isNull(_cursorIndexOfGoal)) {
                _tmp = null;
              } else {
                _tmp = _cursor.getString(_cursorIndexOfGoal);
              }
              _tmpGoal = __dbConverters.toFitnessGoal(_tmp);
              final LocalDate _tmpStartDate;
              final String _tmp_1;
              if (_cursor.isNull(_cursorIndexOfStartDate)) {
                _tmp_1 = null;
              } else {
                _tmp_1 = _cursor.getString(_cursorIndexOfStartDate);
              }
              _tmpStartDate = __dbConverters.toLocalDate(_tmp_1);
              final int _tmpSessionsPerWeek;
              _tmpSessionsPerWeek = _cursor.getInt(_cursorIndexOfSessionsPerWeek);
              final PlanStatus _tmpStatus;
              final String _tmp_2;
              if (_cursor.isNull(_cursorIndexOfStatus)) {
                _tmp_2 = null;
              } else {
                _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
              }
              _tmpStatus = __dbConverters.toPlanStatus(_tmp_2);
              final Instant _tmpCreatedAt;
              final String _tmp_3;
              if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
                _tmp_3 = null;
              } else {
                _tmp_3 = _cursor.getString(_cursorIndexOfCreatedAt);
              }
              _tmpCreatedAt = __dbConverters.toInstant(_tmp_3);
              _tmpPlan = new WorkoutPlanEntity(_tmpId,_tmpUserId,_tmpTitle,_tmpGoal,_tmpStartDate,_tmpSessionsPerWeek,_tmpStatus,_tmpCreatedAt);
              final ArrayList<WorkoutSessionEntity> _tmpSessionsCollection;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpSessionsCollection = _collectionSessions.get(_tmpKey_1);
              _result = new WorkoutPlanWithSessions(_tmpPlan,_tmpSessionsCollection);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getActivePlanWithSessions(final long userId,
      final Continuation<? super WorkoutPlanWithSessions> $completion) {
    final String _sql = "SELECT * FROM workout_plans WHERE userId = ? AND status = 'ACTIVE' ORDER BY createdAt DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, true, _cancellationSignal, new Callable<WorkoutPlanWithSessions>() {
      @Override
      @Nullable
      public WorkoutPlanWithSessions call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "goal");
            final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
            final int _cursorIndexOfSessionsPerWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionsPerWeek");
            final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final LongSparseArray<ArrayList<WorkoutSessionEntity>> _collectionSessions = new LongSparseArray<ArrayList<WorkoutSessionEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfId);
              if (!_collectionSessions.containsKey(_tmpKey)) {
                _collectionSessions.put(_tmpKey, new ArrayList<WorkoutSessionEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipworkoutSessionsAscomAthallahHybridfitDataLocalEntityWorkoutSessionEntity(_collectionSessions);
            final WorkoutPlanWithSessions _result;
            if (_cursor.moveToFirst()) {
              final WorkoutPlanEntity _tmpPlan;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final long _tmpUserId;
              _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final FitnessGoal _tmpGoal;
              final String _tmp;
              if (_cursor.isNull(_cursorIndexOfGoal)) {
                _tmp = null;
              } else {
                _tmp = _cursor.getString(_cursorIndexOfGoal);
              }
              _tmpGoal = __dbConverters.toFitnessGoal(_tmp);
              final LocalDate _tmpStartDate;
              final String _tmp_1;
              if (_cursor.isNull(_cursorIndexOfStartDate)) {
                _tmp_1 = null;
              } else {
                _tmp_1 = _cursor.getString(_cursorIndexOfStartDate);
              }
              _tmpStartDate = __dbConverters.toLocalDate(_tmp_1);
              final int _tmpSessionsPerWeek;
              _tmpSessionsPerWeek = _cursor.getInt(_cursorIndexOfSessionsPerWeek);
              final PlanStatus _tmpStatus;
              final String _tmp_2;
              if (_cursor.isNull(_cursorIndexOfStatus)) {
                _tmp_2 = null;
              } else {
                _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
              }
              _tmpStatus = __dbConverters.toPlanStatus(_tmp_2);
              final Instant _tmpCreatedAt;
              final String _tmp_3;
              if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
                _tmp_3 = null;
              } else {
                _tmp_3 = _cursor.getString(_cursorIndexOfCreatedAt);
              }
              _tmpCreatedAt = __dbConverters.toInstant(_tmp_3);
              _tmpPlan = new WorkoutPlanEntity(_tmpId,_tmpUserId,_tmpTitle,_tmpGoal,_tmpStartDate,_tmpSessionsPerWeek,_tmpStatus,_tmpCreatedAt);
              final ArrayList<WorkoutSessionEntity> _tmpSessionsCollection;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpSessionsCollection = _collectionSessions.get(_tmpKey_1);
              _result = new WorkoutPlanWithSessions(_tmpPlan,_tmpSessionsCollection);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
            _statement.release();
          }
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipworkoutSessionsAscomAthallahHybridfitDataLocalEntityWorkoutSessionEntity(
      @NonNull final LongSparseArray<ArrayList<WorkoutSessionEntity>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshipworkoutSessionsAscomAthallahHybridfitDataLocalEntityWorkoutSessionEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`planId`,`dayOfWeek`,`title`,`category`,`focusArea`,`targetDurationMinutes`,`targetDistanceKm`,`targetSets`,`targetReps`,`restSeconds`,`guidance`,`orderInDay`,`plannerState`,`sessionNotes` FROM `workout_sessions` WHERE `planId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "planId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfPlanId = 1;
      final int _cursorIndexOfDayOfWeek = 2;
      final int _cursorIndexOfTitle = 3;
      final int _cursorIndexOfCategory = 4;
      final int _cursorIndexOfFocusArea = 5;
      final int _cursorIndexOfTargetDurationMinutes = 6;
      final int _cursorIndexOfTargetDistanceKm = 7;
      final int _cursorIndexOfTargetSets = 8;
      final int _cursorIndexOfTargetReps = 9;
      final int _cursorIndexOfRestSeconds = 10;
      final int _cursorIndexOfGuidance = 11;
      final int _cursorIndexOfOrderInDay = 12;
      final int _cursorIndexOfPlannerState = 13;
      final int _cursorIndexOfSessionNotes = 14;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        final ArrayList<WorkoutSessionEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final WorkoutSessionEntity _item_1;
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          final long _tmpPlanId;
          _tmpPlanId = _cursor.getLong(_cursorIndexOfPlanId);
          final DayOfWeek _tmpDayOfWeek;
          final String _tmp;
          if (_cursor.isNull(_cursorIndexOfDayOfWeek)) {
            _tmp = null;
          } else {
            _tmp = _cursor.getString(_cursorIndexOfDayOfWeek);
          }
          _tmpDayOfWeek = __dbConverters.toDayOfWeek(_tmp);
          final String _tmpTitle;
          if (_cursor.isNull(_cursorIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
          }
          final WorkoutCategory _tmpCategory;
          final String _tmp_1;
          if (_cursor.isNull(_cursorIndexOfCategory)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
          }
          _tmpCategory = __dbConverters.toWorkoutCategory(_tmp_1);
          final String _tmpFocusArea;
          if (_cursor.isNull(_cursorIndexOfFocusArea)) {
            _tmpFocusArea = null;
          } else {
            _tmpFocusArea = _cursor.getString(_cursorIndexOfFocusArea);
          }
          final int _tmpTargetDurationMinutes;
          _tmpTargetDurationMinutes = _cursor.getInt(_cursorIndexOfTargetDurationMinutes);
          final Double _tmpTargetDistanceKm;
          if (_cursor.isNull(_cursorIndexOfTargetDistanceKm)) {
            _tmpTargetDistanceKm = null;
          } else {
            _tmpTargetDistanceKm = _cursor.getDouble(_cursorIndexOfTargetDistanceKm);
          }
          final Integer _tmpTargetSets;
          if (_cursor.isNull(_cursorIndexOfTargetSets)) {
            _tmpTargetSets = null;
          } else {
            _tmpTargetSets = _cursor.getInt(_cursorIndexOfTargetSets);
          }
          final Integer _tmpTargetReps;
          if (_cursor.isNull(_cursorIndexOfTargetReps)) {
            _tmpTargetReps = null;
          } else {
            _tmpTargetReps = _cursor.getInt(_cursorIndexOfTargetReps);
          }
          final int _tmpRestSeconds;
          _tmpRestSeconds = _cursor.getInt(_cursorIndexOfRestSeconds);
          final String _tmpGuidance;
          if (_cursor.isNull(_cursorIndexOfGuidance)) {
            _tmpGuidance = null;
          } else {
            _tmpGuidance = _cursor.getString(_cursorIndexOfGuidance);
          }
          final int _tmpOrderInDay;
          _tmpOrderInDay = _cursor.getInt(_cursorIndexOfOrderInDay);
          final SessionPlannerState _tmpPlannerState;
          final String _tmp_2;
          if (_cursor.isNull(_cursorIndexOfPlannerState)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _cursor.getString(_cursorIndexOfPlannerState);
          }
          _tmpPlannerState = __dbConverters.toSessionPlannerState(_tmp_2);
          final String _tmpSessionNotes;
          if (_cursor.isNull(_cursorIndexOfSessionNotes)) {
            _tmpSessionNotes = null;
          } else {
            _tmpSessionNotes = _cursor.getString(_cursorIndexOfSessionNotes);
          }
          _item_1 = new WorkoutSessionEntity(_tmpId,_tmpPlanId,_tmpDayOfWeek,_tmpTitle,_tmpCategory,_tmpFocusArea,_tmpTargetDurationMinutes,_tmpTargetDistanceKm,_tmpTargetSets,_tmpTargetReps,_tmpRestSeconds,_tmpGuidance,_tmpOrderInDay,_tmpPlannerState,_tmpSessionNotes);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
