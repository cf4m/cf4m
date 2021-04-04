package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.annotation.config.Config;
import cn.enaium.cf4m.annotation.config.Load;
import cn.enaium.cf4m.annotation.config.Save;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.ConfigContainer;
import cn.enaium.cf4m.provider.ConfigProvider;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Project: cf4m
 * Author: Enaium
 */
public final class ConfigManager {


    public final ConfigContainer configContainer;

    public ConfigManager(List<Class<?>> classes, String path, IConfiguration configuration) {
        final HashMap<Object, ConfigProvider> configs = Maps.newHashMap();

        try {
            for (Class<?> klass : classes) {
                if (klass.isAnnotationPresent(Config.class)) {
                    configs.put(klass.newInstance(), new ConfigProvider() {
                        @Override
                        public String getName() {
                            return klass.getAnnotation(Config.class).value();
                        }

                        @Override
                        public String getDescription() {
                            return klass.getAnnotation(Config.class).description();
                        }

                        @Override
                        public String getPath() {
                            return path + File.separator + "configs" + File.separator + getName() + ".json";
                        }
                    });
                }
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        configContainer = new ConfigContainer() {
            @Override
            public ArrayList<ConfigProvider> getAll() {
                return Lists.newArrayList(configs.values());
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
            public void load() {
                if (configuration.config().enable()) {
                    configs.keySet().forEach(config -> {
                        for (Method method : config.getClass().getMethods()) {
                            method.setAccessible(true);
                            if (method.isAnnotationPresent(Load.class)) {
                                try {
                                    if (new File(getByInstance(config).getPath()).exists()) {
                                        method.invoke(config);
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
                if (configuration.config().enable()) {
                    new File(path).mkdir();
                    new File(path, "configs").mkdir();
                    configs.keySet().forEach(config -> {
                        for (Method method : config.getClass().getMethods()) {
                            method.setAccessible(true);
                            if (method.isAnnotationPresent(Save.class)) {
                                try {
                                    new File(getByInstance(config).getPath()).createNewFile();
                                    if (new File(getByInstance(config).getPath()).exists()) {
                                        method.invoke(config);
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
