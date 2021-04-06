package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.annotation.Configuration;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.ClassContainer;

/**
 * @author Enaium
 */
public class ConfigurationManager {

    public IConfiguration configuration = new IConfiguration() {
    };

    public ConfigurationManager(ClassContainer classContainer) {
        for (Class<?> klass : classContainer.getAll()) {
            if (klass.isAnnotationPresent(Configuration.class)) {
                configuration = classContainer.create(klass);
            }
        }
    }
}
