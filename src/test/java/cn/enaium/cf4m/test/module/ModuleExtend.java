package cn.enaium.cf4m.test.module;

import cn.enaium.cf4m.annotation.module.Extend;

/**
 * Project: cf4m
 * Author: Enaium
 */
@Extend
public class ModuleExtend {
    public String tag;
    public int age;
    public Action fun;

    public interface Action {
        void on();
    }
}
