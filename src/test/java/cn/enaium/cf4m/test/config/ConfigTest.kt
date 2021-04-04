package cn.enaium.cf4m.test.config

import org.junit.jupiter.api.Assertions
import cn.enaium.cf4m.CF4M.CF4M

/**
 * Project: cf4m
 * Author: Enaium
 */
class ConfigTest {
    init {
        Assertions.assertNotEquals(0, CF4M.config.all.size)
        for (configProvider in CF4M.config.all) {
            println(configProvider.name + "|" + configProvider.path)
        }
    }
}