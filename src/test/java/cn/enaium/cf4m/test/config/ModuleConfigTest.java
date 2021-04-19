package cn.enaium.cf4m.test.config;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.config.Config;
import cn.enaium.cf4m.annotation.config.Save;
import cn.enaium.cf4m.provider.ConfigProvider;
import org.junit.jupiter.api.Assertions;

/**
 * @author Enaium
 */
@Config("Module")
public class ModuleConfigTest {
    @Save
    public void save() {
        ConfigProvider configProvider = CF4M.INSTANCE.getConfig().getByInstance(this);
        Assertions.assertNotNull(configProvider);
        Assertions.assertEquals("Module", configProvider.getName());
        System.out.println(configProvider.getName() + " Save");
    }
}
