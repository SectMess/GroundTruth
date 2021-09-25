package com.astute.mission_interactors

import com.astute.core.DataState
import com.astute.core.domain.ProgressBarState
import com.astute.core.domain.UIComponent
import com.astute.mission_datasource.network.MissionService
import com.astute.mission_domain.Mission
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetMissions(
    private val service: MissionService

    //TODO: Add Caching
) {

    fun execute(): Flow<DataState<List<Mission>>> = flow{
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val missions: List<Mission> = try {
                service.getMissionStats()
            } catch (e: Exception){
                e.printStackTrace()
                emit(
                    DataState.Response<List<Mission>>(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Error",
                            description = e.message?: "Unknown NetworkError!"
                        )
                    )
                )
                listOf()
            }

            //TODO: Caching
            emit(DataState.Data(missions))

        } catch (e: Exception){
            e.printStackTrace()
            emit(
                DataState.Response<List<Mission>>(
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