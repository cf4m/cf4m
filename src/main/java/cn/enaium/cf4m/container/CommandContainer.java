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

import cn.enaium.cf4m.provider.CommandProvider;
import java.util.ArrayList;

/**
 * @author Enaium
 */
public interface CommandContainer {
    /**
     * NotNull
     *
     * @return command list
     */
    ArrayList<CommandProvider> getAll();

    /**
     * Nullable
     *
     * @param instance command
     * @return command
     */
    @Deprecated
    CommandProvider getByInstance(Object instance);

    CommandProvider get(Object instance);

    /**
     * @param klass class
     * @param <T>   command
     * @return command
     */
    @Deprecated
    <T> CommandProvider getByClass(Class<T> klass);

    <T> CommandProvider get(Class<T> klass);

    /**
     * Nullable
     *
     * @param key command key
     * @return command
     */
    @Deprecated
    CommandProvider getByKey(String key);

    CommandProvider get(String key);

    /**
     * NotNull
     *
     * @param rawMessage message
     * @return Whether the execution is success
     */
    boolean execCommand(String rawMessage);
}
