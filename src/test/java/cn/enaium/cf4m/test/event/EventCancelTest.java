package cn.enaium.cf4m.test.event;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.test.event.event.EventCancelEventTest;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class EventCancelTest {

    public EventCancelTest() {
        CF4M.INSTANCE.event.register(this);
        EventCancelEventTest eventCancelEventTest = new EventCancelEventTest();
        if (!eventCancelEventTest.getCancel()) {
            eventCancelEventTest.call();
        }

        if (!eventCancelEventTest.getCancel()) {
            eventCancelEventTest.call();
        }
    }

    @Event
    public void onUpdate(EventCancelEventTest event) {
        event.setCancel(true);
        System.out.println("Event");
    }
}
