package example;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class GenerationTest {

  @Test
  public void test() {
    assertNotNull(_Address.class);
    assertNotNull(_Employee.class);
    assertNotNull(AddressDaoImpl.class);
    assertNotNull(EmployeeDaoImpl.class);
  }
}
