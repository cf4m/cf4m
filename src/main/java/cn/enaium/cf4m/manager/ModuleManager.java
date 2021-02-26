package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.*;
import cn.enaium.cf4m.annotation.module.extend.Extend;
import cn.enaium.cf4m.annotation.module.extend.Value;
import cn.enaium.cf4m.annotation.module.*;
import cn.enaium.cf4m.event.events.KeyboardEvent;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.module.ValueBean;
import com.google.common.collect.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@SuppressWarnings({"unchecked", "unused"})
public class ModuleManager {

    /**
     * <K> module
     * <V> values
     */
    private final LinkedHashMap<Object, LinkedHashSet<ValueBean>> modules = Maps.newLinkedHashMap();

    public ModuleManager() {
        CF4M.INSTANCE.event.register(this);
        try {
            //Find Value
            Class<?> extend = null;//Extend class
            HashMap<String, Field> findFields = Maps.newHashMap();
            for (Class<?> type : CF4M.INSTANCE.type.getClasses()) {
                if (type.isAnnotationPresent(Extend.class)) {
                    extend = type;
                    for (Field field : type.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(Value.class)) {
                            Value value = field.getAnnotation(Value.class);
                            findFields.put(value.value(), field);//Add value
                        }
                    }
                }
            }

            //Add Modules
            for (Class<?> type : CF4M.INSTANCE.type.getClasses()) {
                if (type.isAnnotationPresent(Module.class)) {
                    Object extendObject = extend != null ? extend.newInstance() : null;
                    Object moduleObject = type.newInstance();
                    LinkedHashSet<ValueBean> valueBeans = Sets.newLinkedHashSet();
                    for (Map.Entry<String, Field> entry : findFields.entrySet()) {
                        valueBeans.add(new ValueBean(entry.getKey(), entry.getValue(), extendObject));
                    }
                    modules.put(moduleObject, valueBeans);
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
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

    public void setEnable(Object module, boolean value) {
        if (modules.containsKey(module)) {
            try {
                TypeAnnotation(Proxy.getInvocationHandler(module.getClass().getAnnotation(Module.class)), "enable", value);
            } catch (Exception e) {
                e.getCause().printStackTrace();
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
            } catch (Exception e) {
                e.getCause().printStackTrace();
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

    public <T> T getValue(Object module, String name) {
        try {
            if (modules.containsKey(module)) {
                for (ValueBean valueBean : modules.get(module)) {
                    if (valueBean.getName().equals(name)) {
                        return (T) valueBean.getField().get(valueBean.getObject());
                    }
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
        return null;
    }

    public <T> void setValue(Object module, String name, T value) {
        try {
            if (modules.containsKey(module)) {
                for (ValueBean valueBean : modules.get(module)) {
                    if (valueBean.getName().equals(name)) {
                        valueBean.getField().set(valueBean.getObject(), value);
                    }
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
    }

    public void enable(Object module) {
        if (modules.containsKey(module)) {
            Class<?> type = module.getClass();

            setEnable(module, !getEnable(module));

            if (getEnable(module)) {
                CF4M.INSTANCE.event.register(module);
            } else {
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
                } catch (Exception e) {
                    e.getCause().printStackTrace();
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
