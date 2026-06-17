package com.athallah.hybridfit.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.athallah.hybridfit.data.repository.HybridFitRepository
import com.athallah.hybridfit.domain.model.DashboardSnapshot
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class MainUiState(
    val isLoading: Boolean = true,
    val snapshot: DashboardSnapshot? = null
)

class MainViewModel(
    private val repository: HybridFitRepository
) : ViewModel() {
    var uiState by mutableStateOf(MainUiState())
        private set

    init {
        viewModelScope.launch {
            repository.bootstrap()
            repository.observeDashboard().collectLatest { snapshot ->
                uiState = MainUiState(
                    isLoading = false,
                    snapshot = snapshot
                )
            }
        }
    }

    fun simulateWorkoutCompletion() {
        viewModelScope.launch {
            repository.recordQuickWorkout()
        }
    }

    fun refreshRecommendation() {
        viewModelScope.launch {
            repository.refreshRecommendation()
        }
    }

    fun submitFeedback(wasHelpful: Boolean) {
        viewModelScope.launch {
            repository.recordFeedback(wasHelpful)
        }
    }

    class Factory(
        private val repository: HybridFitRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
