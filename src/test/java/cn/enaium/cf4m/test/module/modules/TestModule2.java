package cn.enaium.cf4m.test.module.modules;

import cn.enaium.cf4m.annotation.Setting;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.setting.settings.*;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module(value = "TestModule2", category = Category.COMBAT)
public class TestModule2 {
    @Setting
    private EnableSetting test1 = new EnableSetting(this, "test1", "test1", false);

    @Setting
    private IntegerSetting test2 = new IntegerSetting(this, "test1", "test1", 1, 1, 1);
}
