package cn.enaium.cf4m.event;

import cn.enaium.cf4m.CF4M;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class Listener {

    private final At at;
    private boolean cancelled;

    public Listener(At at) {
        this.at = at;
        cancelled = false;
    }

    /**
     * Call all event
     */
    public void call() {
        Collection<MethodBean> methodBeans = CF4M.INSTANCE.event.getEvent(this.getClass());

        if (methodBeans == null) {
            return;
        }

        methodBeans.forEach(event -> {
            try {
                event.getMethod().invoke(event.getObject(), this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean getCancelled() {
        return cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public At getAt() {
        return at;
    }

    /**
     * At head or tail
     */
    public enum At {
        HEAD,
        TAIL
    }
}
