/**
 * Copyright (C) 2020 Enaium
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.cf4m.plugin;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author Enaium
 */
@SuppressWarnings("deprecation")
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
