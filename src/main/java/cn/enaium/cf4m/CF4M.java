package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.manager.*;

import java.io.File;

/**
 * @author Enaium
 */
public final class CF4M {

    public static ICF4M INSTANCE;

    public CF4M(Class<?> mainClass, String dir) {
        final ClassContainer classContainer = new ClassManager(mainClass.getClassLoader(), mainClass.getPackage().getName()).classContainer;
        final EventContainer eventContainer = new EventManager().eventContainer;
        final IConfiguration configuration = new ConfigurationManager(classContainer).configuration;
        final ModuleContainer moduleContainer = new ModuleManager(classContainer, configuration).moduleContainer;
        final CommandContainer commandContainer = new CommandManager(classContainer, configuration).commandContainer;
        final ConfigContainer configContainer = new ConfigManager(classContainer, configuration, dir).configContainer;
        INSTANCE = new ICF4M() {
            @Override
            public ClassContainer getKlass() {
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
        classContainer.accept();
    }

    private static boolean run = false;

    /**
     * @param mainClass MainClass.
     * @param path      .minecraft/{clientName} path.
     */
    public static void run(Class<?> mainClass, String path) {
        if (run) {
            new Exception("CF4M already run").printStackTrace();
        } else {
            new CF4M(mainClass, path);
            INSTANCE.getConfig().load();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> INSTANCE.getConfig().save()));
            run = true;
        }
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
