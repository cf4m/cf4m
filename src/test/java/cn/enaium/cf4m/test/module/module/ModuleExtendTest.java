package cn.enaium.cf4m.test.module.module;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.module.Disable;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.test.module.ModuleExtend;

/**
 * Project: cf4m
 * Author: Enaium
 */
@Module(value = "ModuleExtendTest", key = 2)
public class ModuleExtendTest {

    private ModuleExtend moduleExtend;

    @Enable
    public void enable() {
        moduleExtend = CF4M.module.getExtend(this);
        moduleExtend.tag = "tag1";
        moduleExtend.age = 1;
        moduleExtend.fun = () -> System.out.println("FUN1");
    }

    @Disable
    public void disable() {
        System.out.println(moduleExtend);
        System.out.println(moduleExtend.tag);
        System.out.println(moduleExtend.age);
        moduleExtend.fun.on();
    }
}
