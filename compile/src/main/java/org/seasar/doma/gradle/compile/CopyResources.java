package org.seasar.doma.gradle.compile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;

public class CopyResources extends Copy {

  public static final String NAME = "domaCopyResources";

  public static final List<String> DOMA_RESOURCES =
      Collections.unmodifiableList(
          Arrays.asList("doma.compile.config", "META-INF/**/*.sql", "META-INF/**/*.script"));

  @Inject
  public CopyResources(SourceSet sourceSet, Object destinationDirectory) {
    from(sourceSet.getResources().getSourceDirectories());
    into(destinationDirectory);
    include(DOMA_RESOURCES);
  }
}
