package cn.enaium.cf4m.service;

import cn.enaium.cf4m.provider.ModuleProvider;

/**
 * @author Enaium
 */
public interface ModuleService {
    /**
     * Process before module enable
     *
     * @param moduleProvider module
     */
    default void beforeEnable(ModuleProvider moduleProvider) {

    }

    /**
     * Process before module disable
     *
     * @param moduleProvider module
     */
    default void beforeDisable(ModuleProvider moduleProvider) {

    }

    /**
     * Process after module enable
     *
     * @param moduleProvider module
     */
    default void afterEnable(ModuleProvider moduleProvider) {

    }

    /**
     * Process after module disable
     *
     * @param moduleProvider module
     */
    default void afterDisable(ModuleProvider moduleProvider) {

    }
}
