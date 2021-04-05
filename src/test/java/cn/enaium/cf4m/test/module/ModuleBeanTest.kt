package cn.enaium.cf4m.test.module

import cn.enaium.cf4m.CF4M.INSTANCE
import org.junit.jupiter.api.Assertions

/**
 * @author Enaium
 */
class ModuleBeanTest {
    init {
        for (module in INSTANCE.module.all) {
            println(module.name)
        }
        Assertions.assertNotNull(INSTANCE.module.all)
    }
}