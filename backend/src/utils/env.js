const fs = require("fs");
const path = require("path");

function loadEnvFile(envPath = path.resolve(__dirname, "../../.env")) {
  if (!fs.existsSync(envPath)) {
    return;
  }

  const file = fs.readFileSync(envPath, "utf8");
  file.split(/\r?\n/).forEach((line) => {
    const trimmed = line.trim();
    if (!trimmed || trimmed.startsWith("#")) {
      return;
    }

    const separatorIndex = trimmed.indexOf("=");
    if (separatorIndex <= 0) {
      return;
    }

    const key = trimmed.slice(0, separatorIndex).trim();
    if (!key || process.env[key] != null) {
      return;
    }

    let value = trimmed.slice(separatorIndex + 1).trim();
    if (
      (value.startsWith("\"") && value.endsWith("\"")) ||
      (value.startsWith("'") && value.endsWith("'"))
    ) {
      value = value.slice(1, -1);
    }

    process.env[key] = value;
  });
}

module.exports = {
  loadEnvFile
};
