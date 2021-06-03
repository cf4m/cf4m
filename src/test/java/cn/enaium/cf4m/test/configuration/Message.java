package cn.enaium.cf4m.test.configuration;

import cn.enaium.cf4m.annotation.configuration.Impl;
import cn.enaium.cf4m.configuration.CommandConfiguration;

/**
 * @author Enaium
 */
@Impl
public class Message extends CommandConfiguration {
    @Override
    public String getPrefix() {
        return "-";
    }
}
