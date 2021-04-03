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
     * ClassManager
     * Nullable
     * Only read
     */
    public static ClassManager klass;

    public static I instance;

    public CF4M(Class<?> mainClass, String dir) {
        klass = new ClassManager(mainClass.getClassLoader(), mainClass.getPackage().getName());
        EventManager eventManager = new EventManager();
        ModuleContainer moduleContainer = new ModuleManager().moduleContainer;
        CommandContainer commandContainer = new CommandManager().commandContainer;
        ConfigContainer configContainer = new ConfigManager(dir).configContainer;
        instance = new I() {
            @Override
            public EventManager getEvent() {
                return eventManager;
            }

            @Override
            public ModuleContainer getModule() {
                return moduleContainer;
            }

            @Override
            public CommandContainer getCommand() {
                return commandContainer;
            }

            @Override
            public ConfigContainer getConfig() {
                return configContainer;
            }
        };
    }

    public interface I {
        /**
         * EventManager
         * Nullable
         * Only read
         */
        EventManager getEvent();

        /**
         * ModuleContainer
         * Nullable
         * Only read
         */
        ModuleContainer getModule();

        /**
         * CommandContainer
         * Nullable
         * Only read
         */
        CommandContainer getCommand();

        /**
         * ConfigContainer
         * Nullable
         * Only read
         */
        ConfigContainer getConfig();
    }

    /**
     * CF4M configuration
     */
    public static IConfiguration configuration;

    private static boolean run = false;

    /**
     * @param mainClass MainClass.
     * @param path      .minecraft/{clientName} path.
     */
    public static void run(Class<?> mainClass, String path) {
        if (run) {
            throw new ExceptionInInitializerError();
        }
        new CF4M(mainClass, path);
        configuration = new IConfiguration() {
        };
        if (configuration.config().enable()) {
            instance.getConfig().load();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> instance.getConfig().save()));
        }
        run = true;
    }

    /**
     * @param mainClass MainClass.
     */
    public static void run(Class<?> mainClass) {
        run(mainClass, new File(".", mainClass.getSimpleName()).toString());
    }

    /**
     * @param instance MainClass instance.
     */
    public static void run(Object instance) {
        run(instance.getClass());
    }

    /**
     * @param instance MainClass instance.
     * @param path     .minecraft/{clientName} path.
     */
    public static void run(Object instance, String path) {
        run(instance.getClass(), path);
    }
}
