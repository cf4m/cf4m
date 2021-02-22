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
    private final EventPriority priority;

    public MethodBean(Object object, Method method, EventPriority priority) {
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

    public EventPriority getPriority() {
        return priority;
    }
}
