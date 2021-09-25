package com.astute.mission_interactors

import com.astute.mission_datasource.network.MissionService

data class MissionInteractors(
    val getMissions: GetMissions,

){
    companion object Factory {
        fun build(): MissionInteractors{
            val service = MissionService.build()
            return MissionInteractors(
                getMissions = GetMissions(
                    service = service
                )
            )
        }
    }
}