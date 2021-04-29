package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.*;

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
}