package cn.enaium.cf4m.test

import cn.enaium.cf4m.annotation.Configuration
import cn.enaium.cf4m.configuration.ICommandConfiguration
import cn.enaium.cf4m.configuration.IConfiguration
import cn.enaium.cf4m.configuration.IModuleConfiguration
import cn.enaium.cf4m.provider.ModuleProvider

/**
 * @author Enaium
 */
@Configuration
class ConfigurationTest : IConfiguration {
    override fun getCommand(): ICommandConfiguration {
        return object : ICommandConfiguration {
            override fun getPrefix(): String {
                return "-"
            }
        }
    }

    override fun getModule(): IModuleConfiguration {
        return object : IModuleConfiguration {
            override fun enable(module: ModuleProvider) {
                println(module.name + " Enable")
            }

            override fun disable(module: ModuleProvider) {
                println(module.name + " Disable")
            }
        }
    }
}