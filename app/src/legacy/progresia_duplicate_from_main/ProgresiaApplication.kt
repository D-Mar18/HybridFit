package com.athallah.hybridfit

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.athallah.hybridfit.data.local.HybridFitDatabase
import com.athallah.hybridfit.data.repository.HybridFitRepository
import com.athallah.hybridfit.domain.service.AdaptiveRecommendationEngine
import com.athallah.hybridfit.domain.service.ProgressAnalyzer
import com.athallah.hybridfit.domain.service.StarterPlanBuilder

class HybridFitApplication : Application() {
    val container: AppContainer by lazy { AppContainer(this) }
}

class AppContainer(context: Context) {
    private val database = Room.databaseBuilder(
        context,
        HybridFitDatabase::class.java,
        "hybridfit.db"
    ).fallbackToDestructiveMigration().build()

    val repository = HybridFitRepository(
        database = database,
        starterPlanBuilder = StarterPlanBuilder(),
        recommendationEngine = AdaptiveRecommendationEngine(),
        progressAnalyzer = ProgressAnalyzer()
    )
}
