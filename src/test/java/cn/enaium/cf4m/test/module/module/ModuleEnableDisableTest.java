package cn.enaium.cf4m.test.module.module;

import cn.enaium.cf4m.annotation.module.Disable;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.annotation.module.Toggle;

/**
 * @author Enaium
 */
@Module(value = "ModuleEnableDisableTest", key = 1)
public class ModuleEnableDisableTest {
    @Enable
    public void enable() {
        System.out.println("ModuleTest Enable");
    }

    @Disable
    public void disable() {
        System.out.println("ModuleTest Disable");
    }

    @Toggle
    public void toggle() {
        System.out.println("Toggle");
    }
}
