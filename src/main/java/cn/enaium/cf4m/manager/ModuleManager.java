package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.*;
import cn.enaium.cf4m.annotation.module.extend.Extend;
import cn.enaium.cf4m.annotation.module.extend.Value;
import cn.enaium.cf4m.annotation.module.*;
import cn.enaium.cf4m.event.events.KeyboardEvent;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.module.ValueBean;
import cn.enaium.cf4m.setting.SettingBase;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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
    private final HashMap<Object, Set<ValueBean>> modules = Maps.newHashMap();

    /**
     * <K> module
     * <V> settings
     */
    private final HashMap<Object, Set<SettingBase>> settings = Maps.newHashMap();

    public ModuleManager() {
        CF4M.INSTANCE.event.register(this);
        try {
            //Find Value
            Class<?> extend = null;
            HashMap<String, Field> findFields = Maps.newHashMap();
            for (Class<?> type : CF4M.INSTANCE.type.getClasses()) {
                if (type.isAnnotationPresent(Extend.class)) {
                    extend = type;
                    for (Field field : type.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(Value.class)) {
                            Value value = field.getAnnotation(Value.class);
                            findFields.put(value.value(), field);
                        }
                    }
                }
            }

            //Add ModuleBean and ValueBean
            for (Class<?> type : CF4M.INSTANCE.type.getClasses()) {
                if (type.isAnnotationPresent(Module.class)) {
                    Object o = null;
                    if (extend != null) {
                        o = extend.newInstance();
                    }
                    Set<ValueBean> valueBeans = Sets.newHashSet();
                    for (Map.Entry<String, Field> entry : findFields.entrySet()) {
                        valueBeans.add(new ValueBean(entry.getKey(), entry.getValue(), o));
                    }
                    modules.put(type.newInstance(), valueBeans);
                }
            }

            //Add Setting
            for (Object module : modules.keySet()) {
                for (Field field : module.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (Objects.equals(field.getType().getSuperclass(), SettingBase.class)) {
                        settings.put(module, Collections.singleton((SettingBase) field.get(module)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName(Object object) {
        if (modules.containsKey(object)) {
            return object.getClass().getAnnotation(Module.class).value();
        }
        return null;
    }

    public boolean getEnable(Object object) {
        if (modules.containsKey(object)) {
            return object.getClass().getAnnotation(Module.class).enable();
        }
        return false;
    }

    public void setEnable(Object object, boolean value) {
        if (modules.containsKey(object)) {
            try {
                TypeAnnotation(Proxy.getInvocationHandler(object.getClass().getAnnotation(Module.class)), "enable", value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public int getKey(Object object) {
        if (modules.containsKey(object)) {
            return object.getClass().getAnnotation(Module.class).key();
        }
        return 0;
    }

    public void setKey(Object object, int value) {
        if (modules.containsKey(object)) {
            try {
                TypeAnnotation(Proxy.getInvocationHandler(object.getClass().getAnnotation(Module.class)), "key", value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public Category getCategory(Object object) {
        if (modules.containsKey(object)) {
            return object.getClass().getAnnotation(Module.class).category();
        }
        return Category.NONE;
    }

    public <T> T getValue(Object object, String name) {
        try {
            if (modules.containsKey(object)) {
                for (ValueBean valueBean : modules.get(object)) {
                    if (valueBean.getName().equals(name)) {
                        return (T) valueBean.getField().get(valueBean.getObject());
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> void setValue(Object object, String name, T value) {
        try {
            if (modules.containsKey(object)) {
                for (ValueBean valueBean : modules.get(object)) {
                    if (valueBean.getName().equals(name)) {
                        valueBean.getField().set(valueBean.getObject(), value);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void enable(Object object) {
        try {
            for (Object module : getModules()) {
                if (!module.equals(object))
                    continue;

                Class<?> type = object.getClass();
                setEnable(object, !getEnable(object));
                for (Method method : type.getDeclaredMethods()) {
                    method.setAccessible(true);
                    if (getEnable(object)) {
                        CF4M.INSTANCE.event.register(object);
                        if (method.isAnnotationPresent(Enable.class)) {
                            method.invoke(object);
                        }
                    } else {
                        CF4M.INSTANCE.event.unregister(object);
                        if (method.isAnnotationPresent(Disable.class)) {
                            method.invoke(object);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Event
    private void onKey(KeyboardEvent keyboardEvent) {
        for (Object module : getModules()) {
            if (getKey(module) == keyboardEvent.getKey()) {
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

    public Set<Object> getModules() {
        return modules.keySet();
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

    public SettingBase getSetting(Object module, String name) {
        return settings.get(module).stream().filter(setting -> setting.getName().equals(name)).collect(Collectors.toCollection(Lists::newArrayList)).get(0);
    }

    public HashMap<Object, Set<SettingBase>> getSettings() {
        return settings;
    }

    public Set<SettingBase> getSettings(Object module) {
        return settings.get(module);
    }
}
