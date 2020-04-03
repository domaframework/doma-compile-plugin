package example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GenerationTest {

  @Test
  public void test() {
    assertNotNull(_Employee.class);
    assertNotNull(EmployeeDao.class);
  }
}
