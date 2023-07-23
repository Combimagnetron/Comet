plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("org.jetbrains:annotations:24.0.0")
    implementation("org.spongepowered:configurate-hocon:4.0.0")
    implementation(project(":api"))
    implementation(project(":paper-v1_19_R3", configuration = "reobf"))
    //implementation(project(mapOf("path" to ":paper-v1_19_R3")))
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
}

bukkit {
    name = "Lagoon"
    main = "me.combimagnetron.lagoon.LagoonPlugin"
    apiVersion = "1.19"
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}