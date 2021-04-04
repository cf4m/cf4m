package cn.enaium.cf4m;

import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.manager.EventManager;

import java.io.File;

public interface ICF4M {

    /**
     * ClassContainer
     * Nullable
     * Only read
     */
    ClassContainer getClassContainer();
    
    /**
     * EventContainer
     * Nullable
     * Only read
     */
    EventContainer getEvent();

    /**
     * Configuration
     * NotNull
     * Only read
     */
    IConfiguration getConfiguration();

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