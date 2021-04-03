package cn.enaium.cf4m.test.event;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.test.event.event.EventRegisterUnregisterEventTest;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class EventRegisterUnregisterTest {
    public EventRegisterUnregisterTest() {
        CF4M.getEvent().register(this);
        new EventRegisterUnregisterEventTest().call();
        CF4M.getEvent().unregister(this);
        new EventRegisterUnregisterEventTest().call();
    }

    @Event
    private void onUpdate(EventRegisterUnregisterEventTest event) {
        System.out.println("FIRE");
    }
}
