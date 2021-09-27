package ui_missionlist.ui

import com.astute.core.domain.UIComponentState
import com.astute.mission_domain.MissionAttribute
import com.astute.mission_domain.MissionFilter

sealed class MissionListEvents {

    object GetMissions: MissionListEvents()

    object FilterMissions: MissionListEvents()

    data class  UpdateMissionName(
        val missionName: String
    ): MissionListEvents()

    data class UpdateMissionFilter(
        val missionFilter: MissionFilter
    ): MissionListEvents()

    data class UpdateFilterDialogState(
        val uiComponentState: UIComponentState
    ): MissionListEvents()

    data class UpdateAttributeFilter(
        val attribute: MissionAttribute
    ): MissionListEvents()
}