package org.seasar.doma.gradle.compile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JavaCompileConfiguratorTest {

  private static final String DOMA_VERSION = System.getProperty("domaVersion");

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
            java
            id("org.domaframework.doma.compile")
        }

        repositories {
            mavenCentral()
        }

        dependencies {
            annotationProcessor("org.seasar.doma:doma-processor:%s")
            implementation("org.seasar.doma:doma-core:%s")
        }
        """
            .formatted(DOMA_VERSION, DOMA_VERSION));
    write(
        "src/main/java/example/EmployeeDao.java",
        """
        package example;

        import org.seasar.doma.Dao;
        import org.seasar.doma.Select;

        @Dao
        public interface EmployeeDao {
          @Select
          String selectById(Integer id);
        }
        """);
    write(SQL_PATH, "select name from employee where id = /* id */0\n");
    write("src/main/resources/application.properties", "greeting=hello\n");
  }

  @Test
  void sqlFileChangeRerunsCompilation() throws IOException {
    assertEquals(TaskOutcome.SUCCESS, compileJava().task(":compileJava").getOutcome());

    // An invalid directive must be reported by the annotation processor
    write(SQL_PATH, "select name from employee /*%where*/ id = /* id */0\n");
    BuildResult result = runner().buildAndFail();

    assertEquals(TaskOutcome.FAILED, result.task(":compileJava").getOutcome());
    assertTrue(result.getOutput().contains("DOMA4069"), result.getOutput());
  }

  @Test
  void unrelatedResourceChangeKeepsCompilationUpToDate() throws IOException {
    assertEquals(TaskOutcome.SUCCESS, compileJava().task(":compileJava").getOutcome());

    write("src/main/resources/application.properties", "greeting=bye\n");

    assertEquals(TaskOutcome.UP_TO_DATE, compileJava().task(":compileJava").getOutcome());
  }

  @Test
  void resourceDirAddedAfterTaskRealizationIsUsed() throws IOException {
    Files.move(
        projectDir.resolve(SQL_PATH),
        Files.createDirectories(projectDir.resolve("src/main/sql/META-INF/example/EmployeeDao"))
            .resolve("selectById.sql"));
    // Eagerly realize compileJava before the resource dir is added
    append(
        "build.gradle.kts",
        """

        tasks.withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        sourceSets.main {
            resources.srcDir("src/main/sql")
        }
        """);

    assertEquals(TaskOutcome.SUCCESS, compileJava().task(":compileJava").getOutcome());
  }

  private BuildResult compileJava() {
    return runner().build();
  }

  private GradleRunner runner() {
    return GradleRunner.create()
        .withProjectDir(projectDir.toFile())
        .withPluginClasspath()
        .withArguments("compileJava", "--stacktrace");
  }

  private void append(String path, String content) throws IOException {
    Files.writeString(projectDir.resolve(path), content, StandardOpenOption.APPEND);
  }

  private void write(String path, String content) throws IOException {
    Path file = projectDir.resolve(path);
    Files.createDirectories(file.getParent());
    Files.writeString(file, content);
  }
}
