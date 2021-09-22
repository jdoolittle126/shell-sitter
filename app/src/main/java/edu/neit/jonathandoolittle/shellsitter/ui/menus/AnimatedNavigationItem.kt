package edu.neit.jonathandoolittle.shellsitter.ui.menus

import androidx.annotation.StringRes

/**
 *
 * An item to be displayed in an [AnimatedBottomNavigationBar].
 *
 * Since these are concrete, and not dynamically generated, each
 * item should be displayed using a String resource. Optionally,
 * each item can also identify a icon resource. Since Material
 * Design icons come in the form of composable functions, using
 * icon resources for all icons allows for non-material icons to
 * be included. Note, if no icon is indicated, the item may not
 * appear visible, since titles aren't rendered unless selected
 * by default.
 *
 * @property displayName The string resource to display
 * @property iconRes The optional icon resource to display
 * @property route The string route to navigate to on click
 *
 * @author Jonathan Doolittle
 * @version 0.1 - 9/10/2021
 *
 */
data class AnimatedNavigationItem(
    @StringRes val displayName: Int,
    val iconRes: Int?,
    val route: String,
    val arguments: List<String> = listOf()
) {
    val parameterizedRoute = RouteBuilder(this)
    val navArguments = ArgBuilder(this)
}