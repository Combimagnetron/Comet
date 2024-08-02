rootProject.name = "Comet"

dependencyResolutionManagement {
    repositories {
        mavenCentral() //
        maven(url = uri("https://jitpack.io")) //PacketEvents
        maven(url = uri("https://repo.papermc.io/repository/maven-public/")) //Paper, Paperweight, Velocity
        maven(url = uri("https://repo.spongepowered.org/repository/maven-public/")) //Configurate
    }
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

include("api")
include("paper-plugin")
include("paper-v1_19_R3")
include("proxy-plugin")
include("services:service-discord-link")
include("services:pilot")
include("halo")
include("services:service-modelagent")
include("services:service-base")
