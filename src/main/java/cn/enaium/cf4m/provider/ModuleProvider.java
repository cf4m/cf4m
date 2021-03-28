package cn.enaium.cf4m.provider;

import cn.enaium.cf4m.container.SettingContainer;
import cn.enaium.cf4m.module.Category;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface ModuleProvider extends Provider {
    /**
     * @return module enable
     */
    boolean getEnable();

    /**
     * enable or disable module
     */
    void enable();

    /**
     * @return module keyboard
     */
    int getKey();

    /**
     * @param key module keyboard
     */
    void setKey(int key);

    /**
     * @return module category
     */
    Category getCategory();

    /**
     * @param <T> extend
     * @return module extend
     */
    <T> T getExtend();

    SettingContainer getSetting();
}
