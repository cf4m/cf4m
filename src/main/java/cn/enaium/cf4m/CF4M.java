package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.CommandContainer;
import cn.enaium.cf4m.container.ConfigContainer;
import cn.enaium.cf4m.container.EventContainer;
import cn.enaium.cf4m.container.ModuleContainer;
import cn.enaium.cf4m.manager.*;

import java.io.File;

/**
 * Project: cf4m
 * Author: Enaium
 */
public final class CF4M {

    public static ICF4M CF4M;

    public CF4M(Class<?> mainClass, String dir) {
        ClassManager classManager = new ClassManager(mainClass.getClassLoader(), mainClass.getPackage().getName());
        IConfiguration configuration = new ConfigurationManager(classManager.getClasses()).configuration;
        EventContainer eventContainer = new EventManager().eventContainer;
        ModuleContainer moduleContainer = new ModuleManager(classManager.getClasses(), configuration).moduleContainer;
        CommandContainer commandContainer = new CommandManager(classManager.getClasses(), configuration).commandContainer;
        ConfigContainer configContainer = new ConfigManager(classManager.getClasses(), dir, configuration).configContainer;
        CF4M = new ICF4M() {
            @Override
            public EventContainer getEvent() {
                return eventContainer;
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

            @Override
            public IConfiguration getConfiguration() {
                return configuration;
            }
        };
    }

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
        CF4M.getConfig().load();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> CF4M.getConfig().save()));
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
