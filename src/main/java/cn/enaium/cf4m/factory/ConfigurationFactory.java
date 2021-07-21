package cn.enaium.cf4m.factory;

import cn.enaium.cf4m.annotation.configuration.Configuration;
import cn.enaium.cf4m.annotation.configuration.Key;
import cn.enaium.cf4m.container.ClassContainer;
import cn.enaium.cf4m.container.ConfigurationContainer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Enaium
 */
@SuppressWarnings("unchecked")
public final class ConfigurationFactory {

    public final ConfigurationContainer configurationContainer;
    public final Properties properties = new Properties();

    public ConfigurationFactory(ClassContainer classContainer, ClassLoader classLoader) {

        final HashMap<String, Object> configurations = new HashMap<>();

        for (Class<?> klass : classContainer.getAll()) {
            if (klass.isAnnotationPresent(Configuration.class)) {
                configurations.put(klass.getAnnotation(Configuration.class).value(), classContainer.create(klass));
            }
        }

        InputStream resourceAsStream = classLoader.getResourceAsStream("cf4m.configuration.properties");
        try {
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, Object> stringObjectEntry : configurations.entrySet()) {
            for (Field declaredField : stringObjectEntry.getValue().getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                if (declaredField.isAnnotationPresent(Key.class)) {
                    final Key annotation = declaredField.getAnnotation(Key.class);
                    final String name;
                    if (annotation.value().isEmpty()) {
                        name = declaredField.getName();
                    } else {
                        name = annotation.value();
                    }

                    String keyAndValue = stringObjectEntry.getKey() + "." + name;
                    if (properties.containsKey(keyAndValue)) {
                        try {
                            declaredField.set(stringObjectEntry.getValue(), properties.get(keyAndValue));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        for (Class<?> klass : classContainer.getAll()) {
            if (klass.getSuperclass() != null && klass.getSuperclass().isAnnotationPresent(Configuration.class)) {
                Configuration annotation = klass.getSuperclass().getAnnotation(Configuration.class);
                configurations.put(annotation.value(), classContainer.put(klass.getSuperclass(), classContainer.create(klass)));
            }
        }

        configurationContainer = new ConfigurationContainer() {
            @Override
            public <T> T getByKey(String key) {
                return (T) configurations.get(key);
            }

            @Override
            public <T> T getByClass(Class<T> klass) {
                return classContainer.create(klass);
            }
        };
    }
}
