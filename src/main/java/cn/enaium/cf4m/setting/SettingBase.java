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
    private Object module;

    /**
     * Name.
     */
    private String name;

    /**
     * Info.
     */
    private String info;

    /**
     * @param module Setting module
     * @param name   Setting module
     * @param info   Setting module
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

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }
}
