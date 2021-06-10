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
        public ConfigurationContainer getConfiguration() {
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

    public static final ClassContainer CLASS = INSTANCE.getKlass();
    public static final ConfigurationContainer CONFIGURATION = INSTANCE.getConfiguration();
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
            ICF4M cf4m = new ICF4M() {
                @Override
                public ClassContainer getKlass() {
                    return classContainer;
                }

                @Override
                public ConfigurationContainer getConfiguration() {
                    return configuration;
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
                setStaticFinalField("CLASS", classContainer);
                setStaticFinalField("CONFIGURATION", configuration);
                setStaticFinalField("EVENT", eventContainer);
                setStaticFinalField("MODULE", moduleContainer);
                setStaticFinalField("COMMAND", commandContainer);
                setStaticFinalField("CONFIG", configContainer);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
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

    private static void setStaticFinalField(String name, Object value) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field field = CF4M.class.getDeclaredField(name);
        field.setAccessible(true);

        Field f = null;

        try {
            f = Field.class.getDeclaredField("modifiers");
        } catch (NoSuchFieldException e) {

            Method getDeclaredFields0 = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
            boolean accessible = getDeclaredFields0.isAccessible();

            getDeclaredFields0.setAccessible(true);
            Field[] declaredFields = (Field[]) getDeclaredFields0.invoke(Field.class, false);
            getDeclaredFields0.setAccessible(accessible);

            for (Field declaredField : declaredFields) {
                if ("modifiers".equals(declaredField.getName())) {
                    f = declaredField;
                    break;
                }
            }
        }


        assert f != null;
        f.setAccessible(true);
        f.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, value);
    }
}