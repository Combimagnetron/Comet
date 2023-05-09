rootProject.name = "Tropicadia"

dependencyResolutionManagement {
    repositories {
        mavenCentral() //
        maven(url = uri("https://jitpack.io")) //
        maven(url = uri("https://repo.papermc.io/repository/maven-public/")) //Paper, Paperweight
        maven(url = uri("https://repo.spongepowered.org/repository/maven-public/")) //Configurate
    }
    //repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) Disabled due to paperweight adding maven repositories during compile
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

include("service-sharding")
include("api")
include("paper-plugin")
include("paper-v1_19_R3")
include("proxy-plugin")
include("instance-base")
include("instance-city")
