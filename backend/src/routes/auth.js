const express = require("express");
const bcrypt = require("bcryptjs");
const { OAuth2Client } = require("google-auth-library");
const { body, validationResult } = require("express-validator");
const {
  FITNESS_GOALS,
  FITNESS_LEVELS,
  WORKOUT_CATEGORIES,
  archiveActivePlans,
  createUser,
  getActivePlan,
  getProfile,
  getUserByEmail,
  getUserByGoogleSubject,
  getUserById,
  linkGoogleAccount,
  nextId,
  nowIso,
  savePlan,
  saveProfile,
  saveRecommendation,
  updateUserFullName,
  sanitizeUser
} = require("../models/store");
const {
  authMiddleware,
  buildAuthTokens,
  verifyRefreshToken
} = require("../middleware/auth");
const {
  buildStarterPlan,
  generateRecommendation,
  sortSessions,
  toDateString
} = require("../services/recommendationEngine");

const router = express.Router();
const SALT_ROUNDS = 12;
const GOOGLE_WEB_CLIENT_ID =
  process.env.GOOGLE_WEB_CLIENT_ID ||
  "142276241927-2tkc91a8j0affb1ahtm72ea4me4h9lfm.apps.googleusercontent.com";
const googleClient = new OAuth2Client(GOOGLE_WEB_CLIENT_ID);

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

function buildSurveyProfilePayload(user, input) {
  return {
    id: user.id,
    fullName: input.fullName ?? user.fullName,
    age: Number(input.age),
    weightKg: Number(input.weightKg),
    heightCm: Number(input.heightCm),
    genderLabel: "Belum diisi",
    goal: input.goal,
    fitnessLevel: input.fitnessLevel,
    preferredFocus: input.preferredFocus,
    workoutDaysPerWeek: Number(input.workoutDaysPerWeek),
    sessionDurationMinutes: Number(input.sessionDurationMinutes),
    experienceNotes: input.experienceNotes || "",
    createdAt: nowIso()
  };
}

function buildPlanRecord(userId, input, sessions) {
  archiveActivePlans(userId);

  return {
    id: nextId("plan"),
    userId,
    title: input.title,
    goal: input.goal,
    startDate: input.startDate || toDateString(),
    sessionsPerWeek: Number(input.sessionsPerWeek),
    status: "ACTIVE",
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

function ensureStarterPlan(userId, profile) {
  const activePlan = getActivePlan(userId);
  if (activePlan) {
    return {
      ...activePlan,
      sessions: sortSessions(activePlan.sessions || [])
    };
  }

  const starterDraft = buildStarterPlan(profile);
  const plan = finalizePlanSessions(
    buildPlanRecord(
      userId,
      {
        title: starterDraft.title,
        goal: profile.goal,
        startDate: starterDraft.startDate,
        sessionsPerWeek: starterDraft.sessionsPerWeek
      },
      starterDraft.sessions
    )
  );

  savePlan(userId, plan);
  return {
    ...plan,
    sessions: sortSessions(plan.sessions || [])
  };
}

function generateAndStoreRecommendation(userId) {
  const profile = getProfile(userId);
  if (!profile) {
    return null;
  }

  const activePlan = getActivePlan(userId);
  const recommendation = generateRecommendation({
    userId,
    profile,
    sessions: activePlan?.sessions || [],
    recentLogs: [],
    feedback: [],
    recommendationId: nextId("recommendation")
  });

  return saveRecommendation(userId, recommendation);
}

router.post(
  "/register",
  [
    body("email").isEmail().withMessage("Email tidak valid").normalizeEmail(),
    body("password")
      .isLength({ min: 8 })
      .withMessage("Password minimal 8 karakter"),
    body("fullName")
      .trim()
      .isLength({ min: 3 })
      .withMessage("Nama lengkap minimal 3 karakter")
  ],
  async (req, res) => {
    const validationResponse = validationErrorResponse(req, res);
    if (validationResponse) {
      return validationResponse;
    }

    const { email, password, fullName } = req.body;
    const existingUser = getUserByEmail(email);
    if (existingUser) {
      return sendResponse(res, 409, false, "Email sudah terdaftar", {});
    }

    const passwordHash = await bcrypt.hash(password, SALT_ROUNDS);
    const user = createUser({ email, passwordHash, fullName });
    const tokens = buildAuthTokens(user);

    return sendResponse(res, 201, true, "Registrasi berhasil", {
      user: sanitizeUser(user),
      ...tokens
    });
  }
);

router.post(
  "/login",
  [
    body("email").isEmail().withMessage("Email tidak valid").normalizeEmail(),
    body("password").notEmpty().withMessage("Password wajib diisi")
  ],
  async (req, res) => {
    const validationResponse = validationErrorResponse(req, res);
    if (validationResponse) {
      return validationResponse;
    }

    const { email, password } = req.body;
    const user = getUserByEmail(email);
    if (!user || !user.passwordHash) {
      return sendResponse(res, 401, false, "Email atau password salah", {});
    }

    const isValidPassword = await bcrypt.compare(password, user.passwordHash);
    if (!isValidPassword) {
      return sendResponse(res, 401, false, "Email atau password salah", {});
    }

    const tokens = buildAuthTokens(user);
    return sendResponse(res, 200, true, "Login berhasil", {
      user: sanitizeUser(user),
      ...tokens
    });
  }
);

router.post(
  "/google",
  [body("idToken").notEmpty().withMessage("Google ID token wajib diisi")],
  async (req, res) => {
    const validationResponse = validationErrorResponse(req, res);
    if (validationResponse) {
      return validationResponse;
    }

    try {
      const ticket = await googleClient.verifyIdToken({
        idToken: req.body.idToken,
        audience: GOOGLE_WEB_CLIENT_ID
      });
      const payload = ticket.getPayload();

      if (!payload?.sub || !payload?.email || payload.email_verified !== true) {
        return sendResponse(res, 401, false, "Google token tidak valid", {});
      }

      let user =
        getUserByGoogleSubject(payload.sub) ||
        getUserByEmail(payload.email);

      if (!user) {
        user = createUser({
          email: payload.email,
          fullName: payload.name || payload.email,
          passwordHash: null,
          authProvider: "GOOGLE",
          googleSubject: payload.sub,
          avatarUrl: payload.picture || null
        });
      } else {
        user = linkGoogleAccount(user.id, {
          googleSubject: payload.sub,
          fullName: payload.name || user.fullName,
          avatarUrl: payload.picture || null
        });
      }

      const tokens = buildAuthTokens(user);
      return sendResponse(res, 200, true, "Login Google berhasil", {
        user: sanitizeUser(user),
        ...tokens
      });
    } catch (error) {
      return sendResponse(res, 401, false, "Google token tidak valid atau kedaluwarsa", {});
    }
  }
);

router.post(
  "/survey",
  authMiddleware,
  [
    body("fullName")
      .optional()
      .trim()
      .isLength({ min: 3 })
      .withMessage("Nama lengkap minimal 3 karakter"),
    body("age")
      .isInt({ min: 10, max: 100 })
      .withMessage("Umur harus 10 sampai 100 tahun"),
    body("weightKg")
      .isFloat({ min: 30, max: 250 })
      .withMessage("Berat badan harus 30 sampai 250 kg")
      .toFloat(),
    body("heightCm")
      .isInt({ min: 120, max: 230 })
      .withMessage("Tinggi badan harus 120 sampai 230 cm"),
    body("goal").isIn(FITNESS_GOALS).withMessage("Goal latihan tidak valid"),
    body("fitnessLevel")
      .isIn(FITNESS_LEVELS)
      .withMessage("Tingkat pengalaman tidak valid"),
    body("preferredFocus")
      .isIn(WORKOUT_CATEGORIES)
      .withMessage("Fokus latihan tidak valid"),
    body("workoutDaysPerWeek")
      .isInt({ min: 1, max: 7 })
      .withMessage("Frekuensi latihan harus 1 sampai 7 hari"),
    body("sessionDurationMinutes")
      .isInt({ min: 10, max: 240 })
      .withMessage("Durasi sesi harus 10 sampai 240 menit"),
    body("experienceNotes")
      .optional()
      .isString()
      .withMessage("Catatan pengalaman harus berupa teks")
  ],
  (req, res) => {
    const validationResponse = validationErrorResponse(req, res);
    if (validationResponse) {
      return validationResponse;
    }

    const user = getUserById(req.user.userId);
    if (!user) {
      return sendResponse(res, 404, false, "User tidak ditemukan", {});
    }

    const profile = saveProfile(
      req.user.userId,
      buildSurveyProfilePayload(user, req.body)
    );

    if (profile.fullName && profile.fullName !== user.fullName) {
      updateUserFullName(req.user.userId, profile.fullName);
    }

    const plan = ensureStarterPlan(req.user.userId, profile);
    const recommendation = generateAndStoreRecommendation(req.user.userId);

    return sendResponse(res, 200, true, "Survey berhasil disimpan", {
      user: sanitizeUser(getUserById(req.user.userId)),
      profile,
      plan,
      recommendation
    });
  }
);

router.get("/survey", authMiddleware, (req, res) => {
  const profile = getProfile(req.user.userId);
  const plan = getActivePlan(req.user.userId);

  return sendResponse(res, 200, true, "Status survey berhasil diambil", {
    hasCompletedSurvey: Boolean(profile),
    profile,
    plan: plan
      ? {
          ...plan,
          sessions: sortSessions(plan.sessions || [])
        }
      : null
  });
});

router.post(
  "/refresh",
  [body("refreshToken").isJWT().withMessage("Refresh token tidak valid")],
  (req, res) => {
    const validationResponse = validationErrorResponse(req, res);
    if (validationResponse) {
      return validationResponse;
    }

    try {
      const { refreshToken } = req.body;
      const payload = verifyRefreshToken(refreshToken);
      const user = getUserById(payload.userId);

      if (!user) {
        return sendResponse(res, 401, false, "Refresh token tidak valid", {});
      }

      const tokens = buildAuthTokens(user);
      return sendResponse(res, 200, true, "Access token berhasil diperbarui", {
        user: sanitizeUser(user),
        ...tokens
      });
    } catch (error) {
      return sendResponse(res, 401, false, "Refresh token tidak valid atau kedaluwarsa", {});
    }
  }
);

router.get("/me", authMiddleware, (req, res) => {
  const user = getUserById(req.user.userId);
  return sendResponse(res, 200, true, "Data user aktif berhasil diambil", {
    user: sanitizeUser(user)
  });
});

router.post("/logout", authMiddleware, (req, res) => {
  return sendResponse(res, 200, true, "Logout berhasil", {
    user: {
      userId: req.user.userId,
      email: req.user.email
    }
  });
});

module.exports = router;
