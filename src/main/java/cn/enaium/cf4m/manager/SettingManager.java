package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.module.ModuleBean;
import cn.enaium.cf4m.setting.Setting;
import cn.enaium.cf4m.annotation.SettingAT;
import cn.enaium.cf4m.setting.settings.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class SettingManager {
    /**
     * Setting list.
     */
    public ArrayList<Setting> settings;

    public SettingManager() {
        settings = new ArrayList<>();
        try {
            for (ModuleBean moduleBean : CF4M.getInstance().module.getModules()) {
                for (Field field : moduleBean.getClazz().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(SettingAT.class)) {
                        if (field.getType().equals(EnableSetting.class)) {
                            settings.add((EnableSetting) field.get(moduleBean.getClazz()));
                        } else if (field.getType().equals(IntegerSetting.class)) {
                            settings.add((IntegerSetting) field.get(moduleBean.getClazz()));
                        } else if (field.getType().equals(FloatSetting.class)) {
                            settings.add((FloatSetting) field.get(moduleBean.getClazz()));
                        } else if (field.getType().equals(DoubleSetting.class)) {
                            settings.add((DoubleSetting) field.get(moduleBean.getClazz()));
                        } else if (field.getType().equals(LongSetting.class)) {
                            settings.add((LongSetting) field.get(moduleBean.getClazz()));
                        } else if (field.getType().equals(ModeSetting.class)) {
                            settings.add((ModeSetting) field.get(moduleBean.getClazz()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
