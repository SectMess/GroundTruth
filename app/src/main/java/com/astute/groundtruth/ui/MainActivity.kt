package com.astute.groundtruth.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.astute.groundtruth.ui.navigation.Screen
import com.astute.groundtruth.ui.theme.GroundTruthTheme
import com.astute.ui_missiondetail.MissionDetail
import dagger.hilt.android.AndroidEntryPoint
import ui_missiondetail.ui.MissionDetailViewModel
import ui_missionlist.ui.MissionList
import ui_missionlist.ui.MissionListViewModel
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GroundTruthTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.MissionList.route,
                    builder = {
                        addMissionList(
                            navController = navController,
                            imageLoader = imageLoader
                        )
                        addMissionDetail(
                            imageLoader = imageLoader
                        )
                    }
                )
            }
        }
    }
}

fun NavGraphBuilder.addMissionList(
    navController: NavController,
    imageLoader: ImageLoader
){
    composable(
        route = Screen.MissionList.route
    ){
        val viewModel: MissionListViewModel = hiltViewModel()
        MissionList(
            state = viewModel.state.value,
            imageLoader = imageLoader,
            navigateToDetailScreen = { missionId ->
                navController.navigate("${Screen.MissionDetail.route}/$missionId")
            }
        )
    }
}

fun NavGraphBuilder.addMissionDetail(
    imageLoader: ImageLoader
){
    composable(
        route = Screen.MissionDetail.route + "/{missionId}",
        arguments = Screen.MissionDetail.arguments
    ){
        val viewModel: MissionDetailViewModel = hiltViewModel()
        MissionDetail(
            state = viewModel.state.value,
            imageLoader = imageLoader,
        )
    }
}