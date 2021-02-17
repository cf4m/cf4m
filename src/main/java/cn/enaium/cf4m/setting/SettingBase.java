package cn.enaium.cf4m.setting;

import cn.enaium.cf4m.module.ModuleBean;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class SettingBase {

    /**
     * Module.
     */
    private final Object module;

    /**
     * Name.
     */
    private final String name;

    /**
     * Info.
     */
    private final String info;

    /**
     * @param module Setting module
     * @param name   Setting name
     * @param info   Setting info
     */
    public SettingBase(Object module, String name, String info) {
        this.module = module;
        this.name = name;
        this.info = info;
    }

    /**
     * @return Module
     */
    public Object getModule() {
        return module;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }
}
