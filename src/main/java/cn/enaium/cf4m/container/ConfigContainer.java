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

import cn.enaium.cf4m.provider.ConfigProvider;

import java.util.ArrayList;

/**
 * @author Enaium
 */
public interface ConfigContainer {

    /**
     * NotNull
     *
     * @return config list
     */
    ArrayList<ConfigProvider> getAll();

    /**
     * Nullable
     *
     * @param name config name
     * @return config
     */
    ConfigProvider get(String name);

    /**
     * Nullable
     *
     * @param instance config
     * @return config
     */
    ConfigProvider get(Object instance);

    /**
     * @param klass class
     * @param <T>   config
     * @return config
     */
    <T> ConfigProvider get(Class<T> klass);

    /**
     * load config
     */
    void load();

    /**
     * save config
     */
    void save();
}
