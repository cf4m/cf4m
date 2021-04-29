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
public final class Bootstrap {

    private Bootstrap() {
        throw new IllegalStateException("Utility class");
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
                setStaticFinalField(CF4M.class.getDeclaredField("INSTANCE"), cf4m);
                setStaticFinalField(CF4M.class.getDeclaredField("KLASS"), classContainer);
                setStaticFinalField(CF4M.class.getDeclaredField("CONFIGURATION"), configuration.configuration);
                setStaticFinalField(CF4M.class.getDeclaredField("EVENT"), eventContainer);
                setStaticFinalField(CF4M.class.getDeclaredField("MODULE"), moduleContainer);
                setStaticFinalField(CF4M.class.getDeclaredField("COMMAND"), commandContainer);
                setStaticFinalField(CF4M.class.getDeclaredField("CONFIG"), configContainer);
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

    private static void setStaticFinalField(Field field, Object value) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, value);
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
