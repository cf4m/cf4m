package cn.enaium.cf4m.test.event

import cn.enaium.cf4m.CF4M.INSTANCE
import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.test.event.event.EventRegisterUnregisterEventTest

/**
 * Project: cf4m
 *
 * @author Enaium
 */
class EventRegisterUnregisterTest {
    @Event
    private fun onUpdate(event: EventRegisterUnregisterEventTest) {
        println("FIRE")
    }

    init {
        INSTANCE.event.register(this)
        INSTANCE.event.call(EventRegisterUnregisterEventTest())
        INSTANCE.event.unregister(this)
        INSTANCE.event.call(EventRegisterUnregisterEventTest())
    }
}