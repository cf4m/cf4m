package cn.enaium.cf4m.builder;

import cn.enaium.cf4m.annotation.Configuration;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.ClassContainer;

/**
 * @author Enaium
 */
public final class ConfigurationBuilder {

    public IConfiguration configuration = new IConfiguration() {
    };

    public ConfigurationBuilder(ClassContainer classContainer) {
        for (Class<?> klass : classContainer.getAll()) {
            if (klass.isAnnotationPresent(Configuration.class)) {
                configuration = classContainer.create(klass);
            }
        }
    }
}
