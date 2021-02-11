package cn.enaium.cf4m.setting.settings;

import cn.enaium.cf4m.module.ModuleBean;
import cn.enaium.cf4m.setting.Setting;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class LongSetting extends Setting {

    private Long current;
    private Long min;
    private Long max;

    public LongSetting(Object module, String name, String info, Long current, Long min, Long max) {
        super(module, name, info);
        this.current = current;
        this.min = min;
        this.max = max;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }
}
