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
     * Info.
     */
    private final String info;

    /**
     * @param name Setting name
     * @param info Setting info
     */
    public SettingBase(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }
}
