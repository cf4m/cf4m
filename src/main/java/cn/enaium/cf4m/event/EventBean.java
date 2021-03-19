package cn.enaium.cf4m.event;

import java.lang.reflect.Method;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class EventBean {
    private final Object object;
    private final Method method;
    private final int priority;

    public EventBean(Object object, Method method, int priority) {
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
