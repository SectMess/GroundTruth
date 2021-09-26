package ui_missiondetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astute.core.DataState
import com.astute.mission_interactors.GetMissionFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MissionDetailViewModel
@Inject
constructor(
    private val getMissionFromCache: GetMissionFromCache,
    private val savedStateHandle: SavedStateHandle,
): ViewModel(){

    val state: MutableState<MissionDetailState> = mutableStateOf(MissionDetailState())

    init {
        savedStateHandle.get<Int>("missionId")?.let { missionId ->
            onTriggerEvent(MissionDetailEvents.GetMissionFromCache(missionId))
        }
    }

    fun onTriggerEvent(event: MissionDetailEvents){
        when(event){
            is MissionDetailEvents.GetMissionFromCache -> {
                getMissionFromCache(event.id)
            }
        }
    }

    private fun getMissionFromCache(id: Int) {
        getMissionFromCache.execute(id).onEach { dataState ->
            when(dataState){
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }

                is DataState.Data -> {
                    state.value = state.value.copy(mission = dataState.data)
                }

                is DataState.Response -> {
                    //TODO(Handle Errors)
                }
            }
        }.launchIn(viewModelScope)
    }


}