package cn.enaium.cf4m.annotation.module;

import cn.enaium.cf4m.module.Category;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleAT {
    String name();

    boolean enable() default false;

    int keyCode() default 0;

    Category category();
}
