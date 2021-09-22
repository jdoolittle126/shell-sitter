package edu.neit.jonathandoolittle.shellsitter.ui.util

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.math.MathUtils
import edu.neit.jonathandoolittle.shellsitter.ui.screens.SegmentViewModel
import edu.neit.jonathandoolittle.shellsitter.ui.menus.ShellSitterModelRender
import edu.neit.jonathandoolittle.shellsitter.ui.menus.SlidingNavigationBar
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


@SuppressLint("RestrictedApi")
@ExperimentalPagerApi
@Composable
fun PageView(
    viewModel: SegmentViewModel,
    navigationController: NavHostController,
    whenEmpty: @Composable () -> Unit
) {

    val pagerState = rememberPagerState(pageCount = viewModel.getTabs().size)

    Column {
        SlidingNavigationBar(titleResource = viewModel.titleResource, items = viewModel.getTabs(), state = pagerState)
        HorizontalPager(
            state = pagerState
        ) { page ->
            Box(
                Modifier
                    .fillMaxSize(0.95f)
                    .padding(bottom = 24.dp)
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        alpha = MathUtils.lerp(
                            0.1f,
                            1f,
                            1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .verticalScroll(rememberScrollState())
            ) {
                Column() {
                    viewModel.getPage(pagerState.currentPage).entities.forEach {
                        ShellSitterModelRender(model = it, viewModel = viewModel, navigationController = navigationController)
                    }
                }

            }
        }

        if(viewModel.pages.isEmpty()) {
            whenEmpty()
        }
    }
}

@ExperimentalAnimationApi
@SuppressLint("RestrictedApi")
@ExperimentalPagerApi
@Composable
fun PageView2(
    viewModel: SegmentViewModel,
    pagerState: PagerState,
    whenEmpty: @Composable () -> Unit,
    content: @Composable () -> Unit
) {

    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    var currentPage = remember { mutableStateOf(pagerState.currentPage) }
    var visible = remember{ mutableStateOf(false)}
    var coroutineScope = rememberCoroutineScope()

    // TODO FIX

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.distinctUntilChanged().collect {
            visible.value = true
        }
    }

    Column {
        SlidingNavigationBar(titleResource = viewModel.titleResource, items = viewModel.getTabs(), state = pagerState)
        HorizontalPager(
            state = pagerState
        ) { page ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp, top = 10.dp)
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    }
                    .verticalScroll(rememberScrollState())
            ) {
                Column() {
                    AnimatedVisibility(
                        visible = visible.value,
                        enter = slideInVertically(),
                        exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
                        content()
                    }
                }
            }
        }

        if(viewModel.pages.isEmpty()) {
            whenEmpty()
        }

    }
}