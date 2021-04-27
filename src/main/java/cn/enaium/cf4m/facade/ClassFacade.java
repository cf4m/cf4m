package cn.enaium.cf4m.facade;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.Plugin;
import cn.enaium.cf4m.annotation.Service;
import cn.enaium.cf4m.annotation.Scan;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.plugin.PluginInitialize;
import cn.enaium.cf4m.provider.*;
import cn.enaium.cf4m.service.*;
import cn.enaium.cf4m.plugin.PluginLoader;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
@SuppressWarnings({"UnstableApiUsage", "unchecked"})
public final class ClassFacade {

    public final ClassContainer classContainer;
    private final HashMap<Class<?>, Object> all = new HashMap<>();

    public ClassFacade(Class<?> mainClass) {
        List<String> allClassName = getAllClassName(mainClass.getClassLoader());

        final ArrayList<String> scan = new ArrayList<>();

        PluginLoader.loadPlugin(PluginInitialize.class).forEach(pluginInitialize -> {
            Plugin plugin = pluginInitialize.getClass().getAnnotation(Plugin.class);
            System.out.println("Load plugin " + plugin.name()
                    + " | " + plugin.description()
                    + " | " + plugin.version()
                    + " | " + plugin.author());
            pluginInitialize.initialize();
            scan.add(pluginInitialize.getClass().getPackage().getName());
        });

        scan.add(mainClass.getPackage().getName());
        if (mainClass.isAnnotationPresent(Scan.class)) {
            Collections.addAll(scan, mainClass.getAnnotation(Scan.class).value());
        }

        for (String className : allClassName) {
            for (String packageName : scan) {
                if (className.startsWith(packageName)) {
                    try {
                        Class<?> klass = mainClass.getClassLoader().loadClass(className);
                        if (klass.isAnnotationPresent(Service.class)) {
                            all.put(klass, klass.newInstance());
                        } else {
                            all.put(klass, null);
                        }
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        ArrayList<ClassService> classServices = getService(ClassService.class);

        classContainer = new ClassContainer() {
            @Override
            public ArrayList<Class<?>> getAll() {
                return new ArrayList<>(all.keySet());
            }

            @Override
            public <T> T create(Class<T> klass) {
                if (all.get(klass) == null) {
                    try {
                        Object instance = klass.newInstance();
                        classServices.forEach(classService -> classService.beforeCreate(klass, instance));
                        all.put(klass, instance);
                        classServices.forEach(classService -> classService.afterCreate(klass, instance));
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                autowired();
                return (T) all.get(klass);
            }

            @Override
            public <T> ArrayList<T> getService(Class<T> type) {
                return ClassFacade.this.getService(type);
            }

            @Override
            public void after() {
                autowired();
            }

            public void autowired() {
                all.forEach((klass, instance) -> {

                    if (instance == null || !CF4M.isRun()) {
                        return;
                    }

                    for (Field field : klass.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (!klass.isAnnotationPresent(Autowired.class) && !field.isAnnotationPresent(Autowired.class)) {
                            continue;
                        }

                        try {
                            getService(AutowiredService.class).forEach(postProcessor -> postProcessor.beforePut(field, instance));
                            if (field.getType().equals(ClassContainer.class)) {
                                field.set(instance, this);
                            } else if (field.getType().equals(EventContainer.class)) {
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
                            getService(AutowiredService.class).forEach(autowiredService -> autowiredService.afterPut(field, instance));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    for (Method method : klass.getDeclaredMethods()) {
                        method.setAccessible(true);

                        if (!klass.isAnnotationPresent(Autowired.class) && !method.isAnnotationPresent(Autowired.class)) {
                            continue;
                        }

                        if (method.getParameterCount() != 1) {
                            continue;
                        }

                        Class<?> type = method.getParameterTypes()[0];

                        try {
                            getService(AutowiredService.class).forEach(autowiredService -> autowiredService.beforeSet(method, instance));
                            if (type.equals(ClassContainer.class)) {
                                method.invoke(instance, this);
                            } else if (type.equals(EventContainer.class)) {
                                method.invoke(instance, CF4M.INSTANCE.getEvent());
                            } else if (type.equals(ModuleContainer.class)) {
                                method.invoke(instance, CF4M.INSTANCE.getModule());
                            } else if (type.equals(CommandContainer.class)) {
                                method.invoke(instance, CF4M.INSTANCE.getCommand());
                            } else if (type.equals(ConfigContainer.class)) {
                                method.invoke(instance, CF4M.INSTANCE.getConfig());
                            } else if (type.equals(IConfiguration.class)) {
                                method.invoke(instance, CF4M.INSTANCE.getConfiguration());
                            } else if (type.equals(ModuleProvider.class)) {
                                method.invoke(instance, CF4M.INSTANCE.getModule().getByInstance(instance));
                            } else if (type.equals(CommandProvider.class)) {
                                method.invoke(instance, CF4M.INSTANCE.getCommand().getByInstance(instance));
                            } else if (type.equals(ConfigProvider.class)) {
                                method.invoke(instance, CF4M.INSTANCE.getConfig().getByInstance(instance));
                            }
                            getService(AutowiredService.class).forEach(autowiredService -> autowiredService.afterSet(method, instance));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
    }

    private <T> ArrayList<T> getService(Class<T> type) {
        return all.keySet().stream().filter(klass -> klass.isAnnotationPresent(Service.class)).filter(type::isAssignableFrom).map(klass -> (T) all.get(klass)).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<String> getAllClassName(ClassLoader classLoader) {
        List<String> classes = new ArrayList<>();
        try {
            for (ClassPath.ClassInfo topLevelClass : ClassPath.from(classLoader).getTopLevelClasses()) {
                classes.add(topLevelClass.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }
}
