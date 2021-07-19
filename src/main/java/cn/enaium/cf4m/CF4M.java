package cn.enaium.cf4m;

import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.facade.*;

import java.io.File;
import java.lang.reflect.*;
import java.util.Objects;

/**
 * @author Enaium
 */
public final class CF4M {

    private CF4M() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Nullable
     * Only read
     */
    public static ClassContainer CLASS;

    /**
     * Nullable
     * Only read
     */
    public static ConfigurationContainer CONFIGURATION;

    /**
     * Nullable
     * Only read
     */
    public static EventContainer EVENT;

    /**
     * Nullable
     * Only read
     */
    public static ModuleContainer MODULE;

    /**
     * Nullable
     * Only read
     */
    public static CommandContainer COMMAND;

    /**
     * Nullable
     * Only read
     */
    public static ConfigContainer CONFIG;

    private static boolean run = false;

    /**
     * @param mainClass MainClass.
     * @param path      .minecraft/{clientName} path.
     */
    public static void run(Class<?> mainClass, String path) {
        if (run) {
            new Exception("CF4M already run").printStackTrace();
        } else {
            ClassFacade classFacade = new ClassFacade(mainClass);
            final ClassContainer classContainer = classFacade.classContainer;
            final ConfigurationContainer configuration = classFacade.configuration;
            final EventContainer eventContainer = new EventFacade(classContainer).eventContainer;
            final ModuleContainer moduleContainer = new ModuleFacade(classContainer).moduleContainer;
            final CommandContainer commandContainer = new CommandFacade(classContainer, configuration).commandContainer;
            final ConfigContainer configContainer = new ConfigFacade(classContainer, configuration, path).configContainer;
            classContainer.create(EventContainer.class, eventContainer);
            classContainer.create(ModuleContainer.class, moduleContainer);
            classContainer.create(CommandContainer.class, commandContainer);
            classContainer.create(ClassContainer.class, classContainer);
            classContainer.create(ConfigContainer.class, configContainer);
            classContainer.create(ConfigurationContainer.class, configuration);
            CLASS = classContainer;
            CONFIGURATION = configuration;
            EVENT = eventContainer;
            MODULE = moduleContainer;
            COMMAND = commandContainer;
            CONFIG = configContainer;

            run = true;
            classFacade.after();
            configContainer.load();
            Runtime.getRuntime().addShutdownHook(new Thread("CF4M Shutdown Thread") {
                @Override
                public void run() {
                    configContainer.save();
                }
            });
        }
    }

    /**
     * @param mainClass MainClass.
     */
    public static void run(Class<?> mainClass) {
        run(mainClass, new File(mainClass.getSimpleName()).toString());
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