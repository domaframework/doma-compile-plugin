buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
    }
    dependencies {
        classpath("org.domaframework.doma:compile")
    }
}

plugins {
    id("java")
    id("org.domaframework.doma.compile")
}

val domaVersion = "3.1.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    test {
        useJUnitPlatform()
    }
}

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencies {
    annotationProcessor("org.seasar.doma:doma-processor:$domaVersion")
    implementation("org.seasar.doma:doma-core:$domaVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.3")
}