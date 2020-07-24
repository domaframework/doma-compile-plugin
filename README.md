Doma Compile Plugin
===================

![Java CI with Gradle](https://github.com/domaframework/doma-compile-plugin/workflows/Java%20CI%20with%20Gradle/badge.svg)

Doma compile plugin is a gradle plugin.  
It allows annotation processors to read Doma resources at compile-time.

The plugin supports Java and Kotlin.

How to use
----------

See [Gradle Plugin Portal](https://plugins.gradle.org/plugin/org.seasar.doma.compile).


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

Example build.gradle
--------------------

- Java: https://github.com/domaframework/simple-examples/blob/master/build.gradle
- Kotlin: https://github.com/domaframework/kotlin-sample/blob/master/build.gradle

License
-------

```
Copyright 2019 domaframework.org

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
