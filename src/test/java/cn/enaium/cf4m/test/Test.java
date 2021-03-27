package cn.enaium.cf4m.test;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.test.event.EventCancelTest;
import cn.enaium.cf4m.test.event.EventPriorityTest;
import cn.enaium.cf4m.test.event.EventRegisterUnregisterTest;
import cn.enaium.cf4m.test.module.ModuleBeanTest;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class Test {
    public static void main(String[] args) {
        CF4M.run(Test.class);
        System.out.println("Check ModuleBeanTest Start");
        new ModuleBeanTest();
        System.out.println("Check ModuleBeanTest End");
        System.out.println();
        System.out.println("Check EventRegisterUnregister Start");
        new EventRegisterUnregisterTest();
        System.out.println("Check EventRegisterUnregister End");
        System.out.println();
        System.out.println("Check EventPriorityTest Start");
        new EventPriorityTest();
        System.out.println("Check EventPriorityTest End");
        System.out.println();
        System.out.println("Check EventCancelTest Start");
        new EventCancelTest();
        System.out.println("Check EventCancelTest End");
        System.out.println();
        System.out.println("Check ModuleEnableDisableTest Start");
        CF4M.module.onKey(1);
        CF4M.module.onKey(1);
        System.out.println("Check ModuleEnableDisableTest End");
        System.out.println();
        System.out.println("Check ModuleExtendTest Start");
        CF4M.module.onKey(2);
        CF4M.module.onKey(2);
        System.out.println("Check ModuleExtendTest1 End");
        System.out.println();
        CF4M.module.onKey(3);
        CF4M.module.onKey(3);
        System.out.println("Check ModuleExtendTest1 End");
        System.out.println();
        System.out.println("Check CommandTest Start");
        System.out.println(CF4M.command.isCommand("`t var11111"));
        System.out.println("Check CommandTest End");
        System.out.println();
    }
}
