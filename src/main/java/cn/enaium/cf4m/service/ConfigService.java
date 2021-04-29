package cn.enaium.cf4m.service;

import cn.enaium.cf4m.provider.ConfigProvider;

/**
 * @author Enaium
 */
public interface ConfigService {
    /**
     * Before config load
     *
     * @param configProvider config
     */
    default void beforeLoad(ConfigProvider configProvider) {

    }

    /**
     * Before config save
     *
     * @param configProvider config
     */
    default void beforeSave(ConfigProvider configProvider) {

    }


    /**
     * After config load
     *
     * @param configProvider config
     */
    default void afterLoad(ConfigProvider configProvider) {

    }

    /**
     * After config save
     *
     * @param configProvider config
     */
    default void afterSave(ConfigProvider configProvider) {

    }
}
