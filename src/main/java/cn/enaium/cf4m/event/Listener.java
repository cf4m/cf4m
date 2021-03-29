package cn.enaium.cf4m.event;

import cn.enaium.cf4m.CF4M;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class Listener {
    /**
     * Call all event
     */
    public void call() {
        CopyOnWriteArrayList<EventBean> eventBeans = CF4M.event.getEvent(this.getClass());

        if (eventBeans == null) {
            return;
        }

        for (EventBean event : eventBeans) {
            try {
                event.getTarget().invoke(event.getInstance(), this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
