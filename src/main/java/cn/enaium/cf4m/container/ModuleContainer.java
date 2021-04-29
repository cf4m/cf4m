package cn.enaium.cf4m.container;

import cn.enaium.cf4m.provider.ModuleProvider;

import java.util.ArrayList;

/**
 * @author Enaium
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
     * @param type module type
     * @return module
     */
    ArrayList<ModuleProvider> getAllByType(String type);

    /**
     * NotNull
     *
     * @return module type list
     */
    ArrayList<String> getAllType();

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

    /**
     * enable all module
     *
     * @param key keyboard
     */
    void onKey(int key);
}
