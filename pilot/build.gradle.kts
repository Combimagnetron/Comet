plugins {
    id("java")
}

group = "org.example"
version = "unspecified"

dependencies {
    implementation(project(mapOf("path" to ":api")))
    implementation("io.kubernetes:client-java:15.0.1")
    implementation("org.kohsuke:github-api:1.315")
}

tasks.test {
    useJUnitPlatform()
}