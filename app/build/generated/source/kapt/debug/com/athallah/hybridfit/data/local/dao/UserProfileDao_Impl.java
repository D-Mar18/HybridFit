package com.athallah.hybridfit.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.EntityUpsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.athallah.hybridfit.data.local.converter.DbConverters;
import com.athallah.hybridfit.data.local.entity.UserProfileEntity;
import com.athallah.hybridfit.domain.model.FitnessGoal;
import com.athallah.hybridfit.domain.model.FitnessLevel;
import com.athallah.hybridfit.domain.model.WorkoutCategory;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserProfileDao_Impl implements UserProfileDao {
  private final RoomDatabase __db;

  private final EntityUpsertionAdapter<UserProfileEntity> __upsertionAdapterOfUserProfileEntity;

  private final DbConverters __dbConverters = new DbConverters();

  public UserProfileDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__upsertionAdapterOfUserProfileEntity = new EntityUpsertionAdapter<UserProfileEntity>(new EntityInsertionAdapter<UserProfileEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT INTO `user_profiles` (`id`,`fullName`,`age`,`weightKg`,`heightCm`,`genderLabel`,`goal`,`fitnessLevel`,`preferredFocus`,`workoutDaysPerWeek`,`sessionDurationMinutes`,`experienceNotes`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserProfileEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getFullName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFullName());
        }
        statement.bindLong(3, entity.getAge());
        statement.bindDouble(4, entity.getWeightKg());
        statement.bindLong(5, entity.getHeightCm());
        if (entity.getGenderLabel() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getGenderLabel());
        }
        final String _tmp = __dbConverters.fromFitnessGoal(entity.getGoal());
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp);
        }
        final String _tmp_1 = __dbConverters.fromFitnessLevel(entity.getFitnessLevel());
        if (_tmp_1 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_1);
        }
        final String _tmp_2 = __dbConverters.fromWorkoutCategory(entity.getPreferredFocus());
        if (_tmp_2 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_2);
        }
        statement.bindLong(10, entity.getWorkoutDaysPerWeek());
        statement.bindLong(11, entity.getSessionDurationMinutes());
        if (entity.getExperienceNotes() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getExperienceNotes());
        }
        final String _tmp_3 = __dbConverters.fromInstant(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, _tmp_3);
        }
      }
    }, new EntityDeletionOrUpdateAdapter<UserProfileEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE `user_profiles` SET `id` = ?,`fullName` = ?,`age` = ?,`weightKg` = ?,`heightCm` = ?,`genderLabel` = ?,`goal` = ?,`fitnessLevel` = ?,`preferredFocus` = ?,`workoutDaysPerWeek` = ?,`sessionDurationMinutes` = ?,`experienceNotes` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserProfileEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getFullName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFullName());
        }
        statement.bindLong(3, entity.getAge());
        statement.bindDouble(4, entity.getWeightKg());
        statement.bindLong(5, entity.getHeightCm());
        if (entity.getGenderLabel() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getGenderLabel());
        }
        final String _tmp = __dbConverters.fromFitnessGoal(entity.getGoal());
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp);
        }
        final String _tmp_1 = __dbConverters.fromFitnessLevel(entity.getFitnessLevel());
        if (_tmp_1 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_1);
        }
        final String _tmp_2 = __dbConverters.fromWorkoutCategory(entity.getPreferredFocus());
        if (_tmp_2 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_2);
        }
        statement.bindLong(10, entity.getWorkoutDaysPerWeek());
        statement.bindLong(11, entity.getSessionDurationMinutes());
        if (entity.getExperienceNotes() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getExperienceNotes());
        }
        final String _tmp_3 = __dbConverters.fromInstant(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, _tmp_3);
        }
        statement.bindLong(14, entity.getId());
      }
    });
  }

  @Override
  public Object upsertProfile(final UserProfileEntity profile,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __upsertionAdapterOfUserProfileEntity.upsert(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<UserProfileEntity> observeProfile(final long userId) {
    final String _sql = "SELECT * FROM user_profiles WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_profiles"}, new Callable<UserProfileEntity>() {
      @Override
      @Nullable
      public UserProfileEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "weightKg");
          final int _cursorIndexOfHeightCm = CursorUtil.getColumnIndexOrThrow(_cursor, "heightCm");
          final int _cursorIndexOfGenderLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "genderLabel");
          final int _cursorIndexOfGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "goal");
          final int _cursorIndexOfFitnessLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "fitnessLevel");
          final int _cursorIndexOfPreferredFocus = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredFocus");
          final int _cursorIndexOfWorkoutDaysPerWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutDaysPerWeek");
          final int _cursorIndexOfSessionDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionDurationMinutes");
          final int _cursorIndexOfExperienceNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "experienceNotes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final UserProfileEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final double _tmpWeightKg;
            _tmpWeightKg = _cursor.getDouble(_cursorIndexOfWeightKg);
            final int _tmpHeightCm;
            _tmpHeightCm = _cursor.getInt(_cursorIndexOfHeightCm);
            final String _tmpGenderLabel;
            if (_cursor.isNull(_cursorIndexOfGenderLabel)) {
              _tmpGenderLabel = null;
            } else {
              _tmpGenderLabel = _cursor.getString(_cursorIndexOfGenderLabel);
            }
            final FitnessGoal _tmpGoal;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfGoal)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfGoal);
            }
            _tmpGoal = __dbConverters.toFitnessGoal(_tmp);
            final FitnessLevel _tmpFitnessLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfFitnessLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfFitnessLevel);
            }
            _tmpFitnessLevel = __dbConverters.toFitnessLevel(_tmp_1);
            final WorkoutCategory _tmpPreferredFocus;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPreferredFocus)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPreferredFocus);
            }
            _tmpPreferredFocus = __dbConverters.toWorkoutCategory(_tmp_2);
            final int _tmpWorkoutDaysPerWeek;
            _tmpWorkoutDaysPerWeek = _cursor.getInt(_cursorIndexOfWorkoutDaysPerWeek);
            final int _tmpSessionDurationMinutes;
            _tmpSessionDurationMinutes = _cursor.getInt(_cursorIndexOfSessionDurationMinutes);
            final String _tmpExperienceNotes;
            if (_cursor.isNull(_cursorIndexOfExperienceNotes)) {
              _tmpExperienceNotes = null;
            } else {
              _tmpExperienceNotes = _cursor.getString(_cursorIndexOfExperienceNotes);
            }
            final Instant _tmpCreatedAt;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dbConverters.toInstant(_tmp_3);
            _result = new UserProfileEntity(_tmpId,_tmpFullName,_tmpAge,_tmpWeightKg,_tmpHeightCm,_tmpGenderLabel,_tmpGoal,_tmpFitnessLevel,_tmpPreferredFocus,_tmpWorkoutDaysPerWeek,_tmpSessionDurationMinutes,_tmpExperienceNotes,_tmpCreatedAt);
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
  public Object getProfile(final long userId,
      final Continuation<? super UserProfileEntity> $completion) {
    final String _sql = "SELECT * FROM user_profiles WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserProfileEntity>() {
      @Override
      @Nullable
      public UserProfileEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "weightKg");
          final int _cursorIndexOfHeightCm = CursorUtil.getColumnIndexOrThrow(_cursor, "heightCm");
          final int _cursorIndexOfGenderLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "genderLabel");
          final int _cursorIndexOfGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "goal");
          final int _cursorIndexOfFitnessLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "fitnessLevel");
          final int _cursorIndexOfPreferredFocus = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredFocus");
          final int _cursorIndexOfWorkoutDaysPerWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutDaysPerWeek");
          final int _cursorIndexOfSessionDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionDurationMinutes");
          final int _cursorIndexOfExperienceNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "experienceNotes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final UserProfileEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final double _tmpWeightKg;
            _tmpWeightKg = _cursor.getDouble(_cursorIndexOfWeightKg);
            final int _tmpHeightCm;
            _tmpHeightCm = _cursor.getInt(_cursorIndexOfHeightCm);
            final String _tmpGenderLabel;
            if (_cursor.isNull(_cursorIndexOfGenderLabel)) {
              _tmpGenderLabel = null;
            } else {
              _tmpGenderLabel = _cursor.getString(_cursorIndexOfGenderLabel);
            }
            final FitnessGoal _tmpGoal;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfGoal)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfGoal);
            }
            _tmpGoal = __dbConverters.toFitnessGoal(_tmp);
            final FitnessLevel _tmpFitnessLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfFitnessLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfFitnessLevel);
            }
            _tmpFitnessLevel = __dbConverters.toFitnessLevel(_tmp_1);
            final WorkoutCategory _tmpPreferredFocus;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPreferredFocus)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPreferredFocus);
            }
            _tmpPreferredFocus = __dbConverters.toWorkoutCategory(_tmp_2);
            final int _tmpWorkoutDaysPerWeek;
            _tmpWorkoutDaysPerWeek = _cursor.getInt(_cursorIndexOfWorkoutDaysPerWeek);
            final int _tmpSessionDurationMinutes;
            _tmpSessionDurationMinutes = _cursor.getInt(_cursorIndexOfSessionDurationMinutes);
            final String _tmpExperienceNotes;
            if (_cursor.isNull(_cursorIndexOfExperienceNotes)) {
              _tmpExperienceNotes = null;
            } else {
              _tmpExperienceNotes = _cursor.getString(_cursorIndexOfExperienceNotes);
            }
            final Instant _tmpCreatedAt;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dbConverters.toInstant(_tmp_3);
            _result = new UserProfileEntity(_tmpId,_tmpFullName,_tmpAge,_tmpWeightKg,_tmpHeightCm,_tmpGenderLabel,_tmpGoal,_tmpFitnessLevel,_tmpPreferredFocus,_tmpWorkoutDaysPerWeek,_tmpSessionDurationMinutes,_tmpExperienceNotes,_tmpCreatedAt);
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
  public Object countProfiles(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM user_profiles";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
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
