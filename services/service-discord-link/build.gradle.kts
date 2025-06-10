plugins {
    id("java")
    id("com.bmuschko.docker-java-application") version "9.3.3"
}

group = "me.combimagnetron"
version = "unspecified"

val service = "discord"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

docker {
    javaApplication {
        baseImage.set("azul/zulu-openjdk:20-jre")
        maintainer.set("Alec \"Combimagnetron\" van der Veen")
        images.add("alecvdveen/cosmorise:$service")
        jvmArgs.set(listOf("-Xms256m", "-Xmx256m"))
    }
    registryCredentials {
        url.set("https://index.docker.io/v1/")
        username.set("")
        password.set("")
        email.set("")
    }
}

tasks.test {
    useJUnitPlatform()
}