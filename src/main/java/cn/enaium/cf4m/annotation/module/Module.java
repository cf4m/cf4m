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

package cn.enaium.cf4m.annotation.module;

import cn.enaium.cf4m.provider.ModuleProvider;

import java.lang.annotation.*;

/**
 * Add this annotation to the class to that this class is a module
 *
 * @author Enaium
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {
    /**
     * {@link ModuleProvider#getName()}
     *
     * @return module name
     */
    String value() default "";

    /**
     * {@link ModuleProvider#getKey()}
     *
     * @return module key
     */
    int key() default 0;

    /**
     * {@link ModuleProvider#getType()}
     *
     * @return module type
     */
    String type() default "UNKNOWN";

    /**
     * {@link ModuleProvider#getDescription()}
     *
     * @return module description
     */
    String description() default "";
}
