package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Auto;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.provider.CommandProvider;
import cn.enaium.cf4m.provider.ConfigProvider;
import cn.enaium.cf4m.provider.ModuleProvider;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Enaium
 */
@SuppressWarnings({"UnstableApiUsage", "unchecked"})
public final class ClassManager {

    public final ClassContainer classContainer;

    public ClassManager(ClassLoader classLoader, String packName) {
        final HashMap<Class<?>, Object> all = new HashMap<>();
        try {
            for (ClassPath.ClassInfo info : ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClasses()) {
                if (info.getName().startsWith(packName)) {
                    Class<?> klass = classLoader.loadClass(info.getName());
                    all.put(klass, null);
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        classContainer = new ClassContainer() {
            @Override
            public ArrayList<Class<?>> getClasses() {
                return new ArrayList<>(all.keySet());
            }

            @Override
            public <T> T create(Class<?> klass) {
                if (all.get(klass) == null) {
                    try {
                        all.put(klass, klass.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new ExceptionInInitializerError();
                }
                return get(klass);
            }

            @Override
            public <T> T get(Class<?> klass) {
                return (T) all.get(klass);
            }

            @Override
            public void accept() {
                all.forEach((klass, instance) -> {
                    for (Field field : klass.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (klass.isAnnotationPresent(Auto.class) || field.isAnnotationPresent(Auto.class)) {
                            try {
                                if (field.getType().equals(EventContainer.class)) {
                                    field.set(instance, CF4M.INSTANCE.getEvent());
                                } else if (field.getType().equals(ModuleContainer.class)) {
                                    field.set(instance, CF4M.INSTANCE.getModule());
                                } else if (field.getType().equals(CommandContainer.class)) {
                                    field.set(instance, CF4M.INSTANCE.getCommand());
                                } else if (field.getType().equals(ConfigContainer.class)) {
                                    field.set(instance, CF4M.INSTANCE.getConfig());
                                } else if (field.getType().equals(IConfiguration.class)) {
                                    field.set(instance, CF4M.INSTANCE.getConfiguration());
                                } else if (field.getType().equals(ModuleProvider.class)) {
                                    field.set(instance, CF4M.INSTANCE.getModule().getByInstance(instance));
                                } else if (field.getType().equals(CommandProvider.class)) {
                                    field.set(instance, CF4M.INSTANCE.getCommand().getByInstance(instance));
                                } else if (field.getType().equals(ConfigProvider.class)) {
                                    field.set(instance, CF4M.INSTANCE.getConfig().getByInstance(instance));
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        };
    }
}
