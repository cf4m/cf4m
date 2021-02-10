package cn.enaium.cf4m.module;

import cn.enaium.cf4m.CF4M;

import java.util.ArrayList;
import java.util.Set;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class ModuleBean {
    private String name;
    private Class<?> clazz;
    private Object object;
    private Set<ValueBean> valueBeans;

    public ModuleBean(String name, Class<?> clazz, Object object, Set<ValueBean> valueBeans) {
        this.name = name;
        this.clazz = clazz;
        this.object = object;
        this.valueBeans = valueBeans;
    }

    public String getName() {
        return name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Object getObject() {
        return object;
    }

    public Set<ValueBean> getValueBeans() {
        return valueBeans;
    }
}
