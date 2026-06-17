package com.athallah.hybridfit.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.athallah.hybridfit.ui.theme.AquaBright
import com.athallah.hybridfit.ui.theme.AquaTeal
import com.athallah.hybridfit.ui.theme.BorderSoft
import com.athallah.hybridfit.ui.theme.OceanBlue
import com.athallah.hybridfit.ui.theme.SurfaceSoft
import com.athallah.hybridfit.ui.theme.SurfaceTint
import com.athallah.hybridfit.ui.theme.SurfaceWhite
import com.athallah.hybridfit.ui.theme.TextPrimary
import com.athallah.hybridfit.ui.theme.TextSecondary


@Composable
internal fun WelcomeScreen(
    primaryActionLabel: String,
    onPrimaryAction: () -> Unit,
    onLogin: () -> Unit,
    onGoogleLogin: () -> Unit,
    onQuickAccess: () -> Unit,
    modifier: Modifier = Modifier,
    showDirectAuthActions: Boolean = true
) {
    AuthContainer(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BrandHeader(icon = Icons.AutoMirrored.Outlined.ShowChart)
            IconButton(onClick = onQuickAccess) {
                Surface(
                    shape = CircleShape,
                    color = SurfaceTint,
                    border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Menu,
                        contentDescription = "Akses cepat",
                        tint = TextSecondary,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(34.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            color = Color.Transparent,
            shadowElevation = 8.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFFE9F7FF), Color(0xFFF8FEFF))
                        )
                    )
                    .padding(24.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(
                        text = "Mulai Lebih Terarah",
                        style = MaterialTheme.typography.displaySmall,
                        color = OceanBlue
                    )
                    Text(
                        text = "HybridFit membantu Anda menyusun program, menjalankan latihan, lalu memantau progres dalam satu alur yang sederhana.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = SurfaceWhite.copy(alpha = 0.88f),
                        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            WelcomeFeatureRow(
                                title = "Survey Personal",
                                body = "Tujuan, pengalaman, dan kondisi latihan akan dipakai sebagai fondasi program awal.",
                                icon = Icons.Outlined.AutoAwesome,
                                tint = OceanBlue
                            )
                            WelcomeFeatureRow(
                                title = "Program Adaptif",
                                body = "Gym, lari, dan AI recommendation dirancang menyesuaikan perkembangan hasil latihan.",
                                icon = Icons.Outlined.FitnessCenter,
                                tint = AquaTeal
                            )
                            WelcomeFeatureRow(
                                title = "Progress Tracker",
                                body = "Pantau langkah, kalori, tidur, streak, dan performa sesi dari satu dashboard.",
                                icon = Icons.AutoMirrored.Outlined.ShowChart,
                                tint = Color(0xFF29436B)
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(22.dp))
        CardSurface {
            Text(
                text = if (showDirectAuthActions) {
                    "Sebelum masuk ke dashboard, Anda bisa membuat akun, login, atau lanjut cepat dengan Google."
                } else {
                    "Tekan mulai, jawab beberapa pertanyaan singkat, lalu Anda akan diarahkan ke halaman login."
                },
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )
            PrimaryGradientButton(
                text = primaryActionLabel,
                onClick = onPrimaryAction
            )
            if (showDirectAuthActions) {
                SecondaryOutlineButton(
                    text = "Masuk ke Akun",
                    onClick = onLogin
                )
                SecondaryOutlineButton(
                    text = "Lanjut dengan Google",
                    onClick = onGoogleLogin
                )
            }
        }
    }
}

@Composable
internal fun RegisterScreen(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    showPassword: Boolean,
    onTogglePassword: () -> Unit,
    showConfirmPassword: Boolean,
    onToggleConfirmPassword: () -> Unit,
    isRegisterInProgress: Boolean,
    onRegister: () -> Unit,
    onLoginInstead: () -> Unit,
    modifier: Modifier = Modifier
) {
    AuthContainer(modifier = modifier) {
        BrandHeader(icon = Icons.AutoMirrored.Outlined.ShowChart)
        Spacer(modifier = Modifier.height(38.dp))
        Text(
            text = "Registrasi\nAkun Baru",
            style = MaterialTheme.typography.displaySmall.copy(fontSize = 34.sp, lineHeight = 38.sp),
            color = OceanBlue
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Mulai perjalanan transformatif Anda bersama komunitas HybridFit hari ini.",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary
        )
        Spacer(modifier = Modifier.height(26.dp))
        CardSurface(
            modifier = Modifier.padding(top = 6.dp)
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            FormTextField(
                value = name,
                onValueChange = onNameChange,
                label = "Nama Lengkap",
                icon = Icons.Outlined.PersonOutline
            )
            FormTextField(
                value = email,
                onValueChange = onEmailChange,
                label = "Email",
                icon = Icons.Outlined.Email
            )
            FormTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = "Kata Sandi",
                icon = Icons.Outlined.Lock,
                obscure = !showPassword,
                onTrailingClick = onTogglePassword
            )
            FormTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = "Konfirmasi Kata Sandi",
                icon = Icons.Outlined.Lock,
                obscure = !showConfirmPassword,
                onTrailingClick = onToggleConfirmPassword
            )
            PrimaryGradientButton(
                text = if (isRegisterInProgress) "Mendaftarkan..." else "Daftar Sekarang",
                onClick = onRegister,
                enabled = !isRegisterInProgress
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = BorderSoft
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sudah punya akun?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                TextButton(
                    onClick = onLoginInstead,
                    enabled = !isRegisterInProgress
                ) {
                    Text("Masuk di sini")
                }
            }
        }
    }
}

@Composable
internal fun LoginScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    showPassword: Boolean,
    onTogglePassword: () -> Unit,
    isCredentialLoginInProgress: Boolean,
    isGoogleLoginInProgress: Boolean,
    onQuickAccess: () -> Unit,
    onLogin: () -> Unit,
    onForgotPassword: () -> Unit,
    onGoogleLogin: () -> Unit,
    onAppleLogin: () -> Unit,
    onCreateAccount: () -> Unit,
    modifier: Modifier = Modifier
) {
    AuthContainer(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BrandHeader(icon = Icons.AutoMirrored.Outlined.ShowChart)
            IconButton(onClick = onQuickAccess) {
                Surface(
                    shape = CircleShape,
                    color = SurfaceTint,
                    border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "Akun",
                        tint = TextSecondary,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(54.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.displaySmall.copy(fontSize = 30.sp, lineHeight = 34.sp),
                color = OceanBlue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Log in to continue your fitness journey.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        CardSurface {
            FormTextField(
                value = email,
                onValueChange = onEmailChange,
                label = "Email",
                icon = Icons.Outlined.Email
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Password",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextSecondary
                    )
                    TextButton(
                        onClick = onForgotPassword,
                        enabled = !isCredentialLoginInProgress && !isGoogleLoginInProgress
                    ) {
                        Text("Forgot Password?")
                    }
                }
                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("........", color = TextSecondary.copy(alpha = 0.65f))
                    },
                    visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    trailingIcon = {
                        IconButton(onClick = onTogglePassword) {
                            Icon(
                                imageVector = if (!showPassword) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = null,
                                tint = TextSecondary
                            )
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceSoft,
                        unfocusedContainerColor = SurfaceSoft,
                        focusedBorderColor = OceanBlue.copy(alpha = 0.65f),
                        unfocusedBorderColor = BorderSoft.copy(alpha = 0.85f),
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        cursorColor = OceanBlue
                    )
                )
            }
            PrimaryGradientButton(
                text = if (isCredentialLoginInProgress) "Memproses..." else "Login",
                onClick = onLogin,
                enabled = !isCredentialLoginInProgress && !isGoogleLoginInProgress
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = BorderSoft)
                Text(
                    text = "OR",
                    modifier = Modifier.padding(horizontal = 18.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge,
                    color = TextSecondary
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = BorderSoft)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SecondaryOutlineButton(
                    text = if (isGoogleLoginInProgress) "Menghubungkan..." else "Google",
                    onClick = onGoogleLogin,
                    modifier = Modifier.weight(1f),
                    enabled = !isGoogleLoginInProgress && !isCredentialLoginInProgress,
                    isLoading = isGoogleLoginInProgress
                )
                SecondaryOutlineButton(
                    text = "Apple",
                    onClick = onAppleLogin,
                    modifier = Modifier.weight(1f),
                    enabled = !isGoogleLoginInProgress && !isCredentialLoginInProgress
                )
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )
            TextButton(
                onClick = onCreateAccount,
                enabled = !isCredentialLoginInProgress && !isGoogleLoginInProgress
            ) {
                Text("Create Account")
            }
        }
    }
}

@Composable
private fun WelcomeFeatureRow(
    title: String,
    body: String,
    icon: ImageVector,
    tint: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(tint.copy(alpha = 0.14f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
    }
}


@Composable
internal fun AuthContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceSoft)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(AquaTeal.copy(alpha = 0.08f), Color.Transparent),
                        radius = 900f
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(OceanBlue.copy(alpha = 0.06f), Color.Transparent),
                        center = Offset(1200f, 1900f),
                        radius = 1000f
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 18.dp),
            content = content
        )
    }
}

@Composable
internal fun BrandHeader(icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.linearGradient(
                        listOf(
                            OceanBlue.copy(alpha = 0.08f),
                            AquaBright.copy(alpha = 0.2f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = OceanBlue,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = "HybridFit",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = OceanBlue
        )
    }
}

@Composable
internal fun CardSurface(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = SurfaceWhite,
        shadowElevation = 10.dp,
        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = content
        )
    }
}

@Composable
internal fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    obscure: Boolean = false,
    onTrailingClick: (() -> Unit)? = null
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = TextSecondary
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = OceanBlue.copy(alpha = 0.75f)
                )
            },
            placeholder = {
                Text(
                    text = when {
                        label.contains("Email", ignoreCase = true) -> "name@example.com"
                        label.contains("Kata Sandi", ignoreCase = true) || label.contains("Password", ignoreCase = true) -> "........"
                        else -> label
                    },
                    color = TextSecondary.copy(alpha = 0.65f)
                )
            },
            trailingIcon = if (onTrailingClick != null) {
                {
                    IconButton(onClick = onTrailingClick) {
                        Icon(
                            imageVector = if (obscure) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = null,
                            tint = TextSecondary
                        )
                    }
                }
            } else {
                null
            },
            visualTransformation = if (obscure) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceSoft,
                unfocusedContainerColor = SurfaceSoft,
                focusedBorderColor = OceanBlue.copy(alpha = 0.65f),
                unfocusedBorderColor = BorderSoft.copy(alpha = 0.85f),
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = OceanBlue,
                focusedTrailingIconColor = OceanBlue,
                unfocusedTrailingIconColor = TextSecondary
            )
        )
    }
}

