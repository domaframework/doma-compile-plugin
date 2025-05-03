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
    id("org.jetbrains.kotlin.jvm") version "2.1.20"
    id("org.jetbrains.kotlin.kapt") version "2.1.20"
}

val domaVersion = "3.7.0"

kapt {
    includeCompileClasspath = false
}

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
    kapt("org.seasar.doma:doma-processor:$domaVersion")
    implementation("org.seasar.doma:doma-core:$domaVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.12.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.12.2")
}