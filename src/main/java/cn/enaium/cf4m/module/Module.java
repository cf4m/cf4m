package cn.enaium.cf4m.module;

import cn.enaium.cf4m.CF4M;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class Module {
    private String name;
    private String info;
    private int keyCode;
    private Category category;
    private boolean enable;

    public Module(String name, String info, int keyCode, Category category) {
        this.name = name;
        this.info = info;
        this.keyCode = keyCode;
        this.category = category;
        this.enable = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    protected void enable() {
        this.enable = !this.enable;
        if (this.enable)
            onEnable();
        else
            onDisable();
    }

    protected void onEnable() {
        CF4M.getInstance().eventManager.register(this);
    }

    private void onDisable() {
        CF4M.getInstance().eventManager.unregister(this);
    }
}
