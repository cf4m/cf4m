package cn.enaium.cf4m.facade;

import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.Service;
import cn.enaium.cf4m.annotation.Scan;
import cn.enaium.cf4m.configuration.Configuration;
import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.plugin.PluginBean;
import cn.enaium.cf4m.plugin.PluginInitialize;
import cn.enaium.cf4m.service.*;
import cn.enaium.cf4m.plugin.PluginLoader;
import cn.enaium.cf4m.struct.Pair;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
@SuppressWarnings({"unchecked"})
public final class ClassFacade {

    public final ClassContainer classContainer;
    public final Configuration configuration;
    private final ArrayList<PluginBean<PluginInitialize>> pluginInitializes = PluginLoader.loadPlugin(PluginInitialize.class);
    private final HashMap<Class<?>, Object> all = new HashMap<>();


    public ClassFacade(Class<?> mainClass) {
        LinkedHashSet<String> allClassName = getAllClassName(mainClass.getClassLoader());

        configuration = new Configuration(mainClass.getClassLoader());

        final List<Pair<ClassLoader, String>> scan = new ArrayList<>();

        pluginInitializes.forEach(pluginInitialize -> scan.add(new Pair<>(pluginInitialize.getClass().getClassLoader(), pluginInitialize.getInstance().getClass().getPackage().getName())));

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

            pluginInitializes.forEach(it -> {
                System.out.println(it.getName()
                        + " | " + it.getDescription()
                        + " | " + it.getVersion()
                        + " | " + it.getAuthor());
                it.getInstance().initialize(() -> configuration.properties);
            });
        }
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
                    if (all.get(field.getType()) != null && field.get(instance) == null) {
                        field.set(instance, all.get(field.getType()));
                    }
                    getService(AutowiredService.class).forEach(autowiredService -> autowiredService.afterPut(field, instance));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private <T> ArrayList<T> getService(Class<T> type) {
        return all.keySet().
                stream().filter(klass -> klass.isAnnotationPresent(Service.class)).
                filter(type::isAssignableFrom).map(klass -> (T) all.get(klass)).
                collect(Collectors.toCollection(ArrayList::new));
    }

    private LinkedHashSet<String> getAllClassName(ClassLoader classLoader) {

        List<ClassLoader> classLoaders = new ArrayList<>();
        findClassLoader(classLoader, classLoaders);

        LinkedHashSet<String> classNames = new LinkedHashSet<>();

        for (ClassLoader cl : classLoaders) {
            if (!(cl instanceof URLClassLoader)) {
                continue;
            }

            URLClassLoader urlClassLoader = (URLClassLoader) cl;
            for (URL url : urlClassLoader.getURLs()) {
                try {
                    if (url.toURI().getScheme().equals("file")) {
                        File file = new File(url.toURI());
                        if (file.exists()) {
                            try {
                                if (file.isDirectory()) {
                                    List<File> files = new ArrayList<>();
                                    listFiles(file, files);
                                    for (File listFile : files) {
                                        String classFile = listFile.getAbsolutePath().replace(file.getAbsolutePath(), "").replace(".class", "");
                                        if (classFile.startsWith(File.separator)) {
                                            classFile = classFile.substring(1);
                                        }
                                        classNames.add(classFile.replace(File.separator, "."));
                                    }
                                } else {
                                    JarFile jarFile = new JarFile(file);
                                    if (url.getFile().endsWith(".jar")) {
                                        Enumeration<JarEntry> entries = jarFile.entries();
                                        while (entries.hasMoreElements()) {
                                            JarEntry jarEntry = entries.nextElement();

                                            if (jarEntry.isDirectory() || jarEntry.getName().equals(JarFile.MANIFEST_NAME)) {
                                                continue;
                                            }

                                            if (jarEntry.getName().endsWith(".class")) {
                                                classNames.add(jarEntry.getName().replace(".class", "").replace("/", "."));
                                            }
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }

        return classNames;
    }

    private void findClassLoader(ClassLoader classLoader, List<ClassLoader> classLoaders) {
        classLoaders.add(classLoader);
        if (classLoader.getParent() != null) {
            findClassLoader(classLoader.getParent(), classLoaders);
        }
    }

    private void listFiles(File file, List<File> list) {
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return;
        }

        for (File listFile : listFiles) {
            if (listFile.isFile() && listFile.getName().endsWith(".class")) {
                list.add(listFile);
            } else if (file.isDirectory()) {
                listFiles(listFile.getAbsoluteFile(), list);
            }
        }
    }
}
