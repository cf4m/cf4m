package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.annotation.Configuration;
import cn.enaium.cf4m.configuration.IConfiguration;

import java.util.List;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class ConfigurationManager {

    public IConfiguration configuration = new IConfiguration() {
    };

    public ConfigurationManager(List<Class<?>> classes) {
        for (Class<?> klass : classes) {
            if (klass.isAnnotationPresent(Configuration.class)) {
                try {
                    configuration = (IConfiguration) klass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
