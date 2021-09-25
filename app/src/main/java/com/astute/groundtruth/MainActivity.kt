package com.astute.groundtruth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.astute.core.DataState
import com.astute.core.domain.ProgressBarState
import com.astute.core.domain.UIComponent
import com.astute.core.util.Logger
import com.astute.groundtruth.ui.theme.GroundTruthTheme
import com.astute.mission_domain.Mission
import com.astute.mission_interactors.MissionInteractors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private val missions: MutableState<List<Mission>> = mutableStateOf(listOf())
    private val progressBarState: MutableState<ProgressBarState> = mutableStateOf(ProgressBarState.Idle)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val getMissions = MissionInteractors.build().getMissions
        val logger = Logger("GetMissionsTest")

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
                    missions.value = dataState.data ?: listOf()
                }

                is DataState.Loading -> {
                    progressBarState.value = dataState.progressBarState
                }
            }
        }.launchIn(CoroutineScope(IO))


        setContent {
            GroundTruthTheme {
                Box(modifier = Modifier.fillMaxSize()){
                    LazyColumn{
                        items(missions.value){ mission ->
                            Text(mission.localizedName)
                        }
                    }
                    if(progressBarState.value is ProgressBarState.Loading){
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}