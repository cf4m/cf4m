package cn.enaium.cf4m.test;

import cn.enaium.cf4m.annotation.Configuration;
import cn.enaium.cf4m.configuration.ICommandConfiguration;
import cn.enaium.cf4m.configuration.IConfiguration;

/**
 * @author Enaium
 */
@Configuration
public class ConfigurationTest implements IConfiguration {
    @Override
    public ICommandConfiguration getCommand() {
        return new ICommandConfiguration() {
            @Override
            public String getPrefix() {
                return "-";
            }
        };
    }
}
