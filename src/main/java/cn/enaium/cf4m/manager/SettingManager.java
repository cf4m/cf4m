package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Setting;
import com.google.common.collect.LinkedHashMultimap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Set;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class SettingManager {

    /**
     * <K> module
     * <V> settingFields
     */
    private final LinkedHashMultimap<Object, Field> settings = LinkedHashMultimap.create();

    public SettingManager() {
        for (Object module : CF4M.module.getModules()) {
            for (Field field : module.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Setting.class)) {
                    settings.put(module, field);
                }
            }
        }
    }

    /**
     * @param module  module instance
     * @param setting setting instance
     * @return setting name
     */
    public String getName(Object module, Object setting) {
        if (settings.containsKey(module)) {
            for (Field field : settings.get(module)) {
                try {
                    if (field.get(module).equals(setting)) {
                        return field.getAnnotation(Setting.class).value();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * @param module  module instance
     * @param setting setting instance
     * @return description
     */
    public String getDescription(Object module, Object setting) {
        if (settings.containsKey(module)) {
            for (Field field : settings.get(module)) {
                try {
                    if (field.get(module).equals(setting)) {
                        return field.getAnnotation(Setting.class).description();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * @param module module instance
     * @param name   setting name
     * @return setting instance
     */
    public Object getSetting(Object module, String name) {
        for (Object setting : getSettings(module)) {
            if (getName(module, setting).equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }

    /**
     * @param module module instance
     * @return settings
     */
    public ArrayList<Object> getSettings(Object module) {
        if (settings.containsKey(module)) {
            ArrayList<Object> setting = new ArrayList<>();
            settings.get(module).forEach(field -> {
                try {
                    setting.add(field.get(module));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            return setting;
        }
        return null;
    }
}
