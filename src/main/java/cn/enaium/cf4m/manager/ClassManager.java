package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Configuration;
import cn.enaium.cf4m.configuration.IConfiguration;
import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Project: cf4m
 * Author: Enaium
 */
public final class ClassManager {
    private final ArrayList<Class<?>> classes = Lists.newArrayList();

    @SuppressWarnings("UnstableApiUsage")
    public ClassManager(ClassLoader classLoader, String packName) {
        try {
            for (ClassPath.ClassInfo info : ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClasses()) {
                if (info.getName().startsWith(packName)) {
                    Class<?> klass = classLoader.loadClass(info.getName());
                    classes.add(klass);
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Class<?>> getClasses() {
        return classes;
    }
}
