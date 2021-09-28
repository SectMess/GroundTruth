package com.astute.groundtruth.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.ImageLoader
import com.astute.groundtruth.ui.navigation.Screen
import com.astute.groundtruth.ui.theme.GroundTruthTheme
import com.astute.ui_missiondetail.MissionDetail
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import ui_missiondetail.ui.MissionDetailViewModel
import ui_missionlist.ui.MissionList
import ui_missionlist.ui.MissionListViewModel
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader


    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GroundTruthTheme {
                val navController = rememberAnimatedNavController()
                BoxWithConstraints() {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.MissionList.route,
                        builder = {
                            addMissionList(
                                navController = navController,
                                imageLoader = imageLoader,
                                width = constraints.maxWidth/2
                            )
                            addMissionDetail(
                                imageLoader = imageLoader,
                                width = constraints.maxWidth/2
                            )
                        }
                    )
                }

            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.addMissionList(
    navController: NavController,
    imageLoader: ImageLoader,
    width: Int
){
    composable(
        route = Screen.MissionList.route,
        exitTransition = { _,_ ->
            slideOutHorizontally(
                targetOffsetX = {-width},
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {_,_ ->
            slideInHorizontally(
                initialOffsetX = {-width},
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        }
    ){
        val viewModel: MissionListViewModel = hiltViewModel()
        MissionList(
            state = viewModel.state.value,
            events = viewModel::onTriggerEvent,
            imageLoader = imageLoader,
            navigateToDetailScreen = { missionId ->
                navController.navigate("${Screen.MissionDetail.route}/$missionId")
            }
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addMissionDetail(
    imageLoader: ImageLoader,
    width: Int
){
    composable(
        route = Screen.MissionDetail.route + "/{missionId}",
        arguments = Screen.MissionDetail.arguments,
        enterTransition = { _,_ ->
            slideInHorizontally(
                initialOffsetX = {width},
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {_,_ ->
            slideOutHorizontally(
                targetOffsetX = {width},
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ){
        val viewModel: MissionDetailViewModel = hiltViewModel()
        MissionDetail(
            state = viewModel.state.value,
            imageLoader = imageLoader,
            events = viewModel::onTriggerEvent,
        )
    }
}