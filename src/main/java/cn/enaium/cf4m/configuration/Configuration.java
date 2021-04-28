package cn.enaium.cf4m.configuration;

import cn.enaium.cf4m.struct.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * @author Enaium
 */
public class Configuration {

    public final IConfiguration configuration;

    public final Properties properties = new Properties();
    public final Pair<String, String> COMMAND_PREFIX = new Pair<>("cf4m.command.prefix", "`");
    public final Pair<String, String> COMMAND_MESSAGE = new Pair<>("cf4m.command.message", this.getClass().getName() + ":message");
    public final Pair<String, Boolean> CONFIG_ENABLE = new Pair<>("cf4m.config.enable", true);

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
                            Class<?> klass = classLoader.loadClass(split[0]);
                            Method method = klass.getDeclaredMethod(split[1], String.class);
                            method.setAccessible(true);
                            if (Modifier.isStatic(method.getModifiers())) {
                                method.invoke(null, message);
                            } else {
                                method.invoke(klass.newInstance(), message);
                            }
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException | InstantiationException e) {
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

    private static void message(String message) {
        System.err.println(message);
    }

    @SuppressWarnings({"unchecked"})
    private <T> T getOrDefault(Properties properties, Pair<String, T> pair) {
        return (T) properties.getOrDefault(pair.getKey(), pair.getValue());
    }
}
