package cn.enaium.cf4m.test.module.module;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.module.Disable;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.test.module.ModuleExtend;

/**
 * @author Enaium
 */
@Module(value = "ModuleExtendTest", key = 3)
public class ModuleExtendTest1 {
    private ModuleExtend moduleExtend;

    @Enable
    public void enable() {
        moduleExtend = CF4M.MODULE.get(this).getExtend(ModuleExtend.class);
        moduleExtend.tag = "tag2";
        moduleExtend.age = 2;
        moduleExtend.func = () -> System.out.println("FUN2");
    }

    @Disable
    public void disable() {
        System.out.println(moduleExtend);
        System.out.println(moduleExtend.tag);
        System.out.println(moduleExtend.age);
        moduleExtend.func.on();
    }
}
