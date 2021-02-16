package cn.enaium.cf4m.test.settings;

import cn.enaium.cf4m.setting.SettingBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class BlockListSetting extends SettingBase {

    private final List<String> blockList;

    public BlockListSetting(Object module, String name, String info, List<String> blockList) {
        super(module, name, info);
        this.blockList = blockList;
    }
}
