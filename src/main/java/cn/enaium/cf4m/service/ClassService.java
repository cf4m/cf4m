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

package cn.enaium.cf4m.service;

import java.util.Map;

/**
 * @author Enaium
 */
public interface ClassService {
    /**
     * Before instance put to Class container
     *
     * @param klass    class
     * @param instance class instance
     */
    default void beforeCreate(Class<?> klass, Object instance) {

    }

    /**
     * After instance put to Class container
     *
     * @param klass    class
     * @param instance class instance
     */
    default void afterCreate(Class<?> klass, Object instance) {

    }

    /**
     * @since 1.11.0
     */
    default void afterProcessor() {

    }
}
