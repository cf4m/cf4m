package cn.enaium.cf4m.event;

import java.lang.reflect.Method;

/**
 * Project: cf4m
 * Author: Enaium
 */
public final class EventBean {
    private final Object instance;
    private final Method target;
    private final int priority;

    public EventBean(Object object, Method method, int priority) {
        this.instance = object;
        this.target = method;
        this.priority = priority;
    }

    public Object getInstance() {
        return instance;
    }

    public Method getTarget() {
        return target;
    }

    public int getPriority() {
        return priority;
    }
}
