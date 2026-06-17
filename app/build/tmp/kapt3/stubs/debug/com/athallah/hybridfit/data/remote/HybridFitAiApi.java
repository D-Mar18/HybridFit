package com.athallah.hybridfit.data.remote;

import com.athallah.hybridfit.BuildConfig;
import kotlinx.coroutines.Dispatchers;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0002J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0002J$\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00032\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u000e\u0010\u0013\u001a\u00020\u0003*\u0004\u0018\u00010\u0014H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/athallah/hybridfit/data/remote/HybridFitAiApi;", "", "baseUrl", "", "(Ljava/lang/String;)V", "extractResponseMessage", "json", "Lorg/json/JSONObject;", "fallbackMessage", "readResponseBody", "connection", "Ljava/net/HttpURLConnection;", "sendCoachChat", "Lcom/athallah/hybridfit/data/remote/CoachChatResponse;", "accessToken", "messages", "", "Lcom/athallah/hybridfit/data/remote/CoachChatMessage;", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readTextSafely", "Ljava/io/InputStream;", "app_debug"})
public final class HybridFitAiApi {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String baseUrl = null;
    
    public HybridFitAiApi(@org.jetbrains.annotations.NotNull()
    java.lang.String baseUrl) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendCoachChat(@org.jetbrains.annotations.NotNull()
    java.lang.String accessToken, @org.jetbrains.annotations.NotNull()
    java.util.List<com.athallah.hybridfit.data.remote.CoachChatMessage> messages, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.athallah.hybridfit.data.remote.CoachChatResponse> $completion) {
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
    
    public HybridFitAiApi() {
        super();
    }
}