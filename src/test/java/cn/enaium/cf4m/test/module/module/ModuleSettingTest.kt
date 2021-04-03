package cn.enaium.cf4m.test.module.module


import cn.enaium.cf4m.test.setting.EnableSettingTest
import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.CF4M
import cn.enaium.cf4m.annotation.Setting
import cn.enaium.cf4m.annotation.module.Module

/**
 * Project: cf4m
 * Author: Enaium
 */
@Module(value = "ModuleSettingTest", key = 4)
class ModuleSettingTest {
    @Setting("setting1")
    private val enableSettingTest = EnableSettingTest(true)
    @Enable
    fun enable() {
        println(enableSettingTest.enable)
        println(CF4M.instance.module.getByInstance(this).setting.getByName("setting1").getSetting<EnableSettingTest>())
        for (settingProvider in CF4M.instance.module.getByInstance(this).setting.all) {
            if (settingProvider.getSetting<Any>() is EnableSettingTest) {
                println(settingProvider.name)
            }
        }
    }
}