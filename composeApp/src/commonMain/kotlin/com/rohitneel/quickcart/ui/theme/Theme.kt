package com.rohitneel.quickcart.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Brand palette, loosely inspired by quick-commerce apps: a punchy accent
// green for primary actions and a warm yellow for highlights/badges.
object BrandColors {
    val Green = Color(0xFF0C831F)
    val GreenDark = Color(0xFF0A6B19)
    val Yellow = Color(0xFFFFC300)
    val Background = Color(0xFFF7F7F9)
    val Surface = Color.White
    val TextPrimary = Color(0xFF1A1A1A)
    val TextSecondary = Color(0xFF6B6B6B)
    val Discount = Color(0xFFE53935)
    val Divider = Color(0xFFEDEDED)
}

private val AppColorScheme = lightColorScheme(
    primary = BrandColors.Green,
    onPrimary = Color.White,
    secondary = BrandColors.Yellow,
    onSecondary = BrandColors.TextPrimary,
    background = BrandColors.Background,
    onBackground = BrandColors.TextPrimary,
    surface = BrandColors.Surface,
    onSurface = BrandColors.TextPrimary,
    error = BrandColors.Discount
)

private val AppTypography = Typography(
    titleLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 17.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 15.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.Normal, fontSize = 13.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)
)

@Composable
fun QuickCartTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = AppTypography,
        content = content
    )
}
