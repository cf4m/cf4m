package cn.enaium.cf4m;

import cn.enaium.cf4m.command.CommandManager;
import cn.enaium.cf4m.config.ConfigManager;
import cn.enaium.cf4m.event.EventManager;
import cn.enaium.cf4m.module.ModuleManager;
import cn.enaium.cf4m.setting.SettingManager;

import java.io.File;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class CF4M {
    /**
     * Instance.
     */
    private static CF4M instance;

    /**
     * Client package.
     */
    public String packName;

    /**
     * .minecraft/{clientName} path.
     */
    public String clientDataDir;

    /**
     * EventManager.
     */
    public EventManager eventManager;

    /**
     * ModuleManager.
     */
    public ModuleManager moduleManager;

    /**
     * SettingManager.
     */
    public SettingManager settingManager;

    /**
     * CommandManager.
     */
    public CommandManager commandManager;

    /**
     * ConfigManager.
     */
    public ConfigManager configManager;

    /**
     * @param o             MainClass.
     * @param clientDataDir .minecraft/{clientName} path.
     */
    public CF4M(Object o, String clientDataDir) {
        instance = this;
        this.packName = o.getClass().getPackage().getName();
        this.clientDataDir = clientDataDir;
    }

    /**
     * Start.
     */
    public void start() {
        new File(CF4M.getInstance().clientDataDir).mkdir();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        settingManager = new SettingManager();
        commandManager = new CommandManager();
        configManager = new ConfigManager();
        configManager.load();
    }

    /**
     * Stop.
     */
    public void stop() {
        configManager.save();
    }

    /**
     * @return Instance.
     */
    public static CF4M getInstance() {
        return instance;
    }
}
