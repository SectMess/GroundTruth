buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        //change from 1.5.31 to 1.5.1-
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
        classpath(Build.sqlDelightGradlePlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}