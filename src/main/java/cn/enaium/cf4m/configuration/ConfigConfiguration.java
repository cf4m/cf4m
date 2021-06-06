package cn.enaium.cf4m.configuration;

import cn.enaium.cf4m.annotation.configuration.Configuration;
import cn.enaium.cf4m.annotation.configuration.Value;

/**
 * @author Enaium
 */
@Configuration("cf4m.config")
public class ConfigConfiguration {
    @Value
    private boolean enable = true;

    public boolean getEnable() {
        return enable;
    }
}
