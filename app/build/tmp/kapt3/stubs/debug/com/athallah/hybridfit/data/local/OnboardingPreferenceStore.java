package com.athallah.hybridfit.data.local;

import android.content.Context;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0015\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\tR\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/athallah/hybridfit/data/local/OnboardingPreferenceStore;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "preferences", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "hasCompletedIntroSurvey", "", "userId", "", "(Ljava/lang/Long;)Z", "setCompletedIntroSurvey", "", "completed", "Companion", "app_debug"})
public final class OnboardingPreferenceStore {
    private final android.content.SharedPreferences preferences = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREF_NAME = "hybridfit_onboarding";
    @org.jetbrains.annotations.NotNull()
    public static final com.athallah.hybridfit.data.local.OnboardingPreferenceStore.Companion Companion = null;
    
    public OnboardingPreferenceStore(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final boolean hasCompletedIntroSurvey(@org.jetbrains.annotations.Nullable()
    java.lang.Long userId) {
        return false;
    }
    
    public final void setCompletedIntroSurvey(long userId, boolean completed) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/athallah/hybridfit/data/local/OnboardingPreferenceStore$Companion;", "", "()V", "PREF_NAME", "", "surveyKey", "userId", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        private final java.lang.String surveyKey(long userId) {
            return null;
        }
    }
}