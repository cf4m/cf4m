package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Setting;
import cn.enaium.cf4m.annotation.module.Extend;
import cn.enaium.cf4m.annotation.module.*;
import cn.enaium.cf4m.container.ModuleContainer;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.provider.ModuleProvider;
import cn.enaium.cf4m.container.SettingContainer;
import cn.enaium.cf4m.provider.SettingProvider;
import com.google.common.collect.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project: cf4m
 * Author: Enaium
 */
@SuppressWarnings({"unchecked", "unused"})
public final class ModuleManager {

    private final HashMap<Object, ModuleProvider> modules = new HashMap<>();

    public final ModuleContainer moduleContainer = new ModuleContainer() {
        @Override
        public ArrayList<ModuleProvider> getAll() {
            return Lists.newArrayList(modules.values());
        }

        @Override
        public ArrayList<ModuleProvider> getAllByCategory(Category category) {
            return modules.values().stream().filter(moduleProvider -> moduleProvider.getCategory().equals(category)).collect(Collectors.toCollection(Lists::newArrayList));
        }

        @Override
        public ModuleProvider getByName(String name) {
            for (ModuleProvider moduleProvider : getAll()) {
                if (moduleProvider.getName().equalsIgnoreCase(name)) {
                    return moduleProvider;
                }
            }
            return null;
        }

        @Override
        public ModuleProvider getByInstance(Object instance) {
            return modules.get(instance);
        }

        @Override
        public void onKey(int key) {
            for (ModuleProvider module : CF4M.module.getAll()) {
                if (module.getKey() == key) {
                    module.enable();
                }
            }
        }
    };


    public ModuleManager() {
        try {
            //Find Extend
            Class<?> extend = null;//Extend class
            HashMap<String, Field> findFields = Maps.newHashMap();
            for (Class<?> klass : CF4M.klass.getClasses()) {
                if (klass.isAnnotationPresent(Extend.class)) {
                    extend = klass;
                }
            }

            //Add Modules
            for (Class<?> klass : CF4M.klass.getClasses()) {
                if (klass.isAnnotationPresent(Module.class)) {
                    Module module = klass.getAnnotation(Module.class);
                    Object extendInstance = extend != null ? extend.newInstance() : null;
                    Object moduleInstance = klass.newInstance();

                    //Add Setting
                    ArrayList<SettingProvider> settingProviders = new ArrayList<>();

                    for (Field field : klass.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(Setting.class)) {
                            settingProviders.add(new SettingProvider() {
                                @Override
                                public String getName() {
                                    return field.getAnnotation(Setting.class).value();
                                }

                                @Override
                                public String getDescription() {
                                    return field.getAnnotation(Setting.class).description();
                                }

                                @Override
                                public <T> T getSetting() {
                                    try {
                                        return (T) field.get(moduleInstance);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                    return null;
                                }
                            });
                        }
                    }

                    SettingContainer settingContainer = new SettingContainer() {
                        @Override
                        public ArrayList<SettingProvider> getAll() {
                            return settingProviders;
                        }

                        @Override
                        public <T> T getByName(String name) {
                            for (SettingProvider settingProvider : getAll()) {
                                if (settingProvider.getName().equalsIgnoreCase(name)) {
                                    return settingProvider.getSetting();
                                }
                            }
                            return null;
                        }
                    };

                    modules.put(moduleInstance, new ModuleProvider() {
                        @Override
                        public String getName() {
                            return module.value();
                        }

                        @Override
                        public boolean getEnable() {
                            return module.enable();
                        }

                        @Override
                        public void enable() {
                            Class<?> klass = moduleInstance.getClass();
                            Module module = klass.getAnnotation(Module.class);
                            TypeAnnotation(moduleInstance.getClass().getAnnotation(Module.class), "enable", !module.enable());

                            if (module.enable()) {
                                CF4M.configuration.module().enable(moduleInstance);
                                CF4M.event.register(moduleInstance);
                            } else {
                                CF4M.configuration.module().disable(moduleInstance);
                                CF4M.event.unregister(moduleInstance);
                            }

                            for (Method method : klass.getDeclaredMethods()) {
                                method.setAccessible(true);
                                try {
                                    if (module.enable()) {
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
                            return module.key();
                        }

                        @Override
                        public void setKey(int key) {
                            TypeAnnotation(moduleInstance.getClass().getAnnotation(Module.class), "key", key);
                        }

                        @Override
                        public Category getCategory() {
                            return module.category();
                        }

                        @Override
                        public String getDescription() {
                            return module.description();
                        }

                        @Override
                        public <T> T getExtend() {
                            return (T) extendInstance;
                        }

                        @Override
                        public SettingContainer getSetting() {
                            return settingContainer;
                        }
                    });
                }
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void TypeAnnotation(Annotation annotation, String name, Object value) {
        try {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
            Field memberValues = invocationHandler.getClass().getDeclaredField("memberValues");
            memberValues.setAccessible(true);
            Map<String, Object> map = (Map<String, Object>) memberValues.get(invocationHandler);
            map.put(name, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
