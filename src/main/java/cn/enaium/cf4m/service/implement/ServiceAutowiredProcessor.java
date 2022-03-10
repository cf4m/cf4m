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
import cn.enaium.cf4m.service.AutowiredService;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 * @since 1.11.0
 */
@Service
public class ServiceAutowiredProcessor implements AutowiredService {
    @Override
    public void beforePut(Field field, Object instance) {
        try {

            Class<?> type;
            if (!isList(field)) {
                type = field.getType();
            } else {
                type = instance.getClass().getClassLoader().loadClass(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName());
            }

            ArrayList<?> service = CF4M.CLASS.getService(type);
            if (field.get(instance) == null && !service.isEmpty()) {
                field.set(instance, isList(field) ? service : service.get(0));
            }


        } catch (IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isList(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }
}
