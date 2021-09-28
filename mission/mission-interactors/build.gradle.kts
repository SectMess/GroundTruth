apply {
    from("$rootDir/library-build.gradle")
}
dependencies{
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.missionDataSource))
    "implementation"(project(Modules.missionDomain))

    "implementation"(Kotlinx.coroutinesCore)

    "testImplementation"(project(Modules.missionDataSourceTest))
    "testImplementation"(Junit.junit4)
    "testImplementation"(Ktor.ktorClientMock)
    "testImplementation"(Ktor.clientSerialization)

}