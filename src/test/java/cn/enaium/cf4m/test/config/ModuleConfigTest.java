package cn.enaium.cf4m.test.config;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.config.Config;
import cn.enaium.cf4m.annotation.config.Load;
import cn.enaium.cf4m.annotation.config.Save;
import cn.enaium.cf4m.provider.ConfigProvider;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project: cf4m
 * Author: Enaium
 */
@Config("Module")
public class ModuleConfigTest {
    @Save
    public void save() {
        ConfigProvider configProvider = CF4M.config.getByInstance(this);
        assertNotNull(configProvider);
        assertEquals("Module", configProvider.getName());
        System.out.println(configProvider.getName() + " Save");
    }
}
