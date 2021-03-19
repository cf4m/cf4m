package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.manager.*;

import java.io.File;

/**
 * Project: cf4m
 * Author: Enaium
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
    public String dir;

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
     * SettingManager
     */
    public SettingManager setting;

    /**
     * CommandManager.
     */
    public CommandManager command;

    /**
     * ConfigManager.
     */
    public ConfigManager config;

    /**
     * @param mainClass MainClass.
     */
    public void run(Class<?> mainClass) {
        this.packName = mainClass.getPackage().getName();
        this.dir = new File(".", mainClass.getSimpleName()).toString();
        this.configuration = new IConfiguration() {
        };
        type = new ClassManager(mainClass.getClassLoader());
        event = new EventManager();
        module = new ModuleManager();
        setting = new SettingManager();
        command = new CommandManager();
        config = new ConfigManager();
        config.load();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> config.save()));
    }

    /**
     * @param o MainObject.
     */
    public void run(Object o) {
        run(o.getClass());
    }

    /**
     * @param mainClass MainClass.
     * @param dir       .minecraft/{clientName} path.
     */
    public void run(Class<?> mainClass, String dir) {
        this.dir = dir;
        run(mainClass);
    }

    /**
     * @param o   MainObject.
     * @param dir .minecraft/{clientName} path.
     */
    public void run(Object o, String dir) {
        this.dir = dir;
        run(o);
    }
}
