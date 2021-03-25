package cn.enaium.cf4m.test;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.event.KeyboardEvent;
import cn.enaium.cf4m.test.event.EventCancelTest;
import cn.enaium.cf4m.test.event.EventOrderTest;
import cn.enaium.cf4m.test.event.EventRegisterUnregisterTest;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class Test {
    public static void main(String[] args) {
        CF4M.INSTANCE.run(Test.class);
        System.out.println("Check EventRegisterUnregister Start");
        new EventRegisterUnregisterTest();
        System.out.println("Check EventRegisterUnregister End");
        System.out.println();
        System.out.println("Check EventOrderTest Start");
        new EventOrderTest();
        System.out.println("Check EventOrderTest End");
        System.out.println();
        System.out.println("Check EventCancelTest Start");
        new EventCancelTest();
        System.out.println("Check EventCancelTest End");
        System.out.println();
        System.out.println("Check ModuleEnableDisableTest Start");
        new KeyboardEvent(1).call();
        new KeyboardEvent(1).call();
        System.out.println("Check ModuleEnableDisableTest End");
        System.out.println();
        System.out.println("Check ModuleExtendTest Start");
        new KeyboardEvent(2).call();
        new KeyboardEvent(2).call();
        System.out.println("Check ModuleExtendTest1 End");
        System.out.println();
        new KeyboardEvent(3).call();
        new KeyboardEvent(3).call();
        System.out.println("Check ModuleExtendTest1 End");
        System.out.println();
    }
}
