package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Setting;
import cn.enaium.cf4m.setting.SettingBean;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Sets;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class SettingManager {

    /**
     * <K> module
     * <V> settings
     */
    private final HashMultimap<Object, SettingBean> settings = HashMultimap.create();

    public SettingManager() {
        try {
            for (Object module : CF4M.INSTANCE.module.getModules()) {
                for (Field field : module.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Setting.class)) {
                        settings.put(module, new SettingBean(field.getAnnotation(Setting.class).value(), field, field.get(module)));
                    }
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
    }

    public String getName(Object module, Object setting) {
        if (settings.containsKey(module)) {
            for (SettingBean settingBean : settings.get(module)) {
                if (settingBean.getFieldObject().equals(setting)) {
                    return settingBean.getField().getAnnotation(Setting.class).value();
                }
            }
        }
        return null;
    }

    public Set<Object> getSettings(Object module) {
        Set<Object> setting = Sets.newHashSet();
        for (SettingBean settingBean : settings.get(module)) {
            setting.add(settingBean.getFieldObject());
        }
        return setting;
    }
}
