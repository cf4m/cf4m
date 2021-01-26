package cn.enaium.cf4m.manager;

import java.util.ArrayList;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public interface IClassManager {
    Class<?> loadClass(Class<?> clazz) throws ClassNotFoundException;

    ArrayList<Class<?>> getClasses();
}
