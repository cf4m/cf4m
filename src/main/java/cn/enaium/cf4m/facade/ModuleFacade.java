package cn.enaium.cf4m.facade;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.annotation.module.Setting;
import cn.enaium.cf4m.annotation.module.Extend;
import cn.enaium.cf4m.annotation.module.*;
import cn.enaium.cf4m.container.ClassContainer;
import cn.enaium.cf4m.container.ModuleContainer;
import cn.enaium.cf4m.service.ModuleService;
import cn.enaium.cf4m.provider.ModuleProvider;
import cn.enaium.cf4m.container.SettingContainer;
import cn.enaium.cf4m.provider.SettingProvider;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
@SuppressWarnings({"unchecked", "unused"})
public final class ModuleFacade {

    public final ModuleContainer moduleContainer;

    public ModuleFacade(ClassContainer classContainer) {
        final HashMap<Object, ModuleProvider> modules = new HashMap<>();
        //Find Extend
        Set<Class<?>> extendClasses = new HashSet<>();
        for (Class<?> klass : classContainer.getAll()) {
            if (klass.isAnnotationPresent(Extend.class)) {
                extendClasses.add(klass);
            }
        }

        final Map<Object, Map<Class<?>, Object>> multipleExtend = new HashMap<>();

        //Add Modules
        for (Class<?> klass : classContainer.getAll()) {
            if (klass.isAnnotationPresent(Module.class)) {
                Module module = klass.getAnnotation(Module.class);
                Object moduleInstance = classContainer.create(klass);

                Map<Class<?>, Object> extend = new HashMap<>();

                for (Class<?> extendClass : extendClasses) {
                    try {
                        extend.put(extendClass, extendClass.getConstructor().newInstance());
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

                multipleExtend.put(moduleInstance, extend);

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
                            public Object getInstance() {
                                try {
                                    return field.get(moduleInstance);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }

                            @Override
                            public <T> T getSetting() {
                                return (T) getInstance();
                            }

                            @Override
                            public <T> T setSetting(Object value) {
                                try {
                                    field.set(moduleInstance, value);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                                return (T) getInstance();
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
                    public SettingProvider getByName(String name) {
                        for (SettingProvider settingProvider : getAll()) {
                            if (settingProvider.getName().equalsIgnoreCase(name)) {
                                return settingProvider;
                            }
                        }
                        return null;
                    }
                };

                ArrayList<ModuleService> processors = classContainer.getService(ModuleService.class);

                modules.put(moduleInstance, new ModuleProvider() {

                    private boolean enable;
                    private int key;

                    {
                        enable = false;
                        key = module.key();
                    }

                    @Override
                    public String getName() {
                        return module.value();
                    }

                    @Override
                    public boolean getEnable() {
                        return enable;
                    }

                    @Override
                    public Object getInstance() {
                        return moduleInstance;
                    }

                    @Override
                    public void enable() {
                        Class<?> klass = moduleInstance.getClass();
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

        moduleContainer = new ModuleContainer() {
            @Override
            public ArrayList<ModuleProvider> getAll() {
                return new ArrayList<>(modules.values());
            }

            @Override
            public ArrayList<ModuleProvider> getAllByType(String type) {
                return modules.values().stream().filter(moduleProvider -> moduleProvider.getType().equals(type)).collect(Collectors.toCollection(ArrayList::new));
            }

            @Override
            public ArrayList<String> getAllType() {
                return getAll().stream().map(ModuleProvider::getType).distinct().collect(Collectors.toCollection(ArrayList::new));
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
            public <T> ModuleProvider getByClass(Class<T> klass) {
                return getByInstance(classContainer.create(klass));
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

        for (Object module : modules.keySet()) {
            Class<?> klass = module.getClass();
            if (klass.isAnnotationPresent(Autowired.class)) {
                for (Field field : klass.getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        if (field.getType().equals(ModuleContainer.class)) {
                            field.set(module, moduleContainer);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
