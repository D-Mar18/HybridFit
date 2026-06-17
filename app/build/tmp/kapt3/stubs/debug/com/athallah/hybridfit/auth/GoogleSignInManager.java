package com.athallah.hybridfit.auth;

import android.content.Context;
import android.content.Intent;
import com.athallah.hybridfit.BuildConfig;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/athallah/hybridfit/auth/GoogleSignInManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "googleSignInClient", "Lcom/google/android/gms/auth/api/signin/GoogleSignInClient;", "createSignInIntent", "Landroid/content/Intent;", "extractAccountProfile", "Lcom/athallah/hybridfit/auth/GoogleAccountProfile;", "data", "app_debug"})
public final class GoogleSignInManager {
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.auth.api.signin.GoogleSignInClient googleSignInClient = null;
    
    public GoogleSignInManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Intent createSignInIntent() {
        return null;
    }
    
    @kotlin.jvm.Throws(exceptionClasses = {com.google.android.gms.common.api.ApiException.class, java.lang.IllegalStateException.class})
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.auth.GoogleAccountProfile extractAccountProfile(@org.jetbrains.annotations.Nullable()
    android.content.Intent data) throws com.google.android.gms.common.api.ApiException, java.lang.IllegalStateException {
        return null;
    }
}