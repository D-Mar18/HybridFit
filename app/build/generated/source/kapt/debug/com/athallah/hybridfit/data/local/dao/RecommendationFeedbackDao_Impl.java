package com.athallah.hybridfit.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.athallah.hybridfit.data.local.converter.DbConverters;
import com.athallah.hybridfit.data.local.entity.RecommendationFeedbackEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RecommendationFeedbackDao_Impl implements RecommendationFeedbackDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RecommendationFeedbackEntity> __insertionAdapterOfRecommendationFeedbackEntity;

  private final DbConverters __dbConverters = new DbConverters();

  public RecommendationFeedbackDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecommendationFeedbackEntity = new EntityInsertionAdapter<RecommendationFeedbackEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `recommendation_feedback` (`id`,`recommendationId`,`wasHelpful`,`rating`,`notes`,`recordedAt`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecommendationFeedbackEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getRecommendationId());
        final int _tmp = entity.getWasHelpful() ? 1 : 0;
        statement.bindLong(3, _tmp);
        statement.bindLong(4, entity.getRating());
        if (entity.getNotes() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNotes());
        }
        final String _tmp_1 = __dbConverters.fromInstant(entity.getRecordedAt());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_1);
        }
      }
    };
  }

  @Override
  public Object insertFeedback(final RecommendationFeedbackEntity feedback,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRecommendationFeedbackEntity.insertAndReturnId(feedback);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RecommendationFeedbackEntity>> observeAllFeedback(final long userId) {
    final String _sql = "SELECT recommendation_feedback.* FROM recommendation_feedback INNER JOIN recommendations ON recommendations.id = recommendation_feedback.recommendationId WHERE recommendations.userId = ? ORDER BY recommendation_feedback.recordedAt DESC, recommendation_feedback.id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recommendation_feedback",
        "recommendations"}, new Callable<List<RecommendationFeedbackEntity>>() {
      @Override
      @NonNull
      public List<RecommendationFeedbackEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRecommendationId = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendationId");
          final int _cursorIndexOfWasHelpful = CursorUtil.getColumnIndexOrThrow(_cursor, "wasHelpful");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfRecordedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "recordedAt");
          final List<RecommendationFeedbackEntity> _result = new ArrayList<RecommendationFeedbackEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecommendationFeedbackEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpRecommendationId;
            _tmpRecommendationId = _cursor.getLong(_cursorIndexOfRecommendationId);
            final boolean _tmpWasHelpful;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfWasHelpful);
            _tmpWasHelpful = _tmp != 0;
            final int _tmpRating;
            _tmpRating = _cursor.getInt(_cursorIndexOfRating);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpRecordedAt;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRecordedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRecordedAt);
            }
            _tmpRecordedAt = __dbConverters.toInstant(_tmp_1);
            _item = new RecommendationFeedbackEntity(_tmpId,_tmpRecommendationId,_tmpWasHelpful,_tmpRating,_tmpNotes,_tmpRecordedAt);
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
  public Object getAllFeedback(final long userId,
      final Continuation<? super List<RecommendationFeedbackEntity>> $completion) {
    final String _sql = "SELECT recommendation_feedback.* FROM recommendation_feedback INNER JOIN recommendations ON recommendations.id = recommendation_feedback.recommendationId WHERE recommendations.userId = ? ORDER BY recommendation_feedback.recordedAt DESC, recommendation_feedback.id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<RecommendationFeedbackEntity>>() {
      @Override
      @NonNull
      public List<RecommendationFeedbackEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRecommendationId = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendationId");
          final int _cursorIndexOfWasHelpful = CursorUtil.getColumnIndexOrThrow(_cursor, "wasHelpful");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfRecordedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "recordedAt");
          final List<RecommendationFeedbackEntity> _result = new ArrayList<RecommendationFeedbackEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecommendationFeedbackEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpRecommendationId;
            _tmpRecommendationId = _cursor.getLong(_cursorIndexOfRecommendationId);
            final boolean _tmpWasHelpful;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfWasHelpful);
            _tmpWasHelpful = _tmp != 0;
            final int _tmpRating;
            _tmpRating = _cursor.getInt(_cursorIndexOfRating);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpRecordedAt;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRecordedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRecordedAt);
            }
            _tmpRecordedAt = __dbConverters.toInstant(_tmp_1);
            _item = new RecommendationFeedbackEntity(_tmpId,_tmpRecommendationId,_tmpWasHelpful,_tmpRating,_tmpNotes,_tmpRecordedAt);
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
