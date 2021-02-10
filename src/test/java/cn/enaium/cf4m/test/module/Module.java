package cn.enaium.cf4m.test.module;

import cn.enaium.cf4m.annotation.module.collector.ModuleCollector;
import cn.enaium.cf4m.annotation.module.collector.ModuleValue;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@ModuleCollector
public class Module {
    @ModuleValue("Enable")
    Boolean anyNameHaha;

    @ModuleValue("KeyCode")
    Integer anyNameHala;
}
