package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.container.EventContainer;
import com.google.common.collect.Maps;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Enaium
 */
public final class EventManager {


    private final HashMap<Class<?>, CopyOnWriteArrayList<EventBean>> events;

    public final EventContainer eventContainer = new EventContainer() {

        @Override
        public void register(Object instance) {
            EventManager.this.register(instance);
        }

        @Override
        public void unregister(Object instance) {
            EventManager.this.unregister(instance);
        }

        @Override
        public void call(Object instance) {
            if (events.get(instance.getClass()) != null) {
                for (EventBean event : events.get(instance.getClass())) {
                    try {
                        event.target.invoke(event.instance, instance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    public EventManager() {
        events = Maps.newHashMap();
    }
    
    private void register(Object instance) {
        Class<?> klass = instance.getClass();

        for (Method method : klass.getDeclaredMethods()) {
            if (method.getParameterTypes().length == 1 && method.isAnnotationPresent(Event.class)) {
                method.setAccessible(true);
                Class<?> listener = method.getParameterTypes()[0];
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

        events.values().forEach(eventBeans -> eventBeans.sort(((o1, o2) -> (o2.priority - o1.priority))));
    }


    private void unregister(Object instance) {
        events.values().forEach(methodBeans -> methodBeans.removeIf(methodEventBean -> methodEventBean.instance.equals(instance)));
        events.entrySet().removeIf(event -> event.getValue().isEmpty());
    }

    private static final class EventBean {
        private final Object instance;
        private final Method target;
        private final int priority;

        public EventBean(Object object, Method method, int priority) {
            this.instance = object;
            this.target = method;
            this.priority = priority;
        }
    }
}
