package cn.enaium.cf4m.test.module;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.provider.ModuleProvider;
import org.junit.jupiter.api.Assertions;

/**
 * @author Enaium
 */
public class ModuleBeanTest {
    public ModuleBeanTest() {
        for (ModuleProvider moduleProvider : CF4M.INSTANCE.getModule().getAll()) {
            System.out.println(moduleProvider.getName());
        }
        Assertions.assertNotNull(CF4M.INSTANCE.getModule().getAll());
    }
}
