package cn.enaium.cf4m.module;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.event.EventTarget;
import cn.enaium.cf4m.event.events.KeyboardEvent;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class ModuleManager {

    public ArrayList<Module> modules;

    public ModuleManager() {
        modules = new ArrayList<>();
        CF4M.getInstance().eventManager.register(this);
        try {
            for (ClassPath.ClassInfo info : ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClasses()) {
                if (info.getName().startsWith(CF4M.getInstance().packName)) {
                    Class<?> clazz = info.load();
                    if (clazz.isAnnotationPresent(ModuleAT.class)) {
                        modules.add((Module) clazz.newInstance());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @EventTarget
    public void onKey(KeyboardEvent keyboardEvent) {
        System.out.println("@3333");
        for (Module module : modules) {
            if (module.getKeyCode() == keyboardEvent.getKeyCode())
                module.enable();
        }
    }
}
