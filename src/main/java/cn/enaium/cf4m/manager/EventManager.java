package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.event.Listener;
import cn.enaium.cf4m.event.MethodBean;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.lang.reflect.Method;
import java.util.Collection;
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
    private final Multimap<Class<? extends Listener>, MethodBean> events;

    public EventManager() {
        events = ArrayListMultimap.create();
    }

    /**
     * Register all event
     *
     * @param o Object
     */
    public void register(Object o) {
        Class<?> type = o.getClass();

        for (Method method : type.getDeclaredMethods()) {
            if (method.getParameterTypes().length == 1 && method.isAnnotationPresent(Event.class)) {
                method.setAccessible(true);
                @SuppressWarnings("unchecked")
                Class<? extends Listener> listener = (Class<? extends Listener>) method.getParameterTypes()[0];
                events.put(listener, new MethodBean(o, method));
            }
        }
    }

    /**
     * Unregister all event
     *
     * @param o Object
     */
    public void unregister(Object o) {
        events.asMap().values().forEach(methodBeans -> methodBeans.removeIf(methodMethodBean -> methodMethodBean.getObject().equals(o)));
        events.asMap().entrySet().removeIf(event -> event.getValue().isEmpty());
    }

    public Collection<MethodBean> getEvent(Class<? extends Listener> type) {
        return events.get(type);
    }
}
