package com.astute.ui_missiondetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.astute.mission_domain.Mission
import com.astute.mission_domain.maxAttackDmg
import com.astute.mission_domain.minAttackDmg
import ui_missiondetail.ui.MissionDetailState
import kotlin.math.round

@Composable
fun MissionDetail(
    state: MissionDetailState,
    imageLoader: ImageLoader,
) {
    state.mission?.let{ mission ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            item {
                Column {
                    val painter = rememberImagePainter(
                        mission.img,
                        imageLoader = imageLoader,
                        builder = {
                            placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
                        }
                    )
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 200.dp),
                        painter = painter,
                        contentDescription = mission.localizedName,
                        contentScale = ContentScale.Crop,
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 8.dp),
                                text = mission.localizedName,
                                style = MaterialTheme.typography.h1,
                            )
                            val iconPainter = rememberImagePainter(
                                mission.icon,
                                imageLoader = imageLoader,
                                builder = {
                                    placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
                                }
                            )
                            Image(
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                                    .align(Alignment.CenterVertically),
                                painter = iconPainter,
                                contentDescription = mission.localizedName,
                                contentScale = ContentScale.Crop,
                            )
                        }
                        Text(
                            modifier = Modifier
                                .padding(bottom = 4.dp),
                            text = mission.primaryAttribute.uiValue,
                            style = MaterialTheme.typography.subtitle1,
                        )
                        Text(
                            modifier = Modifier
                                .padding(bottom = 12.dp),
                            text = mission.attackType.uiValue,
                            style = MaterialTheme.typography.caption,
                        )
                        MissionBaseStats(
                            mission = mission,
                            padding = 10.dp,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        WinPercentages(mission = mission,)
                    }
                }
            }
        }
    }
}

/**
 * Displays Pro wins % and Turbo wins %
 */
@Composable
fun WinPercentages(
    mission: Mission,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ){
        // Pro Win %
        Column(
            modifier = Modifier.fillMaxWidth(.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
                ,
                text = "Pro Wins",
                style = MaterialTheme.typography.h2,
            )
            val proWinPercentage = remember {round(mission.proWins.toDouble() / mission.proPick.toDouble() * 100).toInt()}
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
                ,
                text = "${proWinPercentage} %",
                style = MaterialTheme.typography.h2,
                color = if(proWinPercentage > 50) Color(0xFF009a34) else MaterialTheme.colors.error
            )
        }
        // Turbo win %
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
                ,
                text = "Turbo Wins",
                style = MaterialTheme.typography.h2,
            )
            val turboWinPercentage = remember {round(mission.turboWins.toDouble() / mission.turboPicks.toDouble() * 100).toInt()}
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
                ,
                text = "${turboWinPercentage} %",
                style = MaterialTheme.typography.h2,
                color = if(turboWinPercentage > 50) Color(0xFF009a34) else MaterialTheme.colors.error
            )
        }
    }
}

@Composable
fun MissionBaseStats(
    mission: Mission,
    padding: Dp,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                text = "Base Stats",
                style = MaterialTheme.typography.h4,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(end = 20.dp)
                ) { // Str, Agi, Int, Health
                    Row( // STR
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.strength)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Row {
                            Text(
                                text = "${mission.baseStr}",
                                style = MaterialTheme.typography.body2,
                            )
                            Text(
                                text = " + ${mission.strGain}",
                                style = MaterialTheme.typography.caption,
                            )
                        }
                    }
                    Row( // AGI
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.agility)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Row {
                            Text(
                                text = "${mission.baseAgi}",
                                style = MaterialTheme.typography.body2,
                            )
                            Text(
                                text = " + ${mission.agiGain}",
                                style = MaterialTheme.typography.caption,
                            )
                        }
                    }
                    Row( // INT
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.intelligence)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Row {
                            Text(
                                text = "${mission.baseInt}",
                                style = MaterialTheme.typography.body2,
                            )
                            Text(
                                text = " + ${mission.intGain}",
                                style = MaterialTheme.typography.caption,
                            )
                        }
                    }
                    Row( // HP
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.health)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        val health =
                            remember { round(mission.baseHealth + mission.baseStr * 20).toInt() }
                        Text(
                            text = "${health}",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) { // Atk Range, proj speed, move speed, atk dmg
                    Row( // Atk Range
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${stringResource(R.string.attack_range)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${mission.attackRange}",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                    Row( // projectile speed
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${stringResource(R.string.projectile_speed)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${mission.projectileSpeed}",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                    Row( // Move speed
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${stringResource(R.string.move_speed)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${mission.moveSpeed}",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                    Row( // Attack damage
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${stringResource(R.string.attack_dmg)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        val atkMin = remember { mission.minAttackDmg() }
                        val atkMax = remember { mission.maxAttackDmg() }
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${atkMin} - ${atkMax}",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
            }
        }
    }
}