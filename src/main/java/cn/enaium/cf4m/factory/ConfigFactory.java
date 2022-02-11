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

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.config.Config;
import cn.enaium.cf4m.annotation.config.Load;
import cn.enaium.cf4m.annotation.config.Save;
import cn.enaium.cf4m.configuration.ConfigConfiguration;
import cn.enaium.cf4m.container.ConfigContainer;
import cn.enaium.cf4m.service.ConfigService;
import cn.enaium.cf4m.provider.ConfigProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Enaium
 */
public final class ConfigFactory {

    public final ConfigContainer configContainer;

    public ConfigFactory(String path) {
        final HashMap<Object, ConfigProvider> configs = new HashMap<>();
        ArrayList<ConfigService> processors = CF4M.CLASS.getService(ConfigService.class);
        for (Class<?> klass : CF4M.CLASS.getAll()) {
            if (klass.isAnnotationPresent(Config.class)) {
                final Object configInstance = CF4M.CLASS.create(klass);
                configs.put(configInstance, new ConfigProvider() {
                    @Override
                    public String getName() {
                        return klass.getAnnotation(Config.class).value();
                    }

                    @Override
                    public String getDescription() {
                        return klass.getAnnotation(Config.class).description();
                    }

                    @Override
                    public Object getInstance() {
                        return configInstance;
                    }

                    @Override
                    public String getPath() {
                        return path + File.separator + "configs" + File.separator + getName() + ".json";
                    }
                });
            }
        }

        configContainer = new ConfigContainer() {
            @Override
            public ArrayList<ConfigProvider> getAll() {
                return new ArrayList<>(configs.values());
            }

            @Override
            public ConfigProvider getByName(String name) {
                for (ConfigProvider configProvider : getAll()) {
                    if (configProvider.getName().equalsIgnoreCase(name)) {
                        return configProvider;
                    }
                }
                return null;
            }

            @Override
            public ConfigProvider getByInstance(Object instance) {
                return configs.get(instance);
            }

            @Override
            public <T> ConfigProvider getByClass(Class<T> klass) {
                return getByInstance(CF4M.CLASS.create(klass));
            }

            @Override
            public void load() {
                if (CF4M.CONFIGURATION.getByClass(ConfigConfiguration.class).getEnable()) {
                    configs.keySet().forEach(config -> {
                        for (Method method : config.getClass().getMethods()) {
                            method.setAccessible(true);
                            if (method.isAnnotationPresent(Load.class)) {
                                try {
                                    if (new File(getByInstance(config).getPath()).exists()) {
                                        processors.forEach(configService -> configService.beforeLoad(getByInstance(config)));
                                        method.invoke(config);
                                        processors.forEach(configService -> configService.afterLoad(getByInstance(config)));
                                    }
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }

            @SuppressWarnings("ResultOfMethodCallIgnored")
            @Override
            public void save() {
                if (CF4M.CONFIGURATION.getByClass(ConfigConfiguration.class).getEnable()) {
                    new File(path).mkdir();
                    new File(path, "configs").mkdir();
                    configs.keySet().forEach(config -> {
                        for (Method method : config.getClass().getMethods()) {
                            method.setAccessible(true);
                            if (method.isAnnotationPresent(Save.class)) {
                                try {
                                    new File(getByInstance(config).getPath()).createNewFile();
                                    if (new File(getByInstance(config).getPath()).exists()) {
                                        processors.forEach(configService -> configService.beforeSave(getByInstance(config)));
                                        method.invoke(config);
                                        processors.forEach(configService -> configService.afterSave(getByInstance(config)));
                                    }
                                } catch (IllegalAccessException | InvocationTargetException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        };
    }
}
