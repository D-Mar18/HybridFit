package com.athallah.hybridfit.data.remote;

import android.content.Context;
import java.time.Instant;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\n\u0018\u0000 !2\u00020\u0001:\u0001!B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u0006\u0010\f\u001a\u00020\rJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nJ\u0010\u0010\u0011\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\nJ\b\u0010\u0012\u001a\u0004\u0018\u00010\u000fJ\u0010\u0010\u0013\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u0010\u0010\u0014\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u0010\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u0010\u0010\u0016\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u0010\u0010\u0019\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u0016\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nJ\u0016\u0010\u001c\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\nJ\u0016\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\nJ\u000e\u0010 \u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020\u000fR\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/athallah/hybridfit/data/remote/AuthSessionStore;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "preferences", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "profilePreferences", "avatarKey", "", "email", "clear", "", "createLocalEmailSession", "Lcom/athallah/hybridfit/data/remote/AuthSession;", "fullName", "getAvatarForEmail", "getSession", "localCreatedAtKey", "localFullNameKey", "localPasswordKey", "localSurveyKey", "localUserIdForEmail", "", "localUserIdKey", "loginLocalEmail", "password", "saveAvatarForEmail", "avatarUrl", "saveLocalEmailAccount", "session", "saveSession", "Companion", "app_debug"})
public final class AuthSessionStore {
    private final android.content.SharedPreferences preferences = null;
    private final android.content.SharedPreferences profilePreferences = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREF_NAME = "hybridfit_auth_session";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PROFILE_PREF_NAME = "hybridfit_profile_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_USER_ID = "user_id";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_EMAIL = "email";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_FULL_NAME = "full_name";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_AUTH_PROVIDER = "auth_provider";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_AVATAR_URL = "avatar_url";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_CREATED_AT = "created_at";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_HAS_COMPLETED_SURVEY = "has_completed_survey";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ACCESS_TOKEN = "access_token";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_REFRESH_TOKEN = "refresh_token";
    @org.jetbrains.annotations.NotNull()
    public static final com.athallah.hybridfit.data.remote.AuthSessionStore.Companion Companion = null;
    
    public AuthSessionStore(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void saveSession(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.remote.AuthSession session) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.athallah.hybridfit.data.remote.AuthSession getSession() {
        return null;
    }
    
    public final void clear() {
    }
    
    public final void saveLocalEmailAccount(@org.jetbrains.annotations.NotNull()
    com.athallah.hybridfit.data.remote.AuthSession session, @org.jetbrains.annotations.NotNull()
    java.lang.String password) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.data.remote.AuthSession createLocalEmailSession(@org.jetbrains.annotations.NotNull()
    java.lang.String fullName, @org.jetbrains.annotations.NotNull()
    java.lang.String email) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.athallah.hybridfit.data.remote.AuthSession loginLocalEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password) {
        return null;
    }
    
    public final void saveAvatarForEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String avatarUrl) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAvatarForEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
        return null;
    }
    
    private final java.lang.String avatarKey(java.lang.String email) {
        return null;
    }
    
    private final int localUserIdForEmail(java.lang.String email) {
        return 0;
    }
    
    private final java.lang.String localUserIdKey(java.lang.String email) {
        return null;
    }
    
    private final java.lang.String localFullNameKey(java.lang.String email) {
        return null;
    }
    
    private final java.lang.String localPasswordKey(java.lang.String email) {
        return null;
    }
    
    private final java.lang.String localCreatedAtKey(java.lang.String email) {
        return null;
    }
    
    private final java.lang.String localSurveyKey(java.lang.String email) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/athallah/hybridfit/data/remote/AuthSessionStore$Companion;", "", "()V", "KEY_ACCESS_TOKEN", "", "KEY_AUTH_PROVIDER", "KEY_AVATAR_URL", "KEY_CREATED_AT", "KEY_EMAIL", "KEY_FULL_NAME", "KEY_HAS_COMPLETED_SURVEY", "KEY_REFRESH_TOKEN", "KEY_USER_ID", "PREF_NAME", "PROFILE_PREF_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}