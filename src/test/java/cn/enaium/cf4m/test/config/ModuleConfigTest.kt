package cn.enaium.cf4m.test.config

import org.junit.jupiter.api.Assertions
import cn.enaium.cf4m.CF4M.INSTANCE
import cn.enaium.cf4m.annotation.config.Config
import cn.enaium.cf4m.annotation.config.Save

/**
 * Project: cf4m
 *
 * @author Enaium
 */
@Config("Module")
class ModuleConfigTest {
    @Save
    fun save() {
        val configProvider = INSTANCE.config.getByInstance(this)
        Assertions.assertNotNull(configProvider)
        Assertions.assertEquals("Module", configProvider.name)
        println(configProvider.name + " Save")
    }
}