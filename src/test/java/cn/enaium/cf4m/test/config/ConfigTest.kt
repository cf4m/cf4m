package cn.enaium.cf4m.test.config

import org.junit.jupiter.api.Assertions
import cn.enaium.cf4m.CF4M.INSTANCE

/**
 * @author Enaium
 */
class ConfigTest {
    init {
        Assertions.assertNotEquals(0, INSTANCE.config.all.size)
        for (configProvider in INSTANCE.config.all) {
            println(configProvider.name + "|" + configProvider.path)
        }
    }
}