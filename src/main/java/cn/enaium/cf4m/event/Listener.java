package cn.enaium.cf4m.event;

import cn.enaium.cf4m.CF4M;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class Listener {

    private final At at;
    private boolean cancel;

    public Listener(At at) {
        this.at = at;
        cancel = false;
    }

    /**
     * Call all event
     */
    public void call() {
        cancel = false;

        CopyOnWriteArrayList<EventBean> eventBeans = CF4M.INSTANCE.event.getEvent(this.getClass());

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

    public boolean getCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public At getAt() {
        return at;
    }

    /**
     * At head or tail
     */
    public enum At {
        HEAD,
        TAIL,
        NONE
    }
}
