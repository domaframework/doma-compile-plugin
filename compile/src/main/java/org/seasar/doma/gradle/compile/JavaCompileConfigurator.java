package org.seasar.doma.gradle.compile;

import java.util.Objects;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.compile.JavaCompile;

/** Configures Java compilation tasks for Doma annotation processing. */
class JavaCompileConfigurator {

  private static final String PARAMETERS_COMPILER_ARG = "-parameters";

  private final Project project;

  JavaCompileConfigurator(Project project) {
    this.project = Objects.requireNonNull(project);
  }

  void configure(SourceSet sourceSet) {
    var resourceDirs = sourceSet.getResources().getSrcDirs();
    var compileTaskName = sourceSet.getCompileJavaTaskName();

    project
        .getTasks()
        .named(compileTaskName, JavaCompile.class)
        .configure(
            javaCompile -> {
              javaCompile.getOptions().setSourcepath(project.files(resourceDirs));
              javaCompile.getOptions().getCompilerArgs().add(PARAMETERS_COMPILER_ARG);
            });
  }
}
