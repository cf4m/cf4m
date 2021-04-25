package cn.enaium.cf4m.test;

import cn.enaium.cf4m.plugin.Plugin;

/**
 * @author Enaium
 */
public class PluginTest implements Plugin {
    @Override
    public String getName() {
        return "Test";
    }

    @Override
    public String getDescription() {
        return "This is starter test";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "Enaium";
    }
}
