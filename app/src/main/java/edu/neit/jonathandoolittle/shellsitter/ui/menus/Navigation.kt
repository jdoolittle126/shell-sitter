package edu.neit.jonathandoolittle.shellsitter.ui.menus

import SettingsView
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import edu.neit.jonathandoolittle.shellsitter.R
import edu.neit.jonathandoolittle.shellsitter.models.ArticleModel
import edu.neit.jonathandoolittle.shellsitter.models.PetModel
import edu.neit.jonathandoolittle.shellsitter.ui.screens.SegmentViewModel
import edu.neit.jonathandoolittle.shellsitter.ui.screens.care.CareView
import edu.neit.jonathandoolittle.shellsitter.ui.screens.home.HomeView
import edu.neit.jonathandoolittle.shellsitter.ui.screens.pets.DisplayPet
import edu.neit.jonathandoolittle.shellsitter.ui.screens.pets.PetsView
import edu.neit.jonathandoolittle.shellsitter.ui.screens.pets.PetsViewModel
import edu.neit.jonathandoolittle.shellsitter.ui.util.ReadingCard

/////////////////////////////////
//// NAVIGATION OPTIONS
////////////////////////////////

/*
    This section is for creating and maintaining all of the menu options.
    Menu options are the choices each menu offers. For instance, the
    primary navigation in Shell Sitter offers 'Home', 'Pets', 'Care',
    and 'Settings' as options. These are held in enums and can be
    easily altered.
 */

/**
 * Contains all of the navigation items for the bottom animated
 * navigation bar
 */
object AnimatedNavigationOptions {

    val HOME = AnimatedNavigationItem(R.string.home, R.drawable.ic_baseline_home_24 , "home")

    /*
    Arguments: edit
        - null, render as normal (show pets as tabs, empty screen if no tabs)
        - 0, create new pet dialog
        - n, when n > 0, load pet for editing
     */
    val PETS = AnimatedNavigationItem(R.string.my_pets, R.drawable.ic_turtle, "pets", listOf("edit"))
    val CARE = AnimatedNavigationItem(R.string.care, R.drawable.ic_baseline_favorite_24, "care", listOf("test"))
    val SETTINGS = AnimatedNavigationItem(R.string.settings, R.drawable.ic_baseline_settings_24, "settings")

    // Order them as we would like them to appear
    val values = listOf(
        HOME,
        PETS,
        CARE,
        SETTINGS
    )
}

/**
 * Contains all of the concrete tabs for various pages
 */
object SlidingNavigationOptions {
    // Home tabs
    val WELCOME = SlidingNavigationItem(titleResource = R.string.welcome)
    val HELP = SlidingNavigationItem(titleResource = R.string.help)
    val ABOUT = SlidingNavigationItem(titleResource = R.string.about)

    // TODO Remove
    val TEST1 = SlidingNavigationItem(title = "Archimedes", subtitle = "Red-Eared Slider")
    val TEST2 = SlidingNavigationItem(title = "Gary", subtitle = "Bog Turtle")
    val TEST3 = SlidingNavigationItem(title = "Squirt", subtitle = "Box Turtle")
    val TEST4 = SlidingNavigationItem(title = "Pokey", subtitle = "African Sideneck Turtle")
}

/**
 * Contains routes for the various navigation options. I am only
 * using it for the four main sections of the app, as I do not need
 * a back stack or anything else, and I am using the [ShellSitterModelRender]
 * function to dynamically populate my data instead.
 *
 * @param navController The navigation controller to use
 * @param startDestination The starting destination when the app first launches
 */

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun ShellSitterNavigationGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AnimatedNavigationOptions.HOME.parameterizedRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {

        // CORE NAVIGATION ROUTES

        composable(
            route = AnimatedNavigationOptions.PETS.parameterizedRoute,
            arguments = AnimatedNavigationOptions.PETS.navArguments
        ) {
            PetsView(navController, it.arguments)
        }

        composable(
            route = AnimatedNavigationOptions.CARE.parameterizedRoute,
            arguments = AnimatedNavigationOptions.CARE.navArguments
        ) {
            CareView()
        }

        composable(
            route = AnimatedNavigationOptions.HOME.parameterizedRoute,
            arguments = AnimatedNavigationOptions.HOME.navArguments
        ) {
            HomeView()
        }

        composable(
            route = AnimatedNavigationOptions.SETTINGS.parameterizedRoute,
            arguments = AnimatedNavigationOptions.SETTINGS.navArguments
        ) {
            SettingsView()
        }
    }
}

fun ArgBuilder(
    option: AnimatedNavigationItem
): List<NamedNavArgument> {

    var result: MutableList<NamedNavArgument> = mutableListOf()

    option.arguments.forEach {
        result.add(navArgument(it) {
            nullable = true
            defaultValue = null
        })
    }

    return result.toList()
}

fun RouteBuilder(
    option: AnimatedNavigationItem
): String {
    var result = option.route
    option.arguments.forEach {
        result += "?$it={$it}"
    }
    return result
}

fun getArgument(
    key: String,
    arguments: Bundle?
): String? {

    return arguments?.getString(key)

}

/**
 * Handles the rendering of various types of entities, and
 * routes them with the correct composable and data entry
 *
 * @param model The model to render
 */
@Composable
fun ShellSitterModelRender(
    model: Any,
    viewModel: SegmentViewModel,
    navigationController: NavHostController
) {
    when(model) {
        is ArticleModel -> {
            ReadingCard(title = model.title, markerColor = model.markerColor, contentPreview = model.preview)
        }
        is PetModel -> {
            DisplayPet(model = model, viewModel = viewModel as PetsViewModel, navigationController = navigationController)
        }
    }
}

// TODO Move to own file?
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebPageScreen(urlToRender: String) {

    val coroutineScope = rememberCoroutineScope()

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            webViewClient = WebViewClient()
            loadUrl(urlToRender)
            isVerticalScrollBarEnabled = false
        }
    }, update = {
        coroutineScope.run {
            it.loadUrl(urlToRender)
        }
    })
}