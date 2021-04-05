package cn.enaium.cf4m.test.module.module

import cn.enaium.cf4m.CF4M
import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.annotation.module.Module

/**
 * Project: cf4m
 *
 * @author Enaium
 */
@Module("ModuleTypeTest", key = 5)
class ModuleTypeTest {
    @Enable
    fun enable() {
        for (s in CF4M.INSTANCE.module.allType) {
            println(s)
        }
    }
}