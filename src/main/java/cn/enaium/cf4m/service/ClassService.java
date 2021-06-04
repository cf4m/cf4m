package cn.enaium.cf4m.service;

/**
 * @author Enaium
 */
public interface ClassService {
    /**
     * Before instance put to Class container
     *
     * @param klass    class
     * @param instance class instance
     */
    default void beforeCreate(Class<?> klass, Object instance) {

    }

    /**
     * After instance put to Class container
     *
     * @param klass    class
     * @param instance class instance
     */
    default void afterCreate(Class<?> klass, Object instance) {

    }
}
