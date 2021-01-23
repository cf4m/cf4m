package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.event.EventAT;
import cn.enaium.cf4m.event.events.KeyboardEvent;
import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.module.ModuleAT;
import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class ModuleManager {
    /**
     * Module list.
     */
    public ArrayList<Module> modules = Lists.newArrayList();

    public ModuleManager() {
        CF4M.getInstance().eventManager.register(this);
        try {
            for (Class<?> clazz : CF4M.getInstance().classManager.getClasses()) {
                if (clazz.isAnnotationPresent(ModuleAT.class)) {
                    modules.add((Module) clazz.newInstance());
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @EventAT
    public void onKey(KeyboardEvent keyboardEvent) {
        for (Module module : modules) {
            if (module.getKeyCode() == keyboardEvent.getKeyCode())
                module.enable();
        }
    }
}
