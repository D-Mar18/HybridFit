package com.athallah.hybridfit.ui.main;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.OutlinedTextFieldDefaults;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.compose.ui.text.style.TextAlign;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000H\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a0\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u001c\u0010\u0004\u001a\u0018\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\u0002\b\u0007\u00a2\u0006\u0002\b\bH\u0001\u001a\u0010\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0001\u001a0\u0010\f\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u001c\u0010\u0004\u001a\u0018\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\u0002\b\u0007\u00a2\u0006\u0002\b\bH\u0001\u001aP\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\u0010\b\u0002\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0015H\u0001\u001a\u00c4\u0001\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u000f2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0019\u001a\u00020\u000f2\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u001b\u001a\u00020\u00132\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\u0006\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u00132\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0001\u001a\u00d2\u0001\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020\u000f2\u0012\u0010'\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0017\u001a\u00020\u000f2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0019\u001a\u00020\u000f2\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010(\u001a\u00020\u000f2\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u001b\u001a\u00020\u00132\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\u0006\u0010*\u001a\u00020\u00132\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\u0006\u0010,\u001a\u00020\u00132\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0001\u001a2\u0010/\u001a\u00020\u00012\u0006\u00100\u001a\u00020\u000f2\u0006\u00101\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00102\u001a\u000203H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b4\u00105\u001a\\\u00106\u001a\u00020\u00012\u0006\u00107\u001a\u00020\u000f2\f\u00108\u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00152\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u00109\u001a\u00020\u0013H\u0001\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006:"}, d2 = {"AuthContainer", "", "modifier", "Landroidx/compose/ui/Modifier;", "content", "Lkotlin/Function1;", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "BrandHeader", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "CardSurface", "FormTextField", "value", "", "onValueChange", "label", "obscure", "", "onTrailingClick", "Lkotlin/Function0;", "LoginScreen", "email", "onEmailChange", "password", "onPasswordChange", "showPassword", "onTogglePassword", "isCredentialLoginInProgress", "isGoogleLoginInProgress", "onQuickAccess", "onLogin", "onForgotPassword", "onGoogleLogin", "onAppleLogin", "onCreateAccount", "RegisterScreen", "name", "onNameChange", "confirmPassword", "onConfirmPasswordChange", "showConfirmPassword", "onToggleConfirmPassword", "isRegisterInProgress", "onRegister", "onLoginInstead", "WelcomeFeatureRow", "title", "body", "tint", "Landroidx/compose/ui/graphics/Color;", "WelcomeFeatureRow-g2O1Hgs", "(Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;J)V", "WelcomeScreen", "primaryActionLabel", "onPrimaryAction", "showDirectAuthActions", "app_debug"})
public final class AuthScreensKt {
    
    @androidx.compose.runtime.Composable()
    public static final void WelcomeScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String primaryActionLabel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPrimaryAction, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogin, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onGoogleLogin, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onQuickAccess, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean showDirectAuthActions) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RegisterScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNameChange, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEmailChange, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPasswordChange, @org.jetbrains.annotations.NotNull()
    java.lang.String confirmPassword, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onConfirmPasswordChange, boolean showPassword, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onTogglePassword, boolean showConfirmPassword, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleConfirmPassword, boolean isRegisterInProgress, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRegister, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLoginInstead, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LoginScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEmailChange, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPasswordChange, boolean showPassword, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onTogglePassword, boolean isCredentialLoginInProgress, boolean isGoogleLoginInProgress, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onQuickAccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogin, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onForgotPassword, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onGoogleLogin, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAppleLogin, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCreateAccount, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AuthContainer(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.ColumnScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void BrandHeader(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CardSurface(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.ColumnScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FormTextField(@org.jetbrains.annotations.NotNull()
    java.lang.String value, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange, @org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, boolean obscure, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onTrailingClick) {
    }
}