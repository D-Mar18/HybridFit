const GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models";
const DEFAULT_GEMINI_MODEL = process.env.GEMINI_MODEL || "gemini-3.5-flash";
const GEMINI_FALLBACK_MODELS = [
  DEFAULT_GEMINI_MODEL,
  "gemini-2.5-flash",
  "gemini-2.0-flash"
].filter((value, index, list) => value && list.indexOf(value) === index);
const LOCAL_GUARDRAIL_MODEL = "guardrail-local";
const FITNESS_KEYWORDS = [
  "olahraga",
  "latihan",
  "workout",
  "gym",
  "lari",
  "running",
  "run",
  "jogging",
  "cardio",
  "kardio",
  "strength",
  "beban",
  "otot",
  "massa otot",
  "bulking",
  "cutting",
  "recovery",
  "pemulihan",
  "istirahat",
  "rest",
  "set",
  "sets",
  "rep",
  "reps",
  "repetisi",
  "squat",
  "deadlift",
  "bench press",
  "push up",
  "pull up",
  "plank",
  "pace",
  "kilometer",
  "km",
  "durasi",
  "menit",
  "jadwal latihan",
  "program latihan",
  "progress",
  "progres",
  "kalori",
  "langkah",
  "tidur",
  "streak",
  "mobilitas",
  "stretching",
  "pemanasan",
  "pendinginan",
  "surplus kalori",
  "defisit kalori",
  "protein",
  "nutrisi olahraga",
  "vo2max",
  "hybrid"
];
const FOLLOW_UP_KEYWORDS = [
  "yang tadi",
  "tadi",
  "itu",
  "ini",
  "kalau",
  "gimana",
  "bagaimana",
  "boleh",
  "bagaimana kalau",
  "gimana kalau",
  "kalau saya",
  "lebih baik",
  "kenapa",
  "cukup",
  "aman",
  "seminggu",
  "sehari",
  "lanjut",
  "sesuaikan",
  "naikin",
  "turunin",
  "tambah",
  "kurangi"
];
const GREETING_KEYWORDS = [
  "halo",
  "hai",
  "hi",
  "pagi",
  "siang",
  "sore",
  "malam",
  "permisi"
];
const RECOVERY_HINT_KEYWORDS = [
  "capek",
  "cape",
  "lelah",
  "pegal",
  "nyeri",
  "ngilu",
  "sesak",
  "kram",
  "recovery",
  "pemulihan"
];
const DEFAULT_REDIRECT_EXAMPLES = [
  "Buatkan jadwal gym 3 hari untuk pemula",
  "Bagaimana meningkatkan squat saya?",
  "Saya capek setelah lari, recovery-nya bagaimana?",
  "Tolong sesuaikan program latihan saya"
];
const PROGRAM_CHANGE_INTENT_KEYWORDS = [
  "buat program",
  "ubah program",
  "ganti program",
  "sesuaikan program",
  "buat jadwal",
  "ubah jadwal",
  "ganti jadwal",
  "sesuaikan jadwal"
];
const PROPOSAL_GOALS = [
  "GENERAL_FITNESS",
  "WEIGHT_LOSS",
  "MUSCLE_GAIN",
  "ENDURANCE"
];
const PROPOSAL_FOCUS = ["STRENGTH", "CARDIO", "MOBILITY", "RECOVERY"];

function normalizeText(value = "") {
  return String(value || "")
    .toLowerCase()
    .replace(/[^a-z0-9\s]/g, " ")
    .replace(/\s+/g, " ")
    .trim();
}

function containsKeyword(text, keywords) {
  return keywords.some((keyword) => text.includes(keyword));
}

function extractLatestUserMessage(messages = []) {
  for (let index = messages.length - 1; index >= 0; index -= 1) {
    const message = messages[index];
    if (message?.role !== "assistant") {
      return String(message?.text || "");
    }
  }
  return "";
}

function messageLooksLikeGreeting(text) {
  return GREETING_KEYWORDS.includes(text);
}

function messageLooksLikeFollowUp(text) {
  return (
    containsKeyword(text, FOLLOW_UP_KEYWORDS) ||
    /\b\d+\s?kali\b/.test(text) ||
    /\b(\d+)\s?(kg|km|menit|set|sets|rep|reps)\b/.test(text) ||
    text.length <= 40
  );
}

function conversationHasFitnessContext(messages = []) {
  return messages.some(
    (message) =>
      message?.role !== "assistant" &&
      containsKeyword(normalizeText(message?.text || ""), FITNESS_KEYWORDS)
  );
}

function shouldBlockNonFitnessTopic(messages = []) {
  const latestUserText = normalizeText(extractLatestUserMessage(messages));
  if (!latestUserText) {
    return false;
  }

  if (messageLooksLikeGreeting(latestUserText)) {
    return false;
  }

  if (containsKeyword(latestUserText, FITNESS_KEYWORDS)) {
    return false;
  }

  const previousMessages = messages.slice(0, -1);
  if (
    conversationHasFitnessContext(previousMessages) &&
    messageLooksLikeFollowUp(latestUserText)
  ) {
    return false;
  }

  return true;
}

function formatExamples(examples) {
  return examples.map((example) => `- ${example}`).join("\n");
}

function buildRelevantRedirectReply(messages = []) {
  const latestUserText = normalizeText(extractLatestUserMessage(messages));
  const previousMessages = messages.slice(0, -1);
  const hasFitnessContext = conversationHasFitnessContext(previousMessages);

  if (hasFitnessContext) {
    return [
      "Saya masih bisa bantu, tapi tolong balas dengan konteks latihan yang lebih spesifik supaya arahnya tepat.",
      "Coba balas salah satu seperti ini:",
      formatExamples([
        "Apakah squat 2 kali seminggu cukup untuk pemula?",
        "Untuk program kaki saya, berapa set yang cocok?",
        "Rest antar set squat sebaiknya berapa detik?"
      ])
    ].join("\n\n");
  }

  if (containsKeyword(latestUserText, RECOVERY_HINT_KEYWORDS)) {
    return [
      "Kalau rasa capek atau pegal ini terkait olahraga atau recovery, saya bisa bantu.",
      "Coba balas dengan salah satu format berikut:",
      formatExamples([
        "Saya capek setelah gym kaki kemarin, sebaiknya recovery bagaimana?",
        "Setelah lari 5 km kaki saya pegal, apa latihan besok perlu dikurangi?",
        "Latihan hari ini terasa terlalu berat, tolong turunkan intensitasnya."
      ])
    ].join("\n\n");
  }

  return [
    "AI Coach HybridFit hanya melayani topik olahraga dan kebugaran, seperti program latihan, gym, lari, recovery, progres, set, repetisi, dan jadwal latihan.",
    "Supaya saya bisa bantu dengan tepat, coba balas salah satu contoh berikut:",
    formatExamples(DEFAULT_REDIRECT_EXAMPLES)
  ].join("\n\n");
}

function looksLikeProgramChangeRequest(messages = []) {
  const latestUserText = normalizeText(extractLatestUserMessage(messages));
  if (!latestUserText) {
    return false;
  }

  return PROGRAM_CHANGE_INTENT_KEYWORDS.some((keyword) =>
    latestUserText.includes(keyword)
  );
}

function summarizeRecentLogs(logs = []) {
  if (!logs.length) {
    return "Belum ada riwayat latihan yang tersimpan.";
  }

  return logs
    .slice(-5)
    .map((log) => {
      const duration = `${log.actualDurationMinutes || 0} menit`;
      const completion = `${log.completionPercent || 0}%`;
      const exertion = `${log.exertionScore || 0}/10`;
      const distance =
        typeof log.actualDistanceKm === "number"
          ? `, jarak ${log.actualDistanceKm} km`
          : "";
      const volume =
        log.completedSets != null && log.completedReps != null
          ? `, volume ${log.completedSets} set / ${log.completedReps} reps`
          : "";
      return `- ${log.sessionTitle} pada ${log.performedOn}: durasi ${duration}${distance}${volume}, completion ${completion}, effort ${exertion}.`;
    })
    .join("\n");
}

function buildSystemInstruction({ user, profile, activePlan, recommendation, recentLogs }) {
  const identityLine = [
    "Anda adalah HybridFit AI Coach.",
    "Jawab dalam Bahasa Indonesia yang hangat, singkat, praktis, dan suportif.",
    "Layani hanya topik olahraga dan kebugaran.",
    "Fokus pada kebugaran, recovery, intensitas latihan, dan penyesuaian program.",
    "Jika user bertanya di luar topik olahraga, tolak dengan singkat lalu arahkan kembali ke topik latihan, recovery, progres, nutrisi olahraga, atau jadwal latihan.",
    "Jangan menjawab topik umum seperti coding, tugas kuliah, politik, hiburan, keuangan, atau percintaan.",
    "Jangan mengaku sebagai dokter. Untuk cedera serius atau gejala medis, arahkan user ke tenaga medis."
  ].join(" ");

  const profileLine = profile
    ? `Profil user: nama ${profile.fullName}, goal ${profile.goal}, level ${profile.fitnessLevel}, fokus ${profile.preferredFocus}, frekuensi ${profile.workoutDaysPerWeek} hari per minggu, durasi target ${profile.sessionDurationMinutes} menit.`
    : `Profil user belum lengkap. Gunakan jawaban yang aman dan arahkan user untuk melengkapi survey bila perlu.`;

  const planLine = activePlan
    ? `Program aktif: ${activePlan.title} dengan ${activePlan.sessionsPerWeek} sesi per minggu. Sesi terdekat: ${(activePlan.sessions || [])
        .slice(0, 3)
        .map((session) => `${session.title} (${session.category}, ${session.dayOfWeek})`)
        .join(", ")}.`
    : "Belum ada program aktif di backend.";

  const recommendationLine = recommendation
    ? `Rekomendasi terbaru: aksi ${recommendation.action}, durasi target ${recommendation.targetDurationMinutes} menit, rest ${recommendation.restSeconds} detik. Ringkasan: ${recommendation.summary}`
    : "Belum ada rekomendasi AI tersimpan.";

  const logsLine = `Riwayat latihan terbaru:\n${summarizeRecentLogs(recentLogs)}`;

  const personalizationLine = `Panggil user sebagai ${user.fullName || user.email}. Jika user bertanya soal beban, set, reps, atau lari, kaitkan jawaban dengan profil dan progresnya.`;
  const programActionLine = [
    "Jika user meminta membuat, mengubah, mengganti, atau menyesuaikan program atau jadwal latihan, Anda boleh menawarkan perubahan program.",
    "Untuk kasus itu, jangan bilang program sudah diubah. Anda harus meminta persetujuan user lebih dulu.",
    "Jika memang perlu konfirmasi perubahan program, isi field proposal dengan data yang spesifik.",
    "Jika tidak ada permintaan perubahan program yang jelas, proposal harus null.",
    "Program gym/beban biasanya gunakan goal MUSCLE_GAIN dan preferredFocus STRENGTH.",
    "Program lari/cardio biasanya gunakan goal ENDURANCE dan preferredFocus CARDIO.",
    "Program hybrid gunakan goal GENERAL_FITNESS dan preferredFocus STRENGTH kecuali ada alasan kuat lain.",
    "Gunakan sessionDurationMinutes dari permintaan user bila ada; jika tidak ada, pakai durasi target profil user, atau 45 menit bila profil belum ada."
  ].join(" ");
  const outputFormatLine = [
    "Anda wajib menjawab dalam JSON valid tanpa markdown dan tanpa teks tambahan di luar JSON.",
    'Format wajib: {"reply":"string","proposal":null}',
    "atau",
    '{"reply":"string","proposal":{"goal":"MUSCLE_GAIN","preferredFocus":"STRENGTH","workoutDaysPerWeek":4,"sessionDurationMinutes":45,"programLabel":"gym 4 hari","summaryLabel":"Program gym 4 hari • target 45 menit per sesi"}}'
  ].join(" ");

  return [
    identityLine,
    personalizationLine,
    profileLine,
    planLine,
    recommendationLine,
    logsLine,
    programActionLine,
    outputFormatLine
  ].join("\n\n");
}

function normalizeChatHistory(messages = []) {
  return messages.map((message) => ({
    role: message.role === "assistant" ? "model" : "user",
    parts: [{ text: String(message.text || "").trim() }]
  }));
}

function extractReply(data) {
  const parts = data?.candidates?.[0]?.content?.parts || [];
  const reply = parts
    .map((part) => part?.text || "")
    .join("\n")
    .trim();

  if (!reply) {
    throw new Error("Gemini tidak mengembalikan jawaban teks.");
  }

  return reply;
}

function parseCoachJsonReply(rawText) {
  const fallback = {
    reply: String(rawText || "").trim(),
    proposal: null
  };

  if (!rawText || typeof rawText !== "string") {
    return fallback;
  }

  try {
    const parsed = JSON.parse(rawText);
    const reply = String(parsed?.reply || "").trim();
    const rawProposal = parsed?.proposal;
    let proposal = null;

    if (rawProposal && typeof rawProposal === "object") {
      const goal = String(rawProposal.goal || "").trim().toUpperCase();
      const preferredFocus = String(rawProposal.preferredFocus || "")
        .trim()
        .toUpperCase();
      const workoutDaysPerWeek = Number(rawProposal.workoutDaysPerWeek);
      const sessionDurationMinutes = Number(rawProposal.sessionDurationMinutes);
      const programLabel = String(rawProposal.programLabel || "").trim();
      const summaryLabel = String(rawProposal.summaryLabel || "").trim();

      if (
        PROPOSAL_GOALS.includes(goal) &&
        PROPOSAL_FOCUS.includes(preferredFocus) &&
        Number.isInteger(workoutDaysPerWeek) &&
        workoutDaysPerWeek >= 1 &&
        workoutDaysPerWeek <= 7 &&
        Number.isInteger(sessionDurationMinutes) &&
        sessionDurationMinutes >= 10 &&
        sessionDurationMinutes <= 240 &&
        programLabel &&
        summaryLabel
      ) {
        proposal = {
          goal,
          preferredFocus,
          workoutDaysPerWeek,
          sessionDurationMinutes,
          programLabel,
          summaryLabel
        };
      }
    }

    if (!reply) {
      return fallback;
    }

    return {
      reply,
      proposal
    };
  } catch (_error) {
    return fallback;
  }
}

function looksTemporaryModelFailure(error) {
  const message = String(error?.message || "").toLowerCase();
  return (
    message.includes("high demand") ||
    message.includes("try again later") ||
    message.includes("currently unavailable") ||
    message.includes("service unavailable") ||
    message.includes("resource exhausted") ||
    message.includes("quota") ||
    message.includes("429") ||
    message.includes("503")
  );
}

async function generateCoachReply({
  apiKey,
  user,
  profile,
  activePlan,
  recommendation,
  recentLogs,
  messages
}) {
  if (shouldBlockNonFitnessTopic(messages)) {
    return {
      reply: buildRelevantRedirectReply(messages),
      model: LOCAL_GUARDRAIL_MODEL,
      proposal: null
    };
  }

  if (!apiKey) {
    throw new Error("GEMINI_API_KEY belum diatur di backend.");
  }

  const payload = {
    system_instruction: {
      parts: [
        {
          text: buildSystemInstruction({
            user,
            profile,
            activePlan,
            recommendation,
            recentLogs
          })
        }
      ]
    },
    contents: normalizeChatHistory(messages),
    generationConfig: {
      responseMimeType: "application/json",
      thinkingConfig: {
        thinkingLevel: "low"
      }
    }
  };

  let lastError = null;

  for (const modelName of GEMINI_FALLBACK_MODELS) {
    const response = await fetch(
      `${GEMINI_API_URL}/${encodeURIComponent(modelName)}:generateContent`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "x-goog-api-key": apiKey
        },
        body: JSON.stringify(payload)
      }
    );

    const responseText = await response.text();
    const data = responseText ? JSON.parse(responseText) : {};

    if (!response.ok) {
      const message =
        data?.error?.message ||
        `Gemini API error dengan status ${response.status}.`;
      lastError = new Error(message);

      if (response.status === 503 || response.status === 429) {
        continue;
      }

      throw lastError;
    }

    const parsed = parseCoachJsonReply(extractReply(data));

    if (looksLikeProgramChangeRequest(messages) && !parsed.proposal) {
      return {
        reply: "Saya bisa bantu menyesuaikan program Anda. Jelaskan jenis program, jumlah hari latihan, dan bila perlu durasi per sesi, lalu saya siapkan konfirmasi perubahan program.",
        model: modelName,
        proposal: null
      };
    }

    return {
      reply: parsed.reply,
      model: modelName,
      proposal: parsed.proposal
    };
  }

  if (looksTemporaryModelFailure(lastError)) {
    throw new Error("Gemini sedang sibuk sementara. Coba lagi beberapa saat.");
  }

  throw lastError || new Error("Semua model Gemini sedang tidak tersedia.");
}

module.exports = {
  DEFAULT_GEMINI_MODEL,
  generateCoachReply
};
