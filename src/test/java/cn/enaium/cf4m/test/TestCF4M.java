package cn.enaium.cf4m.test;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.module.ModuleBean;
import cn.enaium.cf4m.module.ValueBean;

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

        for (ModuleBean moduleBean : CF4M.getInstance().module.getModules()) {
            System.out.println("Module============");
            System.out.println(moduleBean.getName());
            System.out.println("Values============");
            for (ValueBean valueBean : moduleBean.getValueBeans()) {
                if (moduleBean.getName().equals("TestModule2")) {
                    if (valueBean.getField().getType().getName().equals("java.lang.Boolean")) {
                        valueBean.getField().set(valueBean.getObject(), true);
                    } else if (valueBean.getField().getType().getName().equals("java.lang.Integer")) {
                        valueBean.getField().set(valueBean.getObject(), 23333);
                    }
                }

                System.out.println(valueBean.getName() + "|" + valueBean.getField().get(valueBean.getObject()));
            }
        }
    }
}
