package cn.enaium.cf4m.test.module.module;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Setting;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.provider.SettingProvider;
import cn.enaium.cf4m.test.setting.EnableSettingTest;

/**
 * Project: cf4m
 * Author: Enaium
 */
@Module(value = "ModuleSettingTest", key = 4)
public class ModuleSettingTest {

    @Setting("setting1")
    private final EnableSettingTest enableSettingTest = new EnableSettingTest(true);

    @Enable
    public void enable() {
        System.out.println(enableSettingTest.getEnable());
        System.out.println(CF4M.module.getByInstance(this).getSetting().<EnableSettingTest>getByName("setting1").getEnable());
        for (SettingProvider settingProvider : CF4M.module.getByInstance(this).getSetting().getAll()) {
            if (settingProvider.getSetting() instanceof EnableSettingTest) {
                System.out.println(settingProvider.getName());
            }
        }
    }
}
