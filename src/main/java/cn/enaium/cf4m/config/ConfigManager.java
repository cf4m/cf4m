package cn.enaium.cf4m.config;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.module.ModuleAT;
import com.google.common.reflect.ClassPath;

import java.io.File;
import java.util.ArrayList;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class ConfigManager {

    public ArrayList<Config> configs = new ArrayList<>();

    public ConfigManager() {
        new File(CF4M.getInstance().clientDataDir + "/configs/").mkdir();
        try {
            for (ClassPath.ClassInfo info : ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClasses()) {
                if (info.getName().startsWith(CF4M.getInstance().packName)) {
                    Class<?> clazz = info.load();
                    if (clazz.isAnnotationPresent(ConfigAT.class)) {
                        configs.add((Config) clazz.newInstance());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void load() {
        for (Config c : configs) {
            if (new File(c.getPath()).exists()) {
                c.load();
            }
        }
    }

    public void save() {
        for (Config c : configs) {
            c.save();
        }
    }
}
