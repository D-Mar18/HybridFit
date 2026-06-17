package com.athallah.hybridfit.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.athallah.hybridfit.data.local.entity.StrengthExerciseEntryEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
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
public final class StrengthExerciseEntryDao_Impl implements StrengthExerciseEntryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StrengthExerciseEntryEntity> __insertionAdapterOfStrengthExerciseEntryEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteEntriesForLog;

  public StrengthExerciseEntryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStrengthExerciseEntryEntity = new EntityInsertionAdapter<StrengthExerciseEntryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `strength_exercise_entries` (`id`,`workoutLogId`,`exerciseName`,`actualSets`,`actualReps`,`actualWeightKg`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StrengthExerciseEntryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getWorkoutLogId());
        if (entity.getExerciseName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getExerciseName());
        }
        statement.bindLong(4, entity.getActualSets());
        statement.bindLong(5, entity.getActualReps());
        statement.bindDouble(6, entity.getActualWeightKg());
        if (entity.getNotes() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getNotes());
        }
      }
    };
    this.__preparedStmtOfDeleteEntriesForLog = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM strength_exercise_entries WHERE workoutLogId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertEntries(final List<StrengthExerciseEntryEntity> entries,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfStrengthExerciseEntryEntity.insert(entries);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEntriesForLog(final long workoutLogId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteEntriesForLog.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, workoutLogId);
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
          __preparedStmtOfDeleteEntriesForLog.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getEntriesForLog(final long workoutLogId,
      final Continuation<? super List<StrengthExerciseEntryEntity>> $completion) {
    final String _sql = "SELECT * FROM strength_exercise_entries WHERE workoutLogId = ? ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workoutLogId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<StrengthExerciseEntryEntity>>() {
      @Override
      @NonNull
      public List<StrengthExerciseEntryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkoutLogId = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutLogId");
          final int _cursorIndexOfExerciseName = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseName");
          final int _cursorIndexOfActualSets = CursorUtil.getColumnIndexOrThrow(_cursor, "actualSets");
          final int _cursorIndexOfActualReps = CursorUtil.getColumnIndexOrThrow(_cursor, "actualReps");
          final int _cursorIndexOfActualWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "actualWeightKg");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<StrengthExerciseEntryEntity> _result = new ArrayList<StrengthExerciseEntryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StrengthExerciseEntryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkoutLogId;
            _tmpWorkoutLogId = _cursor.getLong(_cursorIndexOfWorkoutLogId);
            final String _tmpExerciseName;
            if (_cursor.isNull(_cursorIndexOfExerciseName)) {
              _tmpExerciseName = null;
            } else {
              _tmpExerciseName = _cursor.getString(_cursorIndexOfExerciseName);
            }
            final int _tmpActualSets;
            _tmpActualSets = _cursor.getInt(_cursorIndexOfActualSets);
            final int _tmpActualReps;
            _tmpActualReps = _cursor.getInt(_cursorIndexOfActualReps);
            final double _tmpActualWeightKg;
            _tmpActualWeightKg = _cursor.getDouble(_cursorIndexOfActualWeightKg);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new StrengthExerciseEntryEntity(_tmpId,_tmpWorkoutLogId,_tmpExerciseName,_tmpActualSets,_tmpActualReps,_tmpActualWeightKg,_tmpNotes);
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
  public Flow<List<StrengthExerciseEntryEntity>> observeAllEntries(final long userId) {
    final String _sql = "SELECT strength_exercise_entries.* FROM strength_exercise_entries INNER JOIN workout_logs ON workout_logs.id = strength_exercise_entries.workoutLogId INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId WHERE workout_plans.userId = ? ORDER BY strength_exercise_entries.workoutLogId DESC, strength_exercise_entries.id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"strength_exercise_entries",
        "workout_logs", "workout_sessions",
        "workout_plans"}, new Callable<List<StrengthExerciseEntryEntity>>() {
      @Override
      @NonNull
      public List<StrengthExerciseEntryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkoutLogId = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutLogId");
          final int _cursorIndexOfExerciseName = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseName");
          final int _cursorIndexOfActualSets = CursorUtil.getColumnIndexOrThrow(_cursor, "actualSets");
          final int _cursorIndexOfActualReps = CursorUtil.getColumnIndexOrThrow(_cursor, "actualReps");
          final int _cursorIndexOfActualWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "actualWeightKg");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<StrengthExerciseEntryEntity> _result = new ArrayList<StrengthExerciseEntryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StrengthExerciseEntryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkoutLogId;
            _tmpWorkoutLogId = _cursor.getLong(_cursorIndexOfWorkoutLogId);
            final String _tmpExerciseName;
            if (_cursor.isNull(_cursorIndexOfExerciseName)) {
              _tmpExerciseName = null;
            } else {
              _tmpExerciseName = _cursor.getString(_cursorIndexOfExerciseName);
            }
            final int _tmpActualSets;
            _tmpActualSets = _cursor.getInt(_cursorIndexOfActualSets);
            final int _tmpActualReps;
            _tmpActualReps = _cursor.getInt(_cursorIndexOfActualReps);
            final double _tmpActualWeightKg;
            _tmpActualWeightKg = _cursor.getDouble(_cursorIndexOfActualWeightKg);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new StrengthExerciseEntryEntity(_tmpId,_tmpWorkoutLogId,_tmpExerciseName,_tmpActualSets,_tmpActualReps,_tmpActualWeightKg,_tmpNotes);
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
  public Object getAllEntries(final long userId,
      final Continuation<? super List<StrengthExerciseEntryEntity>> $completion) {
    final String _sql = "SELECT strength_exercise_entries.* FROM strength_exercise_entries INNER JOIN workout_logs ON workout_logs.id = strength_exercise_entries.workoutLogId INNER JOIN workout_sessions ON workout_sessions.id = workout_logs.sessionId INNER JOIN workout_plans ON workout_plans.id = workout_sessions.planId WHERE workout_plans.userId = ? ORDER BY strength_exercise_entries.workoutLogId DESC, strength_exercise_entries.id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<StrengthExerciseEntryEntity>>() {
      @Override
      @NonNull
      public List<StrengthExerciseEntryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkoutLogId = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutLogId");
          final int _cursorIndexOfExerciseName = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseName");
          final int _cursorIndexOfActualSets = CursorUtil.getColumnIndexOrThrow(_cursor, "actualSets");
          final int _cursorIndexOfActualReps = CursorUtil.getColumnIndexOrThrow(_cursor, "actualReps");
          final int _cursorIndexOfActualWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "actualWeightKg");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<StrengthExerciseEntryEntity> _result = new ArrayList<StrengthExerciseEntryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StrengthExerciseEntryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkoutLogId;
            _tmpWorkoutLogId = _cursor.getLong(_cursorIndexOfWorkoutLogId);
            final String _tmpExerciseName;
            if (_cursor.isNull(_cursorIndexOfExerciseName)) {
              _tmpExerciseName = null;
            } else {
              _tmpExerciseName = _cursor.getString(_cursorIndexOfExerciseName);
            }
            final int _tmpActualSets;
            _tmpActualSets = _cursor.getInt(_cursorIndexOfActualSets);
            final int _tmpActualReps;
            _tmpActualReps = _cursor.getInt(_cursorIndexOfActualReps);
            final double _tmpActualWeightKg;
            _tmpActualWeightKg = _cursor.getDouble(_cursorIndexOfActualWeightKg);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new StrengthExerciseEntryEntity(_tmpId,_tmpWorkoutLogId,_tmpExerciseName,_tmpActualSets,_tmpActualReps,_tmpActualWeightKg,_tmpNotes);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
