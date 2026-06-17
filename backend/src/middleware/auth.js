const jwt = require("jsonwebtoken");
const { getUserById } = require("../models/store");

const ACCESS_TOKEN_SECRET = process.env.ACCESS_TOKEN_SECRET || "hybridfit-access-secret";
const REFRESH_TOKEN_SECRET = process.env.REFRESH_TOKEN_SECRET || "hybridfit-refresh-secret";
const ACCESS_TOKEN_EXPIRES_IN = "7d";
const REFRESH_TOKEN_EXPIRES_IN = "30d";

function buildTokenPayload(user) {
  return {
    userId: user.id,
    email: user.email
  };
}

function signAccessToken(user) {
  return jwt.sign(
    {
      ...buildTokenPayload(user),
      tokenType: "access"
    },
    ACCESS_TOKEN_SECRET,
    {
      expiresIn: ACCESS_TOKEN_EXPIRES_IN
    }
  );
}

function signRefreshToken(user) {
  return jwt.sign(
    {
      ...buildTokenPayload(user),
      tokenType: "refresh"
    },
    REFRESH_TOKEN_SECRET,
    {
      expiresIn: REFRESH_TOKEN_EXPIRES_IN
    }
  );
}

function buildAuthTokens(user) {
  return {
    accessToken: signAccessToken(user),
    refreshToken: signRefreshToken(user)
  };
}

function verifyRefreshToken(token) {
  const payload = jwt.verify(token, REFRESH_TOKEN_SECRET);
  if (payload.tokenType !== "refresh") {
    throw new Error("Invalid refresh token type");
  }
  return payload;
}

function authMiddleware(req, res, next) {
  const authorization = req.headers.authorization || "";
  const [scheme, token] = authorization.split(" ");

  if (scheme !== "Bearer" || !token) {
    return res.status(401).json({
      success: false,
      message: "Bearer token dibutuhkan",
      data: {}
    });
  }

  try {
    const payload = jwt.verify(token, ACCESS_TOKEN_SECRET);
    if (payload.tokenType !== "access") {
      return res.status(401).json({
        success: false,
        message: "Token akses tidak valid",
        data: {}
      });
    }

    const user = getUserById(payload.userId);
    if (!user) {
      return res.status(401).json({
        success: false,
        message: "User tidak ditemukan",
        data: {}
      });
    }

    req.user = {
      userId: payload.userId,
      email: payload.email
    };
    return next();
  } catch (error) {
    return res.status(401).json({
      success: false,
      message: "Token akses tidak valid atau kedaluwarsa",
      data: {}
    });
  }
}

module.exports = {
  ACCESS_TOKEN_EXPIRES_IN,
  REFRESH_TOKEN_EXPIRES_IN,
  authMiddleware,
  buildAuthTokens,
  signAccessToken,
  signRefreshToken,
  verifyRefreshToken
};
