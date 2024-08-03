plugins {
    id("java")
    id("com.bmuschko.docker-remote-api") version "9.4.0"
    id("com.bmuschko.docker-java-application") version "9.4.0"
    id("comet")
}

group = "me.combimagnetron"
version = "1.0.0"

dependencies {
    implementation(project(mapOf("path" to ":api")))
    implementation("org.kohsuke:github-api:1.315")
    implementation("io.avaje:avaje-inject:9.4")
    implementation("com.marcnuri.yakc:kubernetes-api:0.0.28")
    implementation("com.marcnuri.yakc:kubernetes-client:0.0.28")
    annotationProcessor("io.avaje:avaje-inject-generator:9.4")
}

comet {
    service {
        identifier = "service:pilot"
        version = project.version.toString()
    }
    deployment {
        minInstanceCount = 1
        maxInstanceCount = 2
        playerInstanceThreshold = -1
        image = "self"
    }
    component {
        monitor {
            type = listOf("*")
        }
        intercept {
            type = listOf("*")
        }
    }
}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(22))
}

val service = "pilot"
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

tasks.test {
    useJUnitPlatform()
}