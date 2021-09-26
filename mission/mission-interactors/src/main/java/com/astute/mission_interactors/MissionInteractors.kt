package com.astute.mission_interactors

import com.astute.mission_datasource.cache.MissionCache
import com.astute.mission_datasource.network.MissionService
import com.squareup.sqldelight.db.SqlDriver

data class MissionInteractors(
    val getMissions: GetMissions,
    val getMissionFromCache: GetMissionFromCache
){
    companion object Factory {
        fun build(sqlDriver: SqlDriver): MissionInteractors{
            val service = MissionService.build()
            val cache = MissionCache.build(sqlDriver)
            return MissionInteractors(
                getMissions = GetMissions(
                    service = service,
                    cache = cache
                ),
                getMissionFromCache = GetMissionFromCache(
                    cache = cache
                )
            )
        }

        val schema: SqlDriver.Schema = MissionCache.schema

        val dbName: String = MissionCache.dbName
    }
}