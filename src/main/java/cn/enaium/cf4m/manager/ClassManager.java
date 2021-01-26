package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import com.google.common.reflect.ClassPath;

import java.util.ArrayList;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ClassManager implements IClassManager {
    private final ArrayList<Class<?>> classes = new ArrayList<>();

    public ClassManager() {
        try {
            for (ClassPath.ClassInfo info : ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClasses()) {
                if (info.getName().startsWith(CF4M.getInstance().packName)) {
                    classes.add(loadClass(info.load()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<?> loadClass(Class<?> clazz) throws ClassNotFoundException {
        return clazz;
    }

    @Override
    public ArrayList<Class<?>> getClasses() {
        return classes;
    }
}
