package com.athallah.hybridfit.data.local.converter

import androidx.room.TypeConverter
import com.athallah.hybridfit.domain.model.FitnessGoal
import com.athallah.hybridfit.domain.model.FitnessLevel
import com.athallah.hybridfit.domain.model.PlanStatus
import com.athallah.hybridfit.domain.model.RecommendationAction
import com.athallah.hybridfit.domain.model.WorkoutCategory
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate

class DbConverters {
    @TypeConverter
    fun fromFitnessGoal(value: FitnessGoal): String = value.name

    @TypeConverter
    fun toFitnessGoal(value: String): FitnessGoal = FitnessGoal.valueOf(value)

    @TypeConverter
    fun fromFitnessLevel(value: FitnessLevel): String = value.name

    @TypeConverter
    fun toFitnessLevel(value: String): FitnessLevel = FitnessLevel.valueOf(value)

    @TypeConverter
    fun fromWorkoutCategory(value: WorkoutCategory): String = value.name

    @TypeConverter
    fun toWorkoutCategory(value: String): WorkoutCategory = WorkoutCategory.valueOf(value)

    @TypeConverter
    fun fromPlanStatus(value: PlanStatus): String = value.name

    @TypeConverter
    fun toPlanStatus(value: String): PlanStatus = PlanStatus.valueOf(value)

    @TypeConverter
    fun fromRecommendationAction(value: RecommendationAction): String = value.name

    @TypeConverter
    fun toRecommendationAction(value: String): RecommendationAction =
        RecommendationAction.valueOf(value)

    @TypeConverter
    fun fromDayOfWeek(value: DayOfWeek): String = value.name

    @TypeConverter
    fun toDayOfWeek(value: String): DayOfWeek = DayOfWeek.valueOf(value)

    @TypeConverter
    fun fromLocalDate(value: LocalDate): String = value.toString()

    @TypeConverter
    fun toLocalDate(value: String): LocalDate = LocalDate.parse(value)

    @TypeConverter
    fun fromInstant(value: Instant): String = value.toString()

    @TypeConverter
    fun toInstant(value: String): Instant = Instant.parse(value)
}
