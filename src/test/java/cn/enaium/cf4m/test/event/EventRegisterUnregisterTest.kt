package cn.enaium.cf4m.test.event

import cn.enaium.cf4m.CF4M.CF4M
import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.test.event.event.EventRegisterUnregisterEventTest

/**
 * Project: cf4m
 * Author: Enaium
 */
class EventRegisterUnregisterTest {
    @Event
    private fun onUpdate(event: EventRegisterUnregisterEventTest) {
        println("FIRE")
    }

    init {
        CF4M.event.register(this)
        CF4M.event.call(EventRegisterUnregisterEventTest())
        CF4M.event.unregister(this)
        CF4M.event.call(EventRegisterUnregisterEventTest())
    }
}