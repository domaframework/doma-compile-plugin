plugins {
    id("java")
    id("org.domaframework.doma.compile")
}

val domaVersion = "3.9.1"

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
}

dependencies {
    annotationProcessor("org.seasar.doma:doma-processor:$domaVersion")
    implementation("org.seasar.doma:doma-core:$domaVersion")
    // Use JUnit BOM for version management
    testImplementation(platform("org.junit:junit-bom:5.13.2"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
