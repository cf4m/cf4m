package cn.enaium.cf4m.configuration;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface ICommandConfiguration {
    /**
     * @return Command prefix.
     */
    default String prefix() {
        return "`";
    }

    /**
     * @param message print chat message.
     */
    default void message(String message) {
        System.err.println(message);
    }
}
