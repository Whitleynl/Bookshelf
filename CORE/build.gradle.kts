plugins {
    id("java")
    id("de.jjohannes.extra-java-module-info") version "0.14"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.cirdles:commons:bc38781605")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

extraJavaModuleInfo {
    failOnMissingModuleInfo.set(false)
    automaticModule("commons-bc38781605.jar", "commons.bc38781605")}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}