package common;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

@Entity
public class Department {
  @Id public Integer id;
  public String name;
}
