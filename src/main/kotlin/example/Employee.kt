package example

import org.seasar.doma.Entity
import org.seasar.doma.Id

@Entity
class Employee {
    @Id
    var id: Int? = null
    var name: String? = null
}