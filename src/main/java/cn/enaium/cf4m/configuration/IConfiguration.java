package cn.enaium.cf4m.configuration;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public interface IConfiguration {

    /**
     * @param message print chat message.
     */
    default void message(String message) {
        System.out.println(message);
    }

    /**
     * Before the module is enabled.
     *
     * @param module module.
     */
    default void enable(Object module) {
    }

    /**
     * Before the module is disable.
     *
     * @param module module.
     */
    default void disable(Object module) {
    }

    /**
     * @return Command prefix.
     */
    default String prefix() {
        return "`";
    }

    /**
     * @return enable or disable config.
     */
    default boolean config() {
        return true;
    }
}
