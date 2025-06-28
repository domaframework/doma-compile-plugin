plugins {
    id("groovy")
    id("java-gradle-plugin")
    alias(libs.plugins.gradle.plugin.publish)
    alias(libs.plugins.spotless)
}

gradlePlugin {
    website.set("https://github.com/domaframework/doma-compile-plugin")
    vcsUrl.set("https://github.com/domaframework/doma-compile-plugin.git")
    plugins {
        create("compilePlugin") {
            id = "org.domaframework.doma.compile"
            displayName = "Doma Compile Plugin"
            description = "Allows annotation processors to read Doma resources at compile-time"
            implementationClass = "org.seasar.doma.gradle.compile.CompilePlugin"
            tags.set(listOf("doma", "compile", "annotation-processing"))
        }
    }
}

sourceSets {
    main {
        java {
            setSrcDirs(emptyList<String>())
        }
        groovy {
            setSrcDirs(listOf("src/main/groovy", "src/main/java"))
        }
    }
}

spotless {
    java {
        googleJavaFormat(libs.versions.google.java.format.get())
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get().toInt()))
}

repositories {
    mavenCentral()
}

dependencies {
    // Use JUnit BOM for version management
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks {
    test {
        useJUnitPlatform()
    }

    javadoc {
        enabled = false
    }

    groovydoc {
        enabled = false
    }

    build {
        dependsOn(spotlessApply)
    }
}
