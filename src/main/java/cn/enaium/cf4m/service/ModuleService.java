package cn.enaium.cf4m.service;

import cn.enaium.cf4m.provider.ModuleProvider;

/**
 * @author Enaium
 */
public interface ModuleService {
    /**
     * Before module enable
     *
     * @param moduleProvider module
     */
    default void beforeEnable(ModuleProvider moduleProvider) {

    }

    /**
     * Before module disable
     *
     * @param moduleProvider module
     */
    default void beforeDisable(ModuleProvider moduleProvider) {

    }

    /**
     * After module enable
     *
     * @param moduleProvider module
     */
    default void afterEnable(ModuleProvider moduleProvider) {

    }

    /**
     * After module disable
     *
     * @param moduleProvider module
     */
    default void afterDisable(ModuleProvider moduleProvider) {

    }
}
