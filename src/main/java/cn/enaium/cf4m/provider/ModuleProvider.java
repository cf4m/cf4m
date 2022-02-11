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

package cn.enaium.cf4m.provider;

import cn.enaium.cf4m.container.SettingContainer;
/**
 * @author Enaium
 */
public interface ModuleProvider extends Provider {
    /**
     * get module enable
     *
     * @return module enable
     */
    boolean getEnable();

    /**
     * enable or disable module
     */
    void enable();

    /**
     * NotNull
     *
     * @return module keyboard
     */
    int getKey();

    /**
     * set module keyboard
     *
     * @param key keyboard
     */
    void setKey(int key);

    /**
     * @return module type
     */
    String getType();

    /**
     * Nullable
     *
     * @param klass class
     * @param <T>   extend class
     * @return extend instance
     */
    <T> T getExtend(Class<T> klass);

    /**
     * @return module setting
     */
    SettingContainer getSetting();
}
