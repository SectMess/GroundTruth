package ui_missiondetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astute.core.DataState
import com.astute.core.domain.Queue
import com.astute.core.domain.UIComponent
import com.astute.core.util.Logger
import com.astute.mission_interactors.GetMissionFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MissionDetailViewModel
@Inject
constructor(
    private val getMissionFromCache: GetMissionFromCache,
    private val savedStateHandle: SavedStateHandle,
    private @Named("missionDetailLogger") val logger: Logger

): ViewModel(){

    val state: MutableState<MissionDetailState> = mutableStateOf(MissionDetailState())

    init {
        savedStateHandle.get<Int>("missionId")?.let { missionId ->
            onTriggerEvent(MissionDetailEvents.GetMissionFromCache(missionId))
        }
//        appendToMessageQueue(
//            uiComponent = UIComponent.Dialog(
//                title = "Test 1",
//                description = "Nothing here"
//            )
//        )
//        appendToMessageQueue(
//            uiComponent = UIComponent.Dialog(
//                title = "Test 2",
//                description = "Nothing there"
//            )
//        )
    }

    fun onTriggerEvent(event: MissionDetailEvents){
        when(event){
            is MissionDetailEvents.GetMissionFromCache -> {
                getMissionFromCache(event.id)
            }

            is MissionDetailEvents.onRemoveHeadFromQueue -> {
                removeHeadMessage()
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
                    when(dataState.uiComponent){
                        is UIComponent.Dialog -> {
                            appendToMessageQueue(dataState.uiComponent)
                        }
                        is UIComponent.None -> {
                            logger.log("getMissionFromCache: ${(dataState.uiComponent as UIComponent.None).message}")
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent){
        val queue = state.value.errorQueue
        queue.add((uiComponent))
        state.value = state.value.copy(errorQueue = Queue(mutableListOf())) //force recompose
        state.value = state.value.copy(errorQueue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove()
            state.value = state.value.copy(errorQueue = Queue(mutableListOf())) //force recompose
            state.value = state.value.copy(errorQueue = queue)

        } catch (e: Exception){
            logger.log("Nothing to remove from Dialog Queue")
        }
    }


}