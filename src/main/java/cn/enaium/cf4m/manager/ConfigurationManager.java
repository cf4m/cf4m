package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.annotation.Configuration;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.ClassContainer;

import java.util.List;

/**
 * @author Enaium
 */
public class ConfigurationManager {

    public IConfiguration configuration = new IConfiguration() {
    };

    public ConfigurationManager(ClassContainer classContainer) {
        for (Class<?> klass : classContainer.getClasses()) {
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
