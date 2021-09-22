package edu.neit.jonathandoolittle.shellsitter.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import edu.neit.jonathandoolittle.shellsitter.ui.theme.ColorScheme.primaryNavigationColor
import edu.neit.jonathandoolittle.shellsitter.ui.theme.ColorScheme.systemBackgroundColor

object ColorScheme {
    val backgroundColor: Color = Color.White
    val primaryNavigationColor: Color = Color.Black
    val systemBackgroundColor: Color = RainyDarkGreen.copy(alpha = 0.95f)
}

/**
 * TODO
 *
 * @param content
 */
@Composable
fun ShellSitterTheme(
    content: @Composable () -> Unit
) {
    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = systemBackgroundColor
        )
    }
    content()
}

