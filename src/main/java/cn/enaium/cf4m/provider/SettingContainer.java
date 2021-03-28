package cn.enaium.cf4m.provider;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface SettingContainer {
    <T> T getByName(String name);
}
