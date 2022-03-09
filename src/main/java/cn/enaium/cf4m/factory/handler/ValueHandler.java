package cn.enaium.cf4m.factory.handler;

import cn.enaium.cf4m.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

/**
 * @author Enaium
 */
public class ValueHandler extends BeanHandler {

    private final Properties configurationProperties = new Properties();

    public ValueHandler(ClassLoader classLoader) {
        super(classLoader);
        InputStream resourceAsStream = classLoader.getResourceAsStream("cf4m.configuration.properties");
        try {
            if (resourceAsStream != null) {
                configurationProperties.load(resourceAsStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void handler(Class<?> klass, Object instance) {
        for (Field declaredField : klass.getDeclaredFields()) {
            declaredField.setAccessible(true);
            if (declaredField.isAnnotationPresent(Value.class)) {
                String value = declaredField.getAnnotation(Value.class).value();
                Object o = configurationProperties.get(value);
                try {
                    declaredField.set(instance, o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
