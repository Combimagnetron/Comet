plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

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
}
