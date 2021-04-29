package cn.enaium.cf4m.container;

/**
 * @author Enaium
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

    /**
     * post all event
     *
     * @param instance event
     */
    void post(Object instance);
}
