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

package cn.enaium.cf4m.factory;

import cn.enaium.cf4m.provider.Provider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Enaium
 */
public class ProviderFactory<T extends Provider> {
    private final Map<Object, T> provider = new HashMap<>();

    public void addProvider(Object instance, T provider) {
        getProvider().put(instance, provider);
    }

    public Map<Object, T> getProvider() {
        return provider;
    }
}
