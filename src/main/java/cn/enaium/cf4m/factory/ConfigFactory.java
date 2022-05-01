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
import cn.enaium.cf4m.configuration.NameGeneratorConfiguration;
import cn.enaium.cf4m.container.ConfigContainer;
import cn.enaium.cf4m.service.ConfigService;
import cn.enaium.cf4m.provider.ConfigProvider;
import cn.enaium.cf4m.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author Enaium
 */
@SuppressWarnings("unchecked")
public final class ConfigFactory extends ProviderFactory<ConfigProvider> {

    public ConfigFactory(String path) {
        ArrayList<ConfigService> processors = CF4M.CLASS.getService(ConfigService.class);
        for (Class<?> klass : CF4M.CLASS.getInstance().keySet()) {
            if (klass.isAnnotationPresent(Config.class)) {
                final Object configInstance = CF4M.CLASS.create(klass);
                addProvider(configInstance, new ConfigProvider() {
                    @Override
                    public String getName() {
                        String value = klass.getAnnotation(Config.class).value();

                        if (StringUtil.isEmpty(value)) {
                            return CF4M.CONFIGURATION.get(NameGeneratorConfiguration.class).generate(klass);
                        }

                        return value;
                    }

                    @Override
                    public String getDescription() {
                        return klass.getAnnotation(Config.class).description();
                    }

                    @Override
                    public <T> T as() {
                        return (T) configInstance;
                    }

                    @Override
                    public String getPath() {
                        return path + File.separator + "configs" + File.separator + getName() + ".json";
                    }
                });
            }
        }

        CF4M.CONFIG = new ConfigContainer() {
            @Override
            public ArrayList<ConfigProvider> getAll() {
                return new ArrayList<>(getProvider().values());
            }

            @Override
            public ConfigProvider get(String name) {
                for (ConfigProvider configProvider : getAll()) {
                    if (configProvider.getName().equalsIgnoreCase(name)) {
                        return configProvider;
                    }
                }
                return null;
            }

            @Override
            public ConfigProvider get(Object instance) {
                return getProvider().get(instance);
            }

            @Override
            public <T> ConfigProvider get(Class<T> klass) {
                return get(CF4M.CLASS.create(klass));
            }

            @Override
            public void load() {
                if (CF4M.CONFIGURATION.get(ConfigConfiguration.class).getEnable()) {
                    getProvider().keySet().forEach(config -> {
                        for (Method method : config.getClass().getDeclaredMethods()) {
                            method.setAccessible(true);
                            if (method.isAnnotationPresent(Load.class)) {
                                try {
                                    if (new File(get(config).getPath()).exists()) {
                                        processors.forEach(configService -> configService.beforeLoad(get(config)));
                                        method.invoke(config);
                                        processors.forEach(configService -> configService.afterLoad(get(config)));
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
                if (CF4M.CONFIGURATION.get(ConfigConfiguration.class).getEnable()) {
                    new File(path).mkdir();
                    new File(path, "configs").mkdir();
                    getProvider().keySet().forEach(config -> {
                        for (Method method : config.getClass().getDeclaredMethods()) {
                            method.setAccessible(true);
                            if (method.isAnnotationPresent(Save.class)) {
                                try {
                                    new File(get(config).getPath()).createNewFile();
                                    if (new File(get(config).getPath()).exists()) {
                                        processors.forEach(configService -> configService.beforeSave(get(config)));
                                        method.invoke(config);
                                        processors.forEach(configService -> configService.afterSave(get(config)));
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
