package ui_missionlist.ui

import com.astute.core.domain.ProgressBarState
import com.astute.mission_domain.Mission

data class MissionListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val missions: List<Mission> = listOf(),
    val filteredMissions: List<Mission> = listOf(),
    val missionName: String = "",

)
