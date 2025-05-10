package org.seasar.doma.gradle.compile

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet

class ConfigureKotlin {

    static void configure(Project project, SourceSet sourceSet) {
        project.plugins.withId('kotlin-kapt') {
            def kapt = project.extensions.getByName('kapt')
            def resourceDirs = sourceSet.resources.srcDirs
            kapt.javacOptions {
                option '--source-path', resourceDirs.join(File.pathSeparator)
                option '-parameters', ''
            }
        }
    }
}
