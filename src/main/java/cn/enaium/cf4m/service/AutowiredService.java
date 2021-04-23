package cn.enaium.cf4m.service;

import java.lang.reflect.Field;

/**
 * @author Enaium
 */
public interface AutowiredService {
    /**
     * Process before put field
     *
     * @param field    field
     * @param instance object
     */
    default void beforePut(Field field, Object instance) {

    }

    /**
     * Process after put field
     *
     * @param field    field
     * @param instance object
     */
    default void afterPut(Field field, Object instance) {

    }
}
