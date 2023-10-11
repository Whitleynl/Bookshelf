plugins {
    id("java")
    id("application")
    id("org.javamodularity.moduleplugin") version "1.8.12"
    id("org.openjfx.javafxplugin") version "0.0.10"
    id("de.jjohannes.extra-java-module-info") version "0.14"
}

javafx {
    version = "17.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

    dependencies {
        implementation(project(":CORE"))
        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    extraJavaModuleInfo {
        failOnMissingModuleInfo.set(false)
        automaticModule("commons-bc38781605.jar", "commons.bc38781605")
    }

    tasks.test {
        useJUnitPlatform()
    }

    application {
        mainClass.set("org.example.app.BookshelfApp")
    }
