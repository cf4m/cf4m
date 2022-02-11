package cn.enaium.cf4m.test.configuration;

import cn.enaium.cf4m.annotation.configuration.Configuration;
import cn.enaium.cf4m.annotation.configuration.Key;

/**
 * @author Enaium
 */
@Configuration("test")
public class ConfigurationTest {
    @Key
    private String value;

    public String getValue() {
        return value;
    }
}
