package cn.enaium.cf4m.plugin;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author Enaium
 */
public final class PluginLoader {
    private static final String PLUGIN = "plugin";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String VERSION = "version";
    private static final String AUTHOR = "author";

    private static List<Properties> load(ClassLoader classLoader) {
        List<Properties> configList = new ArrayList<>();
        try {
            Enumeration<URL> resources = classLoader.getResources("cf4m.plugin.properties");
            while (resources.hasMoreElements()) {
                Properties properties = new Properties();
                properties.load(resources.nextElement().openStream());
                configList.add(properties);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configList;
    }


    @SuppressWarnings("unchecked")
    public static <T> ArrayList<PluginBean<T>> loadPlugin(Class<T> klass) {
        ArrayList<PluginBean<T>> instance = new ArrayList<>();
        try {
            for (Properties properties : load(klass.getClassLoader())) {
                if (properties.containsKey(PLUGIN)) {
                    Class<?> plugin = klass.getClassLoader().loadClass(properties.getProperty(PLUGIN));
                    instance.add(new PluginBean<>((T) plugin.newInstance(), properties.getProperty(NAME), properties.getProperty(DESCRIPTION), properties.getProperty(VERSION), properties.getProperty(AUTHOR)));
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }


}
