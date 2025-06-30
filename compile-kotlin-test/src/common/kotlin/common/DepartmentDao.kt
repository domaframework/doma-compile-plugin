package common

import org.seasar.doma.Dao
import org.seasar.doma.Script
import org.seasar.doma.Select

@Dao
interface DepartmentDao {
    @Select
    fun selectById(id: Int?): Department?

    @Script
    fun create()
}