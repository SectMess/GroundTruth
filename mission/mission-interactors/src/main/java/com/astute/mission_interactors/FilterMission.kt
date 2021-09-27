package com.astute.mission_interactors

import com.astute.core.domain.FilterOrder
import com.astute.mission_domain.Mission
import com.astute.mission_domain.MissionAttribute
import com.astute.mission_domain.MissionFilter
import kotlin.math.round

class FilterMission {

    fun execute(
        current: List<Mission>,
        missionName: String,
        missionFilter: MissionFilter,
        attributeFilter: MissionAttribute,
    ): List<Mission> {
        var filteredList: MutableList<Mission> = current.filter{
            it.localizedName.lowercase().contains(missionName.lowercase())
        }.toMutableList()

        when(missionFilter){
            is MissionFilter.Mission -> {
                when(missionFilter.order){
                    is FilterOrder.Descending -> {
                        filteredList.sortByDescending { it.localizedName }
                    }
                    is FilterOrder.Ascending -> {
                        filteredList.sortBy { it.localizedName }
                    }
                }
            }

            is MissionFilter.ProWins -> {
                when(missionFilter.order){
                    is FilterOrder.Descending -> {
                        filteredList.sortByDescending {
                            getWinRate(it.proWins.toDouble(), it.proPick.toDouble())
                        }
                    }
                    is FilterOrder.Ascending -> {
                        filteredList.sortBy {
                            getWinRate(it.proWins.toDouble(), it.proPick.toDouble())
                        }
                    }
                }
            }
        }

        when(attributeFilter){
            is MissionAttribute.Strength -> {
                filteredList = filteredList.filter { it.primaryAttribute is MissionAttribute.Strength}.toMutableList()
            }
            is MissionAttribute.Intelligence -> {
                filteredList = filteredList.filter { it.primaryAttribute is MissionAttribute.Intelligence}.toMutableList()
            }
            is MissionAttribute.Agility -> {
                filteredList = filteredList.filter { it.primaryAttribute is MissionAttribute.Agility}.toMutableList()
            }
            is MissionAttribute.Unknown -> {
                //No filtering
            }
        }

        return filteredList

    }

    private fun getWinRate(proWins: Double, proPick: Double): Int{
        return if(proPick <= 0){
            0
        } else {
            val winRate: Int = round(proWins/proPick*100).toInt()
            winRate
        }
    }
}