package com.theamazinghunt.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val HuntLightColorScheme = lightColorScheme(
    primary = Color(0xFF23614F),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCDEBDD),
    onPrimaryContainer = Color(0xFF062017),
    secondary = Color(0xFF7A5D00),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFDF82),
    onSecondaryContainer = Color(0xFF261A00),
    tertiary = Color(0xFF38618C),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFD3E4FF),
    onTertiaryContainer = Color(0xFF001C37),
    background = Color(0xFFF8FAF7),
    onBackground = Color(0xFF1A1C1A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1C1A),
    surfaceVariant = Color(0xFFE0E7E2),
    onSurfaceVariant = Color(0xFF414941),
    outline = Color(0xFF707971)
)

private val HuntTypography = Typography(
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    )
)

@Composable
fun AmazingHuntTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = HuntLightColorScheme,
        typography = HuntTypography,
        content = content
    )
}
