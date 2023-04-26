pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "TalkEasy"
include(":app")
include(":app-protector")
include(":core:data")
include(":core:domain")
include(":feature:common")
include(":ktlint")
