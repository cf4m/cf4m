package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.event.Listener;
import cn.enaium.cf4m.event.EventBean;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class EventManager {

    /**
     * <K> listener
     * <V> event
     */
    private final HashMap<Class<? extends Listener>, CopyOnWriteArrayList<EventBean>> events;

    public EventManager() {
        events = Maps.newHashMap();
    }

    /**
     * Register all event
     *
     * @param instance Class instance
     */
    public void register(Object instance) {
        Class<?> klass = instance.getClass();

        for (Method method : klass.getDeclaredMethods()) {
            if (method.getParameterTypes().length == 1 && method.isAnnotationPresent(Event.class)) {
                method.setAccessible(true);
                @SuppressWarnings("unchecked")
                Class<? extends Listener> listener = (Class<? extends Listener>) method.getParameterTypes()[0];
                EventBean eventBean = new EventBean(instance, method, method.getAnnotation(Event.class).priority());

                if (events.containsKey(listener)) {
                    if (!events.get(listener).contains(eventBean)) {
                        events.get(listener).add(eventBean);
                    }
                } else {
                    events.put(listener, new CopyOnWriteArrayList<>(Collections.singletonList(eventBean)));
                }
            }
        }

        events.values().forEach(eventBeans -> eventBeans.sort(((o1, o2) -> (o2.getPriority() - o1.getPriority()))));
    }

    /**
     * Unregister all event
     *
     * @param instance Class instance
     */
    public void unregister(Object instance) {
        events.values().forEach(methodBeans -> methodBeans.removeIf(methodEventBean -> methodEventBean.getInstance().equals(instance)));
        events.entrySet().removeIf(event -> event.getValue().isEmpty());
    }

    public CopyOnWriteArrayList<EventBean> getEvent(Class<? extends Listener> type) {
        return events.get(type);
    }
}
