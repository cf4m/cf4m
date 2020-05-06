package cn.enaium.cf4m;

import cn.enaium.cf4m.event.EventManager;
import cn.enaium.cf4m.module.ModuleManager;
import cn.enaium.cf4m.setting.SettingManager;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class CF4M {

    private static CF4M instance;

    public EventManager eventManager;
    public ModuleManager moduleManager;
    public SettingManager settingManager;

    public CF4M() {
        instance = this;
    }

    public void start() {
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        settingManager = new SettingManager();
    }

    public void stop() {

    }

    public static CF4M getInstance() {
        return instance;
    }
}
