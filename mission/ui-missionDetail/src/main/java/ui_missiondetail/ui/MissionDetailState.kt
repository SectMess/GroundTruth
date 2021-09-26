package ui_missiondetail.ui

import com.astute.core.domain.ProgressBarState
import com.astute.mission_domain.Mission

data class MissionDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val mission: Mission? = null,
)
