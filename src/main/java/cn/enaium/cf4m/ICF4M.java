package cn.enaium.cf4m;

import cn.enaium.cf4m.container.*;

/**
 * @author Enaium
 */
public interface ICF4M {

    /**
     * ClassContainer
     * Nullable
     * Only read
     *
     * @return classContainer
     */
    ClassContainer getKlass();

    /**
     * Configuration
     * Nullable
     * Only read
     *
     * @return configurationContainer
     */
    ConfigurationContainer getConfiguration();

    /**
     * EventContainer
     * Nullable
     * Only read
     *
     * @return eventContainer
     */
    EventContainer getEvent();

    /**
     * ModuleContainer
     * Nullable
     * Only read
     *
     * @return moduleContainer
     */
    ModuleContainer getModule();

    /**
     * CommandContainer
     * Nullable
     * Only read
     *
     * @return commandContainer
     */
    CommandContainer getCommand();

    /**
     * ConfigContainer
     * Nullable
     * Only read
     *
     * @return configContainer
     */
    ConfigContainer getConfig();
}