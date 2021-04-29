package cn.enaium.cf4m.test.event;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.test.event.event.EventRegisterUnregisterEventTest;

/**
 * @author Enaium
 */
public class EventRegisterUnregisterTest {

    public EventRegisterUnregisterTest() {
        CF4M.EVENT.register(this);
        CF4M.EVENT.post(new EventRegisterUnregisterEventTest());
        CF4M.EVENT.unregister(this);
        CF4M.EVENT.post(new EventRegisterUnregisterEventTest());
    }

    @Event
    private void onUpdate(EventRegisterUnregisterEventTest event) {
        System.out.println("FIRE");
    }
}
