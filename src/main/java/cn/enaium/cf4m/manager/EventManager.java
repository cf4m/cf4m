package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.event.MethodBean;
import cn.enaium.cf4m.event.EventBase;
import cn.enaium.cf4m.annotation.Event;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class EventManager {

    private final HashMap<Class<? extends EventBase>, CopyOnWriteArrayList<MethodBean>> REGISTRY_MAP;

    public EventManager() {
        REGISTRY_MAP = Maps.newHashMap();
    }

    public void register(Object o) {

        Arrays.stream(o.getClass().getDeclaredMethods()).forEach(method -> {
            if (!isMethodBad(method))
                register(method, o);
        });

        REGISTRY_MAP.values().forEach(flexibleArray -> flexibleArray.sort((Comparator.comparingInt(o2 -> o2.getPriority().getValue()))));
    }

    private void register(Method method, Object o) {

        @SuppressWarnings("unchecked")
        Class<? extends EventBase> clazz = (Class<? extends EventBase>) method.getParameterTypes()[0];

        MethodBean methodBean = new MethodBean(o, method, method.getAnnotation(Event.class).priority());

        if (!methodBean.getTarget().isAccessible())
            methodBean.getTarget().setAccessible(true);


        if (REGISTRY_MAP.containsKey(clazz)) {
            if (!REGISTRY_MAP.get(clazz).contains(methodBean))
                REGISTRY_MAP.get(clazz).add(methodBean);
        } else {
            REGISTRY_MAP.put(clazz, new CopyOnWriteArrayList<>(Collections.singletonList(methodBean)));
        }
    }

    public void unregister(Object o) {
        REGISTRY_MAP.values().forEach(flexibleArray -> flexibleArray.removeIf(methodMethodBean -> methodMethodBean.getSource().equals(o)));
        REGISTRY_MAP.entrySet().removeIf(hashSetEntry -> hashSetEntry.getValue().isEmpty());
    }

    private boolean isMethodBad(Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(Event.class);
    }

    public CopyOnWriteArrayList<MethodBean> get(Class<? extends EventBase> clazz) {
        return REGISTRY_MAP.get(clazz);
    }

}
