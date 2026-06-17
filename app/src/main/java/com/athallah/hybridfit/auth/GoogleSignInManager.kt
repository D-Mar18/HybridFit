@file:Suppress("DEPRECATION")

package com.athallah.hybridfit.auth

import android.content.Context
import android.content.Intent
import com.athallah.hybridfit.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

data class GoogleAccountProfile(
    val id: String,
    val email: String,
    val fullName: String,
    val avatarUrl: String?,
    val idToken: String
)

class GoogleSignInManager(context: Context) {
    private val googleSignInClient = GoogleSignIn.getClient(
        context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .build()
    )

    fun createSignInIntent(): Intent = googleSignInClient.signInIntent

    @Throws(ApiException::class, IllegalStateException::class)
    fun extractAccountProfile(data: Intent?): GoogleAccountProfile {
        val account = GoogleSignIn
            .getSignedInAccountFromIntent(data)
            .getResult(ApiException::class.java)

        val email = account.email?.trim().orEmpty()
        if (email.isBlank()) {
            throw IllegalStateException("Email akun Google tidak tersedia.")
        }
        val idToken = account.idToken?.trim().orEmpty()
        if (idToken.isBlank()) {
            throw IllegalStateException("ID token Google tidak tersedia.")
        }

        return GoogleAccountProfile(
            id = account.id?.trim().orEmpty().ifBlank { email },
            email = email,
            fullName = account.displayName?.trim().orEmpty().ifBlank {
                email.substringBefore("@")
            },
            avatarUrl = account.photoUrl?.toString(),
            idToken = idToken
        )
    }
}
