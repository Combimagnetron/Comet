plugins {
    id("java")
}

group = "org.example"
version = "unspecified"

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
}
