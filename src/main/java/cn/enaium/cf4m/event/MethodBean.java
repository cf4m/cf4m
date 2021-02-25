package cn.enaium.cf4m.event;

import java.lang.reflect.Method;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class MethodBean {
    private final Object object;
    private final Method method;
    private final int priority;

    public MethodBean(Object object, Method method, int priority) {
        this.object = object;
        this.method = method;
        this.priority = priority;
    }

    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }

    public int getPriority() {
        return priority;
    }
}
