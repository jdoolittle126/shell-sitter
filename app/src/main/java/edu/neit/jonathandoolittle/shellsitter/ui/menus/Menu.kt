package edu.neit.jonathandoolittle.shellsitter.ui.menus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.ConfigurationCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import edu.neit.jonathandoolittle.shellsitter.ui.theme.GetIcon
import edu.neit.jonathandoolittle.shellsitter.ui.theme.RainyDarkGreen
import edu.neit.jonathandoolittle.shellsitter.ui.theme.Typography
import kotlinx.coroutines.launch

/////////////////////////////////
//// PRIMARY NAVIGATION BAR
////////////////////////////////

/**
 * Serves a primary navigation unit. The AnimatedBottomNavigationBar
 * uses a nav controller and is used to swing primarily between
 * major sections within an application. In this instance,
 * it holds the four distinct segments of Shell Sitter.
 * Each item is represented with an icon, and a title which
 * is only visible when selected. Items animate in scale
 * when selected.
 *
 * @param navigationController The navigation controller used for this bar. Typically, it is 'remembered' in a root composable.
 * @param items The items to be displayed in this bar. Sizing will be adjusted automatically.
 */
@Composable
fun AnimatedBottomNavigationBar(
    navigationController: NavHostController,
    items: List<AnimatedNavigationItem>
) {

    //TODO Fix back button not working as expected
    BottomNavigation {
        // Keep a stack for using the back button
        val navBackStackEntry by navigationController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        // Populate the bar
        items.forEach { option ->
            BottomNavigationItem(
                icon = { GetIcon(option.iconRes) },
                label = { Text(stringResource(id = option.displayName)) },
                selected = currentDestination?.hierarchy?.any {it.route == option.parameterizedRoute } == true,
                alwaysShowLabel = false,
                onClick = {
                    navigationController.navigate(option.route) {
                        popUpTo(navigationController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.background(Black)
            )
        }
    }
}

/////////////////////////////////
//// SECONDARY NAVIGATION BAR
////////////////////////////////

/**
 * A single tab in a [SlidingNavigationBar]. Each tab
 * has a title, and optionally a subtitle.
 *
 * @param title The title of this tab
 * @param subtitle The subtitle of this tab, if any
 * @param modifier
 */
@Composable
fun SlidingNavigationBarItem(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column {
            Text(
                text= title,
                style = Typography.subtitle1,
                maxLines = 1
            )
            subtitle?.let {
                Text(
                    text=it.uppercase(
                        ConfigurationCompat
                            .getLocales(LocalConfiguration.current)
                            .get(0)),
                    style = Typography.subtitle2,
                    maxLines = 1
                )
            }
        }
    }
}

/**
 * A row of tabs used as a sub-component in the
 * [SlidingNavigationBar]. Handles the rendering of
 * individual tabs
 *
 * @param items The list of tabs to be rendered
 * @param state The state of the [HorizontalPager] being used
 */
@ExperimentalPagerApi
@Composable
fun SlidingNavigationTabs(
    items: List<SlidingNavigationItem>,
    state: PagerState
) {
    ScrollableTabRow(
        selectedTabIndex = state.currentPage,
        indicator = {tabPositions ->
            Box {
                SlidingNavigationIndicator(
                    Modifier
                        .pagerTabIndicatorOffset(state, tabPositions)
                        .align(
                            // TODO Fix alignment for non-subtitled items
                            if (items[state.currentPage].subtitle.isNullOrEmpty()) {
                                Alignment.BottomCenter
                                //Alignment.CenterStart
                            } else {
                                Alignment.BottomCenter
                            }
                        ),
                )
            }

        },
        backgroundColor = Transparent,
        contentColor = White,
        edgePadding = 4.dp,
    ) {
        val coroutineScope = rememberCoroutineScope()
        items.forEachIndexed { index, item ->
            Tab(
                selected = index == state.currentPage,
                onClick = {
                    // Non-UI blocking animation
                    coroutineScope.launch {
                        state.animateScrollToPage(index)
                    }
                },
                text = {
                    SlidingNavigationBarItem(
                        title = item.title ?: item.titleResource?.let { stringResource(id = it) } ?: "",
                        subtitle = item.subtitle ?: item.subtitleResource?.let { stringResource(id = it) }
                    )
                },
                selectedContentColor = Black
            )
        }
    }
}

/**
 * An indicator placed under the current active
 * [SlidingNavigationBarItem] to indicate to the
 * user which tab is selected
 * @param modifier
 * @param color The color of this indicator
 */
@Composable
fun SlidingNavigationIndicator(
    modifier: Modifier = Modifier,
    color: Color = RainyDarkGreen
) {
    Spacer(
        modifier
            .padding(horizontal = 4.dp)
            .height(2.dp)
            .background(color, RoundedCornerShape(percent = 100))
    )
}

/**
 * A top navigation bar that displays the page's
 * title, as well as a list of tabs. Designed to be
 * used with a [HorizontalPager].
 *
 * @param titleResource The resource id for the title of this page
 * @param state The state of the [HorizontalPager] being used
 * @param items A list of navigation items that will be rended as tabs
 */
@ExperimentalPagerApi
@Composable
fun SlidingNavigationBar(
    titleResource: Int,
    state: PagerState,
    items: List<SlidingNavigationItem>
) {
    BoxWithConstraints {
        Column {
            // Display the title
            Row(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = stringResource(id = titleResource),
                    style = Typography.h1,
                    maxLines = 1
                )
            }
            // Display tabs, if any
            if(items.isNotEmpty()) {
                SlidingNavigationTabs(
                    items = items,
                    state = state
                )
            }

        }
    }
}