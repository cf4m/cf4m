package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.manager.*;

import java.util.concurrent.TimeUnit;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public enum CF4M {

    INSTANCE;

    /**
     * Client package.
     */
    public String packName;

    /**
     * .minecraft/{clientName} path.
     */
    public String clientDataDir;

    /**
     * CF4M configuration
     */
    public IConfiguration configuration;

    /**
     * ClassManager.
     */
    public ClassManager type;

    /**
     * EventManager.
     */
    public EventManager event;

    /**
     * ModuleManager.
     */
    public ModuleManager module;

    /**
     * CommandManager.
     */
    public CommandManager command;

    /**
     * ConfigManager.
     */
    public ConfigManager config;

    /**
     * @param mainClass     MainClass.
     * @param clientDataDir .minecraft/{clientName} path.
     */
    public void run(Class<?> mainClass, String clientDataDir) {
        this.packName = mainClass.getPackage().getName();
        this.clientDataDir = clientDataDir;
        this.configuration = new IConfiguration() {
        };
        type = new ClassManager();
        event = new EventManager();
        module = new ModuleManager();
        command = new CommandManager();
        config = new ConfigManager();
        config.load();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> config.save()));
    }

    /**
     * @param o             MainObject.
     * @param clientDataDir .minecraft/{clientName} path.
     */
    public void run(Object o, String clientDataDir) {
        run(o.getClass(), clientDataDir);
    }
}
