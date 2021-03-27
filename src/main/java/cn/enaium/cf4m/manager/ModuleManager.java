package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.module.Extend;
import cn.enaium.cf4m.annotation.module.*;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.module.ModuleBean;
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
    private final LinkedHashMap<Object, Object> modules = Maps.newLinkedHashMap();

    public ModuleManager() {
        try {
            //Find Value
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
                    modules.put(moduleInstance, extendInstance);
                }
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public String getName(Object module) {
        if (modules.containsKey(module)) {
            return module.getClass().getAnnotation(Module.class).value();
        }
        return null;
    }

    public boolean getEnable(Object module) {
        if (modules.containsKey(module)) {
            return module.getClass().getAnnotation(Module.class).enable();
        }
        return false;
    }

    private void setEnable(Object module, boolean value) {
        if (modules.containsKey(module)) {
            try {
                TypeAnnotation(Proxy.getInvocationHandler(module.getClass().getAnnotation(Module.class)), "enable", value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public int getKey(Object module) {
        if (modules.containsKey(module)) {
            return module.getClass().getAnnotation(Module.class).key();
        }
        return 0;
    }

    public void setKey(Object module, int value) {
        if (modules.containsKey(module)) {
            try {
                TypeAnnotation(Proxy.getInvocationHandler(module.getClass().getAnnotation(Module.class)), "key", value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public Category getCategory(Object module) {
        if (modules.containsKey(module)) {
            return module.getClass().getAnnotation(Module.class).category();
        }
        return Category.NONE;
    }

    public String getDescription(Object module) {
        if (modules.containsKey(module)) {
            return module.getClass().getAnnotation(Module.class).description();
        }
        return null;
    }

    public void enable(Object module) {
        if (modules.containsKey(module)) {
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

    public <T> T getExtend(Object module) {
        if (modules.containsKey(module)) {
            return (T) modules.get(module);
        }
        return null;
    }

    public void onKey(int key) {
        for (ModuleBean module : getModules()) {
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

    public ModuleBean getModule(Object instance) {
        return new ModuleBean(instance);
    }

    public ArrayList<ModuleBean> getModules() {
        ArrayList<ModuleBean> moduleBeans = new ArrayList<>();
        modules.keySet().forEach(module -> moduleBeans.add(new ModuleBean(module)));
        return moduleBeans;
    }

    public ArrayList<ModuleBean> getModules(Category category) {
        return getModules().stream().filter(module -> module.getCategory().equals(category)).collect(Collectors.toCollection(Lists::newArrayList));
    }

    public ModuleBean getModule(String name) {
        for (ModuleBean module : getModules()) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }


}
