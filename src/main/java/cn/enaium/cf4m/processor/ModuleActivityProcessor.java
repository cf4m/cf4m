package cn.enaium.cf4m.processor;

import cn.enaium.cf4m.provider.ModuleProvider;

/**
 * @author Enaium
 */
public interface ModuleActivityProcessor {
    default void beforeEnable(ModuleProvider moduleProvider) {

    }

    default void beforeDisable(ModuleProvider moduleProvider) {

    }


    default void afterEnable(ModuleProvider moduleProvider) {

    }

    default void afterDisable(ModuleProvider moduleProvider) {

    }
}
