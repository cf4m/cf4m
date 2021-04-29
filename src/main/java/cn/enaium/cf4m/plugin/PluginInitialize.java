package cn.enaium.cf4m.plugin;

/**
 * @author Enaium
 */
@FunctionalInterface
public interface PluginInitialize {
    /**
     * plugin initialize
     *
     * @param plugin plugin
     */
    void initialize(Plugin plugin);
}
