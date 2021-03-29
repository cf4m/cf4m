package cn.enaium.cf4m.container;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.provider.ModuleProvider;

import java.util.ArrayList;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface ModuleContainer {
    ArrayList<ModuleProvider> getAll();

    ArrayList<ModuleProvider> getAllByCategory(Category category);

    ModuleProvider getByName(String name);

    ModuleProvider getByInstance(Object instance);

    void onKey(int key);
}
