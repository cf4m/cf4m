package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Setting;
import cn.enaium.cf4m.annotation.module.Extend;
import cn.enaium.cf4m.annotation.module.*;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.provider.ModuleProvider;
import cn.enaium.cf4m.container.SettingContainer;
import cn.enaium.cf4m.provider.SettingProvider;
import com.google.common.collect.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project: cf4m
 * Author: Enaium
 */
@SuppressWarnings({"unchecked", "unused"})
public class ModuleManager {

    /**
     * <K> module
     * <V> extend
     */
    private final HashMap<Object, ModuleProvider> modules = new HashMap<>();

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
                            for (SettingProvider settingProvider : settingProviders) {
                                if (settingProvider.getName().equals(name)) {
                                    return settingProvider.getSetting();
                                }
                            }
                            return null;
                        }
                    };

                    modules.put(moduleInstance, new ModuleProvider() {
                        @Override
                        public String getName() {
                            return ModuleManager.this.getName(moduleInstance);
                        }

                        @Override
                        public boolean getEnable() {
                            return ModuleManager.this.getEnable(moduleInstance);
                        }

                        @Override
                        public void enable() {
                            ModuleManager.this.enable(moduleInstance);
                        }

                        @Override
                        public int getKey() {
                            return ModuleManager.this.getKey(moduleInstance);
                        }

                        @Override
                        public void setKey(int key) {
                            ModuleManager.this.setKey(moduleInstance, key);
                        }

                        @Override
                        public Category getCategory() {
                            return ModuleManager.this.getCategory(moduleInstance);
                        }

                        @Override
                        public String getDescription() {
                            return ModuleManager.this.getDescription(moduleInstance);
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

    private String getName(Object module) {
        if (module.getClass().isAnnotationPresent(Module.class)) {
            return module.getClass().getAnnotation(Module.class).value();
        }
        return null;
    }

    private boolean getEnable(Object module) {
        if (module.getClass().isAnnotationPresent(Module.class)) {
            return module.getClass().getAnnotation(Module.class).enable();
        }
        return false;
    }

    private void setEnable(Object module, boolean value) {
        if (module.getClass().isAnnotationPresent(Module.class)) {
            try {
                TypeAnnotation(Proxy.getInvocationHandler(module.getClass().getAnnotation(Module.class)), "enable", value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private int getKey(Object module) {
        if (module.getClass().isAnnotationPresent(Module.class)) {
            return module.getClass().getAnnotation(Module.class).key();
        }
        return 0;
    }

    private void setKey(Object module, int value) {
        if (module.getClass().isAnnotationPresent(Module.class)) {
            try {
                TypeAnnotation(Proxy.getInvocationHandler(module.getClass().getAnnotation(Module.class)), "key", value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Category getCategory(Object module) {
        if (module.getClass().isAnnotationPresent(Module.class)) {
            return module.getClass().getAnnotation(Module.class).category();
        }
        return Category.NONE;
    }

    private String getDescription(Object module) {
        if (module.getClass().isAnnotationPresent(Module.class)) {
            return module.getClass().getAnnotation(Module.class).description();
        }
        return null;
    }

    private void enable(Object module) {
        if (module.getClass().isAnnotationPresent(Module.class)) {
            Class<?> klass = module.getClass();

            setEnable(module, !getEnable(module));

            if (getEnable(module)) {
                CF4M.configuration.module().enable(module);
                CF4M.event.register(module);
            } else {
                CF4M.configuration.module().disable(module);
                CF4M.event.unregister(module);
            }

            for (Method method : klass.getDeclaredMethods()) {
                method.setAccessible(true);
                try {
                    if (getEnable(module)) {
                        if (method.isAnnotationPresent(Enable.class)) {
                            method.invoke(module);
                        }
                    } else {
                        if (method.isAnnotationPresent(Disable.class)) {
                            method.invoke(module);
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onKey(int key) {
        for (ModuleProvider module : getModules()) {
            if (module.getKey() == key) {
                module.enable();
            }
        }
    }

    private void TypeAnnotation(InvocationHandler invocationHandler, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field memberValues = invocationHandler.getClass().getDeclaredField("memberValues");
        memberValues.setAccessible(true);
        Map<String, Object> map = (Map<String, Object>) memberValues.get(invocationHandler);
        map.put(name, value);
    }

    public ModuleProvider getModule(Object instance) {
        return modules.get(instance);
    }

    public ArrayList<ModuleProvider> getModules() {
        return modules.values().stream().collect(Collectors.toCollection(Lists::newArrayList));
    }

    public ArrayList<ModuleProvider> getModules(Category category) {
        return getModules().stream().filter(module -> module.getCategory().equals(category)).collect(Collectors.toCollection(Lists::newArrayList));
    }

    public ModuleProvider getModule(String name) {
        for (ModuleProvider module : getModules()) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
}
