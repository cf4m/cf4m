/**
 * Copyright (C) 2020 Enaium
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    public final Properties configurationProperties = new Properties();

    public ConfigurationFactory(ClassContainer classContainer, ClassLoader classLoader) {

        final HashMap<String, Object> configurations = new HashMap<>();

        final HashMap<String, Object> properties = new HashMap<>();

        for (Class<?> klass : classContainer.getAll()) {
            if (klass.isAnnotationPresent(Configuration.class)) {
                configurations.put(klass.getAnnotation(Configuration.class).value(), classContainer.create(klass));
            }

            if (klass.isAnnotationPresent(cn.enaium.cf4m.annotation.configuration.Properties.class)) {
                properties.put(klass.getAnnotation(cn.enaium.cf4m.annotation.configuration.Properties.class).value(), classContainer.create(klass));
            }
        }

        InputStream resourceAsStream = classLoader.getResourceAsStream("cf4m.configuration.properties");
        try {
            if (resourceAsStream != null) {
                configurationProperties.load(resourceAsStream);
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
                    if (configurationProperties.containsKey(keyAndValue)) {
                        try {
                            declaredField.set(stringObjectEntry.getValue(), configurationProperties.get(keyAndValue));
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

        for (Map.Entry<String, Object> stringObjectEntry : properties.entrySet()) {
            Properties propertiesProperties = new Properties();
            InputStream propertiesInputStream = classLoader.getResourceAsStream(stringObjectEntry.getKey());
            try {
                if (propertiesInputStream != null) {
                    propertiesProperties.load(propertiesInputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Field declaredField : stringObjectEntry.getValue().getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                if (declaredField.isAnnotationPresent(Key.class)) {
                    Key annotation = declaredField.getAnnotation(Key.class);
                    String key = annotation.value();
                    if (propertiesProperties.containsKey(key)) {
                        try {
                            declaredField.set(stringObjectEntry.getValue(), propertiesProperties.get(key));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        configurationContainer = new ConfigurationContainer() {
            @Override
            public <T> T get(String key) {
                return (T) configurations.get(key);
            }

            @Override
            public <T> T get(Class<T> klass) {
                return classContainer.create(klass);
            }
        };
    }
}
