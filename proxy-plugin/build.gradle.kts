plugins {
    id("java")
    //kotlin("jvm") version "1.8.21"
}

group = "org.example"
version = "unspecified"

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
    implementation("com.github.retrooper.packetevents:velocity:2.0-SNAPSHOT")
    //implementation(kotlin("stdlib-jdk8"))
}

repositories {
    mavenCentral()
}
//kotlin {
//    jvmToolchain(11)
//}