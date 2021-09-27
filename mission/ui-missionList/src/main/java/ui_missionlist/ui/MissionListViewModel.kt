package ui_missionlist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astute.core.DataState
import com.astute.core.domain.UIComponent
import com.astute.core.util.Logger
import com.astute.mission_domain.Mission
import com.astute.mission_domain.MissionAttribute
import com.astute.mission_domain.MissionFilter
import com.astute.mission_interactors.FilterMission
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
    private val filterMission: FilterMission,
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

            is MissionListEvents.FilterMissions -> {
                filterMissions()
            }

            is MissionListEvents.UpdateMissionName -> {
                updateMissionName(event.missionName)
            }

            is MissionListEvents.UpdateMissionFilter -> {
                updateMissionFilter(event.missionFilter)
            }

            is MissionListEvents.UpdateFilterDialogState -> {
                state.value = state.value.copy(filterDialogState = event.uiComponentState)
            }

            is MissionListEvents.UpdateAttributeFilter -> {
                updateAttributeFilter(event.attribute)
            }
        }
    }

    private fun updateAttributeFilter(attribute: MissionAttribute) {
        state.value = state.value.copy(primaryAttribute = attribute)
        filterMissions()
    }

    private fun updateMissionFilter(missionFilter: MissionFilter) {
        state.value = state.value.copy(missionFilter = missionFilter)
        filterMissions()
    }

    private fun updateMissionName(missionName: String){
        state.value = state.value.copy(missionName = missionName)
    }

    private fun filterMissions(){
        val filterList = filterMission.execute(
            current = state.value.missions,
            missionName = state.value.missionName,
            missionFilter = state.value.missionFilter,
            attributeFilter = state.value.primaryAttribute,
        )
        state.value = state.value.copy(filteredMissions = filterList)

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
                    filterMissions()
                }

                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }

}