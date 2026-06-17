const express = require("express");
const { loadEnvFile } = require("./utils/env");

loadEnvFile();

const authRoutes = require("./routes/auth");
const aiRoutes = require("./routes/ai");

function createApp() {
  const app = express();

  app.use(express.json({ limit: "1mb" }));

  app.get("/", (_req, res) => {
    res.json({
      success: true,
      message: "HybridFit API aktif",
      data: {
        appPackage: "com.athallah.hybridfit",
        applicationClass: "HybridFitApplication",
        mainActivity: "MainActivity",
        endpoints: ["/api/auth", "/api/auth/survey", "/api/ai", "/api/ai/chat"]
      }
    });
  });

  app.use("/api/auth", authRoutes);
  app.use("/api/ai", aiRoutes);

  app.use((req, res) => {
    res.status(404).json({
      success: false,
      message: `Endpoint ${req.method} ${req.originalUrl} tidak ditemukan`,
      data: {}
    });
  });

  app.use((error, _req, res, _next) => {
    console.error(error);
    res.status(500).json({
      success: false,
      message: "Terjadi kesalahan pada server",
      data: {}
    });
  });

  return app;
}

function startServer(port = Number(process.env.PORT) || 3000) {
  const app = createApp();
  const server = app.listen(port, () => {
    console.log(`HybridFit API berjalan di http://localhost:${port}`);
  });

  server.on("error", (error) => {
    if (error.code === "EADDRINUSE") {
      console.error(
        `Port ${port} sedang dipakai proses lain. Tutup proses yang memakai port tersebut, atau jalankan server di port lain.`
      );
      console.error(
        "Contoh: dari folder HybridFit jalankan `npm run start:3001`."
      );
      process.exit(1);
    }

    throw error;
  });

  return server;
}

if (require.main === module) {
  startServer();
}

module.exports = {
  createApp,
  startServer
};
