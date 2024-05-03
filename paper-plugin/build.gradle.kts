plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("org.jetbrains:annotations:24.0.0")
    implementation("org.spongepowered:configurate-hocon:4.0.0")
    implementation(project(":api"))
    implementation(project(":paper-v1_19_R3", configuration = "reobf"))
    implementation(project(mapOf("path" to ":paper-v1_19_R3")))
    compileOnly("net.kyori:adventure-api:4.16.0")
    compileOnly("net.kyori:adventure-text-serializer-gson:4.16.0")
    compileOnly("com.github.luben:zstd-jni:1.5.2-5")
    //implementation(project(mapOf("path" to ":paper-v1_19_R3")))
    compileOnly("io.papermc.paper:paper-api:1.20.5-R0.1-SNAPSHOT") {
        exclude("net.kyori")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

bukkit {
    name = "Comet"
    main = "me.combimagnetron.comet.CometPlugin"
    apiVersion = "1.20"
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}