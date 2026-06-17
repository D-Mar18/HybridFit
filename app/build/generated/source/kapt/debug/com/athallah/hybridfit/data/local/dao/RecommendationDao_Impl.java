package com.athallah.hybridfit.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.athallah.hybridfit.data.local.converter.DbConverters;
import com.athallah.hybridfit.data.local.entity.RecommendationEntity;
import com.athallah.hybridfit.domain.model.RecommendationAction;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RecommendationDao_Impl implements RecommendationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RecommendationEntity> __insertionAdapterOfRecommendationEntity;

  private final DbConverters __dbConverters = new DbConverters();

  public RecommendationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecommendationEntity = new EntityInsertionAdapter<RecommendationEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `recommendations` (`id`,`userId`,`createdAt`,`title`,`summary`,`action`,`targetDurationMinutes`,`targetDistanceKm`,`targetSets`,`targetReps`,`restSeconds`,`rationale`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecommendationEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        final String _tmp = __dbConverters.fromInstant(entity.getCreatedAt());
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
        if (entity.getSummary() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSummary());
        }
        final String _tmp_1 = __dbConverters.fromRecommendationAction(entity.getAction());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_1);
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
        if (entity.getRationale() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getRationale());
        }
      }
    };
  }

  @Override
  public Object insertRecommendation(final RecommendationEntity recommendation,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRecommendationEntity.insertAndReturnId(recommendation);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<RecommendationEntity> observeLatestRecommendation(final long userId) {
    final String _sql = "SELECT * FROM recommendations WHERE userId = ? ORDER BY createdAt DESC, id DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recommendations"}, new Callable<RecommendationEntity>() {
      @Override
      @Nullable
      public RecommendationEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfSummary = CursorUtil.getColumnIndexOrThrow(_cursor, "summary");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfTargetDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDurationMinutes");
          final int _cursorIndexOfTargetDistanceKm = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDistanceKm");
          final int _cursorIndexOfTargetSets = CursorUtil.getColumnIndexOrThrow(_cursor, "targetSets");
          final int _cursorIndexOfTargetReps = CursorUtil.getColumnIndexOrThrow(_cursor, "targetReps");
          final int _cursorIndexOfRestSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "restSeconds");
          final int _cursorIndexOfRationale = CursorUtil.getColumnIndexOrThrow(_cursor, "rationale");
          final RecommendationEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final Instant _tmpCreatedAt;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dbConverters.toInstant(_tmp);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpSummary;
            if (_cursor.isNull(_cursorIndexOfSummary)) {
              _tmpSummary = null;
            } else {
              _tmpSummary = _cursor.getString(_cursorIndexOfSummary);
            }
            final RecommendationAction _tmpAction;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfAction)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfAction);
            }
            _tmpAction = __dbConverters.toRecommendationAction(_tmp_1);
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
            final String _tmpRationale;
            if (_cursor.isNull(_cursorIndexOfRationale)) {
              _tmpRationale = null;
            } else {
              _tmpRationale = _cursor.getString(_cursorIndexOfRationale);
            }
            _result = new RecommendationEntity(_tmpId,_tmpUserId,_tmpCreatedAt,_tmpTitle,_tmpSummary,_tmpAction,_tmpTargetDurationMinutes,_tmpTargetDistanceKm,_tmpTargetSets,_tmpTargetReps,_tmpRestSeconds,_tmpRationale);
          } else {
            _result = null;
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
  public Object getLatestRecommendation(final long userId,
      final Continuation<? super RecommendationEntity> $completion) {
    final String _sql = "SELECT * FROM recommendations WHERE userId = ? ORDER BY createdAt DESC, id DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RecommendationEntity>() {
      @Override
      @Nullable
      public RecommendationEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfSummary = CursorUtil.getColumnIndexOrThrow(_cursor, "summary");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfTargetDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDurationMinutes");
          final int _cursorIndexOfTargetDistanceKm = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDistanceKm");
          final int _cursorIndexOfTargetSets = CursorUtil.getColumnIndexOrThrow(_cursor, "targetSets");
          final int _cursorIndexOfTargetReps = CursorUtil.getColumnIndexOrThrow(_cursor, "targetReps");
          final int _cursorIndexOfRestSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "restSeconds");
          final int _cursorIndexOfRationale = CursorUtil.getColumnIndexOrThrow(_cursor, "rationale");
          final RecommendationEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final Instant _tmpCreatedAt;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dbConverters.toInstant(_tmp);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpSummary;
            if (_cursor.isNull(_cursorIndexOfSummary)) {
              _tmpSummary = null;
            } else {
              _tmpSummary = _cursor.getString(_cursorIndexOfSummary);
            }
            final RecommendationAction _tmpAction;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfAction)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfAction);
            }
            _tmpAction = __dbConverters.toRecommendationAction(_tmp_1);
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
            final String _tmpRationale;
            if (_cursor.isNull(_cursorIndexOfRationale)) {
              _tmpRationale = null;
            } else {
              _tmpRationale = _cursor.getString(_cursorIndexOfRationale);
            }
            _result = new RecommendationEntity(_tmpId,_tmpUserId,_tmpCreatedAt,_tmpTitle,_tmpSummary,_tmpAction,_tmpTargetDurationMinutes,_tmpTargetDistanceKm,_tmpTargetSets,_tmpTargetReps,_tmpRestSeconds,_tmpRationale);
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
