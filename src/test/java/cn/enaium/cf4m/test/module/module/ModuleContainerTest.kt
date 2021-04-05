package cn.enaium.cf4m.test.module.module

import cn.enaium.cf4m.annotation.Container
import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.container.ModuleContainer

/**
 * Project: cf4m
 *
 * @author Enaium
 */
@Container
@Module("ModuleContainerTest", key = 6)
class ModuleContainerTest {

    private lateinit var moduleContainer: ModuleContainer

    @Enable
    fun enable() {
        for (moduleProvider in moduleContainer.all) {
            println(moduleProvider.name)
        }
    }
}