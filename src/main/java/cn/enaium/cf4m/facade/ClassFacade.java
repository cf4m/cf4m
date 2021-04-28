package cn.enaium.cf4m.facade;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.CF4MBootstrap;
import cn.enaium.cf4m.ICF4M;
import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.Service;
import cn.enaium.cf4m.annotation.Scan;
import cn.enaium.cf4m.configuration.Configuration;
import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.plugin.Plugin;
import cn.enaium.cf4m.plugin.PluginBean;
import cn.enaium.cf4m.plugin.PluginInitialize;
import cn.enaium.cf4m.provider.*;
import cn.enaium.cf4m.service.*;
import cn.enaium.cf4m.plugin.PluginLoader;
import cn.enaium.cf4m.struct.Pair;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
@SuppressWarnings({"UnstableApiUsage", "unchecked"})
public final class ClassFacade {

    public final ClassContainer classContainer;
    public final Configuration configuration;
    private final ArrayList<PluginBean<PluginInitialize>> pluginInitializes = PluginLoader.loadPlugin(PluginInitialize.class);
    private final HashMap<Class<?>, Object> all = new HashMap<>();

    public ClassFacade(Class<?> mainClass) {
        List<String> allClassName = getAllClassName(mainClass.getClassLoader());

        configuration = new Configuration(mainClass.getClassLoader());

        final List<Pair<ClassLoader, String>> scan = new ArrayList<>();

        pluginInitializes.forEach(pluginInitialize -> {
            scan.add(new Pair<>(pluginInitialize.getClass().getClassLoader(), pluginInitialize.getInstance().getClass().getPackage().getName()));
        });

        scan.add(new Pair<>(mainClass.getClassLoader(), mainClass.getPackage().getName()));
        if (mainClass.isAnnotationPresent(Scan.class)) {
            for (String s : mainClass.getAnnotation(Scan.class).value()) {
                scan.add(new Pair<>(mainClass.getClassLoader(), s));
            }
        }

        for (String className : allClassName) {
            for (Pair<ClassLoader, String> s : scan) {
                if (className.startsWith(s.getValue())) {
                    try {
                        Class<?> klass = s.getKey().loadClass(className);
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
            public <T> T create(Class<T> klass, Object instance) {
                if (all.get(klass) == null) {
                    classServices.forEach(classService -> classService.beforeCreate(klass, instance));
                    all.put(klass, instance);
                    classServices.forEach(classService -> classService.afterCreate(klass, instance));
                }
                autowired();
                return (T) all.get(klass);
            }

            @Override
            public <T> T create(Class<T> klass) {
                try {
                    return create(klass, klass.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return (T) all.get(klass);
            }

            @Override
            public <T> ArrayList<T> getService(Class<T> type) {
                return ClassFacade.this.getService(type);
            }
        };
    }

    public void after() {
        initializePlugin();
        autowired();
    }

    private void initializePlugin() {
        if (!pluginInitializes.isEmpty()) {
            System.out.println("Loaded " + pluginInitializes.size() + " Plugin");
        }

        pluginInitializes.forEach(it -> {
            System.out.println(it.getName()
                    + " | " + it.getDescription()
                    + " | " + it.getVersion()
                    + " | " + it.getAuthor());
            it.getInstance().initialize(new Plugin() {
                @Override
                public Properties getConfiguration() {
                    return configuration.properties;
                }
            });
        });
    }

    private void autowired() {
        all.forEach((klass, instance) -> {

            if (instance == null) {
                return;
            }

            for (Field field : klass.getDeclaredFields()) {
                field.setAccessible(true);
                if (!klass.isAnnotationPresent(Autowired.class) && !field.isAnnotationPresent(Autowired.class)) {
                    continue;
                }

                try {
                    getService(AutowiredService.class).forEach(postProcessor -> postProcessor.beforePut(field, instance));
                    if (all.get(field.getType()) != null) {
                        field.set(instance, all.get(field.getType()));
                    } else if (field.getType().equals(ModuleProvider.class) && all.get(ModuleContainer.class) != null) {
                        field.set(instance, ((ModuleContainer) all.get(ModuleContainer.class)).getByInstance(instance));
                    } else if (field.getType().equals(CommandProvider.class) && all.get(CommandContainer.class) != null) {
                        field.set(instance, ((CommandContainer) all.get(CommandContainer.class)).getByInstance(instance));
                    } else if (field.getType().equals(ConfigProvider.class) && all.get(ConfigContainer.class) != null) {
                        field.set(instance, ((ConfigContainer) all.get(ConfigContainer.class)).getByInstance(instance));
                    }
                    getService(AutowiredService.class).forEach(autowiredService -> autowiredService.afterPut(field, instance));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
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
