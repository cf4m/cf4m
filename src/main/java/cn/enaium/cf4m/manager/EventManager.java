package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.event.Listener;
import cn.enaium.cf4m.event.EventBean;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    private final LinkedHashMap<Class<? extends Listener>, CopyOnWriteArrayList<EventBean>> events;

    public EventManager() {
        events = Maps.newLinkedHashMap();
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
                EventBean eventBean = new EventBean(o, method, method.getAnnotation(Event.class).priority());

                if (events.containsKey(listener)) {
                    if (!events.get(listener).contains(eventBean)) {
                        events.get(listener).add(eventBean);
                    }
                } else {
                    events.put(listener, new CopyOnWriteArrayList<>(Collections.singletonList(eventBean)));
                }

                events.values().forEach(methodBeans -> methodBeans.sort((Comparator.comparingInt(EventBean::getPriority))));
            }
        }
    }

    /**
     * Unregister all event
     *
     * @param o Object
     */
    public void unregister(Object o) {
        events.values().forEach(methodBeans -> methodBeans.removeIf(methodEventBean -> methodEventBean.getObject().equals(o)));
        events.entrySet().removeIf(event -> event.getValue().isEmpty());
    }

    public CopyOnWriteArrayList<EventBean> getEvent(Class<? extends Listener> type) {
        return events.get(type);
    }
}
