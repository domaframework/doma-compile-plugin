plugins {
    id("groovy")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "1.3.1"
    id("com.diffplug.spotless") version "7.0.4"
    id("net.researchgate.release") version "3.1.0"
}

configure<net.researchgate.release.ReleaseExtension> {
    newVersionCommitMessage.set("[Gradle Release Plugin] - [skip ci] new version commit: ")
    tagTemplate.set("v\$version")
    git {
        requireBranch.set("master")
    }
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
        googleJavaFormat("1.23.0")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

dependencies {
    // Use JUnit BOM for version management
    testImplementation(platform("org.junit:junit-bom:5.13.2"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
