package cn.enaium.cf4m.service;

import java.lang.reflect.Method;

/**
 * @author Enaium
 */
public interface EventService {
    /**
     * Process before event post
     *
     * @param method   target
     * @param instance object
     */
    default void beforePost(Method method, Object instance) {

    }

    /**
     * Process after event post
     *
     * @param method   target
     * @param instance object
     */
    default void afterPost(Method method, Object instance) {

    }

    /**
     * Process before event register
     *
     * @param instance object
     */
    default void beforeRegister(Object instance) {

    }

    /**
     * Process after event register
     *
     * @param instance object
     */
    default void afterRegister(Object instance) {

    }

    /**
     * Process before event unregister
     *
     * @param instance object
     */
    default void beforeUnregister(Object instance) {

    }

    /**
     * Process after event unregister
     *
     * @param instance object
     */
    default void afterUnregister(Object instance) {

    }
}
