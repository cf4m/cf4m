package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.Configuration;
import cn.enaium.cf4m.configuration.ICommandConfiguration;
import cn.enaium.cf4m.configuration.IConfigConfiguration;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.facade.*;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Enaium
 */
public final class CF4M {

    private CF4M() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Not final
     * Nullable
     * Only read
     */
    @Deprecated
    public static final ICF4M INSTANCE = new ICF4M() {
        @Override
        public ClassContainer getKlass() {
            return null;
        }

        @Override
        public IConfiguration getConfiguration() {
            return null;
        }

        @Override
        public EventContainer getEvent() {
            return null;
        }

        @Override
        public ModuleContainer getModule() {
            return null;
        }

        @Override
        public CommandContainer getCommand() {
            return null;
        }

        @Override
        public ConfigContainer getConfig() {
            return null;
        }
    };

    public static final ClassContainer KLASS = INSTANCE.getKlass();
    public static final IConfiguration CONFIGURATION = INSTANCE.getConfiguration();
    public static final EventContainer EVENT = INSTANCE.getEvent();
    public static final ModuleContainer MODULE = INSTANCE.getModule();
    public static final CommandContainer COMMAND = INSTANCE.getCommand();
    public static final ConfigContainer CONFIG = INSTANCE.getConfig();

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
            final Configuration configuration = classFacade.configuration;
            final EventContainer eventContainer = new EventFacade(classContainer).eventContainer;
            final ModuleContainer moduleContainer = new ModuleFacade(classContainer).moduleContainer;
            final CommandContainer commandContainer = new CommandFacade(classContainer, configuration.configuration).commandContainer;
            final ConfigContainer configContainer = new ConfigFacade(classContainer, configuration.configuration, path).configContainer;
            classContainer.create(EventContainer.class, eventContainer);
            classContainer.create(ModuleContainer.class, moduleContainer);
            classContainer.create(CommandContainer.class, commandContainer);
            classContainer.create(ClassContainer.class, classContainer);
            classContainer.create(ConfigContainer.class, configContainer);
            classContainer.create(IConfiguration.class, configuration.configuration);
            classContainer.create(ICommandConfiguration.class, configuration.configuration.getCommand());
            classContainer.create(IConfigConfiguration.class, configuration.configuration.getConfig());
            ICF4M cf4m = new ICF4M() {
                @Override
                public ClassContainer getKlass() {
                    return classContainer;
                }

                @Override
                public IConfiguration getConfiguration() {
                    return configuration.configuration;
                }

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
            };
            try {
                setStaticFinalField("INSTANCE", cf4m);
                setStaticFinalField("KLASS", classContainer);
                setStaticFinalField("CONFIGURATION", configuration.configuration);
                setStaticFinalField("EVENT", eventContainer);
                setStaticFinalField("MODULE", moduleContainer);
                setStaticFinalField("COMMAND", commandContainer);
                setStaticFinalField("CONFIG", configContainer);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
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

    private static void setStaticFinalField(String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = CF4M.class.getDeclaredField(name);
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, value);
    }
}