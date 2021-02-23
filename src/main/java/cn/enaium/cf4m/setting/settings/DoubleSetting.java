package cn.enaium.cf4m.setting.settings;

import cn.enaium.cf4m.setting.SettingBase;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class DoubleSetting extends SettingBase {

    private Double current;
    private Double min;
    private Double max;

    public DoubleSetting(String name, String info, Double current, Double min, Double max) {
        super(name, info);
        this.current = current;
        this.min = min;
        this.max = max;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }
}
