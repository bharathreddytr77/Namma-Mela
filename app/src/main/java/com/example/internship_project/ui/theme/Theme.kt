package com.example.internship_project.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Gold,
    onPrimary = StageBlack,
    secondary = CurtainRed,
    onSecondary = WarmWhite,
    tertiary = MutedGold,
    onTertiary = StageBlack,
    background = StageBlack,
    onBackground = WarmWhite,
    surface = Color(0xFF170B0B),
    onSurface = WarmWhite,
    surfaceVariant = Color(0xFF2A1717),
    onSurfaceVariant = Color(0xFFEBD9B8),
    outline = Color(0xFF805B22)
)

@Composable
fun Internship_projectTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
