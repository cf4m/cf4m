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

import java.util.ArrayList;

/**
 * @author Enaium
 */
public interface ClassContainer {
    /**
     * get all the classes in the container
     *
     * @return class list
     */
    ArrayList<Class<?>> getAll();

    <T> T put(Class<T> klass, Object instance);

    /**
     * put the classes in the container
     *
     * @param klass    klass
     * @param instance object
     * @param <T>      class
     * @return class instance
     */
    <T> T create(Class<T> klass, Object instance);

    /**
     * put the classes in the container
     *
     * @param klass class
     * @param <T>   class
     * @return class instance
     */
    <T> T create(Class<T> klass);

    /**
     * get service
     *
     * @param type Service
     * @param <T>  Service
     * @return Service list
     */
    <T> ArrayList<T> getService(Class<T> type);
}
