package cn.enaium.cf4m.test.event;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.test.event.event.EventCancelEventTest;

/**
 * @author Enaium
 */
public class EventCancelTest {
    public EventCancelTest() {
        CF4M.EVENT.register(this);
        EventCancelEventTest eventCancelEventTest = new EventCancelEventTest();
        if (!eventCancelEventTest.cancel) {
            CF4M.EVENT.post(eventCancelEventTest);
        }
        if (!eventCancelEventTest.cancel) {
            CF4M.EVENT.post(eventCancelEventTest);
        }
    }

    @Event
    public void onUpdate(EventCancelEventTest event) {
        event.cancel = true;
        System.out.println("Event");
    }
}
