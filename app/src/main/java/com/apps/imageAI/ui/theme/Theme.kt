package com.apps.imageAI.ui.theme

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = PrimaryVariant,
    tertiary = SecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = White,
    onSecondary = OnSecondaryDark,
    error = ErrorColor,
    onTertiary = OnPrimaryDark,
    onSurface = OnSurfaceDark,
    onSurfaceVariant = OnBackgroundDark,
    outline = outlineDark

    /*  ,
      onSurface = OnSurfaceDark,
      onBackground = OnBackgroundDark
      error = ErrorColor,
      */
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = PrimaryVariant,
    tertiary = Secondary,
    background = Background,
    surface = Surface,
    onPrimary = White,
    onSecondary = OnSecondary,
    error = ErrorColor,
    onTertiary = OnPrimary,
    onSurface = OnSurface,
    onSurfaceVariant = OnBackground,
    outline = outline
  /*  onBackground = OnBackground,
    onSurface = OnSurface,
    error = ErrorColor*/
)
@Composable
fun ImageAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.setBackgroundDrawable(ColorDrawable(colorScheme.background.toArgb()))
            window.navigationBarColor = colorScheme.onSecondary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme.not()
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = darkTheme.not()
        }
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}