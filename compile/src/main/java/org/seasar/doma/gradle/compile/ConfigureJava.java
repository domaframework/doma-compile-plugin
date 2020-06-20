package org.seasar.doma.gradle.compile;

import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.language.jvm.tasks.ProcessResources;

public class ConfigureJava {

  public static void configure(Project project, SourceSet sourceSet) {
    TaskContainer tasks = project.getTasks();

    TaskProvider<JavaCompile> javaCompile =
        tasks.named(sourceSet.getCompileJavaTaskName(), JavaCompile.class);
    TaskProvider<ProcessResources> processResources =
        tasks.named(sourceSet.getProcessResourcesTaskName(), ProcessResources.class);
    TaskProvider<CopyResources> copyResources =
        tasks.register(CopyResources.NAME + "Java", CopyResources.class, sourceSet, javaCompile);

    javaCompile.configure(task -> task.dependsOn(copyResources));
    processResources.configure(task -> task.exclude(CopyResources.DOMA_RESOURCES));
  }
}
