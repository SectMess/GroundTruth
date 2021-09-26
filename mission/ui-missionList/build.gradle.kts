apply {
    from("$rootDir/android-library-build.gradle")
}
dependencies{
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.missionDomain))
    "implementation"(project(Modules.missionInteractors))

    "implementation"(SqlDelight.androidDriver)

    "implementation"(Coil.coil)

    "implementation"(Hilt.android)
    "kapt"(Hilt.compiler)




}