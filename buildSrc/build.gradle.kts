plugins {
    id("java")
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(fileTree("libs") { include("*.jar") })
    implementation("com.github.javaparser:javaparser-core:3.24.2")
    implementation("com.github.javaparser:javaparser-symbol-solver-core:3.24.2")
}

group = "me.combimagnetron"
version = "unspecified"

gradlePlugin {
    plugins {
        create("cometPlugins") {
            id = "comet"
            implementationClass = "me.combimagnetron.comet.CometPlugin"
        }
    }
}