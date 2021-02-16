package cn.enaium.cf4m.test.module.modules;

import cn.enaium.cf4m.annotation.Setting;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.setting.settings.EnableSetting;
import cn.enaium.cf4m.setting.settings.IntegerSetting;
import cn.enaium.cf4m.test.settings.BlockListSetting;

import java.util.Arrays;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module(value = "TestModule3", category = Category.COMBAT)
public class TestModule3 {
    @Setting
    private BlockListSetting test2 = new BlockListSetting(this, "test1", "test1", Arrays.asList("1","2"));
}
