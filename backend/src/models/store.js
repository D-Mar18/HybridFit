const FITNESS_GOALS = ["GENERAL_FITNESS", "WEIGHT_LOSS", "MUSCLE_GAIN", "ENDURANCE"];
const FITNESS_LEVELS = ["BEGINNER", "INTERMEDIATE", "ADVANCED"];
const WORKOUT_CATEGORIES = ["STRENGTH", "CARDIO", "MOBILITY", "RECOVERY"];
const PLAN_STATUSES = ["DRAFT", "ACTIVE", "ARCHIVED"];
const RECOMMENDATION_ACTIONS = ["INCREASE", "MAINTAIN", "RECOVER"];
const PROGRESS_TRENDS = ["IMPROVING", "STABLE", "NEEDS_ATTENTION"];
const DAYS_OF_WEEK = [
  "MONDAY",
  "TUESDAY",
  "WEDNESDAY",
  "THURSDAY",
  "FRIDAY",
  "SATURDAY",
  "SUNDAY"
];

const usersById = new Map();
const userIdsByEmail = new Map();
const userIdsByGoogleSubject = new Map();
const profilesByUserId = new Map();
const plansByUserId = new Map();
const logsByUserId = new Map();
const recommendationsByUserId = new Map();
const feedbackByUserId = new Map();

const counters = {
  user: 1,
  plan: 1,
  session: 1,
  log: 1,
  recommendation: 1,
  feedback: 1
};

function nowIso() {
  return new Date().toISOString();
}

function normalizeEmail(email) {
  return String(email || "").trim().toLowerCase();
}

function nextId(key) {
  const nextValue = counters[key];
  counters[key] += 1;
  return nextValue;
}

function ensureUserState(userId) {
  if (!plansByUserId.has(userId)) {
    plansByUserId.set(userId, []);
  }
  if (!logsByUserId.has(userId)) {
    logsByUserId.set(userId, []);
  }
  if (!recommendationsByUserId.has(userId)) {
    recommendationsByUserId.set(userId, []);
  }
  if (!feedbackByUserId.has(userId)) {
    feedbackByUserId.set(userId, []);
  }
}

function createUser({
  email,
  fullName,
  passwordHash = null,
  authProvider = "LOCAL",
  googleSubject = null,
  avatarUrl = null
}) {
  const normalizedEmail = normalizeEmail(email);
  const user = {
    id: nextId("user"),
    email: normalizedEmail,
    fullName: String(fullName || "").trim(),
    passwordHash,
    authProvider,
    googleSubject,
    avatarUrl,
    createdAt: nowIso()
  };

  usersById.set(user.id, user);
  userIdsByEmail.set(normalizedEmail, user.id);
  if (googleSubject) {
    userIdsByGoogleSubject.set(String(googleSubject), user.id);
  }
  ensureUserState(user.id);
  return user;
}

function getUserById(userId) {
  return usersById.get(Number(userId)) || null;
}

function getUserByEmail(email) {
  const userId = userIdsByEmail.get(normalizeEmail(email));
  return userId ? getUserById(userId) : null;
}

function getUserByGoogleSubject(googleSubject) {
  const userId = userIdsByGoogleSubject.get(String(googleSubject || ""));
  return userId ? getUserById(userId) : null;
}

function updateUserFullName(userId, fullName) {
  const user = getUserById(userId);
  if (!user) {
    return null;
  }

  user.fullName = String(fullName || "").trim();
  return user;
}

function linkGoogleAccount(userId, { googleSubject, fullName, avatarUrl }) {
  const user = getUserById(userId);
  if (!user) {
    return null;
  }

  if (googleSubject) {
    user.googleSubject = String(googleSubject);
    userIdsByGoogleSubject.set(String(googleSubject), user.id);
  }

  if (String(fullName || "").trim()) {
    user.fullName = String(fullName).trim();
  }

  user.authProvider = "GOOGLE";
  user.avatarUrl = avatarUrl || user.avatarUrl || null;
  return user;
}

function hasCompletedSurvey(userId) {
  return profilesByUserId.has(Number(userId));
}

function sanitizeUser(user) {
  if (!user) {
    return null;
  }

  return {
    id: user.id,
    email: user.email,
    fullName: user.fullName,
    authProvider: user.authProvider,
    avatarUrl: user.avatarUrl,
    createdAt: user.createdAt,
    hasCompletedSurvey: hasCompletedSurvey(user.id)
  };
}

function saveProfile(userId, profile) {
  ensureUserState(userId);
  profilesByUserId.set(Number(userId), { ...profile });
  return profilesByUserId.get(Number(userId));
}

function getProfile(userId) {
  return profilesByUserId.get(Number(userId)) || null;
}

function listPlans(userId) {
  ensureUserState(userId);
  return plansByUserId.get(Number(userId));
}

function savePlan(userId, plan) {
  const normalizedUserId = Number(userId);
  ensureUserState(normalizedUserId);
  const plans = plansByUserId.get(normalizedUserId);
  const index = plans.findIndex((item) => item.id === plan.id);

  if (index >= 0) {
    plans[index] = { ...plan };
  } else {
    plans.push({ ...plan });
  }

  return plans.find((item) => item.id === plan.id) || null;
}

function archiveActivePlans(userId) {
  const plans = listPlans(userId);
  plans.forEach((plan) => {
    if (plan.status === "ACTIVE") {
      plan.status = "ARCHIVED";
    }
  });
}

function getActivePlan(userId) {
  const plans = listPlans(userId)
    .filter((plan) => plan.status === "ACTIVE")
    .sort((left, right) => {
      if (left.startDate === right.startDate) {
        return right.id - left.id;
      }
      return right.startDate.localeCompare(left.startDate);
    });

  return plans[0] || null;
}

function findSessionById(userId, sessionId) {
  const normalizedSessionId = Number(sessionId);
  for (const plan of listPlans(userId)) {
    const session = (plan.sessions || []).find((item) => item.id === normalizedSessionId);
    if (session) {
      return { plan, session };
    }
  }

  return null;
}

function listLogs(userId) {
  ensureUserState(userId);
  return logsByUserId.get(Number(userId));
}

function saveLog(userId, log) {
  const normalizedUserId = Number(userId);
  ensureUserState(normalizedUserId);
  logsByUserId.get(normalizedUserId).push({ ...log });
  return log;
}

function listRecommendations(userId) {
  ensureUserState(userId);
  return recommendationsByUserId.get(Number(userId));
}

function saveRecommendation(userId, recommendation) {
  const normalizedUserId = Number(userId);
  ensureUserState(normalizedUserId);
  recommendationsByUserId.get(normalizedUserId).push({ ...recommendation });
  return recommendation;
}

function getLatestRecommendation(userId) {
  const recommendations = listRecommendations(userId);
  return recommendations[recommendations.length - 1] || null;
}

function listFeedback(userId) {
  ensureUserState(userId);
  return feedbackByUserId.get(Number(userId));
}

function saveFeedback(userId, feedback) {
  const normalizedUserId = Number(userId);
  ensureUserState(normalizedUserId);
  feedbackByUserId.get(normalizedUserId).push({ ...feedback });
  return feedback;
}

function countFeedback(userId) {
  return listFeedback(userId).length;
}

module.exports = {
  DAYS_OF_WEEK,
  FITNESS_GOALS,
  FITNESS_LEVELS,
  PLAN_STATUSES,
  PROGRESS_TRENDS,
  RECOMMENDATION_ACTIONS,
  WORKOUT_CATEGORIES,
  archiveActivePlans,
  countFeedback,
  createUser,
  findSessionById,
  getActivePlan,
  getLatestRecommendation,
  getProfile,
  getUserByEmail,
  getUserByGoogleSubject,
  getUserById,
  hasCompletedSurvey,
  linkGoogleAccount,
  listFeedback,
  listLogs,
  listPlans,
  listRecommendations,
  nextId,
  normalizeEmail,
  nowIso,
  sanitizeUser,
  saveFeedback,
  saveLog,
  savePlan,
  saveProfile,
  saveRecommendation,
  updateUserFullName
};
