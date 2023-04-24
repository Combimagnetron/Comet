plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    implementation("org.spongepowered:configurate-hocon:4.0.0")
    implementation(project(":api"))
}

bukkit {
    name = "Lagoon"
    main = "me.combimagnetron.lagoon.LagoonPlugin"
    apiVersion = "1.19"
}
