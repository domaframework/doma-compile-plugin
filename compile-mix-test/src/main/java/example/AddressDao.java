package example;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.Script;

@Dao
interface AddressDao {
    @Select
    Address selectById(Integer id);

    @Script
    void create();
}