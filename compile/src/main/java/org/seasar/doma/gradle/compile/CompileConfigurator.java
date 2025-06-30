package org.seasar.doma.gradle.compile;

import java.util.Objects;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;

/** Configures compilation tasks for Doma annotation processing. */
class CompileConfigurator {

  private final Project project;
  private final JavaCompileConfigurator javaConfigurator;
  private final KotlinCompileConfigurator kotlinConfigurator;

  CompileConfigurator(Project project) {
    this.project = Objects.requireNonNull(project);
    this.javaConfigurator = new JavaCompileConfigurator(project);
    this.kotlinConfigurator = new KotlinCompileConfigurator(project);
  }

  void configure() {
    // Configure immediately for Java
    var javaExtension = project.getExtensions().getByType(JavaPluginExtension.class);
    var mainSourceSet = javaExtension.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME);
    javaConfigurator.configure(mainSourceSet);

    // Defer Kotlin configuration until after project evaluation
    project.afterEvaluate(
        evaluatedProject -> {
          var evaluatedJavaExtension =
              evaluatedProject.getExtensions().getByType(JavaPluginExtension.class);
          var evaluatedMainSourceSet =
              evaluatedJavaExtension.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME);
          kotlinConfigurator.configure(evaluatedMainSourceSet);
        });
  }
}
