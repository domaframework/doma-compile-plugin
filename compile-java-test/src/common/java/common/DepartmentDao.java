package common;

import org.seasar.doma.Dao;
import org.seasar.doma.Script;
import org.seasar.doma.Select;

@Dao
public interface DepartmentDao {
  @Select
  Department selectById(Integer id);

  @Script
  void create();
}
