package com.athallah.hybridfit.domain.service

import com.athallah.hybridfit.domain.model.FitnessGoal
import com.athallah.hybridfit.domain.model.UserProfile
import com.athallah.hybridfit.domain.model.WorkoutCategory
import java.time.DayOfWeek
import java.time.LocalDate

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
        val sessionsPerWeek = profile.workoutDaysPerWeek.coerceIn(3, 5)
        val selectedDays = pickDays(sessionsPerWeek)
        val templates = expandTemplates(profile.goal, sessionsPerWeek)

        val sessions = templates.mapIndexed { index, template ->
            SessionBlueprint(
                dayOfWeek = selectedDays[index],
                title = template.title,
                category = template.category,
                focusArea = template.focusArea,
                targetDurationMinutes = profile.sessionDurationMinutes.coerceAtLeast(template.baseDuration),
                targetDistanceKm = template.baseDistanceKm,
                targetSets = template.baseSets,
                targetReps = template.baseReps,
                restSeconds = template.restSeconds,
                guidance = template.guidance,
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
        val preferred = listOf(
            DayOfWeek.MONDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY
        )
        return preferred.take(totalDays)
    }

    private fun expandTemplates(goal: FitnessGoal, totalDays: Int): List<SessionTemplate> {
        val baseTemplates = templatesForGoal(goal)
        return List(totalDays) { index -> baseTemplates[index % baseTemplates.size] }
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
}
