package ui_missiondetail.ui

sealed class MissionDetailEvents{

    data class GetMissionFromCache(
        val id: Int,
    ): MissionDetailEvents()

    object onRemoveHeadFromQueue: MissionDetailEvents()
}
