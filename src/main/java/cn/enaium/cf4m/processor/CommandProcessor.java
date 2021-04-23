package cn.enaium.cf4m.processor;

import cn.enaium.cf4m.provider.CommandProvider;

/**
 * @author Enaium
 */
public interface CommandProcessor {
    default void beforeExec(CommandProvider commandProvider) {

    }

    default void afterExec(CommandProvider commandProvider) {

    }
}
