package cn.enaium.cf4m.module;

import java.lang.reflect.Field;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class ValueBean {

    private final String name;
    private final Field field;
    private final Object instance;

    /**
     * @param name   value name
     * @param field  value field
     * @param instance for extend
     */
    public ValueBean(String name, Field field, Object instance) {
        this.name = name;
        this.field = field;
        this.instance = instance;
    }

    public String getName() {
        return name;
    }

    public Field getField() {
        return field;
    }

    public Object getInstance() {
        return instance;
    }
}
