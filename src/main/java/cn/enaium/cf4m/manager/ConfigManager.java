package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.config.Config;
import cn.enaium.cf4m.annotation.config.Load;
import cn.enaium.cf4m.annotation.config.Save;
import com.google.common.collect.Maps;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ConfigManager {

    public HashMap<Object, String> configs = Maps.newHashMap();

    public ConfigManager() {
        new File(CF4M.INSTANCE.dir).mkdir();
        new File(CF4M.INSTANCE.dir, "configs").mkdir();
        try {
            for (Class<?> type : CF4M.INSTANCE.type.getClasses()) {
                if (type.isAnnotationPresent(Config.class)) {
                    configs.put(type.newInstance(), type.getAnnotation(Config.class).value());
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
    }

    public String getName(Object object) {
        for (Map.Entry<Object, String> entry : configs.entrySet()) {
            if (entry.getKey().equals(object)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public String getPath(Object object) {
        for (Map.Entry<Object, String> entry : configs.entrySet()) {
            if (entry.getKey().equals(object)) {
                return CF4M.INSTANCE.dir + File.separator + "configs" + File.separator + entry.getValue() + ".json";
            }
        }
        return null;
    }

    public void load() {
        try {
            for (Map.Entry<Object, String> entry : configs.entrySet()) {
                if (new File(getPath(entry.getKey())).exists()) {
                    for (Method method : entry.getKey().getClass().getMethods()) {
                        method.setAccessible(true);
                        if (method.isAnnotationPresent(Load.class)) {
                            method.invoke(entry.getKey());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
    }

    public void save() {
        try {
            for (Map.Entry<Object, String> entry : configs.entrySet()) {
                new File(getPath(entry.getKey())).createNewFile();
                if (new File(getPath(entry.getKey())).exists()) {
                    for (Method method : entry.getKey().getClass().getMethods()) {
                        method.setAccessible(true);
                        if (method.isAnnotationPresent(Save.class)) {
                            method.invoke(entry.getKey());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
    }
}
