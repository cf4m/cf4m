package cn.enaium.cf4m.configuration;

import cn.enaium.cf4m.annotation.configuration.Configuration;
import cn.enaium.cf4m.annotation.configuration.Value;

/**
 * @author Enaium
 */
@Configuration("cf4m.command")
public class CommandConfiguration {
    @Value
    private String prefix = "`";

    @Value
    private String message = "cn.enaium.cf4m.configuration.CommandConfiguration:message";

    public String getPrefix() {
        return prefix;
    }

    public String getMessage() {
        return message;
    }

    public void message(String message) {
        System.err.println(message);
    }
}
