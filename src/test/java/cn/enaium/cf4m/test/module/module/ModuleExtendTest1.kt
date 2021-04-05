package cn.enaium.cf4m.test.module.module

import cn.enaium.cf4m.test.module.ModuleExtend
import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.CF4M.INSTANCE
import cn.enaium.cf4m.annotation.module.Disable
import cn.enaium.cf4m.annotation.module.Module

/**
 * Project: cf4m
 *
 * @author Enaium
 */
@Module(value = "ModuleExtendTest1", key = 3)
class ModuleExtendTest1 {
    private var moduleExtend: ModuleExtend? = null

    @Enable
    fun enable() {
        moduleExtend = INSTANCE.module.getByInstance(this).getExtend()
        moduleExtend!!.tag = "tag2"
        moduleExtend!!.age = 2
        moduleExtend!!.func = object : ModuleExtend.Action {
            override fun on() {
                println("FUN2")
            }
        }
    }

    @Disable
    fun disable() {
        println(moduleExtend)
        println(moduleExtend!!.tag)
        println(moduleExtend!!.age)
        moduleExtend!!.func!!.on()
    }
}