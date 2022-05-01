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

package cn.enaium.cf4m.service.implement;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Service;
import cn.enaium.cf4m.annotation.Value;
import cn.enaium.cf4m.service.ClassService;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Enaium
 * @since 1.11.0
 */
@Service
public class ValueReadProcessor implements ClassService {
    @Override
    public void afterProcessor() {
        for (Map.Entry<Class<?>, Object> classObjectEntry : CF4M.CLASS.getInstance().entrySet()) {
            Class<?> klass = classObjectEntry.getKey();
            Object instance = classObjectEntry.getValue();

            if (instance == null) {
                continue;
            }


            for (Field declaredField : klass.getDeclaredFields()) {
                declaredField.setAccessible(true);
                if (declaredField.isAnnotationPresent(Value.class)) {
                    String value = declaredField.getAnnotation(Value.class).value();
                    Object o = CF4M.CONFIGURATION.getProperties().get(value);
                    try {
                        declaredField.set(instance, o);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
