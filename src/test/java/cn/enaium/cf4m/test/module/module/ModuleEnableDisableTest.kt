package cn.enaium.cf4m.test.module.module

import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.annotation.module.Disable
import cn.enaium.cf4m.annotation.module.Module

/**
 * Project: cf4m
 * Author: Enaium
 */
@Module(value = "ModuleEnableDisableTest", key = 1)
class ModuleEnableDisableTest {
    @Enable
    fun enable() {
        println("ModuleTest Enable")
    }

    @Disable
    fun disable() {
        println("ModuleTest Disable")
    }
}