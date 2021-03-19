package cn.enaium.cf4m.test;

import cn.enaium.cf4m.annotation.Configuration;
import cn.enaium.cf4m.configuration.IConfigConfiguration;
import cn.enaium.cf4m.configuration.IConfiguration;

/**
 * Project: cf4m
 * Author: Enaium
 */
@Configuration
public class ConfigurationTest implements IConfiguration {
    @Override
    public IConfigConfiguration config() {
        return new IConfigConfiguration() {
            @Override
            public boolean enable() {
                return false;
            }
        };
    }
}
