package com.afaneca.afascore.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import com.google.accompanist.systemuicontroller.rememberSystemUiController

typealias C = Colors

private val DarkColorScheme = darkColorScheme(
    primary = C.Purple80,
    secondary = C.PurpleGrey80,
    tertiary = C.Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = C.Purple40,
    secondary = C.PurpleGrey40,
    tertiary = C.Pink40,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun AFAScoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        /*dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }*/

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        /*SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }*/
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(
                color = colorScheme.background,
            )
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}