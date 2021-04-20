package cn.enaium.cf4m.processor;

import cn.enaium.cf4m.provider.ConfigProvider;

/**
 * @author Enaium
 */
public interface ConfigProcessor {
    /**
     * Process before config load
     *
     * @param configProvider config
     */
    default void beforeLoad(ConfigProvider configProvider) {

    }

    /**
     * Process before config save
     *
     * @param configProvider config
     */
    default void beforeSave(ConfigProvider configProvider) {

    }


    /**
     * Process after config load
     *
     * @param configProvider config
     */
    default void afterLoad(ConfigProvider configProvider) {

    }

    /**
     * Process after config save
     *
     * @param configProvider config
     */
    default void afterSave(ConfigProvider configProvider) {

    }
}
