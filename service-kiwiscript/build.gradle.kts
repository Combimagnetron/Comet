plugins {
    id("java")
    kotlin("jvm") version "1.8.21"
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":api"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.21")
    implementation("net.kyori:adventure-api:4.13.1")
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(17)
}