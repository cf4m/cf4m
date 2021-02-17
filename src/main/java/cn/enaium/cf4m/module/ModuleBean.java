package cn.enaium.cf4m.module;

import java.util.Set;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ModuleBean {
    private final String name;
    private final Object object;
    private final Set<ValueBean> valueBeans;

    public ModuleBean(String name, Object object, Set<ValueBean> valueBeans) {
        this.name = name;
        this.object = object;
        this.valueBeans = valueBeans;
    }

    public String getName() {
        return name;
    }

    public Object getObject() {
        return object;
    }

    public Set<ValueBean> getValueBeans() {
        return valueBeans;
    }
}
