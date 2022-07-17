package org.seasar.doma.gradle.compile

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet

import java.util.function.Supplier

class ConfigureKotlin {

    static void configure(Project project, SourceSet sourceSet) {
        project.plugins.withId('kotlin-kapt') {
            def tasks = project.tasks
            def kapt = project.extensions.getByName('kapt')
            def compileKotlin = tasks.named('compileKotlin')
            def dir = compileKotlin.get().destinationDirectory.getAsFile().get()
            kapt.arguments { arg('doma.resources.dir', dir) }
            def copyResources = tasks.register(CopyResources.NAME + "Kotlin", CopyResources.class, sourceSet, dir)
            compileKotlin.configure {
                dependsOn copyResources
            }
        }
    }
}
