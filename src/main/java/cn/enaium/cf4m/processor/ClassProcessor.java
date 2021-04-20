package cn.enaium.cf4m.processor;

/**
 * @author Enaium
 */
public interface ClassProcessor {
    /**
     * Process before instance put to Class container
     *
     * @param klass    class
     * @param instance class instance
     */
    default void beforeCreate(Class<?> klass, Object instance) {

    }

    /**
     * Process after instance put to Class container
     *
     * @param klass    class
     * @param instance class instance
     */
    default void afterCreate(Class<?> klass, Object instance) {

    }
}
