package cn.enaium.cf4m.plugin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * @author Enaium
 */
public final class PluginLoader {

    private static final String CF4M_PLUGIN = "cf4m.plugin";

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
    public static <T> ArrayList<T> loadPlugin(Class<T> klass) {
        ArrayList<T> instance = new ArrayList<>();
        try {
            for (Properties properties : load(klass.getClassLoader())) {
                if (properties.containsKey(CF4M_PLUGIN)) {
                    instance.add((T) klass.getClassLoader().loadClass((String) properties.get(CF4M_PLUGIN)).newInstance());
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
