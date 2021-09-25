apply {
    from("$rootDir/library-build.gradle")
}
dependencies{
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.missionDataSource))
    "implementation"(project(Modules.missionDomain))

    "implementation"(Kotlinx.coroutinesCore)
        
}