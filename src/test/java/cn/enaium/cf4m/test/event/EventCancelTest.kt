package cn.enaium.cf4m.test.event

import cn.enaium.cf4m.test.event.event.EventCancelEventTest
import cn.enaium.cf4m.CF4M.INSTANCE
import cn.enaium.cf4m.annotation.Event

/**
 * Project: cf4m
 *
 * @author Enaium
 */
class EventCancelTest {
    @Event
    fun onUpdate(event: EventCancelEventTest) {
        event.cancel = true
        println("Event")
    }

    init {
        INSTANCE.event.register(this)
        val eventCancelEventTest = EventCancelEventTest()
        if (!eventCancelEventTest.cancel) {
            INSTANCE.event.call(eventCancelEventTest);
        }
        if (!eventCancelEventTest.cancel) {
            INSTANCE.event.call(eventCancelEventTest);
        }
    }
}