package com.athallah.hybridfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.athallah.hybridfit.auth.GoogleSignInManager
import com.google.android.gms.common.api.ApiException
import com.athallah.hybridfit.ui.main.MainScreen
import com.athallah.hybridfit.ui.main.MainViewModel
import com.athallah.hybridfit.ui.theme.HybridFitTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory(
            repository = (application as HybridFitApplication).container.repository,
            authApi = (application as HybridFitApplication).container.authApi,
            aiApi = (application as HybridFitApplication).container.aiApi,
            authSessionStore = (application as HybridFitApplication).container.authSessionStore,
            onboardingPreferenceStore = (application as HybridFitApplication).container.onboardingPreferenceStore
        )
    }
    private val googleSignInManager by lazy { GoogleSignInManager(this) }
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) {
            viewModel.failGoogleAuth("Login Google dibatalkan.")
            return@registerForActivityResult
        }

        try {
            val accountProfile = googleSignInManager.extractAccountProfile(result.data)
            viewModel.completeGoogleAuth(accountProfile)
        } catch (error: ApiException) {
            viewModel.failGoogleAuth(
                error.localizedMessage ?: "Login Google gagal diproses."
            )
        } catch (error: Exception) {
            viewModel.failGoogleAuth(
                error.message ?: "Terjadi kendala saat login dengan Google."
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HybridFitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        viewModel = viewModel,
                        onGoogleLoginRequested = ::startGoogleLogin
                    )
                }
            }
        }
    }

    private fun startGoogleLogin() {
        viewModel.beginGoogleAuth()
        googleSignInLauncher.launch(googleSignInManager.createSignInIntent())
    }
}
