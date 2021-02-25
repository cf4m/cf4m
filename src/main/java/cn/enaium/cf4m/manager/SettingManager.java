package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Setting;
import com.google.common.collect.LinkedHashMultimap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Set;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class SettingManager {

    /**
     * <K> module
     * <V> settingFields
     */
    private final LinkedHashMultimap<Object, Field> settings = LinkedHashMultimap.create();

    public SettingManager() {
        try {
            for (Object module : CF4M.INSTANCE.module.getModules()) {
                for (Field field : module.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Setting.class)) {
                        settings.put(module, field);
                    }
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
    }

    public String getName(Object module, Object setting) {
        if (settings.containsKey(module)) {
            for (Field field : settings.get(module)) {
                try {
                    if (field.get(module).equals(setting)) {
                        return field.getAnnotation(Setting.class).value();
                    }
                } catch (Exception e) {
                    e.getCause().printStackTrace();
                }
            }
        }
        return null;
    }

    public String getDescription(Object module, Object setting) {
        if (settings.containsKey(module)) {
            for (Field field : settings.get(module)) {
                try {
                    if (field.get(module).equals(setting)) {
                        return field.getAnnotation(Setting.class).description();
                    }
                } catch (Exception e) {
                    e.getCause().printStackTrace();
                }
            }
        }
        return null;
    }

    public Object getSetting(Object module, String name) {
        for (Object setting : getSettings(module)) {
            if (getName(module, setting).equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }

    public ArrayList<Object> getSettings(Object module) {
        if (settings.containsKey(module)) {
            ArrayList<Object> setting = new ArrayList<>();
            settings.get(module).forEach(field -> {
                try {
                    setting.add(field.get(module));
                } catch (Exception e) {
                    e.getCause().printStackTrace();
                }
            });
            return setting;
        }
        return null;
    }
}
