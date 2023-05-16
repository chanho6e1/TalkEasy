pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven { setUrl("https://jitpack.io") }
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://devrepo.kakao.com/nexus/content/groups/public/") }
        mavenCentral()
    }
}
rootProject.name = "TalkEasy"
include(":app")
include(":protector")
include(":core:data")
include(":core:domain")
include(":feature:common")
include(":feature:aac")
include(":feature:auth")
include(":core:di")
include(":feature:follow")
include(":feature:setting")
include(":feature:chat")
include(":feature:location")