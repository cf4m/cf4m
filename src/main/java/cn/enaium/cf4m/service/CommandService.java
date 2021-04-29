package cn.enaium.cf4m.service;

import cn.enaium.cf4m.provider.CommandProvider;

/**
 * @author Enaium
 */
public interface CommandService {
    /**
     * Before exec
     *
     * @param commandProvider command
     */
    default void beforeExec(CommandProvider commandProvider) {

    }

    /**
     * After exec
     *
     * @param commandProvider command
     */
    default void afterExec(CommandProvider commandProvider) {

    }
}
