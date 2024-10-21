plugins {
    id("groovy")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "1.3.0"
    id("com.diffplug.spotless") version "6.25.0"
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
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.3")
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
