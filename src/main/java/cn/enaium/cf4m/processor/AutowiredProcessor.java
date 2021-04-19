package cn.enaium.cf4m.processor;

import java.lang.reflect.Field;

/**
 * @author Enaium
 */
public interface AutowiredProcessor {
    default void beforeAutowired(Field field, Object instance) throws IllegalAccessException {

    }

    default void afterAutowired(Field field, Object instance) throws IllegalAccessException {

    }
}
