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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\t\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0002"}, d2 = {"DEFAULT_USER_ID", "", "app_debug"})
public final class EntitiesKt {
    public static final long DEFAULT_USER_ID = 1L;
}