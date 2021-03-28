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
@Module(value = "ModuleExtendTest1", key = 3)
public class ModuleExtendTest1 {

    private ModuleExtend moduleExtend;

    @Enable
    public void enable() {
        moduleExtend = CF4M.module.getByInstance(this).getExtend();
        moduleExtend.tag = "tag2";
        moduleExtend.age = 2;
        moduleExtend.fun = () -> System.out.println("FUN2");
    }

    @Disable
    public void disable() {
        System.out.println(moduleExtend);
        System.out.println(moduleExtend.tag);
        System.out.println(moduleExtend.age);
        moduleExtend.fun.on();
    }
}
