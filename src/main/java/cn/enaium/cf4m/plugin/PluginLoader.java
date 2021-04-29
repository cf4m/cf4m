package cn.enaium.cf4m.plugin;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author Enaium
 */
public final class PluginLoader {
    private static final String CF4M_PLUGIN = "cf4m.plugin";
    private static final String CF4M_PLUGIN_NAME = "cf4m.plugin.name";
    private static final String CF4M_PLUGIN_DESCRIPTION = "cf4m.plugin.description";
    private static final String CF4M_PLUGIN_VERSION = "cf4m.plugin.version";
    private static final String CF4M_PLUGIN_AUTHOR = "cf4m.plugin.author";

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
                if (properties.containsKey(CF4M_PLUGIN)) {
                    Class<?> plugin = klass.getClassLoader().loadClass(properties.getProperty(CF4M_PLUGIN));
                    instance.add(new PluginBean<>((T) plugin.newInstance(), properties.getProperty(CF4M_PLUGIN_NAME), properties.getProperty(CF4M_PLUGIN_DESCRIPTION), properties.getProperty(CF4M_PLUGIN_VERSION), properties.getProperty(CF4M_PLUGIN_AUTHOR)));
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }


}
