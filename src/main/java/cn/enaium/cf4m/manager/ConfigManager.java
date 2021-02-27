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
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ConfigManager {

    /**
     * <K> config
     * <V> name
     */
    private HashMap<String, Object> configs;

    public ConfigManager() {
        if (!CF4M.INSTANCE.configuration.config())
            return;

        configs = Maps.newHashMap();

        new File(CF4M.INSTANCE.dir).mkdir();
        new File(CF4M.INSTANCE.dir, "configs").mkdir();
        try {
            for (Class<?> type : CF4M.INSTANCE.type.getClasses()) {
                if (type.isAnnotationPresent(Config.class)) {
                    configs.put(type.getAnnotation(Config.class).value(), type.newInstance());
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
    }

    public Object getConfig(String name) {
        if (configs.containsKey(name)) {
            return configs.get(name);
        }
        return null;
    }

    public String getName(Object object) {
        for (Map.Entry<String, Object> entry : configs.entrySet()) {
            if (entry.getValue().equals(object)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String getPath(Object object) {
        for (Map.Entry<String, Object> entry : configs.entrySet()) {
            if (entry.getValue().equals(object)) {
                return CF4M.INSTANCE.dir + File.separator + "configs" + File.separator + entry.getKey() + ".json";
            }
        }
        return null;
    }

    public ArrayList<Object> getConfigs() {
        return Lists.newArrayList(configs.values());
    }

    public void load() {
        if (!CF4M.INSTANCE.configuration.config())
            return;
        try {
            for (Map.Entry<String, Object> entry : configs.entrySet()) {
                if (new File(getPath(entry.getValue())).exists()) {
                    for (Method method : entry.getValue().getClass().getMethods()) {
                        method.setAccessible(true);
                        if (method.isAnnotationPresent(Load.class)) {
                            method.invoke(entry.getValue());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
    }

    public void save() {
        if (!CF4M.INSTANCE.configuration.config())
            return;
        try {
            for (Map.Entry<String, Object> entry : configs.entrySet()) {
                new File(getPath(entry.getValue())).createNewFile();
                if (new File(getPath(entry.getValue())).exists()) {
                    for (Method method : entry.getValue().getClass().getMethods()) {
                        method.setAccessible(true);
                        if (method.isAnnotationPresent(Save.class)) {
                            method.invoke(entry.getValue());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
    }
}
