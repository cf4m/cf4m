package cn.enaium.cf4m.plugin;

import cn.enaium.cf4m.container.ConfigurationContainer;

/**
 * @author Enaium
 */
public interface Plugin {
    /**
     * configuration
     *
     * @return configuration
     */
    ConfigurationContainer getConfiguration();
}
