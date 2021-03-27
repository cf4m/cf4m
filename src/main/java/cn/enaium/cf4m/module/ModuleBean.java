package cn.enaium.cf4m.module;

import cn.enaium.cf4m.CF4M;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class ModuleBean {
    private final Object instance;

    public ModuleBean(Object instance) {
        this.instance = instance;
    }

    public String getName() {
        return CF4M.module.getName(instance);
    }

    public boolean getEnable() {
        return CF4M.module.getEnable(instance);
    }

    public void enable() {
        CF4M.module.enable(instance);
    }

    public int getKey() {
        return CF4M.module.getKey(instance);
    }

    public void setKey(int key) {
        CF4M.module.setKey(instance, key);
    }

    public Category getCategory() {
        return CF4M.module.getCategory(instance);
    }

    public String getDescription() {
        return CF4M.module.getDescription(instance);
    }

    public <T> T getExtend() {
        return CF4M.module.getExtend(instance);
    }

    @Override
    public String toString() {
        return "ModuleBean{" +
                "name=" + getName() +
                ", enable=" + getEnable() +
                ", key=" + getKey() +
                ", category=" + getCategory() +
                ", description=" + getDescription() +
                '}';
    }
}