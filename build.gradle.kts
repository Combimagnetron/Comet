plugins {
    id("java")
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "comet")
    listOf("com.logtail:logback-logtail:0.3.4",
            "ch.qos.logback:logback-classic:1.2.11",
            "ch.qos.logback:logback-core:1.2.11",
            "com.fasterxml.jackson.core:jackson-databind:2.13.5",
            "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.5",
            "org.slf4j:slf4j-api:1.7.7",
            "net.kyori:adventure-api:4.17.0"
    ).forEach {
        dependencies.add("implementation", it)
    }

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(22))
}