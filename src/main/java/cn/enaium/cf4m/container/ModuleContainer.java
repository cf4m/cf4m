/**
 * Copyright (C) 2020 Enaium
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.cf4m.container;

import cn.enaium.cf4m.provider.ModuleProvider;
import cn.enaium.cf4m.struct.AnonymousClass;

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
    ModuleProvider get(String name);

    /**
     * @param klass class
     * @param <T>   module
     * @return module
     */
    <T> ModuleProvider get(Class<T> klass);

    /**
     * Nullable
     *
     * @param instance module name
     * @return module
     */
    ModuleProvider get(Object instance);

    /**
     * enable all module
     *
     * @param key keyboard
     */
    void onKey(int key);

    void create(AnonymousClass anonymousClass);
}
