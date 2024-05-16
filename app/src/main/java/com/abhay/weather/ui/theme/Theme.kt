package com.abhay.weather.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val UiColorScheme = darkColorScheme(
    primary = Color.White,
)

@SuppressLint("SuspiciousIndentation")
@Composable
fun WeatherTheme(
    color: Color = Color.Gray,
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = UiColorScheme
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = color.toArgb()
        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightStatusBars = !darkTheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}