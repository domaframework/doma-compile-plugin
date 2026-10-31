package org.seasar.doma.gradle.compile

import org.gradle.api.Project
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.SourceSet

/**
 * Configures Kotlin/KAPT compilation tasks for Doma annotation processing.
 */
class KotlinCompileConfigurator {

    private static final String KOTLIN_KAPT_PLUGIN_ID = 'kotlin-kapt'
    private static final String KAPT_EXTENSION_NAME = 'kapt'
    private static final String SOURCE_PATH_OPTION = '--source-path'
    private static final String PARAMETERS_OPTION = '-parameters'

    /** Resources read by the Doma annotation processor. */
    private static final List<String> DOMA_RESOURCES = [
            'META-INF/**/*.sql', 'META-INF/**/*.script', 'doma.compile.config'
    ]

    private final Project project

    KotlinCompileConfigurator(Project project) {
        this.project = Objects.requireNonNull(project)
    }

    void configure(SourceSet sourceSet) {
        project.plugins.withId(KOTLIN_KAPT_PLUGIN_ID) {
            configureKapt(sourceSet)
        }
    }

    private void configureKapt(SourceSet sourceSet) {
        def kapt = project.extensions.getByName(KAPT_EXTENSION_NAME)
        def resourceDirs = sourceSet.resources.srcDirs
        def newSourcepath = resourceDirs.join(File.pathSeparator)
        def currentSourcepath = kapt.javacOptions[SOURCE_PATH_OPTION]
        def sourcepath = currentSourcepath == null
                ? newSourcepath
                : currentSourcepath + File.pathSeparator + newSourcepath

        kapt.javacOptions {
            option SOURCE_PATH_OPTION, sourcepath
            option PARAMETERS_OPTION, ''
        }

        // Matched by name, because the kapt task type differs across Kotlin plugin versions
        def kaptTaskName = sourceSet.getTaskName('kapt', 'Kotlin')
        def domaResources = project.files(resourceDirs).asFileTree.matching {
            include DOMA_RESOURCES
        }
        project.tasks.matching { it.name == kaptTaskName }.configureEach { task ->
            // Without this, editing only a SQL file leaves the task up-to-date and skips its validation
            task.inputs.files(domaResources)
                    .withPropertyName('domaResources')
                    .withPathSensitivity(PathSensitivity.RELATIVE)
        }
    }
}