package cn.enaium.cf4m.test.module.module;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.annotation.module.Setting;
import cn.enaium.cf4m.provider.SettingProvider;
import cn.enaium.cf4m.test.setting.EnableSettingTest;

/**
 * @author Enaium
 */
@Module(value = "ModuleSettingTest", key = 4)
public class ModuleSettingTest {
    @Setting("setting1")
    private final EnableSettingTest enableSettingTest = new EnableSettingTest(true);

    @Setting("setting2")
    private final Boolean bool = true;

    @Enable
    public void enable() {
        System.out.println(enableSettingTest.getEnable());
        System.out.println(CF4M.MODULE.getByInstance(this).getSetting().getByName("setting1").<EnableSettingTest>getSetting());
        Object setting2 = CF4M.MODULE.getByInstance(this).getSetting().getByName("setting2").getSetting();
        System.out.println(CF4M.MODULE.getByInstance(this).getSetting().getByName("setting2").<Boolean>getSetting());
        if (setting2 instanceof Boolean) {
            System.out.println(CF4M.MODULE.getByInstance(this).getSetting().getByName("setting2").<Boolean>setSetting(false));
            System.out.println(bool);
        }
        for (SettingProvider settingProvider : CF4M.MODULE.getByInstance(this).getSetting().getAll()) {
            if (settingProvider.getSetting() instanceof EnableSettingTest) {
                System.out.println(settingProvider.getName());
            }
        }
    }
}
