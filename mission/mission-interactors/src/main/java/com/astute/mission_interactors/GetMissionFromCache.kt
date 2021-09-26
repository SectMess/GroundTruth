package com.astute.mission_interactors

import com.astute.core.DataState
import com.astute.core.domain.ProgressBarState
import com.astute.core.domain.UIComponent
import com.astute.mission_datasource.cache.MissionCache
import com.astute.mission_domain.Mission
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetMissionFromCache(
    private val cache: MissionCache,
) {
    fun execute(
        id: Int,
    ): Flow<DataState<Mission>> = flow{
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val cachedMission = cache.getMission(id)
            if(cachedMission == null){
                throw Exception("Mission/Hero does not exist in the cache")
            }

            emit(DataState.Data(cachedMission))

        } catch (e: Exception){
            e.printStackTrace()
            emit(
                DataState.Response<Mission>(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message?: "Unknown Error!"
                    )
                )
            )

        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}