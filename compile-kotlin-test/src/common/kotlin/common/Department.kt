package common

import org.seasar.doma.Entity
import org.seasar.doma.Id

@Entity
class Department {
    @Id
    var id: Int? = null
    var name: String? = null
}