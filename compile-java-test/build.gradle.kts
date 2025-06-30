plugins {
    java
    id("org.domaframework.doma.compile")
}


java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get().toInt()))
}

tasks {
    test {
        useJUnitPlatform()
    }
}

dependencies {
    annotationProcessor(libs.doma.processor)
    implementation(libs.doma.core)
    // Use JUnit BOM for version management
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
}

sourceSets {
    main {
        java.srcDir("src/common/java")
        resources.srcDir("src/common/resources")
    }
}
