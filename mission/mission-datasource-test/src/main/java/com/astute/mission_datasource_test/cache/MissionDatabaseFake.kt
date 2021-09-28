package com.astute.mission_datasource_test.cache

import com.astute.mission_domain.Mission

class MissionDatabaseFake {

    val missions: MutableList<Mission> = mutableListOf()
}