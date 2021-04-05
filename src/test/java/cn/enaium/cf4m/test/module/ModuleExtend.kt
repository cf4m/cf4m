package cn.enaium.cf4m.test.module

import cn.enaium.cf4m.annotation.module.Extend

/**
 * Project: cf4m
 *
 * @author Enaium
 */
@Extend
class ModuleExtend {
    var tag: String? = null
    var age = 0
    var func: Action? = null

    interface Action {
        fun on()
    }
}