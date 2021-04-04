package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.manager.*;

import java.io.File;

/**
 * Project: cf4m
 * Author: Enaium
 */
public final class CF4M {

    public static ICF4M CF4M;

    public CF4M(Class<?> mainClass, String dir) {
        final ClassContainer classContainer = new ClassManager(mainClass.getClassLoader(), mainClass.getPackage().getName()).classContainer;
        final EventContainer eventContainer = new EventManager().eventContainer;
        final IConfiguration configuration = new ConfigurationManager(classContainer.getClasses()).configuration;
        final ModuleContainer moduleContainer = new ModuleManager(classContainer.getClasses(), configuration).moduleContainer;
        final CommandContainer commandContainer = new CommandManager(classContainer.getClasses(), configuration).commandContainer;
        final ConfigContainer configContainer = new ConfigManager(classContainer.getClasses(), dir, configuration).configContainer;
        CF4M = new ICF4M() {
            @Override
            public ClassContainer getClassContainer() {
                return classContainer;
            }

            @Override
            public EventContainer getEvent() {
                return eventContainer;
            }

            @Override
            public IConfiguration getConfiguration() {
                return configuration;
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
