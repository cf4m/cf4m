package cn.enaium.cf4m.test.module;

import cn.enaium.cf4m.annotation.module.docker.Docker;
import cn.enaium.cf4m.annotation.module.docker.Value;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Docker
public class Module {
    @Value("AnyName")
    Boolean anyNameHaha = false;//Default value

    @Value("AnyName2")
    Integer anyNameHala = 888;//Default value

    @Value("AnyName3")
    TestValue testValue = new TestValue("CCC", 17);//Default value
}
