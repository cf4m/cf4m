package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Configuration;
import cn.enaium.cf4m.annotation.Container;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.ClassContainer;
import cn.enaium.cf4m.container.EventContainer;
import cn.enaium.cf4m.container.ModuleContainer;
import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Project: cf4m
 *
 * @author Enaium
 */
public final class ClassManager {

    public final ClassContainer classContainer;

    @SuppressWarnings("UnstableApiUsage")
    public ClassManager(ClassLoader classLoader, String packName) {
        final ArrayList<Class<?>> classes = Lists.newArrayList();
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
        classContainer = () -> classes;
    }
}
