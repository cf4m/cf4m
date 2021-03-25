package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.*;
import cn.enaium.cf4m.annotation.module.Extend;
import cn.enaium.cf4m.annotation.module.*;
import cn.enaium.cf4m.event.KeyboardEvent;
import cn.enaium.cf4m.module.Category;
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
     * <V> values
     */
    private final LinkedHashMap<Object, Object> modules = Maps.newLinkedHashMap();

    public ModuleManager() {
        CF4M.INSTANCE.event.register(this);
        try {
            //Find Value
            Class<?> extend = null;//Extend class
            HashMap<String, Field> findFields = Maps.newHashMap();
            for (Class<?> type : CF4M.INSTANCE.type.getClasses()) {
                if (type.isAnnotationPresent(Extend.class)) {
                    extend = type;
                }
            }

            //Add Modules
            for (Class<?> type : CF4M.INSTANCE.type.getClasses()) {
                if (type.isAnnotationPresent(Module.class)) {
                    Object extendInstance = extend != null ? extend.newInstance() : null;
                    Object moduleInstance = type.newInstance();
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

    public <T> T getExtend(Object module) {
        if (modules.containsKey(module)) {
            return (T) modules.get(module);
        }
        return null;
    }

    public void enable(Object module) {
        if (modules.containsKey(module)) {
            Class<?> type = module.getClass();

            setEnable(module, !getEnable(module));

            if (getEnable(module)) {
                CF4M.INSTANCE.configuration.module().enable(module);
                CF4M.INSTANCE.event.register(module);
            } else {
                CF4M.INSTANCE.configuration.module().disable(module);
                CF4M.INSTANCE.event.unregister(module);
            }

            for (Method method : type.getDeclaredMethods()) {
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

    @Event
    private void onKey(KeyboardEvent e) {
        for (Object module : getModules()) {
            if (getKey(module) == e.getKey()) {
                enable(module);
            }
        }
    }

    private void TypeAnnotation(InvocationHandler invocationHandler, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field memberValues = invocationHandler.getClass().getDeclaredField("memberValues");
        memberValues.setAccessible(true);
        Map<String, Object> map = (Map<String, Object>) memberValues.get(invocationHandler);
        map.put(name, value);
    }

    public ArrayList<Object> getModules() {
        return Lists.newArrayList(modules.keySet());
    }

    public ArrayList<Object> getModules(Category category) {
        return getModules().stream().filter(module -> getCategory(module).equals(category)).collect(Collectors.toCollection(Lists::newArrayList));
    }

    public Object getModule(String name) {
        for (Object module : getModules()) {
            if (getName(module).equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
}
