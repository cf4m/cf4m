package com.test;

import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;

/**
 * @author Enaium
 */
@Module(value = "ModuleScanTest", key = 7)
public class ModuleScanTest {
    @Enable
    public void enable() {
        System.out.println("ModuleScanTest");
    }
}
