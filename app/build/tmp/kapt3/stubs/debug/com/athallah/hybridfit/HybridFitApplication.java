package com.athallah.hybridfit;

import android.app.Application;
import android.content.Context;
import androidx.room.Room;
import com.athallah.hybridfit.data.local.HybridFitDatabase;
import com.athallah.hybridfit.data.local.OnboardingPreferenceStore;
import com.athallah.hybridfit.data.remote.HybridFitAiApi;
import com.athallah.hybridfit.data.remote.AuthSessionStore;
import com.athallah.hybridfit.data.remote.HybridFitAuthApi;
import com.athallah.hybridfit.data.repository.HybridFitRepository;
import com.athallah.hybridfit.domain.service.AdaptiveRecommendationEngine;
import com.athallah.hybridfit.domain.service.ProgressAnalyzer;
import com.athallah.hybridfit.domain.service.StarterPlanBuilder;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2 = {"Lcom/athallah/hybridfit/HybridFitApplication;", "Landroid/app/Application;", "()V", "container", "Lcom/athallah/hybridfit/AppContainer;", "getContainer", "()Lcom/athallah/hybridfit/AppContainer;", "container$delegate", "Lkotlin/Lazy;", "app_debug"})
public final class HybridFitApplication extends android.app.Application {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy container$delegate = null;
    
    public HybridFitApplication() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.AppContainer getContainer() {
        return null;
    }
}