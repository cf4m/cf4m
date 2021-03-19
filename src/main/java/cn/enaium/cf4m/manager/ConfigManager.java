package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.config.Config;
import cn.enaium.cf4m.annotation.config.Load;
import cn.enaium.cf4m.annotation.config.Save;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class ConfigManager {

    /**
     * <K> config
     * <V> name
     */
    private HashMap<Object, String> configs;

    public ConfigManager() {
        if (!CF4M.INSTANCE.configuration.config())
            return;

        configs = Maps.newHashMap();

        new File(CF4M.INSTANCE.dir).mkdir();
        new File(CF4M.INSTANCE.dir, "configs").mkdir();
        try {
            for (Class<?> type : CF4M.INSTANCE.type.getClasses()) {
                if (type.isAnnotationPresent(Config.class)) {
                    configs.put(type.newInstance(), type.getAnnotation(Config.class).value());
                }
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public Object getConfig(String name) {
        for (String s : configs.values()) {
            if (s.equals(name)) {
                return s;
            }
        }
        return null;
    }

    public String getName(Object object) {
        return configs.get(object);
    }

    public String getPath(Object object) {
        if (configs.containsKey(object)) {
            return CF4M.INSTANCE.dir + File.separator + "configs" + File.separator + configs.get(object) + ".json";
        }
        return null;
    }

    public ArrayList<Object> getConfigs() {
        return Lists.newArrayList(configs.values());
    }

    public void load() {
        if (!CF4M.INSTANCE.configuration.config())
            return;

        configs.keySet().forEach(config -> {
            for (Method method : config.getClass().getMethods()) {
                method.setAccessible(true);
                if (method.isAnnotationPresent(Load.class)) {
                    try {
                        if (new File(getPath(config)).exists()) {
                            method.invoke(config);
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void save() {
        if (!CF4M.INSTANCE.configuration.config())
            return;

        configs.keySet().forEach(config -> {
            for (Method method : config.getClass().getMethods()) {
                method.setAccessible(true);
                if (method.isAnnotationPresent(Save.class)) {
                    try {
                        new File(getPath(config)).createNewFile();
                        if (new File(getPath(config)).exists()) {
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
