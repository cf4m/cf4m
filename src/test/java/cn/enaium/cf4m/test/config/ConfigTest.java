package cn.enaium.cf4m.test.config;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.provider.ConfigProvider;
import org.junit.jupiter.api.Assertions;

/**
 * @author Enaium
 */
public class ConfigTest {
    public ConfigTest() {
        Assertions.assertNotEquals(0, CF4M.INSTANCE.getConfig().getAll().size());
        for (ConfigProvider configProvider : CF4M.INSTANCE.getConfig().getAll()) {
            System.out.println(configProvider.getName() + "|" + configProvider.getPath());
        }
    }
}
