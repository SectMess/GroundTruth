package ui_missionlist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astute.core.DataState
import com.astute.core.domain.UIComponent
import com.astute.core.util.Logger
import com.astute.mission_interactors.GetMissions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MissionListViewModel
@Inject
constructor(
    private val getMissions: GetMissions,
    private @Named("missionListLogger") val logger: Logger
    //private val savedStateHandle: SavedStateHandle,
):ViewModel() {
    val state: MutableState<MissionListState> = mutableStateOf(MissionListState())

    init {
        onTriggerEvent(MissionListEvents.GetMissions)
    }

    fun onTriggerEvent(event: MissionListEvents){
        when(event){
            is MissionListEvents.GetMissions -> {
                getMissions()
            }
        }
    }

    private fun getMissions(){
        getMissions.execute().onEach { dataState ->
            when(dataState){
                is DataState.Response -> {
                    when(dataState.uiComponent){
                        is UIComponent.Dialog -> {
                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }

                is DataState.Data -> {
                    state.value = state.value.copy(missions = dataState.data ?: listOf())
                }

                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }

}