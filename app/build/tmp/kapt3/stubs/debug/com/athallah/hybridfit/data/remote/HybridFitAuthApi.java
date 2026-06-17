package com.athallah.hybridfit.data.remote;

import com.athallah.hybridfit.BuildConfig;
import kotlinx.coroutines.Dispatchers;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0002J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0003H\u0086@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0003H\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0003H\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0003H\u0002J \u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0003H\u0002J(\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0003H\u0002J\u0018\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0003H\u0002J\u0010\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J&\u0010\u001f\u001a\u00020\n2\u0006\u0010 \u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0003H\u0086@\u00a2\u0006\u0002\u0010!Jf\u0010\"\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u00032\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020$2\u0006\u0010(\u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u00032\u0006\u0010*\u001a\u00020\u00032\u0006\u0010+\u001a\u00020$2\u0006\u0010,\u001a\u00020$2\u0006\u0010-\u001a\u00020\u0003H\u0086@\u00a2\u0006\u0002\u0010.J\u000e\u0010/\u001a\u00020\u0003*\u0004\u0018\u000100H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2 = {"Lcom/athallah/hybridfit/data/remote/HybridFitAuthApi;", "", "baseUrl", "", "(Ljava/lang/String;)V", "extractResponseMessage", "json", "Lorg/json/JSONObject;", "fallbackMessage", "login", "Lcom/athallah/hybridfit/data/remote/AuthSession;", "email", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loginWithGoogle", "idToken", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "accessToken", "parseUser", "Lcom/athallah/hybridfit/data/remote/RemoteAuthUser;", "userJson", "defaultAuthProvider", "postJsonForSession", "path", "body", "postJsonForUser", "postWithoutBodyForMessage", "readResponseBody", "connection", "Ljava/net/HttpURLConnection;", "register", "fullName", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitSurvey", "age", "", "weightKg", "", "heightCm", "goal", "fitnessLevel", "preferredFocus", "workoutDaysPerWeek", "sessionDurationMinutes", "experienceNotes", "(Ljava/lang/String;Ljava/lang/String;IDILjava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readTextSafely", "Ljava/io/InputStream;", "app_debug"})
public final class HybridFitAuthApi {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String baseUrl = null;
    
    public HybridFitAuthApi(@org.jetbrains.annotations.NotNull()
    java.lang.String baseUrl) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object register(@org.jetbrains.annotations.NotNull()
    java.lang.String fullName, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.athallah.hybridfit.data.remote.AuthSession> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object login(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.athallah.hybridfit.data.remote.AuthSession> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loginWithGoogle(@org.jetbrains.annotations.NotNull()
    java.lang.String idToken, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.athallah.hybridfit.data.remote.AuthSession> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    java.lang.String accessToken, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object submitSurvey(@org.jetbrains.annotations.NotNull()
    java.lang.String accessToken, @org.jetbrains.annotations.NotNull()
    java.lang.String fullName, int age, double weightKg, int heightCm, @org.jetbrains.annotations.NotNull()
    java.lang.String goal, @org.jetbrains.annotations.NotNull()
    java.lang.String fitnessLevel, @org.jetbrains.annotations.NotNull()
    java.lang.String preferredFocus, int workoutDaysPerWeek, int sessionDurationMinutes, @org.jetbrains.annotations.NotNull()
    java.lang.String experienceNotes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.athallah.hybridfit.data.remote.RemoteAuthUser> $completion) {
        return null;
    }
    
    private final com.athallah.hybridfit.data.remote.AuthSession postJsonForSession(java.lang.String path, org.json.JSONObject body, java.lang.String defaultAuthProvider) {
        return null;
    }
    
    private final com.athallah.hybridfit.data.remote.RemoteAuthUser postJsonForUser(java.lang.String path, org.json.JSONObject body, java.lang.String accessToken, java.lang.String fallbackMessage) {
        return null;
    }
    
    private final java.lang.String postWithoutBodyForMessage(java.lang.String path, java.lang.String accessToken) {
        return null;
    }
    
    private final com.athallah.hybridfit.data.remote.RemoteAuthUser parseUser(org.json.JSONObject userJson, java.lang.String defaultAuthProvider) {
        return null;
    }
    
    private final java.lang.String readResponseBody(java.net.HttpURLConnection connection) {
        return null;
    }
    
    private final java.lang.String readTextSafely(java.io.InputStream $this$readTextSafely) {
        return null;
    }
    
    private final java.lang.String extractResponseMessage(org.json.JSONObject json, java.lang.String fallbackMessage) {
        return null;
    }
    
    public HybridFitAuthApi() {
        super();
    }
}