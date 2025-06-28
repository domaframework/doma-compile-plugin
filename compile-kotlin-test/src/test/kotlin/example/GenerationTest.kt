package example

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class GenerationTest {
    @Test
    fun test() {
        assertNotNull(_Employee::class.java)
        assertNotNull(EmployeeDaoImpl::class.java)
    }
}
