package cn.enaium.cf4m.test;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.setting.Setting;
import cn.enaium.cf4m.test.module.TestValue;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class TestCF4M {
    public static void main(String[] args) throws IllegalAccessException {
        new TestCF4M().run();
    }

    public void run() throws IllegalAccessException {
        CF4M cf4M = new CF4M(this, null);
        cf4M.run();
        System.out.println("Test modules==========");
        for (Object module : CF4M.getInstance().module.getModules()) {
            System.out.println(module);
        }

        System.out.println("Test settings==========");
        for (Setting setting : CF4M.getInstance().module.getSettings()) {
            System.out.println(setting.getName() + "|" + setting.getModule());
        }

        System.out.println("Test settings for module==========");
        for (Setting setting : CF4M.getInstance().module.getSettings("TestModule2")) {
            System.out.println(setting.getName() + "|" + setting.getModule());
        }

        System.out.println("Test set value==========");
        for (Object module : CF4M.getInstance().module.getModules()) {
            if (CF4M.getInstance().module.getName(module).equals("TestModule")) {
                CF4M.getInstance().module.setValue(module, "AnyName", true);
                CF4M.getInstance().module.setValue(module, "AnyName2", 777);
                CF4M.getInstance().module.setValue(module, "AnyName3", new TestValue("77", 13));
            }
        }
        System.out.println("Test set value^^^^^^^^^^");

        System.out.println("Test get value==========");
        for (Object module : CF4M.getInstance().module.getModules()) {
            System.out.println(CF4M.getInstance().module.<Boolean>getValue(module, "AnyName"));
            System.out.println(CF4M.getInstance().module.<Integer>getValue(module, "AnyName2"));
            System.out.println(CF4M.getInstance().module.<TestValue>getValue(module, "AnyName3"));
        }
    }
}
