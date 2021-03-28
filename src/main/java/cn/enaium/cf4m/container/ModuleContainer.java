package cn.enaium.cf4m.container;

import cn.enaium.cf4m.provider.ModuleProvider;

import java.util.ArrayList;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface ModuleContainer {
    ArrayList<ModuleProvider> getAll();

    ModuleProvider getByName(String name);
}
