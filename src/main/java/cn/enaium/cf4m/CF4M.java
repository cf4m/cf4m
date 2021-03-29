package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.CommandContainer;
import cn.enaium.cf4m.container.ConfigContainer;
import cn.enaium.cf4m.container.ModuleContainer;
import cn.enaium.cf4m.manager.*;

import java.io.File;

/**
 * Project: cf4m
 * Author: Enaium
 */
public final class CF4M {

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
     * ModuleContainer.
     */
    public static ModuleContainer module;

    /**
     * CommandContainer.
     */
    public static CommandContainer command;

    /**
     * ConfigContainer.
     */
    public static ConfigContainer config;

    private static boolean run = false;

    /**
     * @param mainClass MainClass.
     */
    public static void run(Class<?> mainClass) {
        if (run) {
            throw new ExceptionInInitializerError();
        }

        packName = mainClass.getPackage().getName();
        dir = new File(".", mainClass.getSimpleName()).toString();
        configuration = new IConfiguration() {
        };
        klass = new ClassManager(mainClass.getClassLoader());
        event = new EventManager();
        module = new ModuleManager().moduleContainer;
        command = new CommandManager().commandContainer;
        config = new ConfigManager().configContainer;
        if (configuration.config().enable()) {
            config.load();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> config.save()));
        }
        run = true;
    }

    /**
     * @param instance MainClass instance.
     */
    public static void run(Object instance) {
        run(instance.getClass());
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
     * @param instance MainClass instance.
     * @param path     .minecraft/{clientName} path.
     */
    public static void run(Object instance, String path) {
        run(instance);
        dir = path;
    }
}
