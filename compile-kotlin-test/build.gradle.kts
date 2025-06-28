plugins {
    id("java")
    id("org.domaframework.doma.compile")
    id("org.jetbrains.kotlin.jvm") version "2.2.0"
    id("org.jetbrains.kotlin.kapt") version "2.2.0"
}

val domaVersion = "3.9.1"

kapt {
    includeCompileClasspath = false
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

tasks {
    test {
        useJUnitPlatform()
    }
}

dependencies {
    kapt("org.seasar.doma:doma-processor:$domaVersion")
    implementation("org.seasar.doma:doma-core:$domaVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    // Use JUnit BOM for version management
    testImplementation(platform("org.junit:junit-bom:5.13.2"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
