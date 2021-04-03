package cn.enaium.cf4m.test.config

import org.junit.jupiter.api.Assertions
import cn.enaium.cf4m.CF4M
import cn.enaium.cf4m.provider.ConfigProvider
import cn.enaium.cf4m.annotation.config.Save

/**
 * Project: cf4m
 * Author: Enaium
 */
class ConfigTest {
    init {
        Assertions.assertNotEquals(0, CF4M.instance.config.all.size)
        for (configProvider in CF4M.instance.config.all) {
            println(configProvider.name + "|" + configProvider.path)
        }
    }
}