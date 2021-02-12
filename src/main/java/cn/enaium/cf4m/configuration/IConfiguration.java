package cn.enaium.cf4m.configuration;

import java.lang.reflect.Method;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public interface IConfiguration {

    default void message(String message) {
        System.out.println(message);
    }

    default String prefix() {
        return "`";
    }
}
