Doma Compile Plugin
===================

The Doma Compile Plugin is a Gradle plugin that allows annotation processors to read Doma resources at compile-time.

The plugin supports both Java and Kotlin.

[![Java CI with Gradle](https://github.com/domaframework/doma-compile-plugin/workflows/Java%20CI%20with%20Gradle/badge.svg)](https://github.com/domaframework/doma-compile-plugin/actions?query=workflow%3A%22Java+CI+with+Gradle%22)
[![Project Chat](https://img.shields.io/badge/zulip-join_chat-green.svg)](https://domaframework.zulipchat.com)
[![Twitter](https://img.shields.io/badge/twitter-@domaframework-blue.svg?style=flat)](https://twitter.com/domaframework)

How to Use
----------

See the [Gradle Plugin Portal](https://plugins.gradle.org/plugin/org.domaframework.doma.compile).

What Does the Plugin Do?
-------------------------

The plugin is equivalent to the following Gradle Kotlin DSL script:

```kotlin
tasks {
    compileJava {
        val resourceDirs = sourceSets.getByName("main").resources.srcDirs
        options.sourcepath = files(resourceDirs)
        options.compilerArgs.add("-parameters")
    }
}

kapt {
    javacOptions {
        val resourceDirs = sourceSets.getByName("main").resources.srcDirs
        option("--source-path", resourceDirs.join(File.pathSeparator))
        option("-parameters")      
    }
}
```

Example build.gradle.kts
------------------------

- Java: https://github.com/domaframework/simple-examples/blob/master/build.gradle.kts
- Kotlin: https://github.com/domaframework/kotlin-sample/blob/master/build.gradle.kts

Version Information
---------------------

### Status and Repository

| Version               | Status           | Repository                                                                                 | Branch |
|-----------------------|------------------|--------------------------------------------------------------------------------------------|--------|
| Doma Compile Plugin 2 | Limited Support  | [domaframework/doma-compile-plugin](https://github.com/domaframework/doma-compile-plugin/) | 2.x    |
| Doma Compile Plugin 3 | Limited Support  | [domaframework/doma-compile-plugin](https://github.com/domaframework/doma-compile-plugin/) | 3.x    |
| Doma Compile Plugin 4 | Stable           | [domaframework/doma-compile-plugin](https://github.com/domaframework/doma-compile-plugin/) | master |

### Compatibility Matrix

Doma Version Compatibility:

|                       | Doma 2 | Doma 3.0 - 3.7 | Doma 3.8 or later |
|-----------------------|--------|----------------|-------------------|
| Doma Compile Plugin 2 | ✓      |                |                   |
| Doma Compile Plugin 3 |        | ✓              |                   |
| Doma Compile Plugin 4 |        |                | ✓                 |

Java Version Requirements:

|                       | Java 8　or later | Java 17 or later |
|-----------------------|------------------|-------------------|
| Doma Compile Plugin 2 | ✓                |                   |
| Doma Compile Plugin 3 |                  | ✓                 |
| Doma Compile Plugin 4 |                  | ✓                 |
