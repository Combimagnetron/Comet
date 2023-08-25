plugins {
    application
}

group = "me.combimagnetron"
version = "unspecified"

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
}

dependencies {
    implementation("net.fabricmc:javapoet:0.1.0")
    implementation(project(":api"))
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.jetbrains:annotations:24.0.0")
}

tasks {

    application {
        mainClass.set("me.combimagnetron.halo.compiler.Compiler")
    }

    getByName<JavaExec>("run") {
        args = listOf(rootProject.projectDir.resolve("halos").absolutePath ,rootProject.projectDir.resolve("api/src/main/java/").absolutePath)
    }
}

