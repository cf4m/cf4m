package cn.enaium.cf4m.module;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public interface IModule {
    default boolean enable() {
        return false;
    }

    default int key() {
        return 0;
    }
}
