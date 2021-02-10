package cn.enaium.cf4m.setting.settings;

import cn.enaium.cf4m.module.ModuleBean;
import cn.enaium.cf4m.setting.Setting;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class EnableSetting extends Setting {

    private boolean enable;

    public EnableSetting(ModuleBean moduleBean, String name, String info, boolean enable) {
        super(moduleBean, name, info);
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
