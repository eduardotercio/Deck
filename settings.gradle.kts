pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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
include(":common")
include(":common:designsystem")
include(":feature")
include(":feature:deck")
include(":common:data")
include(":common:presentation")
include(":feature:home")
include(":feature:deck:data")
include(":feature:deck:domain")
include(":common:domain")
include(":feature:home:data")
include(":feature:home:domain")
include(":feature:deck:presentation")
