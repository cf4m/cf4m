package cn.enaium.cf4m.facade;

import cn.enaium.cf4m.annotation.Configuration;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.ClassContainer;

/**
 * @author Enaium
 */
public final class ConfigurationFacade {

    public IConfiguration configuration = new IConfiguration() {
    };

    public ConfigurationFacade(ClassContainer classContainer) {
        for (Class<?> klass : classContainer.getAll()) {
            if (klass.isAnnotationPresent(Configuration.class)) {
                configuration = (IConfiguration) classContainer.create(klass);
            }
        }
    }
}
