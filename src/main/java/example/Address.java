package example;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

@Entity
class Address {
    @Id
    public Integer id;
    public String name;
}