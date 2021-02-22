package cn.enaium.cf4m.module;

import java.util.Set;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ModuleBean {
    private final Object object;
    private final Set<ValueBean> valueBeans;

    public ModuleBean(Object object, Set<ValueBean> valueBeans) {
        this.object = object;
        this.valueBeans = valueBeans;
    }

    public Object getObject() {
        return object;
    }

    public Set<ValueBean> getValueBeans() {
        return valueBeans;
    }
}
