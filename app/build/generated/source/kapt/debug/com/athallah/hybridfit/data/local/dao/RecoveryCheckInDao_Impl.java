package com.athallah.hybridfit.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.athallah.hybridfit.data.local.converter.DbConverters;
import com.athallah.hybridfit.data.local.entity.RecoveryCheckInEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class RecoveryCheckInDao_Impl implements RecoveryCheckInDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RecoveryCheckInEntity> __insertionAdapterOfRecoveryCheckInEntity;

  private final DbConverters __dbConverters = new DbConverters();

  private final SharedSQLiteStatement __preparedStmtOfDeleteByDate;

  public RecoveryCheckInDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecoveryCheckInEntity = new EntityInsertionAdapter<RecoveryCheckInEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `recovery_check_ins` (`id`,`userId`,`recordedOn`,`sleepHours`,`recoveryScore`,`energyLevel`,`sorenessLevel`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecoveryCheckInEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        final String _tmp = __dbConverters.fromLocalDate(entity.getRecordedOn());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        statement.bindDouble(4, entity.getSleepHours());
        statement.bindLong(5, entity.getRecoveryScore());
        statement.bindLong(6, entity.getEnergyLevel());
        statement.bindLong(7, entity.getSorenessLevel());
        if (entity.getNotes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNotes());
        }
      }
    };
    this.__preparedStmtOfDeleteByDate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM recovery_check_ins WHERE userId = ? AND recordedOn = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertRecovery(final RecoveryCheckInEntity checkIn,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRecoveryCheckInEntity.insertAndReturnId(checkIn);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByDate(final long userId, final LocalDate recordedOn,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByDate.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        final String _tmp = __dbConverters.fromLocalDate(recordedOn);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, _tmp);
        }
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
          __preparedStmtOfDeleteByDate.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RecoveryCheckInEntity>> observeAllRecovery(final long userId) {
    final String _sql = "SELECT * FROM recovery_check_ins WHERE userId = ? ORDER BY recordedOn DESC, id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recovery_check_ins"}, new Callable<List<RecoveryCheckInEntity>>() {
      @Override
      @NonNull
      public List<RecoveryCheckInEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfRecordedOn = CursorUtil.getColumnIndexOrThrow(_cursor, "recordedOn");
          final int _cursorIndexOfSleepHours = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepHours");
          final int _cursorIndexOfRecoveryScore = CursorUtil.getColumnIndexOrThrow(_cursor, "recoveryScore");
          final int _cursorIndexOfEnergyLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "energyLevel");
          final int _cursorIndexOfSorenessLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "sorenessLevel");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<RecoveryCheckInEntity> _result = new ArrayList<RecoveryCheckInEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecoveryCheckInEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final LocalDate _tmpRecordedOn;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRecordedOn)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRecordedOn);
            }
            _tmpRecordedOn = __dbConverters.toLocalDate(_tmp);
            final double _tmpSleepHours;
            _tmpSleepHours = _cursor.getDouble(_cursorIndexOfSleepHours);
            final int _tmpRecoveryScore;
            _tmpRecoveryScore = _cursor.getInt(_cursorIndexOfRecoveryScore);
            final int _tmpEnergyLevel;
            _tmpEnergyLevel = _cursor.getInt(_cursorIndexOfEnergyLevel);
            final int _tmpSorenessLevel;
            _tmpSorenessLevel = _cursor.getInt(_cursorIndexOfSorenessLevel);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new RecoveryCheckInEntity(_tmpId,_tmpUserId,_tmpRecordedOn,_tmpSleepHours,_tmpRecoveryScore,_tmpEnergyLevel,_tmpSorenessLevel,_tmpNotes);
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
  public Object getLatestRecovery(final long userId,
      final Continuation<? super RecoveryCheckInEntity> $completion) {
    final String _sql = "SELECT * FROM recovery_check_ins WHERE userId = ? ORDER BY recordedOn DESC, id DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RecoveryCheckInEntity>() {
      @Override
      @Nullable
      public RecoveryCheckInEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfRecordedOn = CursorUtil.getColumnIndexOrThrow(_cursor, "recordedOn");
          final int _cursorIndexOfSleepHours = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepHours");
          final int _cursorIndexOfRecoveryScore = CursorUtil.getColumnIndexOrThrow(_cursor, "recoveryScore");
          final int _cursorIndexOfEnergyLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "energyLevel");
          final int _cursorIndexOfSorenessLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "sorenessLevel");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final RecoveryCheckInEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final LocalDate _tmpRecordedOn;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRecordedOn)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRecordedOn);
            }
            _tmpRecordedOn = __dbConverters.toLocalDate(_tmp);
            final double _tmpSleepHours;
            _tmpSleepHours = _cursor.getDouble(_cursorIndexOfSleepHours);
            final int _tmpRecoveryScore;
            _tmpRecoveryScore = _cursor.getInt(_cursorIndexOfRecoveryScore);
            final int _tmpEnergyLevel;
            _tmpEnergyLevel = _cursor.getInt(_cursorIndexOfEnergyLevel);
            final int _tmpSorenessLevel;
            _tmpSorenessLevel = _cursor.getInt(_cursorIndexOfSorenessLevel);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _result = new RecoveryCheckInEntity(_tmpId,_tmpUserId,_tmpRecordedOn,_tmpSleepHours,_tmpRecoveryScore,_tmpEnergyLevel,_tmpSorenessLevel,_tmpNotes);
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
