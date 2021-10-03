package edu.neit.jonathandoolittle.shellsitter.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import edu.neit.jonathandoolittle.shellsitter.ui.menus.AnimatedBottomNavigationBar
import edu.neit.jonathandoolittle.shellsitter.ui.menus.AnimatedNavigationOptions
import edu.neit.jonathandoolittle.shellsitter.ui.menus.ShellSitterNavigationGraph
import edu.neit.jonathandoolittle.shellsitter.ui.theme.ShellSitterTheme

/**
 * TODO
 *
 */
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun ShellSitter() {
    ProvideWindowInsets {
        ShellSitterTheme {
            val bottomNavigationItems = remember { AnimatedNavigationOptions.values}
            val navigationController = rememberNavController()
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                scaffoldState = scaffoldState,
                bottomBar = {
                    AnimatedBottomNavigationBar(navigationController = navigationController, items = bottomNavigationItems)
                },
                backgroundColor = White,
                contentColor = Black
            ) {
                Box(modifier = Modifier.padding(it)) {
                    ShellSitterNavigationGraph(
                        navController = navigationController
                    )
                }
            }
        }
    }
}