package com.astute.mission_domain

sealed class MissionAttackType(
    val uiValue: String
){
    object Melee: MissionAttackType(
        uiValue = "Melee"
    )

    object Ranged: MissionAttackType(
        uiValue = "Ranged"
    )

    object Unknown: MissionAttackType(
        uiValue = "Unknown"
    )
}

fun getMissionAttackType(uiValue: String): MissionAttackType{
    return when(uiValue){
        MissionAttackType.Melee.uiValue -> {
            MissionAttackType.Melee
        }
        MissionAttackType.Ranged.uiValue -> {
            MissionAttackType.Ranged
        }
        else -> {
            MissionAttackType.Unknown
        }
    }
}
