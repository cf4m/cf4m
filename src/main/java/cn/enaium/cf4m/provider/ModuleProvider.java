package cn.enaium.cf4m.provider;

import cn.enaium.cf4m.container.SettingContainer;

/**
 * Project: cf4m
 *
 * @author Enaium
 */
public interface ModuleProvider extends Provider {
    /**
     * NotNull
     *
     * @return module enable
     */
    boolean getEnable();

    /**
     * enable or disable module
     */
    void enable();

    /**
     * NotNull
     *
     * @return module keyboard
     */
    int getKey();

    /**
     * @param key keyboard
     */
    void setKey(int key);

    /**
     * NotNull
     *
     * @return module type
     */
    String getType();

    /**
     * Nullable
     *
     * @param <T> extend class
     * @return extend instance
     */
    <T> T getExtend();

    /**
     * NotNull
     *
     * @return module setting
     */
    SettingContainer getSetting();
}
