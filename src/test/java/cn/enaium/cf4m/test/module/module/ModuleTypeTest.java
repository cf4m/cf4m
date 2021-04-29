package cn.enaium.cf4m.test.module.module;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;

/**
 * @author Enaium
 */
@Module(value = "ModuleTypeTest", key = 5)
public class ModuleTypeTest {
    @Enable
    public void enable() {
        for (String s : CF4M.MODULE.getAllType()) {
            System.out.println(s);
        }
    }
}
