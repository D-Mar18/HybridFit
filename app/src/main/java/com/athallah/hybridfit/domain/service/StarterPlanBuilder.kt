package com.athallah.hybridfit.domain.service

import com.athallah.hybridfit.domain.model.FitnessGoal
import com.athallah.hybridfit.domain.model.FitnessLevel
import com.athallah.hybridfit.domain.model.UserProfile
import com.athallah.hybridfit.domain.model.WorkoutCategory
import java.time.DayOfWeek
import java.time.LocalDate
import kotlin.math.pow
import kotlin.math.roundToInt

data class SessionBlueprint(
    val dayOfWeek: DayOfWeek,
    val title: String,
    val category: WorkoutCategory,
    val focusArea: String,
    val targetDurationMinutes: Int,
    val targetDistanceKm: Double?,
    val targetSets: Int?,
    val targetReps: Int?,
    val restSeconds: Int,
    val guidance: String,
    val orderInDay: Int
)

data class StarterPlanDraft(
    val title: String,
    val startDate: LocalDate,
    val sessionsPerWeek: Int,
    val sessions: List<SessionBlueprint>
)

class StarterPlanBuilder {
    fun build(profile: UserProfile): StarterPlanDraft {
        val sessionsPerWeek = profile.workoutDaysPerWeek.coerceIn(1, 7)
        val selectedDays = pickDays(sessionsPerWeek)
        val templates = expandTemplates(profile.goal, sessionsPerWeek)

        val sessions = templates.mapIndexed { index, template ->
            val adjustedDuration = adjustedDuration(profile, template.baseDuration)
            val adjustedDistance = adjustedDistance(profile, template.baseDistanceKm)
            val adjustedSets = adjustedSets(profile, template.baseSets)
            val adjustedReps = adjustedReps(profile, template.baseReps, template.category)
            SessionBlueprint(
                dayOfWeek = selectedDays[index],
                title = template.title,
                category = template.category,
                focusArea = template.focusArea,
                targetDurationMinutes = adjustedDuration,
                targetDistanceKm = adjustedDistance,
                targetSets = adjustedSets,
                targetReps = adjustedReps,
                restSeconds = adjustedRestSeconds(profile, template.restSeconds),
                guidance = buildGuidance(template.guidance, adjustedSets, adjustedReps, adjustedDistance),
                orderInDay = index + 1
            )
        }

        return StarterPlanDraft(
            title = buildPlanTitle(profile.goal),
            startDate = LocalDate.now(),
            sessionsPerWeek = sessionsPerWeek,
            sessions = sessions
        )
    }

    private fun buildPlanTitle(goal: FitnessGoal): String = when (goal) {
        FitnessGoal.GENERAL_FITNESS -> "Program Dasar Kebugaran"
        FitnessGoal.WEIGHT_LOSS -> "Program Pembakaran Kalori Bertahap"
        FitnessGoal.MUSCLE_GAIN -> "Program Strength dan Hypertrophy"
        FitnessGoal.ENDURANCE -> "Program Peningkatan Daya Tahan"
    }

    private fun pickDays(totalDays: Int): List<DayOfWeek> {
        return when (totalDays.coerceIn(1, 7)) {
            1 -> listOf(DayOfWeek.MONDAY)
            2 -> listOf(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)
            3 -> listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
            4 -> listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY)
            5 -> listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY)
            6 -> listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
            else -> DayOfWeek.entries
        }
    }

    private fun expandTemplates(goal: FitnessGoal, totalDays: Int): List<SessionTemplate> {
        val baseTemplates = templatesForGoal(goal)
        return List(totalDays) { index -> baseTemplates[index % baseTemplates.size] }
    }

    private fun adjustedDuration(profile: UserProfile, baseDuration: Int): Int {
        val levelBoost = when (profile.fitnessLevel) {
            FitnessLevel.BEGINNER -> -5
            FitnessLevel.INTERMEDIATE -> 0
            FitnessLevel.ADVANCED -> 5
        }
        val ageShift = when {
            profile.age in 10..17 -> -5
            profile.age >= 45 -> -5
            else -> 0
        }
        return (profile.sessionDurationMinutes + levelBoost + ageShift)
            .coerceAtLeast(baseDuration.coerceAtLeast(20))
            .coerceAtMost(90)
    }

    private fun adjustedDistance(profile: UserProfile, baseDistanceKm: Double?): Double? {
        baseDistanceKm ?: return null
        val bmi = profile.bmi()
        val levelMultiplier = when (profile.fitnessLevel) {
            FitnessLevel.BEGINNER -> 0.9
            FitnessLevel.INTERMEDIATE -> 1.0
            FitnessLevel.ADVANCED -> 1.12
        }
        val ageMultiplier = when {
            profile.age in 10..17 -> 0.92
            profile.age >= 45 -> 0.9
            else -> 1.0
        }
        val bmiMultiplier = when {
            bmi >= 30.0 -> 0.88
            bmi <= 19.0 -> 0.95
            else -> 1.0
        }
        return ((baseDistanceKm * levelMultiplier * ageMultiplier * bmiMultiplier) * 10.0)
            .roundToInt() / 10.0
    }

    private fun adjustedSets(profile: UserProfile, baseSets: Int?): Int? {
        baseSets ?: return null
        val levelShift = when (profile.fitnessLevel) {
            FitnessLevel.BEGINNER -> if (baseSets >= 4) -1 else 0
            FitnessLevel.INTERMEDIATE -> 0
            FitnessLevel.ADVANCED -> 1
        }
        val ageShift = if (profile.age >= 45) -1 else 0
        return (baseSets + levelShift + ageShift).coerceIn(2, 5)
    }

    private fun adjustedReps(
        profile: UserProfile,
        baseReps: Int?,
        category: WorkoutCategory
    ): Int? {
        baseReps ?: return null
        if (category != WorkoutCategory.STRENGTH && category != WorkoutCategory.MOBILITY) {
            return baseReps
        }
        val goalShift = when (profile.goal) {
            FitnessGoal.MUSCLE_GAIN -> -2
            FitnessGoal.WEIGHT_LOSS -> 2
            else -> 0
        }
        val levelShift = when (profile.fitnessLevel) {
            FitnessLevel.BEGINNER -> 0
            FitnessLevel.INTERMEDIATE -> 0
            FitnessLevel.ADVANCED -> -1
        }
        return (baseReps + goalShift + levelShift).coerceIn(6, 15)
    }

    private fun adjustedRestSeconds(profile: UserProfile, baseRestSeconds: Int): Int {
        val levelShift = when (profile.fitnessLevel) {
            FitnessLevel.BEGINNER -> 10
            FitnessLevel.INTERMEDIATE -> 0
            FitnessLevel.ADVANCED -> -5
        }
        val ageShift = if (profile.age >= 45) 10 else 0
        return (baseRestSeconds + levelShift + ageShift).coerceIn(30, 180)
    }

    private fun buildGuidance(
        baseGuidance: String,
        targetSets: Int?,
        targetReps: Int?,
        targetDistanceKm: Double?
    ): String {
        val adaptiveNote = when {
            targetDistanceKm != null ->
                "Target awal disesuaikan ke ${"%.1f".format(targetDistanceKm)} km agar tetap realistis untuk profil Anda."
            targetSets != null && targetReps != null ->
                "Mulai dari $targetSets set x $targetReps reps sambil menjaga teknik sebelum menambah beban."
            else ->
                "Mulai konservatif dulu agar tubuh beradaptasi dengan aman."
        }
        return "$baseGuidance $adaptiveNote"
    }

    private fun templatesForGoal(goal: FitnessGoal): List<SessionTemplate> = when (goal) {
        FitnessGoal.GENERAL_FITNESS -> listOf(
            SessionTemplate(
                title = "Full Body Foundation",
                category = WorkoutCategory.STRENGTH,
                focusArea = "Kekuatan dasar seluruh tubuh",
                baseDuration = 40,
                baseDistanceKm = null,
                baseSets = 3,
                baseReps = 10,
                restSeconds = 75,
                guidance = "Mulai dari gerakan dasar, jaga teknik, dan utamakan konsistensi."
            ),
            SessionTemplate(
                title = "Cardio Intervals",
                category = WorkoutCategory.CARDIO,
                focusArea = "Latihan jantung dan paru",
                baseDuration = 30,
                baseDistanceKm = 3.0,
                baseSets = null,
                baseReps = null,
                restSeconds = 60,
                guidance = "Gunakan pola lari ringan dan jalan cepat agar ritme tetap stabil."
            ),
            SessionTemplate(
                title = "Mobility and Core",
                category = WorkoutCategory.MOBILITY,
                focusArea = "Mobilitas sendi dan stabilitas inti",
                baseDuration = 25,
                baseDistanceKm = null,
                baseSets = 2,
                baseReps = 12,
                restSeconds = 45,
                guidance = "Fokus pada range of motion dan kontrol napas."
            )
        )

        FitnessGoal.WEIGHT_LOSS -> listOf(
            SessionTemplate(
                title = "Brisk Run",
                category = WorkoutCategory.CARDIO,
                focusArea = "Kalori dan daya tahan",
                baseDuration = 35,
                baseDistanceKm = 3.5,
                baseSets = null,
                baseReps = null,
                restSeconds = 50,
                guidance = "Jaga pace percakapan supaya tubuh bisa menyelesaikan sesi dengan baik."
            ),
            SessionTemplate(
                title = "Circuit Strength",
                category = WorkoutCategory.STRENGTH,
                focusArea = "Latihan sirkuit pembakaran kalori",
                baseDuration = 35,
                baseDistanceKm = null,
                baseSets = 3,
                baseReps = 12,
                restSeconds = 40,
                guidance = "Pilih gerakan gabungan dan minimalkan jeda terlalu panjang."
            ),
            SessionTemplate(
                title = "Low Impact Recovery",
                category = WorkoutCategory.RECOVERY,
                focusArea = "Aktivitas ringan dan pemulihan aktif",
                baseDuration = 25,
                baseDistanceKm = 2.0,
                baseSets = null,
                baseReps = null,
                restSeconds = 30,
                guidance = "Gunakan jalan cepat atau sepeda ringan agar pemulihan tetap aktif."
            )
        )

        FitnessGoal.MUSCLE_GAIN -> listOf(
            SessionTemplate(
                title = "Upper Body Day",
                category = WorkoutCategory.STRENGTH,
                focusArea = "Dada, punggung, bahu, dan lengan",
                baseDuration = 45,
                baseDistanceKm = null,
                baseSets = 4,
                baseReps = 8,
                restSeconds = 90,
                guidance = "Prioritaskan beban yang terkontrol dan progresif."
            ),
            SessionTemplate(
                title = "Lower Body Day",
                category = WorkoutCategory.STRENGTH,
                focusArea = "Kaki dan glute",
                baseDuration = 45,
                baseDistanceKm = null,
                baseSets = 4,
                baseReps = 10,
                restSeconds = 90,
                guidance = "Pastikan pemanasan cukup karena volume latihan cenderung lebih tinggi."
            ),
            SessionTemplate(
                title = "Accessory and Core",
                category = WorkoutCategory.STRENGTH,
                focusArea = "Otot penunjang dan inti tubuh",
                baseDuration = 35,
                baseDistanceKm = null,
                baseSets = 3,
                baseReps = 12,
                restSeconds = 60,
                guidance = "Gunakan tempo yang rapi dan targetkan kontraksi otot yang baik."
            )
        )

        FitnessGoal.ENDURANCE -> listOf(
            SessionTemplate(
                title = "Easy Run",
                category = WorkoutCategory.CARDIO,
                focusArea = "Volume dasar aerobik",
                baseDuration = 35,
                baseDistanceKm = 4.0,
                baseSets = null,
                baseReps = null,
                restSeconds = 45,
                guidance = "Gunakan intensitas ringan agar tubuh nyaman menambah volume."
            ),
            SessionTemplate(
                title = "Tempo Session",
                category = WorkoutCategory.CARDIO,
                focusArea = "Kecepatan dan kontrol pace",
                baseDuration = 30,
                baseDistanceKm = 3.0,
                baseSets = null,
                baseReps = null,
                restSeconds = 60,
                guidance = "Mulai dengan pemanasan, lalu jaga tempo pada intensitas menengah."
            ),
            SessionTemplate(
                title = "Mobility Reset",
                category = WorkoutCategory.MOBILITY,
                focusArea = "Mobilitas pinggul dan pergelangan",
                baseDuration = 20,
                baseDistanceKm = null,
                baseSets = 2,
                baseReps = 10,
                restSeconds = 40,
                guidance = "Gunakan sesi ini untuk memperbaiki recovery dan kualitas gerak."
            )
        )
    }

    private data class SessionTemplate(
        val title: String,
        val category: WorkoutCategory,
        val focusArea: String,
        val baseDuration: Int,
        val baseDistanceKm: Double?,
        val baseSets: Int?,
        val baseReps: Int?,
        val restSeconds: Int,
        val guidance: String
    )

    private fun UserProfile.bmi(): Double {
        if (heightCm <= 0 || weightKg <= 0.0) return 22.0
        val heightInMeter = heightCm / 100.0
        return weightKg / heightInMeter.pow(2)
    }
}
