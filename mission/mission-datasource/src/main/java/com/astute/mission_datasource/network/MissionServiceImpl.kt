package com.astute.mission_datasource.network

import com.astute.mission_datasource.network.model.MissionDto
import com.astute.mission_datasource.network.model.toMission
import com.astute.mission_domain.Mission
import io.ktor.client.*
import io.ktor.client.request.*

class MissionServiceImpl(
    private val httpClient: HttpClient
): MissionService {
    override suspend fun getMissionStats(): List<Mission> {
        return httpClient.get<List<MissionDto>>{
            url(EndPoints.HERO_STATS)
        }.map { it.toMission() }
    }
}