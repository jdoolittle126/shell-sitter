package edu.neit.jonathandoolittle.shellsitter.ui.screens.care

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import edu.neit.jonathandoolittle.shellsitter.R
import edu.neit.jonathandoolittle.shellsitter.ui.screens.settings.SettingsViewModel
import edu.neit.jonathandoolittle.shellsitter.ui.theme.Typography
import edu.neit.jonathandoolittle.shellsitter.ui.util.PageView2

@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun CareView() {

    val viewModel = viewModel(modelClass = CareViewModel::class.java)
    val pagerState = rememberPagerState(pageCount = viewModel.getTabs().size)

    PageView2(
        viewModel = viewModel,
        pagerState = pagerState,
        whenEmpty = {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Under Construction!", style = Typography.h2, modifier = Modifier.weight(2f))
                Spacer(modifier = Modifier.weight(1f))
                Image(painter = painterResource(id = R.drawable.ic_turtle_right), contentDescription = null, modifier = Modifier.weight(2f))
                Spacer(modifier = Modifier.weight(1f))
            }



        }
    ) {


    }
}