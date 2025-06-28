plugins {
    java
    alias(libs.plugins.spotless)
}

val catalog = libs

allprojects {
    apply(plugin = catalog.plugins.spotless.get().pluginId)

    spotless {
        java {
            googleJavaFormat(libs.versions.google.java.format.get())
        }
    }

    repositories {
        mavenCentral()
    }
}