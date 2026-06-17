package com.athallah.hybridfit.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.athallah.hybridfit.data.local.converter.DbConverters;
import com.athallah.hybridfit.data.local.entity.WorkoutLogEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
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
public final class WorkoutLogDao_Impl implements WorkoutLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WorkoutLogEntity> __insertionAdapterOfWorkoutLogEntity;

  private final DbConverters __dbConverters = new DbConverters();

  private final EntityDeletionOrUpdateAdapter<WorkoutLogEntity> __updateAdapterOfWorkoutLogEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteLog;

  public WorkoutLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorkoutLogEntity = new EntityInsertionAdapter<WorkoutLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `workout_logs` (`id`,`sessionId`,`performedOn`,`actualDurationMinutes`,`actualDistanceKm`,`averagePaceSecondsPerKm`,`completedSets`,`completedReps`,`completionPercent`,`exertionScore`,`notes`,`wasCompleted`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutLogEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSessionId());
        final String _tmp = __dbConverters.fromLocalDate(entity.getPerformedOn());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        statement.bindLong(4, entity.getActualDurationMinutes());
        if (entity.getActualDistanceKm() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getActualDistanceKm());
        }
        if (entity.getAveragePaceSecondsPerKm() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getAveragePaceSecondsPerKm());
        }
        if (entity.getCompletedSets() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getCompletedSets());
        }
        if (entity.getCompletedReps() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getCompletedReps());
        }
        statement.bindLong(9, entity.getCompletionPercent());
        statement.bindLong(10, entity.getExertionScore());
        if (entity.getNotes() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getNotes());
        }
        final int _tmp_1 = entity.getWasCompleted() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
      }
    };
    this.__updateAdapterOfWorkoutLogEntity = new EntityDeletionOrUpdateAdapter<WorkoutLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `workout_logs` SET `id` = ?,`sessionId` = ?,`performedOn` = ?,`actualDurationMinutes` = ?,`actualDistanceKm` = ?,`averagePaceSecondsPerKm` = ?,`completedSets` = ?,`completedReps` = ?,`completionPercent` = ?,`exertionScore` = ?,`notes` = ?,`wasCompleted` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutLogEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSessionId());
        final String _tmp = __dbConverters.fromLocalDate(entity.getPerformedOn());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        statement.bindLong(4, entity.getActualDurationMinutes());
        if (entity.getActualDistanceKm() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getActualDistanceKm());
        }
        if (entity.getAveragePaceSecondsPerKm() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getAveragePaceSecondsPerKm());
        }
        if (entity.getCompletedSets() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getCompletedSets());
        }
        if (entity.getCompletedReps() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getCompletedReps());
        }
        statement.bindLong(9, entity.getCompletionPercent());
        statement.bindLong(10, entity.getExertionScore());
        if (entity.getNotes() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getNotes());
        }
        final int _tmp_1 = entity.getWasCompleted() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        statement.bindLong(13, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteLog = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM workout_logs WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertLog(final WorkoutLogEntity log,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWorkoutLogEntity.insertAndReturnId(log);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertLogs(final List<WorkoutLogEntity> logs,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWorkoutLogEntity.insert(logs);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLog(final WorkoutLogEntity log,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkoutLogEntity.handle(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteLog(final long logId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteLog.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, logId);
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
          __preparedStmtOfDeleteLog.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<WorkoutLogEntity>> observeAllLogs(final long userId) {
    final String _sql = "SELECT workout_logs.* FROM workout_logs INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId WHERE workout_plans.userId = ? ORDER BY workout_logs.performedOn DESC, workout_logs.id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"workout_logs", "workout_sessions",
        "workout_plans"}, new Callable<List<WorkoutLogEntity>>() {
      @Override
      @NonNull
      public List<WorkoutLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfPerformedOn = CursorUtil.getColumnIndexOrThrow(_cursor, "performedOn");
          final int _cursorIndexOfActualDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "actualDurationMinutes");
          final int _cursorIndexOfActualDistanceKm = CursorUtil.getColumnIndexOrThrow(_cursor, "actualDistanceKm");
          final int _cursorIndexOfAveragePaceSecondsPerKm = CursorUtil.getColumnIndexOrThrow(_cursor, "averagePaceSecondsPerKm");
          final int _cursorIndexOfCompletedSets = CursorUtil.getColumnIndexOrThrow(_cursor, "completedSets");
          final int _cursorIndexOfCompletedReps = CursorUtil.getColumnIndexOrThrow(_cursor, "completedReps");
          final int _cursorIndexOfCompletionPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "completionPercent");
          final int _cursorIndexOfExertionScore = CursorUtil.getColumnIndexOrThrow(_cursor, "exertionScore");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfWasCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "wasCompleted");
          final List<WorkoutLogEntity> _result = new ArrayList<WorkoutLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WorkoutLogEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final LocalDate _tmpPerformedOn;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPerformedOn)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPerformedOn);
            }
            _tmpPerformedOn = __dbConverters.toLocalDate(_tmp);
            final int _tmpActualDurationMinutes;
            _tmpActualDurationMinutes = _cursor.getInt(_cursorIndexOfActualDurationMinutes);
            final Double _tmpActualDistanceKm;
            if (_cursor.isNull(_cursorIndexOfActualDistanceKm)) {
              _tmpActualDistanceKm = null;
            } else {
              _tmpActualDistanceKm = _cursor.getDouble(_cursorIndexOfActualDistanceKm);
            }
            final Integer _tmpAveragePaceSecondsPerKm;
            if (_cursor.isNull(_cursorIndexOfAveragePaceSecondsPerKm)) {
              _tmpAveragePaceSecondsPerKm = null;
            } else {
              _tmpAveragePaceSecondsPerKm = _cursor.getInt(_cursorIndexOfAveragePaceSecondsPerKm);
            }
            final Integer _tmpCompletedSets;
            if (_cursor.isNull(_cursorIndexOfCompletedSets)) {
              _tmpCompletedSets = null;
            } else {
              _tmpCompletedSets = _cursor.getInt(_cursorIndexOfCompletedSets);
            }
            final Integer _tmpCompletedReps;
            if (_cursor.isNull(_cursorIndexOfCompletedReps)) {
              _tmpCompletedReps = null;
            } else {
              _tmpCompletedReps = _cursor.getInt(_cursorIndexOfCompletedReps);
            }
            final int _tmpCompletionPercent;
            _tmpCompletionPercent = _cursor.getInt(_cursorIndexOfCompletionPercent);
            final int _tmpExertionScore;
            _tmpExertionScore = _cursor.getInt(_cursorIndexOfExertionScore);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpWasCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfWasCompleted);
            _tmpWasCompleted = _tmp_1 != 0;
            _item = new WorkoutLogEntity(_tmpId,_tmpSessionId,_tmpPerformedOn,_tmpActualDurationMinutes,_tmpActualDistanceKm,_tmpAveragePaceSecondsPerKm,_tmpCompletedSets,_tmpCompletedReps,_tmpCompletionPercent,_tmpExertionScore,_tmpNotes,_tmpWasCompleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAllLogs(final long userId,
      final Continuation<? super List<WorkoutLogEntity>> $completion) {
    final String _sql = "SELECT workout_logs.* FROM workout_logs INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId WHERE workout_plans.userId = ? ORDER BY workout_logs.performedOn DESC, workout_logs.id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<WorkoutLogEntity>>() {
      @Override
      @NonNull
      public List<WorkoutLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfPerformedOn = CursorUtil.getColumnIndexOrThrow(_cursor, "performedOn");
          final int _cursorIndexOfActualDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "actualDurationMinutes");
          final int _cursorIndexOfActualDistanceKm = CursorUtil.getColumnIndexOrThrow(_cursor, "actualDistanceKm");
          final int _cursorIndexOfAveragePaceSecondsPerKm = CursorUtil.getColumnIndexOrThrow(_cursor, "averagePaceSecondsPerKm");
          final int _cursorIndexOfCompletedSets = CursorUtil.getColumnIndexOrThrow(_cursor, "completedSets");
          final int _cursorIndexOfCompletedReps = CursorUtil.getColumnIndexOrThrow(_cursor, "completedReps");
          final int _cursorIndexOfCompletionPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "completionPercent");
          final int _cursorIndexOfExertionScore = CursorUtil.getColumnIndexOrThrow(_cursor, "exertionScore");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfWasCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "wasCompleted");
          final List<WorkoutLogEntity> _result = new ArrayList<WorkoutLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WorkoutLogEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final LocalDate _tmpPerformedOn;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPerformedOn)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPerformedOn);
            }
            _tmpPerformedOn = __dbConverters.toLocalDate(_tmp);
            final int _tmpActualDurationMinutes;
            _tmpActualDurationMinutes = _cursor.getInt(_cursorIndexOfActualDurationMinutes);
            final Double _tmpActualDistanceKm;
            if (_cursor.isNull(_cursorIndexOfActualDistanceKm)) {
              _tmpActualDistanceKm = null;
            } else {
              _tmpActualDistanceKm = _cursor.getDouble(_cursorIndexOfActualDistanceKm);
            }
            final Integer _tmpAveragePaceSecondsPerKm;
            if (_cursor.isNull(_cursorIndexOfAveragePaceSecondsPerKm)) {
              _tmpAveragePaceSecondsPerKm = null;
            } else {
              _tmpAveragePaceSecondsPerKm = _cursor.getInt(_cursorIndexOfAveragePaceSecondsPerKm);
            }
            final Integer _tmpCompletedSets;
            if (_cursor.isNull(_cursorIndexOfCompletedSets)) {
              _tmpCompletedSets = null;
            } else {
              _tmpCompletedSets = _cursor.getInt(_cursorIndexOfCompletedSets);
            }
            final Integer _tmpCompletedReps;
            if (_cursor.isNull(_cursorIndexOfCompletedReps)) {
              _tmpCompletedReps = null;
            } else {
              _tmpCompletedReps = _cursor.getInt(_cursorIndexOfCompletedReps);
            }
            final int _tmpCompletionPercent;
            _tmpCompletionPercent = _cursor.getInt(_cursorIndexOfCompletionPercent);
            final int _tmpExertionScore;
            _tmpExertionScore = _cursor.getInt(_cursorIndexOfExertionScore);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpWasCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfWasCompleted);
            _tmpWasCompleted = _tmp_1 != 0;
            _item = new WorkoutLogEntity(_tmpId,_tmpSessionId,_tmpPerformedOn,_tmpActualDurationMinutes,_tmpActualDistanceKm,_tmpAveragePaceSecondsPerKm,_tmpCompletedSets,_tmpCompletedReps,_tmpCompletionPercent,_tmpExertionScore,_tmpNotes,_tmpWasCompleted);
            _result.add(_item);
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
  public Object getLogById(final long userId, final long logId,
      final Continuation<? super WorkoutLogEntity> $completion) {
    final String _sql = "SELECT workout_logs.* FROM workout_logs INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId WHERE workout_plans.userId = ? AND workout_logs.id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, logId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WorkoutLogEntity>() {
      @Override
      @Nullable
      public WorkoutLogEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfPerformedOn = CursorUtil.getColumnIndexOrThrow(_cursor, "performedOn");
          final int _cursorIndexOfActualDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "actualDurationMinutes");
          final int _cursorIndexOfActualDistanceKm = CursorUtil.getColumnIndexOrThrow(_cursor, "actualDistanceKm");
          final int _cursorIndexOfAveragePaceSecondsPerKm = CursorUtil.getColumnIndexOrThrow(_cursor, "averagePaceSecondsPerKm");
          final int _cursorIndexOfCompletedSets = CursorUtil.getColumnIndexOrThrow(_cursor, "completedSets");
          final int _cursorIndexOfCompletedReps = CursorUtil.getColumnIndexOrThrow(_cursor, "completedReps");
          final int _cursorIndexOfCompletionPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "completionPercent");
          final int _cursorIndexOfExertionScore = CursorUtil.getColumnIndexOrThrow(_cursor, "exertionScore");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfWasCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "wasCompleted");
          final WorkoutLogEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final LocalDate _tmpPerformedOn;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPerformedOn)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPerformedOn);
            }
            _tmpPerformedOn = __dbConverters.toLocalDate(_tmp);
            final int _tmpActualDurationMinutes;
            _tmpActualDurationMinutes = _cursor.getInt(_cursorIndexOfActualDurationMinutes);
            final Double _tmpActualDistanceKm;
            if (_cursor.isNull(_cursorIndexOfActualDistanceKm)) {
              _tmpActualDistanceKm = null;
            } else {
              _tmpActualDistanceKm = _cursor.getDouble(_cursorIndexOfActualDistanceKm);
            }
            final Integer _tmpAveragePaceSecondsPerKm;
            if (_cursor.isNull(_cursorIndexOfAveragePaceSecondsPerKm)) {
              _tmpAveragePaceSecondsPerKm = null;
            } else {
              _tmpAveragePaceSecondsPerKm = _cursor.getInt(_cursorIndexOfAveragePaceSecondsPerKm);
            }
            final Integer _tmpCompletedSets;
            if (_cursor.isNull(_cursorIndexOfCompletedSets)) {
              _tmpCompletedSets = null;
            } else {
              _tmpCompletedSets = _cursor.getInt(_cursorIndexOfCompletedSets);
            }
            final Integer _tmpCompletedReps;
            if (_cursor.isNull(_cursorIndexOfCompletedReps)) {
              _tmpCompletedReps = null;
            } else {
              _tmpCompletedReps = _cursor.getInt(_cursorIndexOfCompletedReps);
            }
            final int _tmpCompletionPercent;
            _tmpCompletionPercent = _cursor.getInt(_cursorIndexOfCompletionPercent);
            final int _tmpExertionScore;
            _tmpExertionScore = _cursor.getInt(_cursorIndexOfExertionScore);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpWasCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfWasCompleted);
            _tmpWasCompleted = _tmp_1 != 0;
            _result = new WorkoutLogEntity(_tmpId,_tmpSessionId,_tmpPerformedOn,_tmpActualDurationMinutes,_tmpActualDistanceKm,_tmpAveragePaceSecondsPerKm,_tmpCompletedSets,_tmpCompletedReps,_tmpCompletionPercent,_tmpExertionScore,_tmpNotes,_tmpWasCompleted);
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
  public Object countLogsForSession(final long sessionId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM workout_logs WHERE sessionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
