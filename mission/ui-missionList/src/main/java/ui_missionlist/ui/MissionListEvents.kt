package ui_missionlist.ui

sealed class MissionListEvents {

    object GetMissions: MissionListEvents()

    object FilterMissions: MissionListEvents()

    data class  UpdateMissionName(
        val missionName: String
    ): MissionListEvents()
}