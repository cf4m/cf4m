package cn.enaium.cf4m.test.module;

import cn.enaium.cf4m.annotation.module.Extend;

/**
 * @author Enaium
 */
@Extend
public class ModuleExtend {
    public String tag = null;
    public int age = 0;
    public Action func = null;

    public interface Action {
        void on();
    }
}
