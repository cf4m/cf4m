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

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.annotation.module.Setting;
import cn.enaium.cf4m.annotation.module.Extend;
import cn.enaium.cf4m.annotation.module.*;
import cn.enaium.cf4m.configuration.NameGeneratorConfiguration;
import cn.enaium.cf4m.container.ModuleContainer;
import cn.enaium.cf4m.service.ModuleService;
import cn.enaium.cf4m.provider.ModuleProvider;
import cn.enaium.cf4m.container.SettingContainer;
import cn.enaium.cf4m.provider.SettingProvider;
import cn.enaium.cf4m.util.StringUtil;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
@SuppressWarnings({"unchecked", "unused"})
public final class ModuleFactory extends ProviderFactory<ModuleProvider> {

    public ModuleFactory() {
        //Find Extend
        Set<Class<?>> extendClasses = new HashSet<>();
        for (Class<?> klass : CF4M.CLASS.getAll().keySet()) {
            if (klass.isAnnotationPresent(Extend.class)) {
                extendClasses.add(klass);
            }
        }

        final Map<Object, Map<Class<?>, Object>> multipleExtend = new HashMap<>();

        //Add Modules
        for (Class<?> klass : CF4M.CLASS.getAll().keySet()) {
            if (klass.isAnnotationPresent(Module.class)) {
                Module module = klass.getAnnotation(Module.class);
                Object moduleInstance = CF4M.CLASS.create(klass);

                Map<Class<?>, Object> extend = new HashMap<>();

                for (Class<?> extendClass : extendClasses) {
                    try {
                        extend.put(extendClass, extendClass.getConstructor().newInstance());
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

                multipleExtend.put(moduleInstance, extend);


                ProviderFactory<SettingProvider> providerFactory = new ProviderFactory<SettingProvider>() {
                    {
                        for (Field field : klass.getDeclaredFields()) {
                            field.setAccessible(true);
                            if (field.isAnnotationPresent(Setting.class)) {

                                Setting setting = field.getAnnotation(Setting.class);

                                try {
                                    addProvider(field.get(moduleInstance), new SettingProvider() {
                                        @Override
                                        public String getName() {

                                            if (StringUtil.isEmpty(setting.value())) {
                                                return CF4M.CONFIGURATION.get(NameGeneratorConfiguration.class).generate(field);
                                            }

                                            return setting.value();
                                        }

                                        @Override
                                        public String getDescription() {
                                            return setting.description();
                                        }

                                        @Override
                                        public <T> T as() {
                                            try {
                                                return (T) field.get(moduleInstance);
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                            return null;
                                        }

                                        @Override
                                        public <T> T setSetting(Object value) {
                                            try {
                                                field.set(moduleInstance, value);
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                            return as();
                                        }

                                        @Override
                                        public <T> T set(Object value) {
                                            return setSetting(value);
                                        }
                                    });
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                };


                SettingContainer settingContainer = new SettingContainer() {
                    @Override
                    public ArrayList<SettingProvider> getAll() {
                        return new ArrayList<>(providerFactory.getProviders().values());
                    }

                    @Override
                    public SettingProvider get(String name) {
                        for (SettingProvider settingProvider : getAll()) {
                            if (settingProvider.getName().equalsIgnoreCase(name)) {
                                return settingProvider;
                            }
                        }
                        return null;
                    }
                };

                ArrayList<ModuleService> processors = CF4M.CLASS.getService(ModuleService.class);

                addProvider(moduleInstance, new ModuleProvider() {

                    private boolean enable;
                    private int key;

                    {
                        enable = false;
                        key = module.key();
                    }

                    @Override
                    public String getName() {

                        if (StringUtil.isEmpty(module.value())) {
                            return CF4M.CONFIGURATION.get(NameGeneratorConfiguration.class).generate(klass);
                        }

                        return module.value();
                    }

                    @Override
                    public boolean getEnable() {
                        return enable;
                    }

                    @Override
                    public boolean isEnable() {
                        return enable;
                    }

                    @Override
                    public <T> T as() {
                        return (T) moduleInstance;
                    }

                    @Override
                    public void enable() {
                        Module module = klass.getAnnotation(Module.class);

                        enable = !enable;

                        if (enable) {
                            processors.forEach(moduleService -> moduleService.beforeEnable(this));
                            CF4M.EVENT.register(moduleInstance);
                            processors.forEach(moduleService -> moduleService.afterEnable(this));
                        } else {
                            processors.forEach(moduleService -> moduleService.beforeDisable(this));
                            CF4M.EVENT.unregister(moduleInstance);
                            processors.forEach(moduleService -> moduleService.afterDisable(this));
                        }

                        for (Method method : klass.getDeclaredMethods()) {
                            method.setAccessible(true);
                            try {

                                if (method.isAnnotationPresent(Toggle.class)) {
                                    method.invoke(moduleInstance);
                                }

                                if (enable) {
                                    if (method.isAnnotationPresent(Enable.class)) {
                                        method.invoke(moduleInstance);
                                    }
                                } else {
                                    if (method.isAnnotationPresent(Disable.class)) {
                                        method.invoke(moduleInstance);
                                    }
                                }
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public int getKey() {
                        return key;
                    }

                    @Override
                    public void setKey(int key) {
                        this.key = key;
                    }

                    @Override
                    public String getType() {
                        return module.type();
                    }

                    @Override
                    public String getDescription() {
                        return module.description();
                    }

                    @Override
                    public <T> T getExtend(Class<T> klass) {
                        Map<Class<?>, Object> classObjectMap = multipleExtend.get(moduleInstance);
                        return (T) classObjectMap.get(klass);
                    }

                    @Override
                    public SettingContainer getSetting() {
                        return settingContainer;
                    }
                });
            }
        }

        CF4M.MODULE = new ModuleContainer() {
            @Override
            public ArrayList<ModuleProvider> getAll() {
                return new ArrayList<>(getProviders().values());
            }

            @Override
            public ArrayList<ModuleProvider> getAllByType(String type) {
                return getProviders().values().stream().filter(moduleProvider -> moduleProvider.getType().equals(type)).collect(Collectors.toCollection(ArrayList::new));
            }

            @Override
            public ArrayList<String> getAllType() {
                return getAll().stream().map(ModuleProvider::getType).distinct().collect(Collectors.toCollection(ArrayList::new));
            }

            @Override
            public ModuleProvider get(String name) {
                for (ModuleProvider moduleProvider : getAll()) {
                    if (moduleProvider.getName().equalsIgnoreCase(name)) {
                        return moduleProvider;
                    }
                }
                return null;
            }

            @Override
            public ModuleProvider get(Object instance) {
                return getProviders().get(instance);
            }

            @Override
            public <T> ModuleProvider get(Class<T> klass) {
                return get(CF4M.CLASS.create(klass));
            }

            @Override
            public void onKey(int key) {
                for (ModuleProvider module : getAll()) {
                    if (module.getKey() == key) {
                        module.enable();
                    }
                }
            }
        };
    }
}
