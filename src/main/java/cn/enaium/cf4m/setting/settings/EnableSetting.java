package cn.enaium.cf4m.setting.settings;

import cn.enaium.cf4m.setting.SettingBase;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class EnableSetting extends SettingBase {

    private boolean enable;

    public EnableSetting(Object module, String name, String info, boolean enable) {
        super(module, name, info);
        this.enable = enable;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
