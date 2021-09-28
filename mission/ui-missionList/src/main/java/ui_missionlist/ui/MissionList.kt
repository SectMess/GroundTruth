package ui_missionlist.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.astute.components.DefaultScreenUI
import com.astute.core.domain.ProgressBarState
import com.astute.core.domain.UIComponentState
import com.astute.ui_missionlist.MissionListItem
import ui_missionlist.components.MissionListFilter
import ui_missionlist.components.MissionListToolbar

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun MissionList(
    state: MissionListState,
    events: (MissionListEvents) -> Unit,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit
) {
    DefaultScreenUI(
        queue = state.errorQueue,
        progressBarState = state.progressBarState,
        onRemoveHeadFromQueue = {
            events(MissionListEvents.onRemoveHeadFromQueue)
        },
    ) {
        Column{
            MissionListToolbar(
                missionName = state.missionName ,
                onMissionNameChanged = { missionName ->
                    events(MissionListEvents.UpdateMissionName(missionName))
                } ,
                onExecuteSearch = {
                    events(MissionListEvents.FilterMissions)
                },
                onShowFilterDialog = {
                    events(MissionListEvents.UpdateFilterDialogState(UIComponentState.Show))
                })

            LazyColumn{
                items(state.filteredMissions){ mission ->
                    MissionListItem(
                        mission = mission,
                        imageLoader = imageLoader,
                        onSelectMission = { missionId ->
                            navigateToDetailScreen(missionId)
                        }
                    )
                }
            }
        }

        if(state.filterDialogState is UIComponentState.Show){
            MissionListFilter(
                missionFilter = state.missionFilter,
                onUpdateMissionFilter = { missionFilter ->
                    events(MissionListEvents.UpdateMissionFilter(missionFilter))
                },
                onCloseDialog = {
                    events(MissionListEvents.UpdateFilterDialogState(UIComponentState.Hide))
                },
                attributeFilter = state.primaryAttribute,
                onUpdateAttributeFilter = { missionAttribute ->
                    events(MissionListEvents.UpdateAttributeFilter(missionAttribute))
                }
            )
        }

    }

}