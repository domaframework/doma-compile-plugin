package example;

import org.seasar.doma.Dao;
import org.seasar.doma.Script;
import org.seasar.doma.Select;

@Dao
public interface EmployeeDao {
    @Select
    Employee selectById(Integer id);

    @Script
    void create();
}
