package org.seasar.doma.gradle.compile;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;

public class CompilePlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    project
        .getPlugins()
        .withType(
            JavaPlugin.class,
            javaPlugin -> {
              JavaPluginExtension javaPluginExtension =
                  project.getExtensions().getByType(JavaPluginExtension.class);
              SourceSet mainSourceSet =
                  javaPluginExtension.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME);
              ConfigureJava.configure(project, mainSourceSet);
              ConfigureKotlin.configure(project, mainSourceSet);
            });
  }
}
