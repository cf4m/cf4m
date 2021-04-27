package cn.enaium.cf4m.configuration;

import cn.enaium.cf4m.struct.KeyValue;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * @author Enaium
 */
public class Configuration {

    public final IConfiguration configuration;

    public final Properties properties = new Properties();
    public final KeyValue<String, String> COMMAND_PREFIX = new KeyValue<>("cf4m.command.prefix", "`");
    public final KeyValue<String, String> COMMAND_MESSAGE = new KeyValue<>("cf4m.command.message", this.getClass().getName() + ":message");
    public final KeyValue<String, Boolean> CONFIG_ENABLE = new KeyValue<>("cf4m.config.enable", true);

    public Configuration(ClassLoader classLoader) {
        InputStream resourceAsStream = classLoader.getResourceAsStream("cf4m.configuration.properties");
        try {
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        configuration = new IConfiguration() {
            @Override
            public ICommandConfiguration getCommand() {
                return new ICommandConfiguration() {
                    @Override
                    public String getPrefix() {
                        return getOrDefault(properties, COMMAND_PREFIX);
                    }

                    @Override
                    public void message(String message) {
                        String orDefault = getOrDefault(properties, COMMAND_MESSAGE);
                        try {
                            String[] split = orDefault.split(":");
                            classLoader.loadClass(split[0]).getMethod(split[1], String.class).invoke(null, message);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                };
            }

            @Override
            public IConfigConfiguration getConfig() {
                return () -> getOrDefault(properties, CONFIG_ENABLE);
            }
        };
    }

    public static void message(String message) {
        System.err.println(message);
    }

    @SuppressWarnings({"unchecked"})
    private <T> T getOrDefault(Properties properties, KeyValue<String, T> keyValue) {
        return (T) properties.getOrDefault(keyValue.getKey(), keyValue.getValue());
    }
}
