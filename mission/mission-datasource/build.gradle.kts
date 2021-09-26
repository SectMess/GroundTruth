apply {
    from("$rootDir/library-build.gradle")
}
plugins {
    id(SqlDelight.plugin)
    kotlin(KotlinPlugins.serialization) version Kotlin.version
}

dependencies{
    "implementation"(project(Modules.missionDomain))

    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)

    "implementation"(SqlDelight.runtime)
}
//to generate, need placeholder inside the main com folder
sqldelight{
    database("MissionDatabase"){
        packageName = "com.astute.mission_datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}