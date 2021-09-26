package ui_missionlist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.astute.core.domain.ProgressBarState
import com.astute.ui_missionlist.MissionListItem

@Composable
fun MissionList(
    state: MissionListState,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn{
            items(state.missions){ mission ->
                MissionListItem(
                    mission = mission,
                    imageLoader = imageLoader,
                    onSelectMission = { missionId ->
                        navigateToDetailScreen(missionId)
                    }
                )
            }
        }
        if(state.progressBarState is ProgressBarState.Loading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}