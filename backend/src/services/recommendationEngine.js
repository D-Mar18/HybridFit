const DAY_ORDER = [
  "MONDAY",
  "TUESDAY",
  "WEDNESDAY",
  "THURSDAY",
  "FRIDAY",
  "SATURDAY",
  "SUNDAY"
];

function clamp(value, min, max) {
  return Math.min(Math.max(value, min), max);
}

function toDateString(date = new Date()) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
}

function shiftDate(dateString, days) {
  const date = new Date(`${dateString}T00:00:00`);
  date.setDate(date.getDate() + days);
  return toDateString(date);
}

function average(values, fallback) {
  if (!values.length) {
    return fallback;
  }
  return values.reduce((sum, value) => sum + value, 0) / values.length;
}

function roundToSingleDecimal(value) {
  return Math.round(value * 10) / 10;
}

function bodyMassIndex(weightKg, heightCm) {
  if (!weightKg || !heightCm) {
    return 22;
  }
  const heightMeter = Math.max(Number(heightCm) / 100, 1);
  return Number(weightKg) / (heightMeter * heightMeter);
}

function adjustedDuration(profile, baseDuration) {
  const levelBoost =
    profile.fitnessLevel === "BEGINNER" ? -5 : profile.fitnessLevel === "ADVANCED" ? 5 : 0;
  const ageShift = profile.age >= 45 || profile.age < 18 ? -5 : 0;
  return clamp((Number(profile.sessionDurationMinutes) || baseDuration) + levelBoost + ageShift, 20, 90);
}

function adjustedDistance(profile, baseDistanceKm) {
  if (baseDistanceKm == null) {
    return null;
  }

  const levelMultiplier =
    profile.fitnessLevel === "BEGINNER" ? 0.9 : profile.fitnessLevel === "ADVANCED" ? 1.12 : 1.0;
  const ageMultiplier = profile.age >= 45 || profile.age < 18 ? 0.9 : 1.0;
  const bmi = bodyMassIndex(profile.weightKg, profile.heightCm);
  const bmiMultiplier = bmi >= 30 ? 0.88 : bmi <= 19 ? 0.95 : 1.0;
  return roundToSingleDecimal(baseDistanceKm * levelMultiplier * ageMultiplier * bmiMultiplier);
}

function adjustedSets(profile, baseSets) {
  if (baseSets == null) {
    return null;
  }
  const levelShift =
    profile.fitnessLevel === "BEGINNER"
      ? baseSets >= 4
        ? -1
        : 0
      : profile.fitnessLevel === "ADVANCED"
        ? 1
        : 0;
  const ageShift = profile.age >= 45 ? -1 : 0;
  return clamp(baseSets + levelShift + ageShift, 2, 5);
}

function adjustedReps(profile, baseReps, category) {
  if (baseReps == null) {
    return null;
  }
  if (category !== "STRENGTH" && category !== "MOBILITY") {
    return baseReps;
  }
  const goalShift =
    profile.goal === "MUSCLE_GAIN" ? -2 : profile.goal === "WEIGHT_LOSS" ? 2 : 0;
  const levelShift = profile.fitnessLevel === "ADVANCED" ? -1 : 0;
  return clamp(baseReps + goalShift + levelShift, 6, 15);
}

function adjustedRestSeconds(profile, baseRestSeconds) {
  const levelShift =
    profile.fitnessLevel === "BEGINNER" ? 10 : profile.fitnessLevel === "ADVANCED" ? -5 : 0;
  const ageShift = profile.age >= 45 ? 10 : 0;
  return clamp(baseRestSeconds + levelShift + ageShift, 30, 180);
}

function buildAdaptiveGuidance(profile, template, targetSets, targetReps, targetDistanceKm) {
  const personalizedNote =
    targetDistanceKm != null
      ? `Target awal disesuaikan ke ${targetDistanceKm} km agar tetap realistis untuk profil Anda.`
      : targetSets != null && targetReps != null
        ? `Mulai dari ${targetSets} set x ${targetReps} reps sambil menjaga teknik sebelum menambah beban.`
        : "Mulai konservatif dulu agar tubuh beradaptasi dengan aman.";
  return `${template.guidance} ${personalizedNote}`;
}

function sortSessions(sessions = []) {
  return [...sessions].sort((left, right) => {
    const dayDelta = DAY_ORDER.indexOf(left.dayOfWeek) - DAY_ORDER.indexOf(right.dayOfWeek);
    if (dayDelta !== 0) {
      return dayDelta;
    }
    return (left.orderInDay || 0) - (right.orderInDay || 0);
  });
}

function sortLogsNewestFirst(logs = []) {
  return [...logs].sort((left, right) => {
    if (left.performedOn === right.performedOn) {
      return (right.id || 0) - (left.id || 0);
    }
    return right.performedOn.localeCompare(left.performedOn);
  });
}

function sortRecommendationsNewestFirst(recommendations = []) {
  return [...recommendations].sort((left, right) => {
    if (left.createdAt === right.createdAt) {
      return (right.id || 0) - (left.id || 0);
    }
    return right.createdAt.localeCompare(left.createdAt);
  });
}

function sortFeedbackNewestFirst(feedback = []) {
  return [...feedback].sort((left, right) => {
    if (left.recordedAt === right.recordedAt) {
      return (right.id || 0) - (left.id || 0);
    }
    return right.recordedAt.localeCompare(left.recordedAt);
  });
}

function buildPlanTitle(goal) {
  switch (goal) {
    case "GENERAL_FITNESS":
      return "Program Dasar Kebugaran";
    case "WEIGHT_LOSS":
      return "Program Pembakaran Kalori Bertahap";
    case "MUSCLE_GAIN":
      return "Program Strength dan Hypertrophy";
    case "ENDURANCE":
      return "Program Peningkatan Daya Tahan";
    default:
      return "Program HybridFit";
  }
}

function pickDays(totalDays) {
  return ["MONDAY", "WEDNESDAY", "FRIDAY", "SATURDAY", "SUNDAY"].slice(0, totalDays);
}

function templatesForGoal(goal) {
  switch (goal) {
    case "GENERAL_FITNESS":
      return [
        {
          title: "Full Body Foundation",
          category: "STRENGTH",
          focusArea: "Kekuatan dasar seluruh tubuh",
          baseDuration: 40,
          baseDistanceKm: null,
          baseSets: 3,
          baseReps: 10,
          restSeconds: 75,
          guidance: "Mulai dari gerakan dasar, jaga teknik, dan utamakan konsistensi."
        },
        {
          title: "Cardio Intervals",
          category: "CARDIO",
          focusArea: "Latihan jantung dan paru",
          baseDuration: 30,
          baseDistanceKm: 3.0,
          baseSets: null,
          baseReps: null,
          restSeconds: 60,
          guidance: "Gunakan pola lari ringan dan jalan cepat agar ritme tetap stabil."
        },
        {
          title: "Mobility and Core",
          category: "MOBILITY",
          focusArea: "Mobilitas sendi dan stabilitas inti",
          baseDuration: 25,
          baseDistanceKm: null,
          baseSets: 2,
          baseReps: 12,
          restSeconds: 45,
          guidance: "Fokus pada range of motion dan kontrol napas."
        }
      ];

    case "WEIGHT_LOSS":
      return [
        {
          title: "Brisk Run",
          category: "CARDIO",
          focusArea: "Kalori dan daya tahan",
          baseDuration: 35,
          baseDistanceKm: 3.5,
          baseSets: null,
          baseReps: null,
          restSeconds: 50,
          guidance: "Jaga pace percakapan supaya tubuh bisa menyelesaikan sesi dengan baik."
        },
        {
          title: "Circuit Strength",
          category: "STRENGTH",
          focusArea: "Latihan sirkuit pembakaran kalori",
          baseDuration: 35,
          baseDistanceKm: null,
          baseSets: 3,
          baseReps: 12,
          restSeconds: 40,
          guidance: "Pilih gerakan gabungan dan minimalkan jeda terlalu panjang."
        },
        {
          title: "Low Impact Recovery",
          category: "RECOVERY",
          focusArea: "Aktivitas ringan dan pemulihan aktif",
          baseDuration: 25,
          baseDistanceKm: 2.0,
          baseSets: null,
          baseReps: null,
          restSeconds: 30,
          guidance: "Gunakan jalan cepat atau sepeda ringan agar pemulihan tetap aktif."
        }
      ];

    case "MUSCLE_GAIN":
      return [
        {
          title: "Upper Body Day",
          category: "STRENGTH",
          focusArea: "Dada, punggung, bahu, dan lengan",
          baseDuration: 45,
          baseDistanceKm: null,
          baseSets: 4,
          baseReps: 8,
          restSeconds: 90,
          guidance: "Prioritaskan beban yang terkontrol dan progresif."
        },
        {
          title: "Lower Body Day",
          category: "STRENGTH",
          focusArea: "Kaki dan glute",
          baseDuration: 45,
          baseDistanceKm: null,
          baseSets: 4,
          baseReps: 10,
          restSeconds: 90,
          guidance: "Pastikan pemanasan cukup karena volume latihan cenderung lebih tinggi."
        },
        {
          title: "Accessory and Core",
          category: "STRENGTH",
          focusArea: "Otot penunjang dan inti tubuh",
          baseDuration: 35,
          baseDistanceKm: null,
          baseSets: 3,
          baseReps: 12,
          restSeconds: 60,
          guidance: "Gunakan tempo yang rapi dan targetkan kontraksi otot yang baik."
        }
      ];

    case "ENDURANCE":
      return [
        {
          title: "Easy Run",
          category: "CARDIO",
          focusArea: "Volume dasar aerobik",
          baseDuration: 35,
          baseDistanceKm: 4.0,
          baseSets: null,
          baseReps: null,
          restSeconds: 45,
          guidance: "Gunakan intensitas ringan agar tubuh nyaman menambah volume."
        },
        {
          title: "Tempo Session",
          category: "CARDIO",
          focusArea: "Kecepatan dan kontrol pace",
          baseDuration: 30,
          baseDistanceKm: 3.0,
          baseSets: null,
          baseReps: null,
          restSeconds: 60,
          guidance: "Mulai dengan pemanasan, lalu jaga tempo pada intensitas menengah."
        },
        {
          title: "Mobility Reset",
          category: "MOBILITY",
          focusArea: "Mobilitas pinggul dan pergelangan",
          baseDuration: 20,
          baseDistanceKm: null,
          baseSets: 2,
          baseReps: 10,
          restSeconds: 40,
          guidance: "Gunakan sesi ini untuk memperbaiki recovery dan kualitas gerak."
        }
      ];

    default:
      return [];
  }
}

function buildStarterPlan(profile) {
  const sessionsPerWeek = clamp(Number(profile.workoutDaysPerWeek) || 3, 3, 5);
  const selectedDays = pickDays(sessionsPerWeek);
  const baseTemplates = templatesForGoal(profile.goal);
  const sessions = Array.from({ length: sessionsPerWeek }, (_, index) => {
    const template = baseTemplates[index % baseTemplates.length];
    const targetDurationMinutes = adjustedDuration(profile, template.baseDuration);
    const targetDistanceKm = adjustedDistance(profile, template.baseDistanceKm);
    const targetSets = adjustedSets(profile, template.baseSets);
    const targetReps = adjustedReps(profile, template.baseReps, template.category);
    return {
      dayOfWeek: selectedDays[index],
      title: template.title,
      category: template.category,
      focusArea: template.focusArea,
      targetDurationMinutes,
      targetDistanceKm,
      targetSets,
      targetReps,
      restSeconds: adjustedRestSeconds(profile, template.restSeconds),
      guidance: buildAdaptiveGuidance(profile, template, targetSets, targetReps, targetDistanceKm),
      orderInDay: index + 1
    };
  });

  return {
    title: buildPlanTitle(profile.goal),
    startDate: toDateString(),
    sessionsPerWeek,
    sessions
  };
}

function buildFallbackSession(profile) {
  const preferredFocus = profile.preferredFocus || "STRENGTH";
  return {
    id: 0,
    planId: 0,
    dayOfWeek: "MONDAY",
    title: "Sesi Dasar",
    category: preferredFocus,
    focusArea: "Fondasi kebugaran",
    targetDurationMinutes: Number(profile.sessionDurationMinutes) || 20,
    targetDistanceKm: preferredFocus === "CARDIO" ? 3.0 : null,
    targetSets: preferredFocus === "STRENGTH" ? 3 : null,
    targetReps: preferredFocus === "STRENGTH" ? 10 : null,
    restSeconds: 60,
    guidance: "Gunakan sesi ini sebagai baseline awal.",
    orderInDay: 1
  };
}

function generateRecommendation({ userId, profile, sessions, recentLogs, feedback, recommendationId }) {
  const anchorSession = sortSessions(sessions)[0] || buildFallbackSession(profile);
  const evaluationWindow = sortLogsNewestFirst(recentLogs).slice(0, 5);
  const recentFeedback = sortFeedbackNewestFirst(feedback).slice(0, 3);

  const averageCompletion = average(
    evaluationWindow.map((item) => Number(item.completionPercent) || 0),
    75
  );
  const averageExertion = average(
    evaluationWindow.map((item) => Number(item.exertionScore) || 0),
    7
  );
  const helpfulRatio = average(
    recentFeedback.map((item) => (item.wasHelpful ? 1 : 0)),
    1
  );

  let action = "MAINTAIN";
  if (averageCompletion >= 85 && averageExertion <= 7.0 && helpfulRatio >= 0.5) {
    action = "INCREASE";
  } else if (averageCompletion < 65 || averageExertion >= 8.5 || helpfulRatio < 0.35) {
    action = "RECOVER";
  }

  const durationShift = action === "INCREASE" ? 5 : action === "RECOVER" ? -5 : 0;
  const setShift = action === "INCREASE" ? 1 : action === "RECOVER" ? -1 : 0;
  const repShift = action === "INCREASE" ? 2 : action === "RECOVER" ? -2 : 0;
  const restShift = action === "INCREASE" ? -10 : action === "RECOVER" ? 15 : 0;
  const distanceShift = action === "INCREASE" ? 0.4 : action === "RECOVER" ? -0.4 : 0;

  const summary =
    action === "INCREASE"
      ? "Performa kamu cukup stabil. Sistem menyarankan peningkatan volume kecil agar progres tetap berjalan."
      : action === "RECOVER"
        ? "Tanda kelelahan mulai terlihat. Sistem menyarankan sesi yang lebih ringan agar pemulihan tetap terjaga."
        : "Latihan sudah berada di zona yang sehat. Sistem menjaga beban tetap stabil sambil melihat konsistensi pekan berikutnya.";

  const rationale = `Dasar rekomendasi: penyelesaian rata-rata ${Math.round(
    averageCompletion
  )}%, skor usaha ${Math.round(averageExertion)}/10, dan umpan balik positif ${Math.round(
    helpfulRatio * 100
  )}%.`;

  return {
    id: recommendationId,
    userId,
    createdAt: new Date().toISOString(),
    title: "IRS Minggu Ini",
    summary,
    action,
    targetDurationMinutes: Math.max((Number(anchorSession.targetDurationMinutes) || 20) + durationShift, 20),
    targetDistanceKm:
      anchorSession.targetDistanceKm == null
        ? null
        : Math.max(roundToSingleDecimal(Number(anchorSession.targetDistanceKm) + distanceShift), 1.5),
    targetSets:
      anchorSession.targetSets == null ? null : Math.max(Number(anchorSession.targetSets) + setShift, 2),
    targetReps:
      anchorSession.targetReps == null ? null : Math.max(Number(anchorSession.targetReps) + repShift, 6),
    restSeconds: clamp((Number(anchorSession.restSeconds) || 60) + restShift, 30, 180),
    rationale
  };
}

function analyzeProgress(plannedSessionsPerWeek, logs) {
  const orderedLogs = sortLogsNewestFirst(logs);
  if (!orderedLogs.length) {
    return {
      totalSessionsLogged: 0,
      weeklyConsistencyPercent: 0,
      averageCompletionPercent: 0,
      averageExertionScore: 0,
      bestHighlight: "Belum ada data latihan.",
      trend: "STABLE"
    };
  }

  const thresholdDate = shiftDate(toDateString(), -7);
  const recentWeek = orderedLogs.filter((log) => log.performedOn >= thresholdDate);
  const completedWeek = recentWeek.filter((log) => log.wasCompleted).length;
  const weeklyConsistency =
    plannedSessionsPerWeek > 0
      ? clamp(Math.round((completedWeek / plannedSessionsPerWeek) * 100), 0, 100)
      : 0;

  const averageCompletion = Math.round(
    average(
      orderedLogs.map((item) => Number(item.completionPercent) || 0),
      0
    )
  );
  const averageExertion = Math.round(
    average(
      orderedLogs.map((item) => Number(item.exertionScore) || 0),
      0
    )
  );

  const maxDistance = orderedLogs
    .map((item) => item.actualDistanceKm)
    .filter((value) => typeof value === "number")
    .reduce((best, current) => (current > best ? current : best), 0);

  const maxVolumeLog = orderedLogs.reduce((best, current) => {
    const bestVolume = (best?.completedSets || 0) * (best?.completedReps || 0);
    const currentVolume = (current.completedSets || 0) * (current.completedReps || 0);
    return currentVolume > bestVolume ? current : best;
  }, null);

  let bestHighlight = "Progres konsisten sedang dibangun.";
  if (maxDistance > 0) {
    bestHighlight = `Jarak terbaik: ${roundToSingleDecimal(maxDistance)} km`;
  } else if (maxVolumeLog && maxVolumeLog.completedSets != null && maxVolumeLog.completedReps != null) {
    bestHighlight = `Volume terbaik: ${maxVolumeLog.completedSets} x ${maxVolumeLog.completedReps}`;
  }

  let trend = "STABLE";
  if (averageCompletion >= 85 && averageExertion <= 7) {
    trend = "IMPROVING";
  } else if (averageCompletion < 65 || averageExertion >= 9) {
    trend = "NEEDS_ATTENTION";
  }

  return {
    totalSessionsLogged: orderedLogs.length,
    weeklyConsistencyPercent: weeklyConsistency,
    averageCompletionPercent: averageCompletion,
    averageExertionScore: averageExertion,
    bestHighlight,
    trend
  };
}

function buildQuickWorkoutLog({ activePlan, logs, latestRecommendation, logId }) {
  const sessions = sortSessions(activePlan.sessions || []);
  const targetSession = sessions[logs.length % sessions.length];
  const recommendation = latestRecommendation || {};

  return {
    id: logId,
    sessionId: targetSession.id,
    sessionTitle: targetSession.title,
    performedOn: toDateString(),
    actualDurationMinutes: recommendation.targetDurationMinutes ?? targetSession.targetDurationMinutes,
    actualDistanceKm: recommendation.targetDistanceKm ?? targetSession.targetDistanceKm ?? null,
    completedSets: recommendation.targetSets ?? targetSession.targetSets ?? null,
    completedReps: recommendation.targetReps ?? targetSession.targetReps ?? null,
    completionPercent: 90,
    exertionScore: 7,
    userFeedback: "Latihan selesai dengan baik dan masih terasa realistis.",
    wasCompleted: true
  };
}

function buildRecommendationFeedback({ recommendationId, feedbackId, wasHelpful }) {
  return {
    id: feedbackId,
    recommendationId,
    wasHelpful,
    rating: wasHelpful ? 4 : 2,
    notes: wasHelpful
      ? "Rekomendasi terasa cocok dengan kemampuan saat ini."
      : "Rekomendasi masih perlu disesuaikan lagi.",
    recordedAt: new Date().toISOString()
  };
}

function buildDashboardSnapshot({ profile, activePlan, logs, latestRecommendation, feedbackCount }) {
  const normalizedPlan = activePlan
    ? {
        ...activePlan,
        sessions: sortSessions(activePlan.sessions || [])
      }
    : null;

  return {
    profile: profile || null,
    activePlan: normalizedPlan,
    recentLogs: sortLogsNewestFirst(logs).slice(0, 10),
    latestRecommendation: latestRecommendation || null,
    feedbackCount,
    progressSummary: analyzeProgress(normalizedPlan?.sessionsPerWeek || 0, logs)
  };
}

module.exports = {
  DAY_ORDER,
  analyzeProgress,
  buildDashboardSnapshot,
  buildQuickWorkoutLog,
  buildRecommendationFeedback,
  buildStarterPlan,
  generateRecommendation,
  sortFeedbackNewestFirst,
  sortLogsNewestFirst,
  sortRecommendationsNewestFirst,
  sortSessions,
  toDateString
};
