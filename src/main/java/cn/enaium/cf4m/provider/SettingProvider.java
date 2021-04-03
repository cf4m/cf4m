package cn.enaium.cf4m.provider;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface SettingProvider extends Provider {
    /**
     * Nullable
     *
     * @param <T> setting class
     * @return setting instance
     */
    <T> T getSetting();
}
