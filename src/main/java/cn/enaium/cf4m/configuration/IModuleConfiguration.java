package cn.enaium.cf4m.configuration;

import cn.enaium.cf4m.provider.ModuleProvider;

/**
 * @author Enaium
 */
public interface IModuleConfiguration {
    /**
     * Before the module is enable.
     *
     * @param module module.
     */
    default void enable(ModuleProvider module) {
    }

    /**
     * Before the module is disable.
     *
     * @param module module.
     */
    default void disable(ModuleProvider module) {
    }
}
