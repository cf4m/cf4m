package cn.enaium.cf4m.setting.settings;

import cn.enaium.cf4m.setting.SettingBase;

import java.util.List;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ModeSetting extends SettingBase {

    private String current;
    private final List<String> modes;

    public ModeSetting(String name, String current, List<String> modes) {
        super(name);
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

    public int getCurrentModeIndex() {
        int index = 0;
        for (String s : modes) {
            if (s.equalsIgnoreCase(current)) {
                return index;
            }
            index++;
        }
        return index;
    }
}
