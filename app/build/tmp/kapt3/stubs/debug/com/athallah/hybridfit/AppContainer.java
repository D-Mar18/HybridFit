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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001b"}, d2 = {"Lcom/athallah/hybridfit/AppContainer;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "aiApi", "Lcom/athallah/hybridfit/data/remote/HybridFitAiApi;", "getAiApi", "()Lcom/athallah/hybridfit/data/remote/HybridFitAiApi;", "authApi", "Lcom/athallah/hybridfit/data/remote/HybridFitAuthApi;", "getAuthApi", "()Lcom/athallah/hybridfit/data/remote/HybridFitAuthApi;", "authSessionStore", "Lcom/athallah/hybridfit/data/remote/AuthSessionStore;", "getAuthSessionStore", "()Lcom/athallah/hybridfit/data/remote/AuthSessionStore;", "database", "Lcom/athallah/hybridfit/data/local/HybridFitDatabase;", "onboardingPreferenceStore", "Lcom/athallah/hybridfit/data/local/OnboardingPreferenceStore;", "getOnboardingPreferenceStore", "()Lcom/athallah/hybridfit/data/local/OnboardingPreferenceStore;", "repository", "Lcom/athallah/hybridfit/data/repository/HybridFitRepository;", "getRepository", "()Lcom/athallah/hybridfit/data/repository/HybridFitRepository;", "app_debug"})
public final class AppContainer {
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.local.HybridFitDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.repository.HybridFitRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.remote.HybridFitAuthApi authApi = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.remote.HybridFitAiApi aiApi = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.remote.AuthSessionStore authSessionStore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.athallah.hybridfit.data.local.OnboardingPreferenceStore onboardingPreferenceStore = null;
    
    public AppContainer(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.data.repository.HybridFitRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.data.remote.HybridFitAuthApi getAuthApi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.data.remote.HybridFitAiApi getAiApi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.data.remote.AuthSessionStore getAuthSessionStore() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.data.local.OnboardingPreferenceStore getOnboardingPreferenceStore() {
        return null;
    }
}