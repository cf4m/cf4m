package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.facade.*;

import java.io.File;

/**
 * @author Enaium
 */
public final class CF4M {

    public static ICF4M INSTANCE;

    private static boolean run = false;

    /**
     * @param mainClass MainClass.
     * @param path      .minecraft/{clientName} path.
     */
    public static void run(Class<?> mainClass, String path) {
        if (run) {
            new Exception("CF4M already run").printStackTrace();
        } else {
            final ClassContainer classContainer = new ClassFacade(mainClass).classContainer;
            final EventContainer eventContainer = new EventFacade(classContainer).eventContainer;
            final IConfiguration configuration = new ConfigurationFacade(classContainer).configuration;
            final ModuleContainer moduleContainer = new ModuleFacade(classContainer).moduleContainer;
            final CommandContainer commandContainer = new CommandFacade(classContainer, configuration).commandContainer;
            final ConfigContainer configContainer = new ConfigFacade(classContainer, configuration, path).configContainer;
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
            classContainer.after();
            configContainer.load();
            Runtime.getRuntime().addShutdownHook(new Thread("CF4M Shutdown Thread") {
                @Override
                public void run() {
                    configContainer.save();
                }
            });
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
