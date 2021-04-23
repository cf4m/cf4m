package cn.enaium.cf4m.builder;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.container.ClassContainer;
import cn.enaium.cf4m.container.EventContainer;
import cn.enaium.cf4m.processor.EventProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Enaium
 */
public final class EventBuilder {


    private final ConcurrentHashMap<Class<?>, CopyOnWriteArrayList<EventBean>> events;

    public final EventContainer eventContainer;

    public EventBuilder(ClassContainer classContainer) {
        events = new ConcurrentHashMap<>();
        ArrayList<EventProcessor> processors = classContainer.getProcessor(EventProcessor.class);
        eventContainer = new EventContainer() {
            @Override
            public void register(Object instance) {
                processors.forEach(eventProcessor -> eventProcessor.beforeRegister(instance));
                EventBuilder.this.register(instance);
                processors.forEach(eventProcessor -> eventProcessor.afterRegister(instance));
            }

            @Override
            public void unregister(Object instance) {
                processors.forEach(eventProcessor -> eventProcessor.beforeUnregister(instance));
                EventBuilder.this.unregister(instance);
                processors.forEach(eventProcessor -> eventProcessor.afterUnregister(instance));
            }

            @Override
            public void post(Object instance) {
                if (events.get(instance.getClass()) != null) {
                    for (EventBean event : events.get(instance.getClass())) {
                        try {
                            processors.forEach(eventProcessor -> eventProcessor.beforePost(event.target, event.instance));
                            event.target.invoke(event.instance, instance);
                            processors.forEach(eventProcessor -> eventProcessor.afterPost(event.target, event.instance));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
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
