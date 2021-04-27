package cn.enaium.cf4m.plugin;

import cn.enaium.cf4m.container.ClassContainer;

import java.util.Properties;

/**
 * @author Enaium
 */
@FunctionalInterface
public interface PluginInitialize {
    void initialize(Properties configuration, ClassContainer classContainer);
}
