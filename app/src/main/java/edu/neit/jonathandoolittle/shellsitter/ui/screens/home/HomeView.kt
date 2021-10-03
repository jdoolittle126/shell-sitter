package edu.neit.jonathandoolittle.shellsitter.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import edu.neit.jonathandoolittle.shellsitter.R
import edu.neit.jonathandoolittle.shellsitter.ui.menus.ShellSitterModelRender
import edu.neit.jonathandoolittle.shellsitter.ui.util.Banner
import edu.neit.jonathandoolittle.shellsitter.ui.util.BannerAnchor
import edu.neit.jonathandoolittle.shellsitter.ui.util.PageView2

/*
enum class HomeMenuOptions {
    WELCOME = SlidingNavigationItem(),
    val HELP = SlidingNavigationItem(),
    val ABOUT = SlidingNavigationItem(),
}

 */

@ExperimentalAnimationApi
@SuppressLint("RestrictedApi")
@ExperimentalPagerApi
@Composable
fun HomeView() {

    val viewModel = viewModel(modelClass = HomeViewModel::class.java)
    //val pagerState = rememberPagerState(pageCount = viewModel.getTabs().size)
    val pagerState = rememberPagerState(0)

    PageView2(
        viewModel = viewModel,
        pagerState = pagerState,
        whenEmpty = {  EmptyHome() }
    ) { page ->
        Column {
            when(page) {
                0 -> {
                    Banner(anchor = BannerAnchor.LEFT, 
                        imageResource = R.drawable.ic_turtle_left, 
                        title = stringResource(
                            id = R.string.banner_welcome_title
                        ),
                        subtitle = stringResource(
                            id = R.string.banner_welcome_subtitle
                        )
                    )
                }
                pagerState.pageCount - 1 -> {
                    Banner(anchor = BannerAnchor.RIGHT,
                        imageResource = R.drawable.ic_turtle_right,
                        title = stringResource(
                            id = R.string.banner_about_title
                        ),
                        subtitle = stringResource(
                            id = R.string.banner_about_subtitle
                        ))
                }
                else -> {
                    Banner(anchor = BannerAnchor.CENTER,
                        title = stringResource(
                            id = R.string.banner_help_title
                        ),
                        subtitle = stringResource(
                            id = R.string.banner_help_subtitle
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(15.dp))

            viewModel.getPage(page).entities.forEach {
                ShellSitterModelRender(model = it, viewModel = viewModel, navigationController = rememberNavController())
            }
        }
    }
}

@Composable
fun EmptyHome() {
    Text(text = "Home is empty")
}
