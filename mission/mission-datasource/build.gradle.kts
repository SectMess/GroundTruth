apply {
    from("$rootDir/library-build.gradle")
}
plugins {
    id(SqlDelight.plugin)
}

dependencies{
    "implementation"(project(Modules.missionDomain))

    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)

    "implementation"(SqlDelight.runtime)
}