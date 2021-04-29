package cn.enaium.cf4m.service;

import java.lang.reflect.Method;

/**
 * @author Enaium
 */
public interface EventService {
    /**
     * Before event post
     *
     * @param method   target
     * @param instance object
     */
    default void beforePost(Method method, Object instance) {

    }

    /**
     * After event post
     *
     * @param method   target
     * @param instance object
     */
    default void afterPost(Method method, Object instance) {

    }

    /**
     * Before event register
     *
     * @param instance object
     */
    default void beforeRegister(Object instance) {

    }

    /**
     * After event register
     *
     * @param instance object
     */
    default void afterRegister(Object instance) {

    }

    /**
     * Before event unregister
     *
     * @param instance object
     */
    default void beforeUnregister(Object instance) {

    }

    /**
     * After event unregister
     *
     * @param instance object
     */
    default void afterUnregister(Object instance) {

    }
}
