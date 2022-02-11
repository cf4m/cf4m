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

import java.lang.reflect.Method;

/**
 * @author Enaium
 */
public interface EventService {
    /**
     * Before event post
     *
     * @param method   target
     * @param instance object
     */
    default void beforePost(Method method, Object instance) {

    }

    /**
     * After event post
     *
     * @param method   target
     * @param instance object
     */
    default void afterPost(Method method, Object instance) {

    }

    /**
     * Before event register
     *
     * @param instance object
     */
    default void beforeRegister(Object instance) {

    }

    /**
     * After event register
     *
     * @param instance object
     */
    default void afterRegister(Object instance) {

    }

    /**
     * Before event unregister
     *
     * @param instance object
     */
    default void beforeUnregister(Object instance) {

    }

    /**
     * After event unregister
     *
     * @param instance object
     */
    default void afterUnregister(Object instance) {

    }
}
