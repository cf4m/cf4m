package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.annotation.config.Config;
import cn.enaium.cf4m.annotation.config.Load;
import cn.enaium.cf4m.annotation.config.Save;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.ClassContainer;
import cn.enaium.cf4m.container.ConfigContainer;
import cn.enaium.cf4m.processor.ConfigProcessor;
import cn.enaium.cf4m.provider.ConfigProvider;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Enaium
 */
public final class ConfigManager {


    public final ConfigContainer configContainer;

    public ConfigManager(ClassContainer classContainer, IConfiguration configuration, String path) {
        final HashMap<Object, ConfigProvider> configs = Maps.newHashMap();
        ArrayList<ConfigProcessor> processors = classContainer.getProcessor(ConfigProcessor.class);
        for (Class<?> klass : classContainer.getAll()) {
            if (klass.isAnnotationPresent(Config.class)) {
                configs.put(classContainer.create(klass), new ConfigProvider() {
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
                if (configuration.getConfig().getEnable()) {
                    configs.keySet().forEach(config -> {
                        for (Method method : config.getClass().getMethods()) {
                            method.setAccessible(true);
                            if (method.isAnnotationPresent(Load.class)) {
                                try {
                                    if (new File(getByInstance(config).getPath()).exists()) {
                                        processors.forEach(configProcessor -> configProcessor.beforeLoad(getByInstance(config)));
                                        method.invoke(config);
                                        processors.forEach(configProcessor -> configProcessor.afterLoad(getByInstance(config)));
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
                if (configuration.getConfig().getEnable()) {
                    new File(path).mkdir();
                    new File(path, "configs").mkdir();
                    configs.keySet().forEach(config -> {
                        for (Method method : config.getClass().getMethods()) {
                            method.setAccessible(true);
                            if (method.isAnnotationPresent(Save.class)) {
                                try {
                                    new File(getByInstance(config).getPath()).createNewFile();
                                    if (new File(getByInstance(config).getPath()).exists()) {
                                        processors.forEach(configProcessor -> configProcessor.beforeSave(getByInstance(config)));
                                        method.invoke(config);
                                        processors.forEach(configProcessor -> configProcessor.afterSave(getByInstance(config)));
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
