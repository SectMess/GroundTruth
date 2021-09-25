package com.astute.mission_datasource.network

import com.astute.mission_domain.Mission
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

interface MissionService {

    suspend fun getMissionStats(): List<Mission>

    companion object Factory{
        fun build(): MissionService {
            return MissionServiceImpl(
                httpClient = HttpClient(Android){
                    install(JsonFeature){
                        serializer =KotlinxSerializer(
                            kotlinx.serialization.json.Json {
                                ignoreUnknownKeys = true
                            }
                        )
                    }
                }
            )
        }
    }
}