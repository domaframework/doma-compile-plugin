package org.seasar.doma.gradle.compile;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;

/**
 * Gradle plugin that configures annotation processors to read Doma resources at compile-time. This
 * plugin automatically configures both Java and Kotlin compilation tasks.
 */
public class CompilePlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    project.getPlugins().withType(JavaPlugin.class, javaPlugin -> configureProject(project));
  }

  private void configureProject(Project project) {
    var configurator = new CompileConfigurator(project);
    configurator.configure();
  }
}
