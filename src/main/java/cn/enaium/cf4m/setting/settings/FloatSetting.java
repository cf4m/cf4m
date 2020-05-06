package cn.enaium.cf4m.setting.settings;

import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.setting.Setting;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class FloatSetting extends Setting {

    private Float current;
    private Float min;
    private Float max;

    public FloatSetting(Module module, String name, String info, Float current, Float min, Float max) {
        super(module, name, info);
        this.current = current;
        this.min = min;
        this.max = max;
    }

    public Float getCurrent() {
        return current;
    }

    public void setCurrent(Float current) {
        this.current = current;
    }

    public Float getMin() {
        return min;
    }

    public void setMin(Float min) {
        this.min = min;
    }

    public Float getMax() {
        return max;
    }

    public void setMax(Float max) {
        this.max = max;
    }
}
