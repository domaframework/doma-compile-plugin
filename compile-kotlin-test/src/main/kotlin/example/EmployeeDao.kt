package example

import common.Department
import org.seasar.doma.Dao
import org.seasar.doma.Script
import org.seasar.doma.Select

@Dao
interface EmployeeDao {
    @Select
    fun selectById(id: Int?): Department?

    @Script
    fun create()
}