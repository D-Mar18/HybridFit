package com.athallah.hybridfit.data.remote

import android.content.Context
import java.time.Instant

class AuthSessionStore(context: Context) {
    private val preferences = context.applicationContext.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )
    private val profilePreferences = context.applicationContext.getSharedPreferences(
        PROFILE_PREF_NAME,
        Context.MODE_PRIVATE
    )

    fun saveSession(session: AuthSession) {
        preferences.edit()
            .putInt(KEY_USER_ID, session.user.id)
            .putString(KEY_EMAIL, session.user.email)
            .putString(KEY_FULL_NAME, session.user.fullName)
            .putString(KEY_AUTH_PROVIDER, session.user.authProvider)
            .putString(KEY_AVATAR_URL, session.user.avatarUrl)
            .putString(KEY_CREATED_AT, session.user.createdAt)
            .putBoolean(KEY_HAS_COMPLETED_SURVEY, session.user.hasCompletedSurvey)
            .putString(KEY_ACCESS_TOKEN, session.accessToken)
            .putString(KEY_REFRESH_TOKEN, session.refreshToken)
            .apply()
    }

    fun getSession(): AuthSession? {
        val accessToken = preferences.getString(KEY_ACCESS_TOKEN, null)
        val refreshToken = preferences.getString(KEY_REFRESH_TOKEN, null)
        val email = preferences.getString(KEY_EMAIL, null)
        val fullName = preferences.getString(KEY_FULL_NAME, null)
        val authProvider = preferences.getString(KEY_AUTH_PROVIDER, null)
        val createdAt = preferences.getString(KEY_CREATED_AT, null)
        val userId = preferences.getInt(KEY_USER_ID, -1)

        if (
            accessToken.isNullOrBlank() ||
            refreshToken.isNullOrBlank() ||
            email.isNullOrBlank() ||
            fullName.isNullOrBlank() ||
            authProvider.isNullOrBlank() ||
            createdAt.isNullOrBlank() ||
            userId <= 0
        ) {
            return null
        }

        return AuthSession(
            user = RemoteAuthUser(
                id = userId,
                email = email,
                fullName = fullName,
                authProvider = authProvider,
                avatarUrl = preferences.getString(KEY_AVATAR_URL, null),
                createdAt = createdAt,
                hasCompletedSurvey = preferences.getBoolean(KEY_HAS_COMPLETED_SURVEY, false)
            ),
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

    fun saveLocalEmailAccount(session: AuthSession, password: String) {
        val email = session.user.email.trim().lowercase()
        if (email.isBlank() || password.isBlank()) return

        profilePreferences.edit()
            .putInt(localUserIdKey(email), session.user.id)
            .putString(localFullNameKey(email), session.user.fullName)
            .putString(localPasswordKey(email), password)
            .putString(localCreatedAtKey(email), session.user.createdAt)
            .apply()
    }

    fun createLocalEmailSession(
        fullName: String,
        email: String
    ): AuthSession {
        val normalizedEmail = email.trim().lowercase()
        val localUserId = localUserIdForEmail(normalizedEmail)
        return AuthSession(
            user = RemoteAuthUser(
                id = localUserId,
                email = normalizedEmail,
                fullName = fullName.trim().ifBlank { normalizedEmail.substringBefore("@") },
                authProvider = "LOCAL",
                avatarUrl = getAvatarForEmail(normalizedEmail),
                createdAt = Instant.now().toString(),
                hasCompletedSurvey = false
            ),
            accessToken = "local-email-access-$localUserId",
            refreshToken = "local-email-refresh-$localUserId"
        )
    }

    fun loginLocalEmail(email: String, password: String): AuthSession {
        val normalizedEmail = email.trim().lowercase()
        val savedPassword = profilePreferences.getString(localPasswordKey(normalizedEmail), null)
        if (savedPassword.isNullOrBlank()) {
            throw IllegalStateException("Akun lokal belum ditemukan. Daftar akun dulu di perangkat ini.")
        }
        if (savedPassword != password) {
            throw IllegalStateException("Email atau kata sandi tidak sesuai.")
        }

        val userId = profilePreferences.getInt(
            localUserIdKey(normalizedEmail),
            localUserIdForEmail(normalizedEmail)
        )
        return AuthSession(
            user = RemoteAuthUser(
                id = userId,
                email = normalizedEmail,
                fullName = profilePreferences.getString(
                    localFullNameKey(normalizedEmail),
                    normalizedEmail.substringBefore("@")
                ).orEmpty(),
                authProvider = "LOCAL",
                avatarUrl = getAvatarForEmail(normalizedEmail),
                createdAt = profilePreferences.getString(
                    localCreatedAtKey(normalizedEmail),
                    Instant.now().toString()
                ).orEmpty(),
                hasCompletedSurvey = profilePreferences.getBoolean(
                    localSurveyKey(normalizedEmail),
                    false
                )
            ),
            accessToken = "local-email-access-$userId",
            refreshToken = "local-email-refresh-$userId"
        )
    }

    fun saveAvatarForEmail(email: String, avatarUrl: String) {
        if (email.isBlank()) return
        profilePreferences.edit()
            .putString(avatarKey(email), avatarUrl)
            .apply()
    }

    fun getAvatarForEmail(email: String): String? {
        if (email.isBlank()) return null
        return profilePreferences.getString(avatarKey(email), null)
    }

    private fun avatarKey(email: String): String = "avatar_${email.trim().lowercase()}"

    private fun localUserIdForEmail(email: String): Int =
        email.hashCode().let { hash ->
            if (hash == Int.MIN_VALUE) Int.MAX_VALUE else kotlin.math.abs(hash)
        }.coerceAtLeast(1)

    private fun localUserIdKey(email: String): String = "local_user_id_${email.trim().lowercase()}"
    private fun localFullNameKey(email: String): String = "local_full_name_${email.trim().lowercase()}"
    private fun localPasswordKey(email: String): String = "local_password_${email.trim().lowercase()}"
    private fun localCreatedAtKey(email: String): String = "local_created_at_${email.trim().lowercase()}"
    private fun localSurveyKey(email: String): String = "local_survey_${email.trim().lowercase()}"

    companion object {
        private const val PREF_NAME = "hybridfit_auth_session"
        private const val PROFILE_PREF_NAME = "hybridfit_profile_prefs"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_EMAIL = "email"
        private const val KEY_FULL_NAME = "full_name"
        private const val KEY_AUTH_PROVIDER = "auth_provider"
        private const val KEY_AVATAR_URL = "avatar_url"
        private const val KEY_CREATED_AT = "created_at"
        private const val KEY_HAS_COMPLETED_SURVEY = "has_completed_survey"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}
