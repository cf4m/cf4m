package cn.enaium.cf4m.configuration;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.event.Listener;
import cn.enaium.cf4m.provider.ModuleProvider;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface IModuleConfiguration {
    /**
     * Before the module is enable.
     *
     * @param module module.
     */
    default void enable(Object module) {
    }

    /**
     * Before the module is disable.
     *
     * @param module module.
     */
    default void disable(Object module) {
    }
}
