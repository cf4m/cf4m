package cn.enaium.cf4m.setting;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class SettingBase {

    /**
     * Name.
     */
    private final String name;

    /**
     * @param name Setting name
     */
    public SettingBase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
