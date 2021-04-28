package cn.enaium.cf4m.plugin;

import java.util.Properties;

/**
 * @author Enaium
 */
@FunctionalInterface
public interface PluginInitialize {
    void initialize(Properties configuration);
}
