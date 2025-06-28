package example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GenerationTest {

  @Test
  public void test() {
    assertNotNull(_Address.class);
    assertNotNull(_Employee.class);
    assertNotNull(AddressDao.class);
    assertNotNull(EmployeeDao.class);
  }
}
