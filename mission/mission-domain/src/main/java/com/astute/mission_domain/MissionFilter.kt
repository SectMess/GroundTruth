package com.astute.mission_domain

import com.astute.core.domain.FilterOrder

sealed class MissionFilter(
    val uiValue: String
) {
    data class Mission(
        val order: FilterOrder = FilterOrder.Descending
    ): MissionFilter("Mission")

    data class ProWins(
        val order: FilterOrder = FilterOrder.Descending
    ): MissionFilter("Pro Win-rate")
}
