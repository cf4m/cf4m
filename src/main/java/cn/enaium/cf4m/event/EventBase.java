package cn.enaium.cf4m.event;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.enaium.cf4m.CF4M;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public abstract class EventBase {

    private boolean cancelled;
    private Type type;

    public EventBase(Type type) {
        this.type = type;
        this.cancelled = false;
    }

    public enum Type {
        PRE, POST
    }

    public void call() {
        cancelled = false;

        CopyOnWriteArrayList<MethodBean> methodBeanList = CF4M.INSTANCE.event.get(this.getClass());

        if (methodBeanList == null)
            return;

        methodBeanList.forEach(methodBean -> {

            try {
                methodBean.getMethod().invoke(methodBean.getObject(), this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        });


    }

    public Type getType() {
        return type;
    }

    public boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }


}