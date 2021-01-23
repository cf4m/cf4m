package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.config.Config;
import cn.enaium.cf4m.config.ConfigAT;
import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.module.ModuleAT;
import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;

import java.io.File;
import java.util.ArrayList;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class ConfigManager {

    public ArrayList<Config> configs = Lists.newArrayList();

    public ConfigManager() {
        new File(CF4M.getInstance().clientDataDir + "/configs/").mkdir();
        try {
            for (Class<?> clazz : CF4M.getInstance().classManager.getClasses()) {
                if (clazz.isAnnotationPresent(ConfigAT.class)) {
                    configs.add((Config) clazz.newInstance());
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
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
