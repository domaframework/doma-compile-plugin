# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

The Doma Compile Plugin is a Gradle plugin that allows annotation processors to read Doma resources at compile-time. It supports both Java and Kotlin and is part of the Doma framework ecosystem.

## Common Commands

### Building the Plugin
```bash
./gradlew build
```

### Running Tests
```bash
# Run all tests
./gradlew test

# Run specific module tests
./gradlew :compile:test
./gradlew :compile-java-test:test
./gradlew :compile-kotlin-test:test
./gradlew :compile-mix-test:test
```

### Code Formatting
```bash
# Apply code formatting (Google Java Format)
./gradlew spotlessApply
```

### Publishing (for maintainers)
```bash
# Release a new version (from master branch only)
./gradlew release

# Publish to Gradle Plugin Portal (handled by CI for non-SNAPSHOT versions)
./gradlew :compile:publishPlugins -Pgradle.publish.key=<key> -Pgradle.publish.secret=<secret>
```

## Architecture

### Project Structure
- **compile/**: Main plugin implementation
  - `CompilePlugin.java`: Entry point that applies configurations
  - `ConfigureJava.java`: Configures Java compilation to include resource directories in sourcepath
  - `ConfigureKotlin.groovy`: Configures Kotlin/KAPT to include resource directories
  
- **Test Projects**: Three separate Gradle projects for testing different scenarios
  - `compile-java-test/`: Tests plugin with pure Java
  - `compile-kotlin-test/`: Tests plugin with pure Kotlin  
  - `compile-mix-test/`: Tests plugin with mixed Java/Kotlin

### How the Plugin Works
The plugin modifies the compilation classpath to allow Doma's annotation processor to read SQL resource files during compile-time. It:

1. Adds resource directories to the Java compiler's sourcepath
2. Adds the `-parameters` flag to preserve parameter names
3. For Kotlin, configures KAPT with the same settings

This is equivalent to manually configuring:
```kotlin
compileJava {
    options.sourcepath = files(sourceSets.main.resources.srcDirs)
    options.compilerArgs.add("-parameters")
}

kapt {
    javacOptions {
        option("--source-path", resourceDirs.join(File.pathSeparator))
        option("-parameters")      
    }
}
```

### Resource File Convention
Doma SQL resources follow a specific directory structure:
- Location: `src/main/resources/META-INF/<package>/<DaoName>/`
- SQL files: `<methodName>.sql` (e.g., `selectById.sql`)
- Script files: `<methodName>.script` (e.g., `create.script`)

### Dependencies and Versions
- Java: Requires Java 17 or later
- Gradle: Uses Gradle wrapper
- Doma: Compatible with Doma 3.8 or later
- Testing: JUnit 5 (managed via BOM)

### CI/CD Pipeline
GitHub Actions workflow (`.github/workflows/ci.yml`):
1. Triggers on push/PR to master branch
2. Uses JDK 21 on Ubuntu
3. Builds plugin, then tests all three scenarios
4. Auto-publishes non-SNAPSHOT versions to Gradle Plugin Portal

### Plugin Configuration
The plugin is published as `org.domaframework.doma.compile` and can be applied:
```kotlin
plugins {
    id("org.domaframework.doma.compile") version "4.x.x"
}
```