package org.seasar.doma.gradle.compile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class KotlinCompileConfiguratorTest {

  private static final String DOMA_VERSION = System.getProperty("domaVersion");

  private static final String KOTLIN_VERSION = System.getProperty("kotlinVersion");

  private static final String SQL_PATH =
      "src/main/resources/META-INF/example/EmployeeDao/selectById.sql";

  @TempDir Path projectDir;

  @BeforeEach
  void setUp() throws IOException {
    write("settings.gradle.kts", "rootProject.name = \"example\"\n");
    write(
        "build.gradle.kts",
        """
        plugins {
            kotlin("jvm") version "%s"
            kotlin("kapt") version "%s"
            id("org.domaframework.doma.compile")
        }

        repositories {
            mavenCentral()
        }

        dependencies {
            kapt("org.seasar.doma:doma-processor:%s")
            implementation("org.seasar.doma:doma-core:%s")
        }
        """
            .formatted(KOTLIN_VERSION, KOTLIN_VERSION, DOMA_VERSION, DOMA_VERSION));
    write(
        "src/main/kotlin/example/EmployeeDao.kt",
        """
        package example

        import org.seasar.doma.Dao
        import org.seasar.doma.Select

        @Dao
        interface EmployeeDao {
            @Select
            fun selectById(id: Int?): String?
        }
        """);
    write(SQL_PATH, "select name from employee where id = /* id */0\n");
    write("src/main/resources/application.properties", "greeting=hello\n");
  }

  @Test
  void sqlFileChangeRerunsKapt() throws IOException {
    assertEquals(TaskOutcome.SUCCESS, kapt().task(":kaptKotlin").getOutcome());

    // An invalid directive must be reported by the annotation processor
    write(SQL_PATH, "select name from employee /*%where*/ id = /* id */0\n");
    BuildResult result = runner().buildAndFail();

    assertEquals(TaskOutcome.FAILED, result.task(":kaptKotlin").getOutcome());
    assertTrue(result.getOutput().contains("DOMA4069"), result.getOutput());
  }

  @Test
  void unrelatedResourceChangeKeepsKaptUpToDate() throws IOException {
    assertEquals(TaskOutcome.SUCCESS, kapt().task(":kaptKotlin").getOutcome());

    write("src/main/resources/application.properties", "greeting=bye\n");

    assertEquals(TaskOutcome.UP_TO_DATE, kapt().task(":kaptKotlin").getOutcome());
  }

  private BuildResult kapt() {
    return runner().build();
  }

  private GradleRunner runner() {
    return GradleRunner.create()
        .withProjectDir(projectDir.toFile())
        .withPluginClasspath()
        .withArguments("kaptKotlin", "--stacktrace");
  }

  private void write(String path, String content) throws IOException {
    Path file = projectDir.resolve(path);
    Files.createDirectories(file.getParent());
    Files.writeString(file, content);
  }
}
