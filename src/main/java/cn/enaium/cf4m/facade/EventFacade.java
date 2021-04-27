package cn.enaium.cf4m.facade;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.container.ClassContainer;
import cn.enaium.cf4m.container.EventContainer;
import cn.enaium.cf4m.service.EventService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Enaium
 */
public final class EventFacade {


    private final ConcurrentHashMap<Class<?>, CopyOnWriteArrayList<EventBean>> events;

    public final EventContainer eventContainer;

    public EventFacade(ClassContainer classContainer) {
        events = new ConcurrentHashMap<>();
        final ArrayList<EventService> processors = classContainer.getService(EventService.class);
        eventContainer = new EventContainer() {
            @Override
            public void register(Object instance) {
                processors.forEach(eventService -> eventService.beforeRegister(instance));
                EventFacade.this.register(instance);
                processors.forEach(eventService -> eventService.afterRegister(instance));
            }

            @Override
            public void unregister(Object instance) {
                processors.forEach(eventService -> eventService.beforeUnregister(instance));
                EventFacade.this.unregister(instance);
                processors.forEach(eventService -> eventService.afterUnregister(instance));
            }

            @Override
            public void post(Object instance) {
                if (events.containsKey(instance.getClass())) {
                    for (EventBean event : events.get(instance.getClass())) {
                        processors.forEach(eventService -> eventService.beforePost(event.target, event.instance));
                        new Thread(() -> {
                            try {
                                event.target.invoke(event.instance, instance);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }).start();
                        processors.forEach(eventService -> eventService.afterPost(event.target, event.instance));
                    }
                }
            }
        };
    }

    private void register(Object instance) {
        Class<?> klass = instance.getClass();

        for (Method method : klass.getDeclaredMethods()) {
            if (method.getParameterCount() == 1 && method.isAnnotationPresent(Event.class)) {
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
