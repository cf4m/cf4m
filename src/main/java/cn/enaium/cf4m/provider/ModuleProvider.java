package cn.enaium.cf4m.provider;

import cn.enaium.cf4m.container.SettingContainer;

/**
 * @author Enaium
 */
public interface ModuleProvider extends Provider {
    /**
     * NotNull
     * <p>
     * get module enable
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
     * set module keyboard
     *
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
