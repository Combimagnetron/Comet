plugins {
    id("java")
    id("com.bmuschko.docker-java-application") version "9.3.3"
}

group = "org.example"
version = "unspecified"

dependencies {
    implementation(project(mapOf("path" to ":api")))
    implementation("io.kubernetes:client-java:15.0.1")
    implementation("org.kohsuke:github-api:1.315")
    implementation("io.avaje:avaje-inject:9.4")
    annotationProcessor("io.avaje:avaje-inject-generator:9.4")
}

docker {
    javaApplication {
        baseImage.set("azul/zulu-openjdk:20-jre")
        maintainer.set("Alec \"Combimagnetron\" van der Veen")
        ports.set(listOf(6162, 6162))
        images.add("alecvdveen/cosmorise:pilot")
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