package cn.enaium.cf4m.builder;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.Service;
import cn.enaium.cf4m.annotation.Scan;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.service.AutowiredService;
import cn.enaium.cf4m.service.ClassService;
import cn.enaium.cf4m.provider.CommandProvider;
import cn.enaium.cf4m.provider.ConfigProvider;
import cn.enaium.cf4m.provider.ModuleProvider;
import cn.enaium.cf4m.starter.Starter;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
@SuppressWarnings({"UnstableApiUsage", "unchecked"})
public final class ClassBuilder {

    public final ClassContainer classContainer;
    private final HashMap<Class<?>, Object> all = new HashMap<>();

    public ClassBuilder(Class<?> mainClass) {
        List<String> allClassName = getAllClassName(mainClass.getClassLoader());

        final ArrayList<String> scan = new ArrayList<>();

        for (Starter starter : ServiceLoader.load(Starter.class, mainClass.getClassLoader())) {
            System.out.println("Load starter " + starter.getName()
                    + " | " + starter.getDescription()
                    + " | " + starter.getVersion()
                    + " | " + starter.getAuthor());
            scan.add(starter.getClass().getPackage().getName());
        }

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

        ArrayList<ClassService> classServices = getProcessor(ClassService.class);

        classContainer = new ClassContainer() {
            @Override
            public ArrayList<Class<?>> getAll() {
                return new ArrayList<>(all.keySet());
            }

            @Override
            public <T> T create(Class<?> klass) {
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
                return (T) all.get(klass);
            }

            @Override
            public <T> ArrayList<T> getProcessor(Class<T> type) {
                return ClassBuilder.this.getProcessor(type);
            }

            @Override
            public void after() {
                ArrayList<AutowiredService> processors = getProcessor(AutowiredService.class);
                all.forEach((klass, instance) -> {
                    for (Field field : klass.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (klass.isAnnotationPresent(Autowired.class) || field.isAnnotationPresent(Autowired.class)) {
                            try {
                                processors.forEach(postProcessor -> postProcessor.beforePut(field, instance));
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
                                processors.forEach(autowiredService -> autowiredService.afterPut(field, instance));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        };
    }

    private <T> ArrayList<T> getProcessor(Class<T> type) {
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
