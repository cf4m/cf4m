package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.command.Command;
import cn.enaium.cf4m.command.CommandAT;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ClassManager {
    private final ArrayList<Class<?>> classes = new ArrayList<>();

    public ClassManager() {
        try {
            for (ClassPath.ClassInfo info : ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClasses()) {
                if (info.getName().startsWith(CF4M.getInstance().packName)) {
                    classes.add(info.load());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Class<?>> getClasses() {
        return classes;
    }
}
