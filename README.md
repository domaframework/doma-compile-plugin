Doma Compile Plugin
===================

Doma compile plugin is a gradle plugin.  
It allows annotation processors to read Doma resources at compile-time.

The plugin supports Java and Kotlin.

[![Java CI with Gradle](https://github.com/domaframework/doma-compile-plugin/workflows/Java%20CI%20with%20Gradle/badge.svg)](https://github.com/domaframework/doma-compile-plugin/actions?query=workflow%3A%22Java+CI+with+Gradle%22)
[![project chat](https://img.shields.io/badge/zulip-join_chat-green.svg)](https://domaframework.zulipchat.com)
[![Twitter](https://img.shields.io/badge/twitter-@domaframework-blue.svg?style=flat)](https://twitter.com/domaframework)

How to use
----------

See [Gradle Plugin Portal](https://plugins.gradle.org/plugin/org.domaframework.doma.compile).

What does the plugin do ?
-------------------------

The plugin is equivalent to the following gradle script:

```groovy
def domaResources = ['doma.compile.config', 'META-INF/**/*.sql', 'META-INF/**/*.script']

task copyDomaResourcesJava(type: Copy) {
    from sourceSets.main.resources.srcDirs
    into compileJava.destinationDir
    include domaResources
}

compileJava {
    dependsOn copyDomaResourcesJava
}

processResources {
    exclude domaResources
}

task copyDomaResourcesKotlin(type: Copy) {
    from sourceSets.main.resources.srcDirs
    into compileKotlin.destinationDir
    include domaResources
}

compileKotlin {
    dependsOn copyDomaResourcesKotlin
}

kapt {
    arguments {
        arg('doma.resources.dir', compileKotlin.destinationDir)
    }
}
```

Example build.gradle.kts
------------------------

- Java: https://github.com/domaframework/simple-examples/blob/master/build.gradle.kts
- Kotlin: https://github.com/domaframework/kotlin-sample/blob/master/build.gradle.kts

Major versions
---------------------

### Status and Repository

| Version               | Status          | Repository                                                                                 | Branch |
|-----------------------|-----------------|--------------------------------------------------------------------------------------------|--------|
| Doma Compile Plugin 2 | limited-support | [domaframework/doma-compile-plugin](https://github.com/domaframework/doma-compile-plugin/) | 2.x    |
| Doma Compile Plugin 3 | stable          | [domaframework/doma-compile-plugin](https://github.com/domaframework/doma-compile-plugin/) | master |

### Compatibility matrix

The supported versions of Doma:

|                       | Doma 2 | Doma 3 |
|-----------------------|--------|--------|
| Doma Compile Plugin 2 | v      |        |
| Doma Compile Plugin 3 |        | v      |

The minimum supported versions of Java:

|                       | Java 8 | Java 17 |
|-----------------------|--------|---------|
| Doma Compile Plugin 2 | v      |         |
| Doma Compile Plugin 3 |        | v       |
