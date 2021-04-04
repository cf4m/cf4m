package cn.enaium.cf4m.container;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface EventContainer {
    
    /**
     * Register all event
     *
     * @param instance Class instance
     */
    void register(Object instance);

    /**
     * Unregister all event
     *
     * @param instance Class instance
     */
    void unregister(Object instance);

    void call(Object instance);
}
