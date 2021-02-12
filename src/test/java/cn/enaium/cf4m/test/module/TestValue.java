package cn.enaium.cf4m.test.module;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class TestValue {
    private String name;
    private Integer age;

    public TestValue(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "TestValue{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
