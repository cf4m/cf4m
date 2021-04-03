package cn.enaium.cf4m.test.config

import org.junit.jupiter.api.Assertions
import cn.enaium.cf4m.CF4M
import cn.enaium.cf4m.annotation.config.Config
import cn.enaium.cf4m.provider.ConfigProvider
import cn.enaium.cf4m.annotation.config.Save

/**
 * Project: cf4m
 * Author: Enaium
 */
@Config("Module")
class ModuleConfigTest {
    @Save
    fun save() {
        val configProvider = CF4M.instance.config.getByInstance(this)
        Assertions.assertNotNull(configProvider)
        Assertions.assertEquals("Module", configProvider.name)
        println(configProvider.name + " Save")
    }
}