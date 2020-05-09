package cn.enaium.cf4m.setting;

import cn.enaium.cf4m.module.Module;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class Setting {

    /**
     * Module.
     */
    private Module module;

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
    public Setting(Module module, String name, String info) {
        this.module = module;
        this.name = name;
        this.info = info;
    }

    /**
     * @return Module
     */
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
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
