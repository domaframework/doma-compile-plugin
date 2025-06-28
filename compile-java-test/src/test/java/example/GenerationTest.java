package example;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class GenerationTest {

  @Test
  public void test() {
    assertNotNull(_Employee.class);
    assertNotNull(EmployeeDaoImpl.class);
  }
}
