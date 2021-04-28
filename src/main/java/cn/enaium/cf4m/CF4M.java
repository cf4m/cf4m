package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.*;

/**
 * @author Enaium
 */
public final class CF4M {
    /**
     * Not final
     * Nullable
     * Only read
     */
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
}
