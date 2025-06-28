package example;

import org.seasar.doma.Dao;
import org.seasar.doma.Script;
import org.seasar.doma.Select;

@Dao
interface AddressDao {
  @Select
  Address selectById(Integer id);

  @Script
  void create();
}
