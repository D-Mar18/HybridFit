package com.athallah.hybridfit.data.remote

import com.athallah.hybridfit.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

data class CoachChatMessage(
    val role: String,
    val text: String
)

data class CoachProgramProposalResponse(
    val goal: String,
    val preferredFocus: String,
    val workoutDaysPerWeek: Int,
    val sessionDurationMinutes: Int,
    val programLabel: String,
    val summaryLabel: String
)

data class CoachChatResponse(
    val reply: String,
    val model: String?,
    val proposal: CoachProgramProposalResponse?
)

class HybridFitAiApi(
    private val baseUrl: String = BuildConfig.HYBRIDFIT_API_BASE_URL
) {
    suspend fun sendCoachChat(
        accessToken: String,
        messages: List<CoachChatMessage>
    ): CoachChatResponse = withContext(Dispatchers.IO) {
        val payload = JSONObject().put(
            "messages",
            JSONArray().apply {
                messages.forEach { message ->
                    put(
                        JSONObject()
                            .put("role", message.role)
                            .put("text", message.text)
                    )
                }
            }
        )

        val url = URL("${baseUrl.trimEnd('/')}/api/ai/chat")
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            doOutput = true
            connectTimeout = 15_000
            readTimeout = 20_000
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Authorization", "Bearer $accessToken")
        }

        try {
            connection.outputStream.use { output ->
                output.write(payload.toString().toByteArray(StandardCharsets.UTF_8))
            }

            val responseBody = readResponseBody(connection)
            val json = if (responseBody.isBlank()) JSONObject() else JSONObject(responseBody)
            val success = json.optBoolean("success", false)
            val message = extractResponseMessage(
                json = json,
                fallbackMessage = "AI Coach belum dapat membalas."
            )

            if (!success) {
                throw IllegalStateException(message)
            }

            val data = json.optJSONObject("data")
                ?: throw IllegalStateException("Data balasan AI tidak ditemukan.")
            val reply = data.optString("reply")
            if (reply.isBlank()) {
                throw IllegalStateException("Balasan AI Coach kosong.")
            }
            val model = data.optString("model").ifBlank { null }
            val proposalJson = data.optJSONObject("proposal")
            val proposal = if (proposalJson == null) {
                null
            } else {
                CoachProgramProposalResponse(
                    goal = proposalJson.optString("goal"),
                    preferredFocus = proposalJson.optString("preferredFocus"),
                    workoutDaysPerWeek = proposalJson.optInt("workoutDaysPerWeek"),
                    sessionDurationMinutes = proposalJson.optInt("sessionDurationMinutes"),
                    programLabel = proposalJson.optString("programLabel"),
                    summaryLabel = proposalJson.optString("summaryLabel")
                )
            }

            CoachChatResponse(
                reply = reply,
                model = model,
                proposal = proposal
            )
        } finally {
            connection.disconnect()
        }
    }

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
