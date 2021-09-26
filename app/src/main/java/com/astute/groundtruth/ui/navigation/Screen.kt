package com.astute.groundtruth.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>
) {

    object MissionList: Screen(
        route = "missionList",
        arguments = emptyList()
    )

    object MissionDetail: Screen(
        route = "missionDetail",
        arguments = listOf(
            navArgument("missionId"){
                type = NavType.IntType
            }
        )
    )
}