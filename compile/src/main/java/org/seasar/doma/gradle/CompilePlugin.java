package org.seasar.doma.gradle;

import java.util.Arrays;
import java.util.List;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.language.jvm.tasks.ProcessResources;

public class CompilePlugin implements Plugin<Project> {

  private static final List<String> DOMA_RESOURCES =
      Arrays.asList("doma.compile.config", "META-INF/**/*.sql", "META-INF/**/*.script");

  private static final String SYNC_RESOURCES_TASK_NAME = "domaSyncResources";

  @Override
  public void apply(Project project) {
    project
        .getPlugins()
        .withType(
            JavaPlugin.class,
            javaPlugin -> {
              JavaPluginConvention javaConvention =
                  project.getConvention().getPlugin(JavaPluginConvention.class);
              SourceSet mainSourceSet =
                  javaConvention.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME);
              configureSyncResourcesTask(project, mainSourceSet);
            });
  }

  private void configureSyncResourcesTask(Project project, SourceSet sourceSet) {
    TaskContainer tasks = project.getTasks();

    TaskProvider<Sync> syncResourcesTask =
        tasks.register(
            SYNC_RESOURCES_TASK_NAME,
            Sync.class,
            sync -> {
              sync.from(sourceSet.getResources().getSourceDirectories());
              sync.into(sourceSet.getJava().getOutputDir());
              sync.include(DOMA_RESOURCES);
            });

    tasks.named(
        sourceSet.getCompileJavaTaskName(),
        JavaCompile.class,
        javaCompile -> {
          javaCompile.dependsOn(syncResourcesTask);
        });

    tasks.named(
        sourceSet.getProcessResourcesTaskName(),
        ProcessResources.class,
        processResources -> processResources.exclude(DOMA_RESOURCES));
  }
}
