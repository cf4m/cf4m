package cn.enaium.cf4m.container;

import cn.enaium.cf4m.provider.CommandProvider;

import java.util.ArrayList;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface CommandContainer {
    ArrayList<CommandProvider> getAll();

    CommandProvider getByInstance(Object instance);

    CommandProvider getByKey(String key);

    boolean execCommand(String rawMessage);
}
