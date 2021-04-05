package cn.enaium.cf4m.test.module.module

import cn.enaium.cf4m.annotation.Auto
import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.configuration.IConfiguration
import cn.enaium.cf4m.container.CommandContainer
import cn.enaium.cf4m.container.ModuleContainer
import org.junit.jupiter.api.Assertions;

/**
 * @author Enaium
 */
@Auto
@Module("ModuleAutoTest", key = 6)
class ModuleAutoTest {
    private lateinit var configuration: IConfiguration
    private lateinit var eventContainer: ModuleContainer
    private lateinit var moduleContainer: ModuleContainer
    private lateinit var commandContainer: CommandContainer
    private lateinit var configContainer: CommandContainer


    @Enable
    fun enable() {
        Assertions.assertNotNull(configuration)
        Assertions.assertNotNull(eventContainer)
        Assertions.assertNotNull(moduleContainer)
        Assertions.assertNotNull(commandContainer)
        Assertions.assertNotNull(configContainer)
    }
}