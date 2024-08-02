plugins {
    id("java")
    id("comet")
}

group = "me.combimagnetron"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":api")))
}

comet {
    service {
        identifier = "service:modelagent"
        version = project.version.toString()
    }
    deployment {
        minInstanceCount = 1
        maxInstanceCount = 8
        playerInstanceThreshold = 75
        image = "self"
    }
    component {
        monitor {
            type = listOf()
        }
        intercept {
            type = listOf("UserSendMinecraftPacket")
        }
    }

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(22))
}