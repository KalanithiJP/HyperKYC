pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://s3.ap-south-1.amazonaws.com/hvsdk/android/releases")
        }
    }
}

rootProject.name = "Jetpack HyperKYC"
include(":app")
