rootProject.name = "Lagoon"
include("api")
include("paper-plugin")
include("paper-v1_19_R3")
include("proxy-plugin")

dependencyResolutionManagement {
    repositories {
        mavenCentral() //
        maven(url = uri("https://jitpack.io")) //
        maven(url = uri("https://repo.papermc.io/repository/maven-public/")) //Paper, Paperweight
        maven(url = uri("https://repo.spongepowered.org/repository/maven-public/")) //Configurate
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
