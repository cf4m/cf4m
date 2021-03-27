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

/**
 * Project: cf4m
 * Author: Enaium
 */
public class ConfigManager {

    /**
     * <K> config
     * <V> name
     */
    private final HashMap<Object, String> configs;

    public ConfigManager() {
        configs = Maps.newHashMap();

        try {
            for (Class<?> klass : CF4M.klass.getClasses()) {
                if (klass.isAnnotationPresent(Config.class)) {
                    configs.put(klass.newInstance(), klass.getAnnotation(Config.class).value());
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
            return CF4M.dir + File.separator + "configs" + File.separator + configs.get(object) + ".json";
        }
        return null;
    }

    public ArrayList<Object> getConfigs() {
        return Lists.newArrayList(configs.values());
    }

    /**
     * Load config
     */
    public void load() {
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

    /**
     * Save config
     */
    public void save() {
        new File(CF4M.dir).mkdir();
        new File(CF4M.dir, "configs").mkdir();
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
