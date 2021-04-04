package cn.enaium.cf4m.test.module.module

import cn.enaium.cf4m.test.module.ModuleExtend
import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.CF4M.CF4M
import cn.enaium.cf4m.annotation.module.Disable
import cn.enaium.cf4m.annotation.module.Module

/**
 * Project: cf4m
 * Author: Enaium
 */
@Module(value = "ModuleExtendTest", key = 2)
class ModuleExtendTest {
    private var moduleExtend: ModuleExtend? = null

    @Enable
    fun enable() {
        moduleExtend = CF4M.module.getByInstance(this).getExtend()
        moduleExtend!!.tag = "tag1"
        moduleExtend!!.age = 1
        moduleExtend!!.func = object : ModuleExtend.Action {
            override fun on() {
                println("FUN1")
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