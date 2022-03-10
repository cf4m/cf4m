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
import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.Service;
import cn.enaium.cf4m.service.AutowiredService;
import cn.enaium.cf4m.service.ClassService;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Enaium
 * @since 1.11.0
 */
@Service
public class AutowiredProcessor implements ClassService {
    @Override
    public void afterCreate(Class<?> klass, Object instance) {
        autowired(klass, instance);
    }

    @Override
    public void afterProcessor() {
        CF4M.CLASS.getAll().forEach(this::autowired);
    }

    private void autowired(Class<?> klass, Object instance) {
        if (instance == null) {
            return;
        }

        for (Field field : klass.getDeclaredFields()) {
            field.setAccessible(true);
            if (!klass.isAnnotationPresent(Autowired.class) && !field.isAnnotationPresent(Autowired.class)) {
                continue;
            }

            try {
                CF4M.CLASS.getService(AutowiredService.class).forEach(postProcessor -> postProcessor.beforePut(field, instance));
                if (field.get(instance) == null) {
                    field.set(instance, CF4M.CLASS.getAll().get(field.getType()));
                }
                CF4M.CLASS.getService(AutowiredService.class).forEach(autowiredService -> autowiredService.afterPut(field, instance));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
