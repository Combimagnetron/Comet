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
    implementation("net.minestom:minestom-snapshots:480a7dcaa2")
    implementation("com.github.javaparser:javaparser-core:3.24.2")
    implementation("net.sf.cssbox:cssbox:5.0.2")
    implementation("com.github.javaparser:javaparser-symbol-solver-core:3.24.2")
}

tasks.test {
    useJUnitPlatform()
}