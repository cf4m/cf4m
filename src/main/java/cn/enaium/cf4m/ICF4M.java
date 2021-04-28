package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.*;

import java.io.File;

public interface ICF4M {

    /**
     * ClassContainer
     * Nullable
     * Only read
     */
    ClassContainer getKlass();

    /**
     * Configuration
     * Nullable
     * Only read
     */
    IConfiguration getConfiguration();

    /**
     * EventContainer
     * Nullable
     * Only read
     */
    EventContainer getEvent();

    /**
     * ModuleContainer
     * Nullable
     * Only read
     */
    ModuleContainer getModule();

    /**
     * CommandContainer
     * Nullable
     * Only read
     */
    CommandContainer getCommand();

    /**
     * ConfigContainer
     * Nullable
     * Only read
     */
    ConfigContainer getConfig();
}