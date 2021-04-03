package cn.enaium.cf4m.test.config;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.provider.ConfigProvider;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class ConfigTest {
    public ConfigTest() {
        assertNotEquals(0, CF4M.getConfig().getAll().size());
        for (ConfigProvider configProvider : CF4M.getConfig().getAll()) {
            System.out.println(configProvider.getName() + "|" + configProvider.getPath());
        }
    }
}
