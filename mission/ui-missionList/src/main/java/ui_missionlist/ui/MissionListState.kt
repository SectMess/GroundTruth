package ui_missionlist.ui

import com.astute.core.domain.ProgressBarState
import com.astute.core.domain.Queue
import com.astute.core.domain.UIComponent
import com.astute.core.domain.UIComponentState
import com.astute.mission_domain.Mission
import com.astute.mission_domain.MissionAttribute
import com.astute.mission_domain.MissionFilter

data class MissionListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val missions: List<Mission> = listOf(),
    val filteredMissions: List<Mission> = listOf(),
    val missionName: String = "",
    val missionFilter: MissionFilter = MissionFilter.Mission(),
    val primaryAttribute: MissionAttribute = MissionAttribute.Unknown,
    val filterDialogState: UIComponentState = UIComponentState.Hide,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
)
