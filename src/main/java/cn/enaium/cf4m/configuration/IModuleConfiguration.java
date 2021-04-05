package cn.enaium.cf4m.configuration;

/**
 * Project: cf4m
 *
 * @author Enaium
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
