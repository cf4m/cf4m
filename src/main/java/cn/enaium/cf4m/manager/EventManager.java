package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.event.Listener;
import cn.enaium.cf4m.event.MethodBean;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class EventManager {

    /**
     * <K> listener
     * <V> event
     */
    private final HashMap<Class<? extends Listener>, CopyOnWriteArrayList<MethodBean>> events = new HashMap<>();

    /**
     * Register all event
     * @param o Object
     */
    public void register(Object o) {
        Class<?> type = o.getClass();

        for (Method method : type.getDeclaredMethods()) {
            if (method.getParameterTypes().length == 1 && method.isAnnotationPresent(Event.class)) {
                method.setAccessible(true);
                @SuppressWarnings("unchecked")
                Class<? extends Listener> listener = (Class<? extends Listener>) method.getParameterTypes()[0];

                MethodBean methodBean = new MethodBean(o, method);

                if (events.containsKey(listener)) {
                    if (!events.get(listener).contains(methodBean)) {
                        events.get(listener).add(methodBean);
                    }
                } else {
                    events.put(listener, new CopyOnWriteArrayList<>(Collections.singletonList(methodBean)));
                }
            }
        }
    }

    /**
     * Unregister all event
     * @param o Object
     */
    public void unregister(Object o) {
        events.values().forEach(methodBeans -> methodBeans.removeIf(methodMethodBean -> methodMethodBean.getObject().equals(o)));
        events.entrySet().removeIf(event -> event.getValue().isEmpty());
    }

    public CopyOnWriteArrayList<MethodBean> getEvent(Class<? extends Listener> type) {
        return events.get(type);
    }
}
