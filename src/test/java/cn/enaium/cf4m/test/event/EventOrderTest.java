package cn.enaium.cf4m.test.event;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.test.event.event.EventOrderEventTest;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class EventOrderTest {
    public EventOrderTest() {
        CF4M.INSTANCE.event.register(this);
        new EventOrderEventTest().call();
    }

    @Event(priority = 0)
    public void onUpdate0(EventOrderEventTest event) {
        System.out.println("Event 0");
    }

    @Event(priority = 1)
    public void onUpdate1(EventOrderEventTest event) {
        System.out.println("Event 1");
    }

    @Event(priority = 2)
    public void onUpdate2(EventOrderEventTest event) {
        System.out.println("Event 2");
    }

    @Event(priority = 3)
    public void onUpdate3(EventOrderEventTest event) {
        System.out.println("Event 3");
    }

    @Event(priority = 4)
    public void onUpdate4(EventOrderEventTest event) {
        System.out.println("Event 4");
    }

    @Event(priority = 5)
    public void onUpdate5(EventOrderEventTest event) {
        System.out.println("Event 5");
    }

    @Event(priority = 6)
    public void onUpdate6(EventOrderEventTest event) {
        System.out.println("Event 6");
    }
}
