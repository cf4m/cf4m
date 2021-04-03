package cn.enaium.cf4m.test.event

import cn.enaium.cf4m.test.event.event.EventCancelEventTest
import cn.enaium.cf4m.CF4M
import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.test.event.event.EventPriorityEventTest
import cn.enaium.cf4m.test.event.event.EventRegisterUnregisterEventTest

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
        CF4M.instance.event.register(this)
        val eventCancelEventTest = EventCancelEventTest()
        if (!eventCancelEventTest.cancel) {
            eventCancelEventTest.call()
        }
        if (!eventCancelEventTest.cancel) {
            eventCancelEventTest.call()
        }
    }
}