plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

private val kotlinVersion = "1.7.22"
private val daggerVersion = "2.48"

dependencies {
    implementation("com.google.code.gson:gson:2.10")
    implementation("org.jetbrains:annotations:23.0.0")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("net.kyori:adventure-api:4.11.0")
    implementation("net.kyori:adventure-text-serializer-gson:4.11.0")
    implementation("com.github.luben:zstd-jni:1.5.2-5")
    implementation("io.lettuce:lettuce-core:6.2.2.RELEASE")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.spongepowered:configurate-hocon:4.0.0")
    implementation("org.joml:joml:1.10.5")
    implementation("org.jetbrains:annotations:24.0.0")
    implementation("org.apache.pulsar:pulsar-client:3.0.0")
    implementation("io.github.jglrxavpok.hephaistos:common:2.6.0")
    implementation("com.google.dagger:dagger:$daggerVersion")
    annotationProcessor("com.google.dagger:$daggerVersion")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect", version = kotlinVersion)
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib-jdk8", version = kotlinVersion)
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
}
