package cn.enaium.cf4m.test.module.module;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.module.Disable;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.test.module.ModuleExtend;

/**
 * @author Enaium
 */
@Module(value = "ModuleExtendTest", key = 2)
public class ModuleExtendTest {
    private ModuleExtend moduleExtend;

    @Enable
    public void enable() {
        moduleExtend = CF4M.MODULE.getByInstance(this).getExtend(ModuleExtend.class);
        moduleExtend.tag = "tag1";
        moduleExtend.age = 1;
        moduleExtend.func = () -> System.out.println("FUN1");
    }

    @Disable
    public void disable() {
        System.out.println(moduleExtend);
        System.out.println(moduleExtend.tag);
        System.out.println(moduleExtend.age);
        moduleExtend.func.on();
    }
}
