package cn.enaium.cf4m.container;

import cn.enaium.cf4m.provider.ConfigProvider;

import java.util.ArrayList;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface ConfigContainer {
    ArrayList<ConfigProvider> getAll();

    ConfigProvider getByName(String name);

    ConfigProvider getByInstance(Object instance);

    void load();

    void save();
}
