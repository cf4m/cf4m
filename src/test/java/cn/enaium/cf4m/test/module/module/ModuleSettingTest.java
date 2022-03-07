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

    @Setting
    private final EnableSettingTest enableSettingTest1 = new EnableSettingTest(true);

    @Setting
    private final EnableSettingTest y = new EnableSettingTest(true);

    @Setting("setting2")
    private final Boolean bool = true;

    @Enable
    public void enable() {
        System.out.println(CF4M.MODULE.get(this.getClass()).getName());
        System.out.println("enableSettingTest.getEnable() = " + enableSettingTest.getEnable());
        SettingProvider enable = CF4M.MODULE.get(this).getSetting().get("Enable");
        System.out.println("Enable = " + ((EnableSettingTest) enable.as()).getEnable());
        SettingProvider y = CF4M.MODULE.get(this).getSetting().get("Y");
        System.out.println("Y = " + ((EnableSettingTest) y.as()).getEnable());
        System.out.println(CF4M.MODULE.get(this).getSetting().get("setting1").<EnableSettingTest>as());
        Object setting2 = CF4M.MODULE.get(this).getSetting().get("setting2").as();
        System.out.println(CF4M.MODULE.get(this).getSetting().get("setting2").<Boolean>as());
        if (setting2 instanceof Boolean) {
            System.out.println(CF4M.MODULE.get(this).getSetting().get("setting2").<Boolean>set(false));
            System.out.println(bool);
        }
        for (SettingProvider settingProvider : CF4M.MODULE.get(this).getSetting().getAll()) {
            if (settingProvider.as() instanceof EnableSettingTest) {
                System.out.println(settingProvider.getName());
            }
        }
    }
}
