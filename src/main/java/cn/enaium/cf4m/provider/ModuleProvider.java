package cn.enaium.cf4m.provider;

import cn.enaium.cf4m.container.SettingContainer;
/**
 * @author Enaium
 */
public interface ModuleProvider extends Provider {
    /**
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
     * @return module type
     */
    String getType();

    /**
     * Nullable
     *
     * @param klass class
     * @param <T>   extend class
     * @return extend instance
     */
    <T> T getExtend(Class<T> klass);

    /**
     * @return module setting
     */
    SettingContainer getSetting();
}
