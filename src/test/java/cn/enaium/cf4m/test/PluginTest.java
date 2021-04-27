package cn.enaium.cf4m.test;

import cn.enaium.cf4m.annotation.Plugin;
import cn.enaium.cf4m.container.ClassContainer;
import cn.enaium.cf4m.plugin.PluginInitialize;

import java.util.Properties;

/**
 * @author Enaium
 */
@Plugin(name = "Test", description = "This is starter test", version = "1.0", author = "Enaium")
public class PluginTest implements PluginInitialize {
    @Override
    public void initialize(Properties configuration, ClassContainer classContainer) {

    }
}
