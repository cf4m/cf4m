package cn.enaium.cf4m.configuration;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface IConfigConfiguration {
    /**
     * @return enable or disable config.
     */
    default boolean enable() {
        return true;
    }
}
