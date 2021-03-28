package cn.enaium.cf4m.provider;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface SettingProvider extends Provider {
    <T> T getSetting();
}
