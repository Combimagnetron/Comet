plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.papermc.paperweight.userdev") version "1.7.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

dependencies {
    paperweight.paperDevBundle("1.20.5-R0.1-SNAPSHOT")
    implementation(project(":api"))
}

paperweight {
    reobfArtifactConfiguration.set(io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION)
}

tasks.shadowJar {
    manifest {
        attributes["paperweight-mappings-namespace"] = "mojang"
    }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

tasks {
    reobfJar {
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
