package cn.enaium.cf4m.service;

import cn.enaium.cf4m.provider.CommandProvider;

/**
 * @author Enaium
 */
public interface CommandService {
    default void beforeExec(CommandProvider commandProvider) {

    }

    default void afterExec(CommandProvider commandProvider) {

    }
}
