package cn.enaium.cf4m.test

import org.junit.jupiter.api.Test
import cn.enaium.cf4m.CF4M.run
import cn.enaium.cf4m.CF4M.INSTANCE
import cn.enaium.cf4m.test.module.ModuleBeanTest
import cn.enaium.cf4m.test.event.EventRegisterUnregisterTest
import cn.enaium.cf4m.test.event.EventPriorityTest
import cn.enaium.cf4m.test.event.EventCancelTest
import cn.enaium.cf4m.test.config.ConfigTest

/**
 * Project: cf4m
 *
 * @author Enaium
 */
class T {
    @Test
    fun test() {
        run(this, System.getProperty("user.dir") + "/build/configTest")
        println("Check ModuleBeanTest Start")
        ModuleBeanTest()
        println("Check ModuleBeanTest End")
        println()
        println("Check EventRegisterUnregister Start")
        EventRegisterUnregisterTest()
        println("Check EventRegisterUnregister End")
        println()
        println("Check EventPriorityTest Start")
        EventPriorityTest()
        println("Check EventPriorityTest End")
        println()
        println("Check EventCancelTest Start")
        EventCancelTest()
        println("Check EventCancelTest End")
        println()
        println("Check ModuleEnableDisableTest Start")
        INSTANCE.module.onKey(1)
        INSTANCE.module.onKey(1)
        println("Check ModuleEnableDisableTest End")
        println()
        println("Check ModuleExtendTest Start")
        INSTANCE.module.onKey(2)
        INSTANCE.module.onKey(2)
        println("Check ModuleExtendTest1 End")
        println()
        INSTANCE.module.onKey(3)
        INSTANCE.module.onKey(3)
        println("Check ModuleExtendTest1 End")
        println()
        println("Check ModuleSettingTest Start")
        INSTANCE.module.onKey(4)
        println("Check ModuleSettingTest End")
        println()
        println("Check ModuleType Start")
        INSTANCE.module.onKey(5)
        println("Check ModuleType End")
        println()
        println("Check CommandTest Start")
        println(INSTANCE.command.execCommand("`t"))
        println(INSTANCE.command.execCommand("`t var1"))
        println(INSTANCE.command.execCommand("`t var1 1"))
        println("Check CommandTest End")
        println()
        println("Check ConfigTest Start")
        ConfigTest()
        println("Check ConfigTest End")
    }
}