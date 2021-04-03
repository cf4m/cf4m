package cn.enaium.cf4m.test.module;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.provider.ModuleProvider;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class ModuleBeanTest {
    public ModuleBeanTest() {
        for (ModuleProvider module : CF4M.getModule().getAll()) {
            System.out.println(module.getName());
        }
        assertNotNull(CF4M.getModule().getAll());
    }
}
