package com.astute.mission_domain

sealed class MissionAttribute(
    val uiValue: String,
    val abbreviation: String
){

    object Agility: MissionAttribute(
        uiValue = "Agility",
        abbreviation = "agi"
    )

    object Strength: MissionAttribute(
        uiValue = "Strength",
        abbreviation = "str"
    )
    object Intelligence: MissionAttribute(
        uiValue = "Intelligence",
        abbreviation = "int"
    )
    object Unknown: MissionAttribute(
        uiValue = "Unknown",
        abbreviation = "unknown"
    )
}

fun getMissionAttrFromUiValue(uiValue: String): MissionAttribute{
    return when(uiValue){
        MissionAttribute.Agility.uiValue -> {
            MissionAttribute.Agility
        }
        MissionAttribute.Strength.uiValue -> {
            MissionAttribute.Strength
        }
        MissionAttribute.Intelligence.uiValue -> {
            MissionAttribute.Intelligence
        }
        else -> MissionAttribute.Unknown
    }
}

fun getMissionAttrFromAbbreviation(abbreviation: String): MissionAttribute{
    return when(abbreviation){
        MissionAttribute.Agility.abbreviation -> {
            MissionAttribute.Agility
        }
        MissionAttribute.Strength.abbreviation -> {
            MissionAttribute.Strength
        }
        MissionAttribute.Intelligence.abbreviation -> {
            MissionAttribute.Intelligence
        }
        else -> MissionAttribute.Unknown
    }
}

fun Mission.minAttackDmg(): Int {
    return when(primaryAttribute){
        is MissionAttribute.Strength -> {
            baseAttackMin + baseStr
        }
        is MissionAttribute.Agility -> {
            baseAttackMin + baseAgi
        }
        is MissionAttribute.Intelligence -> {
            baseAttackMin + baseInt
        }
        is MissionAttribute.Unknown -> {
            0
        }
        else -> return 0
    }
}

fun Mission.maxAttackDmg(): Int {
    return when(primaryAttribute){
        is MissionAttribute.Strength -> {
            baseAttackMax + baseStr
        }
        is MissionAttribute.Agility -> {
            baseAttackMax + baseAgi
        }
        is MissionAttribute.Intelligence -> {
            baseAttackMax + baseInt
        }
        is MissionAttribute.Unknown -> {
            0
        }
        else -> return 0
    }
}
