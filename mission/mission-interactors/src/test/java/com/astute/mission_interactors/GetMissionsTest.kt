package com.astute.mission_interactors

import com.astute.core.DataState
import com.astute.core.domain.ProgressBarState
import com.astute.mission_datasource_test.cache.MissionCacheFake
import com.astute.mission_datasource_test.cache.MissionDatabaseFake
import com.astute.mission_datasource_test.network.MissionServiceFake
import com.astute.mission_datasource_test.network.MissionServiceResponseType
import com.astute.mission_datasource_test.network.data.MissionDataValid.NUM_MISSIONS
import com.astute.mission_domain.Mission
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetMissionsTest {

    //system in test
    private lateinit var getMissions: GetMissions

    @Test
    fun getMissions_success() = runBlocking{
        //setup
        val missionDatabase = MissionDatabaseFake()
        val missionCache = MissionCacheFake(missionDatabase)
        val missionService = MissionServiceFake.build(
            type = MissionServiceResponseType.ValidData
        )

        getMissions = GetMissions(
            cache = missionCache,
            service = missionService
        )

        //confirm cache is empty
        var cacheMissions = missionCache.selectAll()
        assert(cacheMissions.isEmpty())

        //execute use case
        val emissions = getMissions.execute().toList()

        //First emission
        assert(emissions[0] == DataState.Loading<List<Mission>>(ProgressBarState.Loading))

        //Second emission
        assert(emissions[1] is DataState.Data)
        assert((emissions[1] as DataState.Data).data?.size ?: 0 == NUM_MISSIONS)

        //confirm cache not empty
        cacheMissions = missionCache.selectAll()
        assert(cacheMissions.size == NUM_MISSIONS)

        //Last emission
        assert(emissions[2] == DataState.Loading<List<Mission>>(ProgressBarState.Idle))
    }
}