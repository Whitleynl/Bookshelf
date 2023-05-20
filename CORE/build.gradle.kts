plugins {
    id("java")
    id("de.jjohannes.extra-java-module-info") version "0.14"
    kotlin("jvm") version "1.7.0"
}

group = "org.example"

repositories {
    mavenCentral()
    maven("https://jitpack.io") // this fixed the cirdles:commons dependency issue
}

dependencies {
    implementation("com.github.cirdles:commons:bc38781605")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.topobyte:jama:master-SNAPSHOT")
}

extraJavaModuleInfo {
    failOnMissingModuleInfo.set(false)
    automaticModule("commons-bc38781605.jar", "commons.bc38781605")}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain {
        this.languageVersion.set(JavaLanguageVersion.of(17))
    }
}