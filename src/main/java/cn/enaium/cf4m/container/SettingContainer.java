package cn.enaium.cf4m.container;

import cn.enaium.cf4m.provider.SettingProvider;

import java.util.ArrayList;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface SettingContainer {
    ArrayList<SettingProvider> getAll();

    SettingProvider getByName(String name);
}
