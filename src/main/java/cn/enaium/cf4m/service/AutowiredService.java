package cn.enaium.cf4m.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Enaium
 */
public interface AutowiredService {
    /**
     * Before put field
     *
     * @param field    field
     * @param instance object
     */
    default void beforePut(Field field, Object instance) {

    }

    /**
     * Before set field
     *
     * @param method   field
     * @param instance object
     */
    default void beforeSet(Method method, Object instance) {

    }

    /**
     * After put field
     *
     * @param field    field
     * @param instance object
     */
    default void afterPut(Field field, Object instance) {

    }

    /**
     * After set field
     *
     * @param method   method
     * @param instance object
     */
    default void afterSet(Method method, Object instance) {

    }
}
