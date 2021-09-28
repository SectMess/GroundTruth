package com.astute.mission_datasource_test.network

import com.astute.mission_datasource.network.MissionService
import com.astute.mission_datasource.network.MissionServiceImpl
import com.astute.mission_datasource_test.network.data.MissionDataEmpty
import com.astute.mission_datasource_test.network.data.MissionDataMalformed
import com.astute.mission_datasource_test.network.data.MissionDataValid
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*

class MissionServiceFake {

    companion object Factory {
        private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
        private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

        fun build(
            type: MissionServiceResponseType
        ): MissionService{
            val client = HttpClient(MockEngine){
                install(JsonFeature){
                    serializer = KotlinxSerializer(
                        kotlinx.serialization.json.Json {
                            ignoreUnknownKeys = true
                        }
                    )
                }

                engine {
                    addHandler { request ->
                        when(request.url.fullUrl){
                            "https://api.opendota.com/api/heroStats" -> {
                                val responseHeaders = headersOf(
                                    "Content-Type" to listOf("application/json", "charset=utf-8")
                                )
                                when(type){
                                    is MissionServiceResponseType.EmptyList -> {
                                        respond(
                                            MissionDataEmpty.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }
                                    is MissionServiceResponseType.MalformedData -> {
                                        respond(
                                            MissionDataMalformed.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }
                                    is MissionServiceResponseType.ValidData -> {
                                        respond(
                                            MissionDataValid.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }
                                    is MissionServiceResponseType.Http404 -> {
                                        respond(
                                            MissionDataEmpty.data,
                                            status = HttpStatusCode.NotFound,
                                            headers = responseHeaders
                                        )
                                    }
                                }

                            }
                            else -> error("Unhandled ${request.url.fullUrl}")
                        }
                    }
                }
            }

            return MissionServiceImpl(client)



        }
    }

}