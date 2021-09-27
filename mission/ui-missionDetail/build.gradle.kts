apply {
    from("$rootDir/android-library-build.gradle")
}
dependencies{
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.components))
    "implementation"(project(Modules.missionInteractors))
    "implementation"(project(Modules.missionDomain))

    "implementation"(Coil.coil)

}