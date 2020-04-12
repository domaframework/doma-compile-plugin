package org.seasar.doma.gradle.compile;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;

public class CompilePlugin implements Plugin<Project> {

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
              ConfigureJava.configure(project, mainSourceSet);
              ConfigureKotlin.configure(project, mainSourceSet);
            });
  }
}
