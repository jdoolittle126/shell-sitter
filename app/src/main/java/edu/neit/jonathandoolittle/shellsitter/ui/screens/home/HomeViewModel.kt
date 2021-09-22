package edu.neit.jonathandoolittle.shellsitter.ui.screens.home

import android.app.Application
import androidx.compose.ui.graphics.Color
import edu.neit.jonathandoolittle.shellsitter.R
import edu.neit.jonathandoolittle.shellsitter.models.ArticleModel
import edu.neit.jonathandoolittle.shellsitter.models.PageModel
import edu.neit.jonathandoolittle.shellsitter.ui.screens.SegmentViewModel
import edu.neit.jonathandoolittle.shellsitter.ui.menus.SlidingNavigationOptions
import edu.neit.jonathandoolittle.shellsitter.ui.theme.RainyDarkBlue
import edu.neit.jonathandoolittle.shellsitter.ui.theme.RainyDarkGreen
import edu.neit.jonathandoolittle.shellsitter.ui.theme.RainyLightBlue
import edu.neit.jonathandoolittle.shellsitter.ui.theme.RainyYellow

/**
 *
 * Class Description - TODO
 *
 * Class Logic - TODO
 *
 * <pre>
 *  Class Usage - TODO
 * </pre>
 *
 * @author Jonathan Doolittle
 * @version 0.1 - 9/18/2021
 *
 */

class HomeViewModel(application: Application) : SegmentViewModel(titleResource = R.string.home, application = application) {

    // ******************************
    // Variables
    // ******************************

    // ******************************
    // Public methods
    // ******************************

    init {
        addPage(PageModel(SlidingNavigationOptions.WELCOME, listOf(
            ArticleModel("New to the app?", "Learn how to make the most of Shell Sitter's care features", RainyLightBlue, ""),
            ArticleModel("Turtle? Tortoise? Terrapin?", "When most people picture a turtle, they think of a sea turtle swimming amongst divers, or a box turtle sitting on a log. So what's with all the names?", RainyDarkGreen, ""),
            ArticleModel("Picking the Perfect Pet!", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis noui", RainyDarkGreen, "")
        )))

        addPage(PageModel(SlidingNavigationOptions.HELP, listOf(
            ArticleModel("Basic Features", "Learn how to navigate within the Shell Sitter app!", RainyYellow, ""),
            ArticleModel("Adding a Pet", "Adding your shelled friends to Shell Sitter!", RainyYellow, ""),
            ArticleModel("Species Identification", "Search our databases for information on different turtles!", RainyYellow, "")
        )))

        addPage(PageModel(SlidingNavigationOptions.ABOUT, listOf(
            ArticleModel("Author", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", RainyLightBlue, ""),
            ArticleModel("GitHub", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", RainyLightBlue, "")
        )))

    }

    // ******************************
    // Private methods
    // ******************************

}