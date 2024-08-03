plugins {
    id("java")
    id("comet")
    id("com.bmuschko.docker-remote-api") version "9.4.0"
    id("com.bmuschko.docker-java-application") version "9.4.0"
}

group = "me.combimagnetron"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":api"))
    implementation(project(":services:service-base"))
}

comet {
    service {
        identifier = "service:modelagent"
        version = project.version.toString()
    }
    deployment {
        minInstanceCount = 1
        maxInstanceCount = 8
        playerInstanceThreshold = 75
        image = "self"
    }
    component {
        monitor {
            type = listOf()
        }
        intercept {
            type = listOf("UserSendMinecraftPacket")
        }
    }

}

val service = "modelagent"
val dockerUsername: String by project
val dockerPassword: String by project
val dockerEmail: String by project

docker {
    javaApplication {
        baseImage.set("azul/zulu-openjdk:21-jre")
        maintainer.set("Alec \"Combimagnetron\" van der Veen")
        ports.set(listOf(6162, 6162))
        images.add("alecvdveen/cosmorise:service-$service")
        jvmArgs.set(listOf("-Xms256m", "-Xmx256m"))
        args.set(listOf("172.17.0.1", "6379"))
    }
    registryCredentials {
        url.set("https://index.docker.io/v1/")
        username.set(dockerUsername)
        password.set(dockerPassword)
        email.set(dockerEmail)
    }
}

tasks.named<com.bmuschko.gradle.docker.tasks.image.Dockerfile>("dockerCreateDockerfile") {
    instruction("ENV REDIS_HOST \"redis://host.docker.internal\"")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(22))
}