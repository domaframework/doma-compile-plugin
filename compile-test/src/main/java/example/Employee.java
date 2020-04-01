package example;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

@Entity
public class Employee {
    @Id
    public Integer id;
    public String name;
}
