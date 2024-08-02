plugins {
    id("java")
}

group = "me.combimagnetron"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":api")))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(22))
}