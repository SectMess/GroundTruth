package com.astute.mission_domain

sealed class MissionRole(
    val uiValue: String,
){
    object Carry: MissionRole(
        uiValue = "Carry"
    )

    object Escape: MissionRole(
        uiValue = "Escape"
    )

    object Nuker: MissionRole(
        uiValue = "Nuker"
    )

    object Initiator: MissionRole(
        uiValue = "Initiator"
    )

    object Durable: MissionRole(
        uiValue = "Durable"
    )

    object Disabler: MissionRole(
        uiValue = "Disabler"
    )

    object Jungler: MissionRole(
        uiValue = "Jungler"
    )

    object Support: MissionRole(
        uiValue = "Support"
    )

    object Pusher: MissionRole(
        uiValue = "Pusher"
    )

    object Unknown: MissionRole(
        uiValue = "Unknown"
    )
}

fun getMissionRole(uiValue: String): MissionRole{
    return when(uiValue){

        MissionRole.Carry.uiValue -> {
            MissionRole.Carry
        }
        MissionRole.Escape.uiValue -> {
            MissionRole.Escape
        }
        MissionRole.Nuker.uiValue -> {
            MissionRole.Nuker
        }
        MissionRole.Initiator.uiValue -> {
            MissionRole.Initiator
        }
        MissionRole.Durable.uiValue -> {
            MissionRole.Durable
        }
        MissionRole.Disabler.uiValue -> {
            MissionRole.Disabler
        }
        MissionRole.Jungler.uiValue -> {
            MissionRole.Jungler
        }
        MissionRole.Support.uiValue -> {
            MissionRole.Support
        }
        MissionRole.Pusher.uiValue -> {
            MissionRole.Pusher
        }
        else -> {
            MissionRole.Unknown
        }
    }
}
