package cn.enaium.cf4m.setting;

import cn.enaium.cf4m.module.ModuleBean;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class Setting {

    /**
     * Module.
     */
    private ModuleBean moduleBean;

    /**
     * Name.
     */
    private String name;

    /**
     * Info.
     */
    private String info;

    /**
     * @param moduleBean Setting module
     * @param name   Setting module
     * @param info   Setting module
     */
    public Setting(ModuleBean moduleBean, String name, String info) {
        this.moduleBean = moduleBean;
        this.name = name;
        this.info = info;
    }

    /**
     * @return Module
     */
    public ModuleBean getModule() {
        return moduleBean;
    }

    public void setModule(ModuleBean moduleBean) {
        this.moduleBean = moduleBean;
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

    public void setInfo(String info) {
        this.info = info;
    }
}
