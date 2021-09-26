package com.astute.mission_datasource.cache

import com.astute.mission_datasource.network.EndPoints
import com.astute.mission_domain.*
import com.astute.missiondatasource.cache.Mission_Entity

fun Mission_Entity.toMission(): Mission {
    return Mission(
        id = id.toInt(),
        localizedName = localizedName,
        primaryAttribute = getMissionAttrFromAbbreviation(primaryAttribute),
        attackType = getMissionAttackType(attackType),
        roles = rolesToList(
            carry = roleCarry?.toInt() == 1,
            escape = roleEscape?.toInt() == 1,
            nuker = roleNuker?.toInt() == 1,
            initiator = roleInitiator?.toInt() == 1,
            durable = roleDurable?.toInt() == 1,
            disabler = roleDisabler?.toInt() == 1,
            jungler = roleJungler?.toInt() == 1,
            support = roleSupport?.toInt() == 1,
            pusher = rolePusher?.toInt() == 1,
        ),
        img = img,
        icon = icon,
        baseHealth = baseHealth.toFloat(),
        baseHealthRegen = baseHealthRegen?.toFloat(),
        baseMana = baseMana.toFloat(),
        baseManaRegen = baseManaRegen?.toFloat(),
        baseArmor = baseArmor.toFloat(),
        baseMoveRate = baseMoveRate.toFloat(),
        baseAttackMin = baseAttackMin.toInt(),
        baseAttackMax = baseAttackMax.toInt(),
        baseStr = baseStr.toInt(),
        baseAgi = baseAgi.toInt(),
        baseInt = baseInt.toInt(),
        strGain = strGain.toFloat(),
        agiGain = agiGain.toFloat(),
        intGain = intGain.toFloat(),
        attackRange = attackRange.toInt(),
        projectileSpeed = projectileSpeed.toInt(),
        attackRate = attackRate.toFloat(),
        moveSpeed = moveSpeed.toInt(),
        turnRate = turnRate?.toFloat(),
        legs = legs.toInt(),
        turboPicks = turboPicks.toInt(),
        turboWins = turboWins.toInt(),
        proWins = proWins.toInt(),
        proPick = proPick.toInt(),
        firstPick = firstPick.toInt(),
        firstWin = firstWin.toInt(),
        secondPick = secondPick.toInt(),
        secondWin = secondWin.toInt(),
        thirdPick = thirdPick.toInt(),
        thirdWin = thirdWin.toInt(),
        fourthPick = fourthPick.toInt(),
        fourthWin = fourthWin.toInt(),
        fifthPick = fifthPick.toInt(),
        fifthWin = fifthWin.toInt(),
        sixthPick = sixthPick.toInt(),
        sixthWin = sixthWin.toInt(),
        seventhPick = seventhPick.toInt(),
        seventhWin = seventhWin.toInt(),
        eighthWin = eighthWin.toInt(),
        eighthPick = eighthPick.toInt(),
    )
}

fun rolesToList(
    carry: Boolean,
    escape: Boolean,
    nuker: Boolean,
    initiator: Boolean,
    durable: Boolean,
    disabler: Boolean,
    jungler: Boolean,
    support: Boolean,
    pusher: Boolean,
): List<MissionRole>{
    val roles: MutableList<MissionRole> = mutableListOf()
    if(carry) roles.add(MissionRole.Carry)
    if(escape) roles.add(MissionRole.Escape)
    if(nuker) roles.add(MissionRole.Nuker)
    if(initiator) roles.add(MissionRole.Initiator)
    if(durable) roles.add(MissionRole.Durable)
    if(disabler) roles.add(MissionRole.Disabler)
    if(jungler) roles.add(MissionRole.Jungler)
    if(support) roles.add(MissionRole.Support)
    if(pusher) roles.add(MissionRole.Pusher)
    return roles.toList()
}