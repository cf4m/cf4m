package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.manager.*;

import java.io.File;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class CF4M {
    /**
     * Client package.
     */
    public static String packName;

    /**
     * .minecraft/{clientName} path.
     */
    public static String dir;

    /**
     * CF4M configuration
     */
    public static IConfiguration configuration;

    /**
     * ClassManager.
     */
    public static ClassManager klass;

    /**
     * EventManager.
     */
    public static EventManager event;

    /**
     * ModuleManager.
     */
    public static ModuleManager module;

    /**
     * CommandManager.
     */
    public static CommandManager command;

    /**
     * ConfigManager.
     */
    public static ConfigManager config;

    /**
     * @param mainClass MainClass.
     */
    public static void run(Class<?> mainClass) {
        packName = mainClass.getPackage().getName();
        dir = new File(".", mainClass.getSimpleName()).toString();
        configuration = new IConfiguration() {
        };
        klass = new ClassManager(mainClass.getClassLoader());
        event = new EventManager();
        module = new ModuleManager();
        command = new CommandManager();
        config = new ConfigManager();
        if (configuration.config().enable()) {
            config.load();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> config.save()));
        }
    }

    /**
     * @param o MainClass instance.
     */
    public static void run(Object o) {
        run(o.getClass());
    }

    /**
     * @param mainClass MainClass.
     * @param path      .minecraft/{clientName} path.
     */
    public static void run(Class<?> mainClass, String path) {
        run(mainClass);
        dir = path;
    }

    /**
     * @param o    MainClass instance.
     * @param path .minecraft/{clientName} path.
     */
    public static void run(Object o, String path) {
        run(o);
        dir = path;
    }
}
