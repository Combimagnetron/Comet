plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.papermc.paperweight.userdev") version "1.5.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

dependencies {
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
    implementation(project(":api"))
}

repositories {
    mavenCentral()
}

tasks {
    build {
        dependsOn(reobfJar)
    }
}
