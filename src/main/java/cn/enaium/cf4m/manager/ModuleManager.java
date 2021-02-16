package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.*;
import cn.enaium.cf4m.annotation.module.expand.Expand;
import cn.enaium.cf4m.annotation.module.expand.Value;
import cn.enaium.cf4m.annotation.module.*;
import cn.enaium.cf4m.event.events.KeyboardEvent;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.module.ModuleBean;
import cn.enaium.cf4m.module.ValueBean;
import cn.enaium.cf4m.setting.SettingBase;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ModuleManager {

    /**
     * Module list.
     */
    private final Set<ModuleBean> moduleBeans = new HashSet<>();

    private final ArrayList<SettingBase> settings = Lists.newArrayList();

    public ModuleManager() {
        CF4M.getInstance().event.register(this);
        try {
            //Find Value
            Class<?> expand = null;
            HashMap<String, Field> findFields = Maps.newHashMap();
            for (Class<?> clazz : CF4M.getInstance().clazz.getClasses()) {
                if (clazz.isAnnotationPresent(Expand.class)) {
                    expand = clazz;
                    for (Field field : clazz.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(Value.class)) {
                            Value value = field.getAnnotation(Value.class);
                            findFields.put(value.value(), field);
                        }
                    }
                }
            }

            //Add ModuleBean and ValueBean
            for (Class<?> clazz : CF4M.getInstance().clazz.getClasses()) {
                if (clazz.isAnnotationPresent(Module.class)) {
                    Module module = clazz.getAnnotation(Module.class);
                    Object o = null;
                    if (expand != null) {
                        o = expand.newInstance();
                    }
                    Set<ValueBean> valueBeans = new HashSet<>();
                    for (Map.Entry<String, Field> entry : findFields.entrySet()) {
                        valueBeans.add(new ValueBean(entry.getKey(), entry.getValue(), o));
                    }
                    moduleBeans.add(new ModuleBean(module.value(), clazz.newInstance(), valueBeans));
                }
            }

            //Add Setting
            for (ModuleBean moduleBean : moduleBeans) {
                for (Field field : moduleBean.getObject().getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Setting.class)) {
                        Class<?> superClass = field.getType().getSuperclass();
                        if (superClass == null)
                            continue;

                        if (superClass.equals(SettingBase.class)) {
                            settings.add((SettingBase) field.get(moduleBean.getObject()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName(Object object) {
        for (ModuleBean moduleBean : moduleBeans) {
            if (moduleBean.getObject().equals(object)) {
                return moduleBean.getObject().getClass().getAnnotation(Module.class).value();
            }
        }
        return null;
    }

    public boolean isEnable(Object object) {
        for (ModuleBean moduleBean : moduleBeans) {
            if (moduleBean.getObject().equals(object)) {
                return moduleBean.getObject().getClass().getAnnotation(Module.class).enable();
            }
        }
        return false;
    }

    public void setEnable(Object object, boolean value) {
        for (ModuleBean moduleBean : moduleBeans) {
            if (moduleBean.getObject().equals(object)) {
                try {
                    TypeAnnotation(Proxy.getInvocationHandler(moduleBean.getObject().getClass().getAnnotation(Module.class)), "enable", value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getKey(Object object) {
        for (ModuleBean moduleBean : moduleBeans) {
            if (moduleBean.getObject().equals(object)) {
                return moduleBean.getObject().getClass().getAnnotation(Module.class).key();
            }
        }
        return 0;
    }

    public void setKey(Object object, int value) {
        for (ModuleBean moduleBean : moduleBeans) {
            if (moduleBean.getObject().equals(object)) {
                try {
                    TypeAnnotation(Proxy.getInvocationHandler(moduleBean.getObject().getClass().getAnnotation(Module.class)), "key", value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Category getCategory(Object object) {
        for (ModuleBean moduleBean : moduleBeans) {
            if (moduleBean.getObject().equals(object)) {
                return moduleBean.getObject().getClass().getAnnotation(Module.class).category();
            }
        }
        return Category.NONE;
    }

    public <T> T getValue(Object object, String name) {
        try {
            for (ModuleBean moduleBean : moduleBeans) {
                if (moduleBean.getObject().equals(object)) {
                    for (ValueBean valueBean : moduleBean.getValueBeans()) {
                        if (valueBean.getName().equals(name)) {
                            return (T) valueBean.getField().get(valueBean.getObject());
                        }
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
            for (ModuleBean moduleBean : moduleBeans) {
                if (moduleBean.getObject().equals(object)) {
                    for (ValueBean valueBean : moduleBean.getValueBeans()) {
                        if (valueBean.getName().equals(name)) {
                            valueBean.getField().set(valueBean.getObject(), value);
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void enable(Object object) {
        try {
            for (ModuleBean moduleBean : moduleBeans) {
                if (!moduleBean.getObject().equals(object))
                    continue;

                Class<?> clazz = object.getClass();
                setEnable(object, !isEnable(object));
                for (Method method : clazz.getDeclaredMethods()) {
                    method.setAccessible(true);
                    if (isEnable(object)) {
                        CF4M.getInstance().event.register(object);
                        if (method.isAnnotationPresent(Enable.class)) {
                            method.invoke(object);
                        }
                    } else {
                        CF4M.getInstance().event.unregister(object);
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
        for (ModuleBean moduleBean : moduleBeans) {
            if (getKey(moduleBean.getObject()) == keyboardEvent.getKey()) {
                enable(moduleBean.getObject());
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
        ArrayList<Object> modules = new ArrayList<>();
        for (ModuleBean moduleBean : moduleBeans) {
            modules.add(moduleBean.getObject());
        }
        return modules;
    }

    public ArrayList<Object> getModules(Category category) {
        ArrayList<Object> modules = new ArrayList<>();
        for (Object module : getModules()) {
            if (getCategory(module).equals(category)) {
                modules.add(module);
            }
        }
        return modules;
    }

    public Object getModule(String name) {
        for (ModuleBean moduleBean : moduleBeans) {
            if (moduleBean.getName().equalsIgnoreCase(name)) {
                return moduleBean.getObject();
            }
        }
        return null;
    }

    public SettingBase getSetting(Object module, String name) {
        for (SettingBase s : settings) {
            if (s.getModule().equals(module) && s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    public ArrayList<SettingBase> getSettings() {
        return settings;
    }

    public ArrayList<SettingBase> getSettings(Object module) {
        ArrayList<SettingBase> s = Lists.newArrayList();
        for (ModuleBean moduleBean : moduleBeans) {
            if (moduleBean.getObject().equals(module)) {
                for (SettingBase setting : settings) {
                    if (setting.getModule().equals(moduleBean.getObject())) {
                        s.add(setting);
                    }
                }
            }
        }
        return s;
    }
}
