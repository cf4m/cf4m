package cn.enaium.cf4m.module;

import cn.enaium.cf4m.CF4M;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class Module {
    /**
     * Name.
     */
    private String name;

    /**
     * Info.
     */
    private String info;

    /**
     * hotkey.
     */
    private int keyCode;

    /**
     * Category.
     */
    private Category category;

    /**
     * Enable.
     */
    private boolean enable;

    /**
     * Tag.
     */
    private String tag;

    /**
     * @param name     Module name.
     * @param info     Module info.
     * @param keyCode  Module hotkey.
     * @param category Module category.
     */
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

    /**
     * enable or disable.
     */
    public void enable() {
        this.enable = !this.enable;
        if (this.enable)
            onEnable();
        else
            onDisable();
    }

    protected void onEnable() {
        CF4M.getInstance().eventManager.register(this);
    }

    protected void onDisable() {
        CF4M.getInstance().eventManager.unregister(this);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
