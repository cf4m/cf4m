package cn.enaium.cf4m.setting;

import java.lang.reflect.Field;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class SettingBean {

    private final String name;
    private final Field field;
    private final Object fieldObject;

    public SettingBean(String name, Field field, Object fieldObject) {
        this.name = name;
        this.field = field;
        this.fieldObject = fieldObject;
    }

    public String getName() {
        return name;
    }

    public Field getField() {
        return field;
    }

    public Object getFieldObject() {
        return fieldObject;
    }
}
