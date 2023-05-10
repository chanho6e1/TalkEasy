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
        maven { setUrl("https://devrepo.kakao.com/nexus/content/groups/public/") }
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