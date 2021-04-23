package cn.enaium.cf4m.service;

import java.util.List;

/**
 * @author Enaium
 */
public interface ClassService {

    default void beforeScan(List<String> scan) {

    }

    default void afterScan(List<String> scan) {

    }

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