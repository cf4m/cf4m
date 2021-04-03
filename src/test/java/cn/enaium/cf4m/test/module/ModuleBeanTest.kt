package cn.enaium.cf4m.test.module

import cn.enaium.cf4m.provider.ModuleProvider
import cn.enaium.cf4m.CF4M
import org.junit.jupiter.api.Assertions

/**
 * Project: cf4m
 * Author: Enaium
 */
class ModuleBeanTest {
    init {
        for (module in CF4M.instance.module.all) {
            println(module.name)
        }
        Assertions.assertNotNull(CF4M.instance.module.all)
    }
}