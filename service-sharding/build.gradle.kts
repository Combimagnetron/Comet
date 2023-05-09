plugins {
    id("java")
    //kotlin("jvm") version "1.8.21"
}

group = "org.example"
version = "unspecified"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    //implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
repositories {
    mavenCentral()
}
//kotlin {
//    jvmToolchain(11)
//}