package cn.enaium.cf4m.configuration;

import cn.enaium.cf4m.annotation.configuration.Configuration;
import cn.enaium.cf4m.annotation.configuration.Key;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Enaium
 */
@Configuration("cf4m.command")
public class CommandConfiguration {
    @Value
    private String prefix = "`";

    @Value
    private String message = "cn.enaium.cf4m.configuration.CommandConfiguration:send";

    public String getPrefix() {
        return prefix;
    }

    public String getMessage() {
        return message;
    }

    public void message(String message) {
        String klass = getMessage().split(":")[0];
        String method = getMessage().split(":")[1];
        try {
            Method send = this.getClass().getClassLoader().loadClass(klass).getDeclaredMethod(method, String.class);
            send.setAccessible(true);
            send.invoke(null, message);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void send(String message) {
        System.err.println(message);
    }
}
