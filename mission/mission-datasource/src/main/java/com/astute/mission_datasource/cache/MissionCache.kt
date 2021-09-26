package com.astute.mission_datasource.cache

import com.astute.mission_domain.Mission
import com.squareup.sqldelight.db.SqlDriver

interface MissionCache {
    suspend fun getMission(id: Int): Mission?

    suspend fun removeMission(id: Int)

    suspend fun selectAll(): List<Mission>

    suspend fun insert(mission: Mission)

    suspend fun insert(missions: List<Mission>)

    suspend fun searchByName(localizedName: String): List<Mission>

    suspend fun searchByAttr(primaryAttr: String): List<Mission>

    suspend fun searchByAttackType(attackType: String): List<Mission>

    // Can select multiple roles
    suspend fun searchByRole(
        carry: Boolean = false,
        escape: Boolean = false,
        nuker: Boolean = false,
        initiator: Boolean = false,
        durable: Boolean = false,
        disabler: Boolean = false,
        jungler: Boolean = false,
        support: Boolean = false,
        pusher: Boolean = false,
    ): List<Mission>

    companion object Factory {
        fun build(sqlDriver: SqlDriver): MissionCache {
            return MissionCacheImpl(MissionDatabase(sqlDriver))
        }
        val schema: SqlDriver.Schema = MissionDatabase.Schema

        val dbName: String = "Missions.db"
    }
}