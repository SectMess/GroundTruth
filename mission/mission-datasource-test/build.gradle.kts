apply {
    from("$rootDir/library-build.gradle")
}

plugins{
    kotlin(KotlinPlugins.serialization) version Kotlin.version
}
dependencies{
    "implementation"(project(Modules.missionDataSource))
    "implementation"(project(Modules.missionDomain))

    "implementation"(Ktor.ktorClientMock)
    "implementation"(Ktor.clientSerialization)






}