package com.astute.mission_datasource_test.network

sealed class MissionServiceResponseType{

    object EmptyList: MissionServiceResponseType()
    object MalformedData: MissionServiceResponseType()
    object ValidData: MissionServiceResponseType()
    object Http404: MissionServiceResponseType()
}
