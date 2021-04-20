package cn.enaium.cf4m.test.event;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.test.event.event.EventRegisterUnregisterEventTest;

import static cn.enaium.cf4m.CF4M.INSTANCE;

/**
 * @author Enaium
 */
public class EventRegisterUnregisterTest {

    public EventRegisterUnregisterTest() {
        INSTANCE.getEvent().register(this);
        INSTANCE.getEvent().post(new EventRegisterUnregisterEventTest());
        INSTANCE.getEvent().unregister(this);
        INSTANCE.getEvent().post(new EventRegisterUnregisterEventTest());
    }

    @Event
    private void onUpdate(EventRegisterUnregisterEventTest event) {
        System.out.println("FIRE");
    }
}
