package cn.enaium.cf4m.setting.settings;

import cn.enaium.cf4m.module.ModuleBean;
import cn.enaium.cf4m.setting.Setting;

import java.util.List;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class ModeSetting extends Setting {

    private String current;
    private List<String> modes;

    public ModeSetting(Object module, String name, String info, String current, List<String> modes) {
        super(module, name, info);
        this.current = current;
        this.modes = modes;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public List<String> getModes() {
        return modes;
    }
}
