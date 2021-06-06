package cn.enaium.cf4m.test.configuration;

import cn.enaium.cf4m.configuration.CommandConfiguration;

/**
 * @author Enaium
 */
public class Message extends CommandConfiguration {
    @Override
    public String getPrefix() {
        return "-";
    }
}
