package com.astute.mission_datasource_test.cache

import com.astute.mission_datasource.cache.MissionCache
import com.astute.mission_domain.Mission
import com.astute.mission_domain.MissionRole

class MissionCacheFake(
    private val db: MissionDatabaseFake
): MissionCache {
    override suspend fun getMission(id: Int): Mission? {
        return db.missions.find { it.id == id }
    }

    override suspend fun removeMission(id: Int) {
        db.missions.removeIf { it.id == id }
    }

    override suspend fun selectAll(): List<Mission> {
        return db.missions
    }

    override suspend fun insert(hero: Mission) {
        if(db.missions.isNotEmpty()){
            var didInsert = false
            for(h in db.missions){
                if(h.id == hero.id){
                    db.missions.remove(h)
                    db.missions.add(hero)
                    didInsert = true
                    break
                }
            }
            if(!didInsert){
                db.missions.add(hero)
            }
        }
        else{
            db.missions.add(hero)
        }
    }

    override suspend fun insert(missions: List<Mission>) {
        if(db.missions.isNotEmpty()){
            for(hero in missions){
                if(db.missions.contains(hero)){
                    db.missions.remove(hero)
                    db.missions.add(hero)
                }
            }
        }
        else{
            db.missions.addAll(missions)
        }
    }

    override suspend fun searchByName(localizedName: String): List<Mission> {
        return db.missions.find { it.localizedName == localizedName }?.let {
            listOf(it)
        }?: listOf()
    }

    override suspend fun searchByAttr(primaryAttr: String): List<Mission> {
        return db.missions.filter { it.primaryAttribute.uiValue == primaryAttr }
    }

    override suspend fun searchByAttackType(attackType: String): List<Mission> {
        return db.missions.filter { it.attackType.uiValue == attackType }
    }

    override suspend fun searchByRole(
        carry: Boolean,
        escape: Boolean,
        nuker: Boolean,
        initiator: Boolean,
        durable: Boolean,
        disabler: Boolean,
        jungler: Boolean,
        support: Boolean,
        pusher: Boolean
    ): List<Mission> {
        val missions: MutableList<Mission> = mutableListOf()
        if(carry){
            missions.addAll(db.missions.filter { it.roles.contains(MissionRole.Carry) })
        }
        if(escape){
            missions.addAll(db.missions.filter { it.roles.contains(MissionRole.Escape) })
        }
        if(nuker){
            missions.addAll(db.missions.filter { it.roles.contains(MissionRole.Nuker) })
        }
        if(initiator){
            missions.addAll(db.missions.filter { it.roles.contains(MissionRole.Initiator) })
        }
        if(durable){
            missions.addAll(db.missions.filter { it.roles.contains(MissionRole.Durable) })
        }
        if(disabler){
            missions.addAll(db.missions.filter { it.roles.contains(MissionRole.Disabler) })
        }
        if(jungler){
            missions.addAll(db.missions.filter { it.roles.contains(MissionRole.Jungler) })
        }
        if(support){
            missions.addAll(db.missions.filter { it.roles.contains(MissionRole.Support) })
        }
        if(pusher){
            missions.addAll(db.missions.filter { it.roles.contains(MissionRole.Pusher) })
        }
        return missions.distinctBy { it.id }
    }
}