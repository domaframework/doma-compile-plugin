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
    var compileTaskName = sourceSet.getCompileJavaTaskName();
    project
        .getTasks()
        .named(compileTaskName, JavaCompile.class)
        .configure(javaCompile -> configureJavaCompile(sourceSet, javaCompile));
  }

  private void configureJavaCompile(SourceSet sourceSet, JavaCompile javaCompile) {
    javaCompile.doFirst(
        __ -> {
          var resourceDirs = sourceSet.getResources().getSrcDirs();
          var options = javaCompile.getOptions();
          var newSourcepath = project.files(resourceDirs);
          var currentSourcepath = options.getSourcepath();
          var sourcepath =
              currentSourcepath == null ? newSourcepath : currentSourcepath.plus(newSourcepath);
          options.setSourcepath(sourcepath);
          options.getCompilerArgs().add(PARAMETERS_COMPILER_ARG);
        });
  }
}
