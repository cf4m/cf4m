package cn.enaium.cf4m.module;

import java.lang.reflect.Field;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class ValueBean {

    private final String name;
    private final Field field;
    private final Object object;

    /**
     * @param name   value name
     * @param field  value field
     * @param object for extend
     */
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
