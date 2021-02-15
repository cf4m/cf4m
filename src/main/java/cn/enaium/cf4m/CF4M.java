package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.manager.*;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
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
     * CF4M configuration
     */
    public IConfiguration configuration;

    /**
     * ClassManager.
     */
    public ClassManager classManager;

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
     * @param clazz         MainClass.
     * @param clientDataDir .minecraft/{clientName} path.
     */
    public CF4M(Class<?> clazz, String clientDataDir) {
        instance = this;
        this.packName = clazz.getPackage().getName();
        this.clientDataDir = clientDataDir;
        this.configuration = new IConfiguration() {
        };
        this.classManager = new ClassManager();
    }

    /**
     * @param o             this.
     * @param clientDataDir .minecraft/{clientName} path.
     */
    public CF4M(Object o, String clientDataDir) {
        this(o.getClass(),clientDataDir);
    }

    /**
     * Start.
     */
    public void run() {
        event = new EventManager();
        module = new ModuleManager();
        command = new CommandManager();
        if (clientDataDir != null) {
            config = new ConfigManager();
            config.load();
        }
    }

    /**
     * Stop.
     */
    public void stop() {
        if (clientDataDir != null) {
            config.save();
        }
    }

    /**
     * @return Instance.
     */
    public static CF4M getInstance() {
        return instance;
    }
}
