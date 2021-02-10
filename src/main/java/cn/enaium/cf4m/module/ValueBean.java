package cn.enaium.cf4m.module;

import java.lang.reflect.Field;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ValueBean {
    private String name;
    private Field field;
    private Object object;

    public ValueBean(String name, Field field, Object object) {
        this.name = name;
        this.field = field;
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public Field getField() {
        return field;
    }

    public Object getObject() {
        return object;
    }
}
