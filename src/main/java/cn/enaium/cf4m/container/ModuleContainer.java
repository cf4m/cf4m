package cn.enaium.cf4m.container;

import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.provider.ModuleProvider;

import java.util.ArrayList;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface ModuleContainer {
    /**
     * NotNull
     *
     * @return module list
     */
    ArrayList<ModuleProvider> getAll();

    /**
     * NotNull
     *
     * @param category module category
     * @return module
     */
    ArrayList<ModuleProvider> getAllByCategory(Category category);

    /**
     * Nullable
     *
     * @param name module name
     * @return module
     */
    ModuleProvider getByName(String name);

    /**
     * Nullable
     *
     * @param instance module name
     * @return module
     */
    ModuleProvider getByInstance(Object instance);

    void onKey(int key);
}
