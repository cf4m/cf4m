package cn.enaium.cf4m.test.configuration;

import cn.enaium.cf4m.annotation.configuration.Configuration;
import cn.enaium.cf4m.annotation.configuration.Value;

/**
 * @author Enaium
 */
@Configuration("test")
public class ConfigurationTest {
    @Value
    private String value;

    public String getValue() {
        return value;
    }
}
