package com.athallah.hybridfit.data.remote

import com.athallah.hybridfit.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

data class RemoteAuthUser(
    val id: Int,
    val email: String,
    val fullName: String,
    val authProvider: String,
    val avatarUrl: String?,
    val createdAt: String,
    val hasCompletedSurvey: Boolean
)

data class AuthSession(
    val user: RemoteAuthUser,
    val accessToken: String,
    val refreshToken: String
)

class HybridFitAuthApi(
    private val baseUrl: String = BuildConfig.HYBRIDFIT_API_BASE_URL
) {
    suspend fun register(fullName: String, email: String, password: String): AuthSession =
        withContext(Dispatchers.IO) {
            val payload = JSONObject()
                .put("fullName", fullName)
                .put("email", email)
                .put("password", password)
            postJsonForSession(
                path = "/api/auth/register",
                body = payload,
                defaultAuthProvider = "LOCAL"
            )
        }

    suspend fun login(email: String, password: String): AuthSession = withContext(Dispatchers.IO) {
        val payload = JSONObject()
            .put("email", email)
            .put("password", password)
        postJsonForSession(
            path = "/api/auth/login",
            body = payload,
            defaultAuthProvider = "LOCAL"
        )
    }

    suspend fun loginWithGoogle(idToken: String): AuthSession = withContext(Dispatchers.IO) {
        val payload = JSONObject()
            .put("idToken", idToken)
        postJsonForSession(
            path = "/api/auth/google",
            body = payload,
            defaultAuthProvider = "GOOGLE"
        )
    }

    suspend fun logout(accessToken: String): String = withContext(Dispatchers.IO) {
        postWithoutBodyForMessage("/api/auth/logout", accessToken)
    }

    suspend fun submitSurvey(
        accessToken: String,
        fullName: String,
        age: Int,
        weightKg: Double,
        heightCm: Int,
        goal: String,
        fitnessLevel: String,
        preferredFocus: String,
        workoutDaysPerWeek: Int,
        sessionDurationMinutes: Int,
        experienceNotes: String
    ): RemoteAuthUser = withContext(Dispatchers.IO) {
        val payload = JSONObject()
            .put("fullName", fullName)
            .put("age", age)
            .put("weightKg", weightKg)
            .put("heightCm", heightCm)
            .put("goal", goal)
            .put("fitnessLevel", fitnessLevel)
            .put("preferredFocus", preferredFocus)
            .put("workoutDaysPerWeek", workoutDaysPerWeek)
            .put("sessionDurationMinutes", sessionDurationMinutes)
            .put("experienceNotes", experienceNotes)

        postJsonForUser(
            path = "/api/auth/survey",
            body = payload,
            accessToken = accessToken,
            fallbackMessage = "Pengiriman survey gagal."
        )
    }

    private fun postJsonForSession(
        path: String,
        body: JSONObject,
        defaultAuthProvider: String
    ): AuthSession {
        val url = URL("${baseUrl.trimEnd('/')}$path")
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            doOutput = true
            connectTimeout = 15_000
            readTimeout = 15_000
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
        }

        return try {
            connection.outputStream.use { output ->
                output.write(body.toString().toByteArray(StandardCharsets.UTF_8))
            }

            val responseBody = readResponseBody(connection)
            val json = if (responseBody.isBlank()) JSONObject() else JSONObject(responseBody)
            val success = json.optBoolean("success", false)
            val message = extractResponseMessage(
                json = json,
                fallbackMessage = "Permintaan autentikasi gagal."
            )
            if (!success) {
                throw IllegalStateException(message)
            }

            val data = json.optJSONObject("data")
                ?: throw IllegalStateException("Data autentikasi dari backend tidak ditemukan.")
            val userJson = data.optJSONObject("user")
                ?: throw IllegalStateException("Data pengguna dari backend tidak ditemukan.")
            val accessToken = data.optString("accessToken")
            val refreshToken = data.optString("refreshToken")

            if (accessToken.isBlank() || refreshToken.isBlank()) {
                throw IllegalStateException("Token autentikasi dari backend tidak lengkap.")
            }

            AuthSession(
                user = parseUser(
                    userJson = userJson,
                    defaultAuthProvider = defaultAuthProvider
                ),
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } finally {
            connection.disconnect()
        }
    }

    private fun postJsonForUser(
        path: String,
        body: JSONObject,
        accessToken: String,
        fallbackMessage: String
    ): RemoteAuthUser {
        val url = URL("${baseUrl.trimEnd('/')}$path")
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            doOutput = true
            connectTimeout = 15_000
            readTimeout = 15_000
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Authorization", "Bearer $accessToken")
        }

        return try {
            connection.outputStream.use { output ->
                output.write(body.toString().toByteArray(StandardCharsets.UTF_8))
            }

            val responseBody = readResponseBody(connection)
            val json = if (responseBody.isBlank()) JSONObject() else JSONObject(responseBody)
            val success = json.optBoolean("success", false)
            val message = extractResponseMessage(
                json = json,
                fallbackMessage = fallbackMessage
            )
            if (!success) {
                throw IllegalStateException(message)
            }

            val data = json.optJSONObject("data")
                ?: throw IllegalStateException("Data survey dari backend tidak ditemukan.")
            val userJson = data.optJSONObject("user")
                ?: throw IllegalStateException("Data pengguna hasil survey tidak ditemukan.")

            parseUser(userJson, defaultAuthProvider = "LOCAL")
        } finally {
            connection.disconnect()
        }
    }

    private fun postWithoutBodyForMessage(path: String, accessToken: String): String {
        val url = URL("${baseUrl.trimEnd('/')}$path")
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            doOutput = false
            connectTimeout = 15_000
            readTimeout = 15_000
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Authorization", "Bearer $accessToken")
        }

        return try {
            val responseBody = readResponseBody(connection)
            val json = if (responseBody.isBlank()) JSONObject() else JSONObject(responseBody)
            val success = json.optBoolean("success", false)
            val message = extractResponseMessage(
                json = json,
                fallbackMessage = "Permintaan logout gagal."
            )
            if (!success) {
                throw IllegalStateException(message)
            }
            message
        } finally {
            connection.disconnect()
        }
    }

    private fun parseUser(
        userJson: JSONObject,
        defaultAuthProvider: String
    ): RemoteAuthUser = RemoteAuthUser(
        id = userJson.optInt("id"),
        email = userJson.optString("email"),
        fullName = userJson.optString("fullName"),
        authProvider = userJson.optString("authProvider", defaultAuthProvider),
        avatarUrl = userJson.optString("avatarUrl").ifBlank { null },
        createdAt = userJson.optString("createdAt"),
        hasCompletedSurvey = userJson.optBoolean("hasCompletedSurvey", false)
    )

    private fun readResponseBody(connection: HttpURLConnection): String {
        val stream = if (connection.responseCode in 200..299) {
            connection.inputStream
        } else {
            connection.errorStream
        }
        return stream.readTextSafely()
    }

    private fun InputStream?.readTextSafely(): String {
        if (this == null) return ""
        return BufferedReader(InputStreamReader(this, StandardCharsets.UTF_8)).use { reader ->
            reader.readText()
        }
    }

    private fun extractResponseMessage(
        json: JSONObject,
        fallbackMessage: String
    ): String {
        val validationMessage = json.optJSONObject("data")
            ?.optJSONArray("errors")
            ?.optJSONObject(0)
            ?.optString("message")
            .orEmpty()

        if (validationMessage.isNotBlank()) {
            return validationMessage
        }

        return json.optString("message").ifBlank { fallbackMessage }
    }
}
