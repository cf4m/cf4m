package cn.enaium.cf4m.setting;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.setting.settings.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class SettingManager {
    public ArrayList<Setting> settings;

    public SettingManager() {
        settings = new ArrayList<>();
        try {
            for (Module module : CF4M.getInstance().moduleManager.modules) {
                for (Field field : module.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(SettingAT.class)) {
                        if (field.getType().equals(EnableSetting.class)) {
                            settings.add((EnableSetting) field.get(module));
                        } else if (field.getType().equals(IntegerSetting.class)) {
                            settings.add((IntegerSetting) field.get(module));
                        } else if (field.getType().equals(FloatSetting.class)) {
                            settings.add((FloatSetting) field.get(module));
                        } else if (field.getType().equals(DoubleSetting.class)) {
                            settings.add((DoubleSetting) field.get(module));
                        } else if (field.getType().equals(LongSetting.class)) {
                            settings.add((LongSetting) field.get(module));
                        } else if (field.getType().equals(ModeSetting.class)) {
                            settings.add((ModeSetting) field.get(module));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
