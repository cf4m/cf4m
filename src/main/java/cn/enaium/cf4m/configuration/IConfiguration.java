package cn.enaium.cf4m.configuration;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public interface IConfiguration {

    /**
     * @param message print chat message
     */
    default void message(String message) {
        System.out.println(message);
    }

    default void enable(Object module) {}

    default void disable(Object module) {}

    /**
     * @return Command prefix
     */
    default String prefix() {
        return "`";
    }

    default boolean config() {
        return true;
    }
}
