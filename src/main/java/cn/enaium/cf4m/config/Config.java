package cn.enaium.cf4m.config;

import cn.enaium.cf4m.CF4M;

import java.io.File;
import java.io.IOException;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class Config {
    /**
     * Config name.
     */
    private String name;

    /**
     * @param name Config name.
     */
    public Config(String name) {
        this.name = name;
    }

    /**
     * @return Get the config name.
     */
    public String getName() {
        return name;
    }

    /**
     * Load config.
     */
    public void load() {

    }

    /**
     * Save config.
     */
    public void save() {
        try {
            new File(getPath()).createNewFile();
        }catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * @return Get the config path.
     */
    public String getPath() {
        return CF4M.getInstance().clientDataDir + "/configs/" + name + ".json";
    }

}
