package cn.enaium.cf4m.test.event

import cn.enaium.cf4m.test.event.event.EventCancelEventTest
import cn.enaium.cf4m.CF4M.CF4M
import cn.enaium.cf4m.annotation.Event

/**
 * Project: cf4m
 * Author: Enaium
 */
class EventCancelTest {
    @Event
    fun onUpdate(event: EventCancelEventTest) {
        event.cancel = true
        println("Event")
    }

    init {
        CF4M.event.register(this)
        val eventCancelEventTest = EventCancelEventTest()
        if (!eventCancelEventTest.cancel) {
            CF4M.event.call(eventCancelEventTest);
        }
        if (!eventCancelEventTest.cancel) {
            CF4M.event.call(eventCancelEventTest);
        }
    }
}