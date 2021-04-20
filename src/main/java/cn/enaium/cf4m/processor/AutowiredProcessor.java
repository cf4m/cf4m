package cn.enaium.cf4m.processor;

import java.lang.reflect.Field;

/**
 * @author Enaium
 */
public interface AutowiredProcessor {
    /**
     * Process before put field
     *
     * @param field    field
     * @param instance object
     * @throws IllegalAccessException IllegalAccessException
     */
    default void beforePut(Field field, Object instance) throws IllegalAccessException {

    }

    /**
     * Process after put field
     *
     * @param field    field
     * @param instance object
     * @throws IllegalAccessException IllegalAccessException
     */
    default void afterPut(Field field, Object instance) throws IllegalAccessException {

    }
}
