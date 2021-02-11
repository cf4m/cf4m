package cn.enaium.cf4m.test.module.modules;

import cn.enaium.cf4m.annotation.SettingAT;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.setting.settings.DoubleSetting;
import cn.enaium.cf4m.setting.settings.FloatSetting;
import cn.enaium.cf4m.setting.settings.LongSetting;
import cn.enaium.cf4m.setting.settings.ModeSetting;

import java.util.Arrays;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module(value = "TestModule", category = Category.COMBAT)
public class TestModule {
    @SettingAT
    private FloatSetting test3 = new FloatSetting(this, "test1", "test1", 1.0F, 1.0F, 1.0F);

    @SettingAT
    private DoubleSetting test4 = new DoubleSetting(this, "test1", "test1", 1.0D, 1.0D, 1.0D);

    @SettingAT
    private LongSetting test5 = new LongSetting(this, "test1", "test1", 1L, 1L, 1L);

    @SettingAT
    private ModeSetting test6 = new ModeSetting(this, "test1", "test1", "Mode1", Arrays.asList("Mode1", "Mode2"));
}
