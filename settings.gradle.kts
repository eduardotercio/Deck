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
    }
}

rootProject.name = "Deck"
include(":app")
include(":common:designsystem")
include(":common:data")
include(":common:presentation")
include(":common:domain")
include(":feature:deck:data")
include(":feature:deck:domain")
include(":feature:deck:presentation")
include(":feature:home:data")
include(":feature:home:domain")
