package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.Processor;
import cn.enaium.cf4m.annotation.Scan;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.processor.AutowiredProcessor;
import cn.enaium.cf4m.processor.ClassProcessor;
import cn.enaium.cf4m.provider.CommandProvider;
import cn.enaium.cf4m.provider.ConfigProvider;
import cn.enaium.cf4m.provider.ModuleProvider;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
@SuppressWarnings({"UnstableApiUsage", "unchecked"})
public final class ClassManager {

    public final ClassContainer classContainer;
    private final HashMap<Class<?>, Object> all = new HashMap<>();

    public ClassManager(Class<?> mainClass) {
        final ArrayList<String> scan = new ArrayList<>();
        scan.add(mainClass.getPackage().getName());
        if (mainClass.isAnnotationPresent(Scan.class)) {
            Collections.addAll(scan, mainClass.getAnnotation(Scan.class).value());
        }
        try {
            for (ClassPath.ClassInfo info : ClassPath.from(mainClass.getClassLoader()).getTopLevelClasses()) {
                for (String packageName : scan) {
                    if (info.getName().startsWith(packageName)) {
                        Class<?> klass = mainClass.getClassLoader().loadClass(info.getName());
                        if (klass.isAnnotationPresent(Processor.class)) {
                            all.put(klass, klass.newInstance());
                        } else {
                            all.put(klass, null);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | IOException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ArrayList<ClassProcessor> classProcessors = getProcessor(ClassProcessor.class);

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
                        classProcessors.forEach(classProcessor -> classProcessor.beforeCreate(klass, instance));
                        all.put(klass, instance);
                        classProcessors.forEach(classProcessor -> classProcessor.afterCreate(klass, instance));
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                return (T) all.get(klass);
            }

            @Override
            public <T> ArrayList<T> getProcessor(Class<?> type) {
                return ClassManager.this.getProcessor(type);
            }

            @Override
            public void after() {
                ArrayList<AutowiredProcessor> processors = getProcessor(AutowiredProcessor.class);
                all.forEach((klass, instance) -> {
                    for (Field field : klass.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (klass.isAnnotationPresent(Autowired.class) || field.isAnnotationPresent(Autowired.class)) {
                            try {
                                for (AutowiredProcessor postProcessor : processors) {
                                    postProcessor.beforePut(field, instance);
                                }
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
                                for (AutowiredProcessor autowiredProcessor : processors) {
                                    autowiredProcessor.afterPut(field, instance);
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

    private <T> ArrayList<T> getProcessor(Class<?> type) {
        return all.keySet().stream().filter(klass -> klass.isAnnotationPresent(Processor.class)).filter(type::isAssignableFrom).map(klass -> (T) all.get(klass)).collect(Collectors.toCollection(ArrayList::new));
    }
}
