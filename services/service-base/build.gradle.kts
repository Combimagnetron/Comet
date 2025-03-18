plugins {
    id("java")
}

group = "me.combimagnetron"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":api"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(23))
}