package org.seasar.doma.gradle.compile

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet

class ConfigureKotlin {

    static void configure(Project project, SourceSet sourceSet) {
        project.plugins.withId('kotlin-kapt') {
            def tasks = project.tasks
            def kapt = project.extensions.getByName('kapt')
            def compileKotlin = tasks.named('compileKotlin')
            kapt.arguments {
                arg('doma.resources.dir', compileKotlin.get().destinationDir)
            }
            def copyResources = tasks.register(CopyResources.NAME + "Kotlin", CopyResources.class, sourceSet, compileKotlin)
            compileKotlin.configure {
                dependsOn copyResources
            }
        }
    }
}
