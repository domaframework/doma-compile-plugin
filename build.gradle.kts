plugins {
    java
    alias(libs.plugins.spotless)
    alias(libs.plugins.release)
}

val catalog = libs

release {
    newVersionCommitMessage.set("[Gradle Release Plugin] - [skip ci] new version commit: ")
    tagTemplate.set("v\$version")
    git {
        requireBranch.set("master")
    }
}

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