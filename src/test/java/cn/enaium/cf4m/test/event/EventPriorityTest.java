package cn.enaium.cf4m.test.event;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.test.event.event.EventPriorityEventTest;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class EventPriorityTest {
    public EventPriorityTest() {
        CF4M.event.register(this);
        new EventPriorityEventTest().call();
    }

    @Event(priority = 0)
    public void onUpdate0(EventPriorityEventTest event) {
        System.out.println("Event 0");
    }

    @Event(priority = 1)
    public void onUpdate1(EventPriorityEventTest event) {
        System.out.println("Event 1");
    }

    @Event(priority = 2)
    public void onUpdate2(EventPriorityEventTest event) {
        System.out.println("Event 2");
    }

    @Event(priority = 3)
    public void onUpdate3(EventPriorityEventTest event) {
        System.out.println("Event 3");
    }

    @Event(priority = 4)
    public void onUpdate4(EventPriorityEventTest event) {
        System.out.println("Event 4");
    }

    @Event(priority = 5)
    public void onUpdate5(EventPriorityEventTest event) {
        System.out.println("Event 5");
    }

    @Event(priority = 6)
    public void onUpdate6(EventPriorityEventTest event) {
        System.out.println("Event 6");
    }
}
