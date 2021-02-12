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

    public HashMap<Object, String> configs = Maps.newHashMap();

    public ConfigManager() {
        new File(CF4M.getInstance().clientDataDir).mkdir();
        new File(CF4M.getInstance().clientDataDir + "/configs/").mkdir();
        try {
            for (Class<?> clazz : CF4M.getInstance().classManager.getClasses()) {
                if (clazz.isAnnotationPresent(Config.class)) {
                    configs.put(clazz.newInstance(), clazz.getAnnotation(Config.class).value());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                return CF4M.getInstance().clientDataDir + "/configs/" + entry.getValue() + ".json";
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
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
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
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
