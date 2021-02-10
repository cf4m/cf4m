package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.EventAT;
import cn.enaium.cf4m.annotation.module.collector.ModuleCollector;
import cn.enaium.cf4m.annotation.module.OnDisable;
import cn.enaium.cf4m.annotation.module.OnEnable;
import cn.enaium.cf4m.annotation.module.collector.ModuleValue;
import cn.enaium.cf4m.event.events.KeyboardEvent;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.module.ModuleBean;
import cn.enaium.cf4m.annotation.module.ModuleAT;
import cn.enaium.cf4m.module.ValueBean;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    private Set<ModuleBean> modules = new HashSet<>();

    private HashMap<String, Field> fieldHashMap = Maps.newHashMap();

    private Class<?> collector = null;

    public ModuleManager() {
        CF4M.getInstance().event.register(this);
        try {
            for (Class<?> clazz : CF4M.getInstance().classManager.getClasses()) {
                if (clazz.isAnnotationPresent(ModuleCollector.class)) {
                    collector = clazz;
                    for (Field field : clazz.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(ModuleValue.class)) {
                            ModuleValue moduleValue = field.getAnnotation(ModuleValue.class);
//                            String value = moduleValue.value();
//                            Object castValue = null;
//                            switch (field.getName()) {
//                                case "java.lang.Boolean":
//                                    castValue = Boolean.parseBoolean(value);
//                                    break;
//                                case "java.lang.Integer":
//                                    castValue = Integer.parseInt(value);
//                                    break;
//                                case "java.lang.Float":
//                                    castValue = Float.parseFloat(value);
//                                    break;
//                                case "java.lang.Double":
//                                    castValue = Double.parseDouble(value);
//                                    break;
//                                case "java.lang.Long":
//                                    castValue = Long.parseLong(value);
//                                    break;
//                            }
//                            if (castValue != null) {
//                                field.set(clazz.newInstance(), castValue);
//                            }
                            fieldHashMap.put(moduleValue.value(), field);
                        }
                    }
                }
            }

            for (Class<?> clazz : CF4M.getInstance().classManager.getClasses()) {
                if (clazz.isAnnotationPresent(ModuleAT.class)) {
                    ModuleAT moduleAT = clazz.getAnnotation(ModuleAT.class);
                    Object o = collector.newInstance();
                    Set<ValueBean> valueBeans = new HashSet<>();
                    for (Map.Entry<String, Field> entry : fieldHashMap.entrySet()) {
                        valueBeans.add(new ValueBean(entry.getKey(), entry.getValue(), o));
                    }
                    modules.add(new ModuleBean(moduleAT.name(), clazz, clazz.newInstance(), valueBeans));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean getBoolean(String name, Object o) throws IllegalAccessException {
        for (ModuleBean moduleBean : modules) {
            for (ValueBean valueBean : moduleBean.getValueBeans()) {
                if (valueBean.getName().equals(name)) {
                    return valueBean.getField().getBoolean(o);
                }
            }
        }
        return false;
    }

    public void setBoolean(String name, Boolean value, Object o) throws IllegalAccessException {
        for (ModuleBean moduleBean : modules) {
            for (ValueBean valueBean : moduleBean.getValueBeans()) {
                if (valueBean.getName().equals(name)) {
                    valueBean.getField().setBoolean(o, value);
                }
            }
        }
    }


    public String getName(Class<?> clazz) {
        return clazz.getAnnotation(ModuleAT.class).name();
    }

    public Category getCategory(Class<?> clazz) {
        return clazz.getAnnotation(ModuleAT.class).category();
    }

    @EventAT
    private void onKey(KeyboardEvent keyboardEvent) {
        try {
            for (ModuleBean moduleBean : modules) {
                if (moduleBean.getClazz().getAnnotation(ModuleAT.class).keyCode() == keyboardEvent.getKeyCode()) {
                    Class<?> clazz = moduleBean.getClazz();
                    Object object = moduleBean.getObject();
                    boolean enable = !getBoolean("Enable", object);
                    setBoolean("Enable", enable, object);
                    for (Method method : clazz.getDeclaredMethods()) {
                        if (enable) {
                            if (method.isAnnotationPresent(OnEnable.class)) {
                                CF4M.getInstance().event.register(object);
                                method.invoke(object);
                            }
                        } else {
                            if (method.isAnnotationPresent(OnDisable.class)) {
                                CF4M.getInstance().event.unregister(object);
                                method.invoke(object);
                            }
                        }
                    }

                }
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public Set<ModuleBean> getModules() {
        return modules;
    }
}
