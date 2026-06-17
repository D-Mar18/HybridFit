const express = require("express");
const { body, query, validationResult } = require("express-validator");
const { authMiddleware } = require("../middleware/auth");
const {
  DAYS_OF_WEEK,
  FITNESS_GOALS,
  FITNESS_LEVELS,
  PLAN_STATUSES,
  WORKOUT_CATEGORIES,
  archiveActivePlans,
  countFeedback,
  findSessionById,
  getActivePlan,
  getLatestRecommendation,
  getProfile,
  getUserById,
  listFeedback,
  listLogs,
  listPlans,
  listRecommendations,
  nextId,
  nowIso,
  saveFeedback,
  saveLog,
  savePlan,
  saveProfile,
  saveRecommendation,
  updateUserFullName
} = require("../models/store");
const {
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
} = require("../services/recommendationEngine");
const { generateCoachReply } = require("../services/geminiCoach");

const router = express.Router();

function sendResponse(res, statusCode, success, message, data = {}) {
  return res.status(statusCode).json({
    success,
    message,
    data
  });
}

function validationErrorResponse(req, res) {
  const errors = validationResult(req);
  if (errors.isEmpty()) {
    return null;
  }

  return sendResponse(res, 400, false, "Validasi input gagal", {
    errors: errors.array().map((error) => ({
      field: error.path,
      message: error.msg
    }))
  });
}

function buildProfilePayload(user, currentProfile, input) {
  const merged = {
    fullName: input.fullName ?? currentProfile?.fullName ?? user.fullName,
    age: input.age ?? currentProfile?.age,
    genderLabel: input.genderLabel ?? currentProfile?.genderLabel,
    goal: input.goal ?? currentProfile?.goal,
    fitnessLevel: input.fitnessLevel ?? currentProfile?.fitnessLevel,
    preferredFocus: input.preferredFocus ?? currentProfile?.preferredFocus,
    workoutDaysPerWeek: input.workoutDaysPerWeek ?? currentProfile?.workoutDaysPerWeek,
    sessionDurationMinutes: input.sessionDurationMinutes ?? currentProfile?.sessionDurationMinutes,
    experienceNotes: input.experienceNotes ?? currentProfile?.experienceNotes ?? "",
    createdAt: currentProfile?.createdAt ?? nowIso()
  };

  return {
    id: user.id,
    ...merged
  };
}

function buildPlanRecord(userId, input, sessions) {
  const planStatus = input.status || "ACTIVE";
  if (planStatus === "ACTIVE") {
    archiveActivePlans(userId);
  }

  return {
    id: nextId("plan"),
    userId,
    title: input.title,
    goal: input.goal,
    startDate: input.startDate || toDateString(),
    sessionsPerWeek: Number(input.sessionsPerWeek),
    status: planStatus,
    createdAt: nowIso(),
    sessions: sessions.map((session, index) => ({
      id: nextId("session"),
      planId: 0,
      dayOfWeek: session.dayOfWeek,
      title: session.title,
      category: session.category,
      focusArea: session.focusArea,
      targetDurationMinutes: session.targetDurationMinutes,
      targetDistanceKm: session.targetDistanceKm ?? null,
      targetSets: session.targetSets ?? null,
      targetReps: session.targetReps ?? null,
      restSeconds: session.restSeconds,
      guidance: session.guidance,
      orderInDay: session.orderInDay ?? index + 1
    }))
  };
}

function finalizePlanSessions(plan) {
  plan.sessions = plan.sessions.map((session) => ({
    ...session,
    planId: plan.id
  }));
  return plan;
}

function latestRecommendationForUser(userId) {
  return getLatestRecommendation(userId);
}

function generateAndStoreRecommendation(userId) {
  const profile = getProfile(userId);
  if (!profile) {
    return null;
  }

  const activePlan = getActivePlan(userId);
  const sessions = activePlan?.sessions || [];
  const logs = listLogs(userId);
  const feedback = listFeedback(userId);
  const recommendation = generateRecommendation({
    userId,
    profile,
    sessions,
    recentLogs: logs,
    feedback,
    recommendationId: nextId("recommendation")
  });

  return saveRecommendation(userId, recommendation);
}

const profileValidators = [
  body("fullName")
    .optional()
    .trim()
    .isLength({ min: 3 })
    .withMessage("fullName minimal 3 karakter"),
  body("age").isInt({ min: 10, max: 100 }).withMessage("age harus 10-100"),
  body("genderLabel").trim().notEmpty().withMessage("genderLabel wajib diisi"),
  body("goal").isIn(FITNESS_GOALS).withMessage("goal tidak valid"),
  body("fitnessLevel").isIn(FITNESS_LEVELS).withMessage("fitnessLevel tidak valid"),
  body("preferredFocus")
    .isIn(WORKOUT_CATEGORIES)
    .withMessage("preferredFocus tidak valid"),
  body("workoutDaysPerWeek")
    .isInt({ min: 1, max: 7 })
    .withMessage("workoutDaysPerWeek harus 1-7"),
  body("sessionDurationMinutes")
    .isInt({ min: 10, max: 240 })
    .withMessage("sessionDurationMinutes harus 10-240"),
  body("experienceNotes")
    .optional()
    .isString()
    .withMessage("experienceNotes harus berupa string")
];

const manualPlanValidators = [
  body("title").trim().notEmpty().withMessage("title wajib diisi"),
  body("goal").isIn(FITNESS_GOALS).withMessage("goal tidak valid"),
  body("startDate").optional().isISO8601().withMessage("startDate tidak valid"),
  body("sessionsPerWeek")
    .isInt({ min: 1, max: 7 })
    .withMessage("sessionsPerWeek harus 1-7"),
  body("status").optional().isIn(PLAN_STATUSES).withMessage("status tidak valid"),
  body("sessions").isArray({ min: 1 }).withMessage("sessions minimal 1 item"),
  body("sessions.*.dayOfWeek")
    .isIn(DAYS_OF_WEEK)
    .withMessage("dayOfWeek tidak valid"),
  body("sessions.*.title")
    .trim()
    .notEmpty()
    .withMessage("title sesi wajib diisi"),
  body("sessions.*.category")
    .isIn(WORKOUT_CATEGORIES)
    .withMessage("category sesi tidak valid"),
  body("sessions.*.focusArea")
    .trim()
    .notEmpty()
    .withMessage("focusArea wajib diisi"),
  body("sessions.*.targetDurationMinutes")
    .isInt({ min: 10, max: 240 })
    .withMessage("targetDurationMinutes harus 10-240"),
  body("sessions.*.restSeconds")
    .isInt({ min: 30, max: 180 })
    .withMessage("restSeconds harus 30-180"),
  body("sessions.*.guidance")
    .trim()
    .notEmpty()
    .withMessage("guidance wajib diisi"),
  body("sessions.*.orderInDay")
    .optional()
    .isInt({ min: 1 })
    .withMessage("orderInDay minimal 1"),
  body("sessions").custom((sessions) => {
    for (const session of sessions) {
      if (session.category === "CARDIO" && (session.targetDistanceKm == null || Number.isNaN(Number(session.targetDistanceKm)))) {
        throw new Error("targetDistanceKm wajib untuk sesi CARDIO");
      }
      if (
        session.category === "STRENGTH" &&
        (session.targetSets == null || session.targetReps == null)
      ) {
        throw new Error("targetSets dan targetReps wajib untuk sesi STRENGTH");
      }
    }
    return true;
  })
];

const manualLogValidators = [
  body("sessionId").isInt({ min: 1 }).withMessage("sessionId tidak valid"),
  body("performedOn").optional().isISO8601().withMessage("performedOn tidak valid"),
  body("actualDurationMinutes")
    .isInt({ min: 1, max: 300 })
    .withMessage("actualDurationMinutes harus 1-300"),
  body("actualDistanceKm")
    .optional({ nullable: true })
    .isFloat({ min: 0 })
    .withMessage("actualDistanceKm tidak valid"),
  body("completedSets")
    .optional({ nullable: true })
    .isInt({ min: 0 })
    .withMessage("completedSets tidak valid"),
  body("completedReps")
    .optional({ nullable: true })
    .isInt({ min: 0 })
    .withMessage("completedReps tidak valid"),
  body("completionPercent")
    .isInt({ min: 0, max: 100 })
    .withMessage("completionPercent harus 0-100"),
  body("exertionScore")
    .isInt({ min: 1, max: 10 })
    .withMessage("exertionScore harus 1-10"),
  body("userFeedback")
    .optional()
    .isString()
    .withMessage("userFeedback harus string"),
  body("wasCompleted").isBoolean().withMessage("wasCompleted harus boolean").toBoolean()
];

const coachChatValidators = [
  body("messages")
    .isArray({ min: 1 })
    .withMessage("messages minimal berisi 1 pesan"),
  body("messages.*.role")
    .isIn(["user", "assistant"])
    .withMessage("role pesan harus user atau assistant"),
  body("messages.*.text")
    .trim()
    .notEmpty()
    .withMessage("isi pesan tidak boleh kosong")
    .isLength({ max: 4000 })
    .withMessage("isi pesan maksimal 4000 karakter")
];

router.use(authMiddleware);

router.post("/profile", profileValidators, (req, res) => {
  const validationResponse = validationErrorResponse(req, res);
  if (validationResponse) {
    return validationResponse;
  }

  const user = getUserById(req.user.userId);
  const currentProfile = getProfile(req.user.userId);
  const profile = buildProfilePayload(user, currentProfile, req.body);
  saveProfile(req.user.userId, profile);

  if (profile.fullName && profile.fullName !== user.fullName) {
    updateUserFullName(req.user.userId, profile.fullName);
  }

  return sendResponse(res, 200, true, "Profil berhasil disimpan", {
    profile
  });
});

router.get("/profile", (req, res) => {
  const profile = getProfile(req.user.userId);
  return sendResponse(res, 200, true, "Profil berhasil diambil", {
    profile
  });
});

router.post("/plans", manualPlanValidators, (req, res) => {
  const validationResponse = validationErrorResponse(req, res);
  if (validationResponse) {
    return validationResponse;
  }

  const sessions = req.body.sessions.map((session, index) => ({
    dayOfWeek: session.dayOfWeek,
    title: session.title,
    category: session.category,
    focusArea: session.focusArea,
    targetDurationMinutes: Number(session.targetDurationMinutes),
    targetDistanceKm:
      session.targetDistanceKm == null ? null : Number(session.targetDistanceKm),
    targetSets: session.targetSets == null ? null : Number(session.targetSets),
    targetReps: session.targetReps == null ? null : Number(session.targetReps),
    restSeconds: Number(session.restSeconds),
    guidance: session.guidance,
    orderInDay: session.orderInDay ?? index + 1
  }));

  const plan = finalizePlanSessions(
    buildPlanRecord(req.user.userId, req.body, sessions)
  );
  savePlan(req.user.userId, plan);

  return sendResponse(res, 201, true, "Workout plan berhasil dibuat", {
    plan: {
      ...plan,
      sessions: sortSessions(plan.sessions)
    }
  });
});

router.post("/plans/starter", (req, res) => {
  const profile = getProfile(req.user.userId);
  if (!profile) {
    return sendResponse(res, 400, false, "Profil pengguna belum tersedia", {});
  }

  const starterDraft = buildStarterPlan(profile);
  const plan = finalizePlanSessions(
    buildPlanRecord(
      req.user.userId,
      {
        title: starterDraft.title,
        goal: profile.goal,
        startDate: starterDraft.startDate,
        sessionsPerWeek: starterDraft.sessionsPerWeek,
        status: "ACTIVE"
      },
      starterDraft.sessions
    )
  );

  savePlan(req.user.userId, plan);

  return sendResponse(res, 201, true, "Starter plan berhasil dibuat", {
    plan: {
      ...plan,
      sessions: sortSessions(plan.sessions)
    }
  });
});

router.get("/plans", (req, res) => {
  const plans = listPlans(req.user.userId)
    .map((plan) => ({
      ...plan,
      sessions: sortSessions(plan.sessions || [])
    }))
    .sort((left, right) => {
      if (left.startDate === right.startDate) {
        return right.id - left.id;
      }
      return right.startDate.localeCompare(left.startDate);
    });

  return sendResponse(res, 200, true, "Daftar plan berhasil diambil", {
    plans
  });
});

router.post("/logs", manualLogValidators, (req, res) => {
  const validationResponse = validationErrorResponse(req, res);
  if (validationResponse) {
    return validationResponse;
  }

  const sessionLookup = findSessionById(req.user.userId, req.body.sessionId);
  if (!sessionLookup) {
    return sendResponse(res, 404, false, "Session tidak ditemukan", {});
  }

  const log = {
    id: nextId("log"),
    sessionId: Number(req.body.sessionId),
    sessionTitle: sessionLookup.session.title,
    performedOn: req.body.performedOn ? req.body.performedOn.slice(0, 10) : toDateString(),
    actualDurationMinutes: Number(req.body.actualDurationMinutes),
    actualDistanceKm:
      req.body.actualDistanceKm == null ? null : Number(req.body.actualDistanceKm),
    completedSets: req.body.completedSets == null ? null : Number(req.body.completedSets),
    completedReps: req.body.completedReps == null ? null : Number(req.body.completedReps),
    completionPercent: Number(req.body.completionPercent),
    exertionScore: Number(req.body.exertionScore),
    userFeedback: req.body.userFeedback || "",
    wasCompleted: Boolean(req.body.wasCompleted)
  };

  saveLog(req.user.userId, log);
  return sendResponse(res, 201, true, "Workout log berhasil disimpan", {
    log
  });
});

router.post("/logs/quick", (req, res) => {
  const activePlan = getActivePlan(req.user.userId);
  if (!activePlan || !activePlan.sessions.length) {
    return sendResponse(res, 400, false, "Active plan belum tersedia", {});
  }

  const currentLogs = listLogs(req.user.userId);
  const latestRecommendation = latestRecommendationForUser(req.user.userId);
  const log = buildQuickWorkoutLog({
    activePlan,
    logs: currentLogs,
    latestRecommendation,
    logId: nextId("log")
  });

  saveLog(req.user.userId, log);
  const recommendation = generateAndStoreRecommendation(req.user.userId);

  return sendResponse(res, 201, true, "Quick workout berhasil dicatat", {
    log,
    latestRecommendation: recommendation
  });
});

router.get(
  "/logs",
  [query("limit").optional().isInt({ min: 1, max: 100 }).withMessage("limit harus 1-100")],
  (req, res) => {
    const validationResponse = validationErrorResponse(req, res);
    if (validationResponse) {
      return validationResponse;
    }

    const limit = req.query.limit ? Number(req.query.limit) : 20;
    const logs = sortLogsNewestFirst(listLogs(req.user.userId)).slice(0, limit);
    return sendResponse(res, 200, true, "Daftar log berhasil diambil", {
      logs
    });
  }
);

router.post("/recommendation/generate", (req, res) => {
  const profile = getProfile(req.user.userId);
  if (!profile) {
    return sendResponse(res, 400, false, "Profil pengguna belum tersedia", {});
  }

  const recommendation = generateAndStoreRecommendation(req.user.userId);
  return sendResponse(res, 201, true, "Rekomendasi berhasil dibuat", {
    recommendation
  });
});

router.get("/recommendation", (req, res) => {
  const recommendation = latestRecommendationForUser(req.user.userId);
  return sendResponse(res, 200, true, "Rekomendasi terbaru berhasil diambil", {
    recommendation
  });
});

router.get("/recommendations", (req, res) => {
  const recommendations = sortRecommendationsNewestFirst(listRecommendations(req.user.userId));
  return sendResponse(res, 200, true, "Riwayat rekomendasi berhasil diambil", {
    recommendations
  });
});

router.post(
  "/recommendation/feedback",
  [body("wasHelpful").isBoolean().withMessage("wasHelpful harus boolean").toBoolean()],
  (req, res) => {
    const validationResponse = validationErrorResponse(req, res);
    if (validationResponse) {
      return validationResponse;
    }

    const latestRecommendation = latestRecommendationForUser(req.user.userId);
    if (!latestRecommendation) {
      return sendResponse(res, 404, false, "Rekomendasi terbaru belum tersedia", {});
    }

    const feedback = buildRecommendationFeedback({
      recommendationId: latestRecommendation.id,
      feedbackId: nextId("feedback"),
      wasHelpful: req.body.wasHelpful
    });

    saveFeedback(req.user.userId, feedback);
    const recommendation = generateAndStoreRecommendation(req.user.userId);

    return sendResponse(res, 201, true, "Feedback rekomendasi berhasil disimpan", {
      feedback,
      latestRecommendation: recommendation
    });
  }
);

router.get("/progress", (req, res) => {
  const activePlan = getActivePlan(req.user.userId);
  const progressSummary = analyzeProgress(
    activePlan?.sessionsPerWeek || 0,
    listLogs(req.user.userId)
  );

  return sendResponse(res, 200, true, "Ringkasan progres berhasil diambil", {
    progressSummary
  });
});

router.get("/dashboard", (req, res) => {
  const dashboard = buildDashboardSnapshot({
    profile: getProfile(req.user.userId),
    activePlan: getActivePlan(req.user.userId),
    logs: listLogs(req.user.userId),
    latestRecommendation: latestRecommendationForUser(req.user.userId),
    feedbackCount: countFeedback(req.user.userId)
  });

  return sendResponse(res, 200, true, "Dashboard snapshot berhasil diambil", {
    dashboard
  });
});

router.post("/chat", coachChatValidators, async (req, res) => {
  const validationResponse = validationErrorResponse(req, res);
  if (validationResponse) {
    return validationResponse;
  }

  try {
    const user = getUserById(req.user.userId);
    const profile = getProfile(req.user.userId);
    const activePlan = getActivePlan(req.user.userId);
    const recommendation = latestRecommendationForUser(req.user.userId);
    const recentLogs = sortLogsNewestFirst(listLogs(req.user.userId)).slice(0, 5);

    const result = await generateCoachReply({
      apiKey: process.env.GEMINI_API_KEY,
      user,
      profile,
      activePlan,
      recommendation,
      recentLogs,
      messages: req.body.messages
    });

    return sendResponse(res, 200, true, "Balasan AI Coach berhasil dibuat", {
      reply: result.reply,
      model: result.model,
      proposal: result.proposal ?? null
    });
  } catch (error) {
    return sendResponse(
      res,
      500,
      false,
      error.message || "AI Coach gagal memberikan balasan",
      {}
    );
  }
});

module.exports = router;
