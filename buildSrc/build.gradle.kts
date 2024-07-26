plugins {
    id("java")
    `java-gradle-plugin`
}

dependencies {
    implementation(fileTree("libs") { include("*.jar") })
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