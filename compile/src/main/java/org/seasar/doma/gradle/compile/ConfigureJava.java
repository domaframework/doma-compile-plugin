package org.seasar.doma.gradle.compile;

import java.io.File;
import java.util.Set;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.compile.JavaCompile;

public class ConfigureJava {

  public static void configure(Project project, SourceSet sourceSet) {
    TaskContainer tasks = project.getTasks();
    Set<File> resourceDirs = sourceSet.getResources().getSrcDirs();

    JavaCompile javaCompile =
        tasks.named(sourceSet.getCompileJavaTaskName(), JavaCompile.class).get();
    javaCompile.getOptions().setSourcepath(project.files(resourceDirs));
    javaCompile.getOptions().getCompilerArgs().add("-parameters");
  }
}
