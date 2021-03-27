package cn.enaium.cf4m.test.module;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.module.ModuleBean;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class ModuleBeanTest {
    public ModuleBeanTest() {
        for (ModuleBean module : CF4M.module.getModules()) {
            System.out.println(module);
        }
    }
}
