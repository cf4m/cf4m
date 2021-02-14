package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Configuration;
import cn.enaium.cf4m.configuration.IConfiguration;
import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ClassManager {
    private final ArrayList<Class<?>> classes = Lists.newArrayList();

    public ClassManager() {
        try {
            for (ClassPath.ClassInfo info : ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClasses()) {
                if (info.getName().startsWith(CF4M.getInstance().packName)) {
                    Class<?> clazz = Class.forName(info.getName());
                    if (clazz.isAnnotationPresent(Configuration.class)) {
                        CF4M.getInstance().configuration = (IConfiguration) clazz.newInstance();
                    }
                    classes.add(clazz);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Class<?>> getClasses() {
        return classes;
    }
}
