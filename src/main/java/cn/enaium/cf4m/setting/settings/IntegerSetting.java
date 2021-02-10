package cn.enaium.cf4m.setting.settings;

import cn.enaium.cf4m.module.ModuleBean;
import cn.enaium.cf4m.setting.Setting;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class IntegerSetting extends Setting {

    private Integer current;
    private Integer min;
    private Integer max;

    public IntegerSetting(ModuleBean moduleBean, String name, String info, Integer current, Integer min, Integer max) {
        super(moduleBean, name, info);
        this.current = current;
        this.min = min;
        this.max = max;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
}
