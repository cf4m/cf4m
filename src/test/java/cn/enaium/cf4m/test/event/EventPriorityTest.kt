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
class EventPriorityTest {
    @Event(priority = 0)
    fun onUpdate0(event: EventPriorityEventTest) {
        println("Event 0")
    }

    @Event(priority = 1)
    fun onUpdate1(event: EventPriorityEventTest) {
        println("Event 1")
    }

    @Event(priority = 2)
    fun onUpdate2(event: EventPriorityEventTest) {
        println("Event 2")
    }

    @Event(priority = 3)
    fun onUpdate3(event: EventPriorityEventTest) {
        println("Event 3")
    }

    @Event(priority = 4)
    fun onUpdate4(event: EventPriorityEventTest) {
        println("Event 4")
    }

    @Event(priority = 5)
    fun onUpdate5(event: EventPriorityEventTest) {
        println("Event 5")
    }

    @Event(priority = 6)
    fun onUpdate6(event: EventPriorityEventTest) {
        println("Event 6")
    }

    init {
        CF4M.instance.event.register(this)
        EventPriorityEventTest().call()
    }
}