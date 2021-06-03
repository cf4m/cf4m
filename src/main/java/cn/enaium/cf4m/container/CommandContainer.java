package cn.enaium.cf4m.container;

import cn.enaium.cf4m.provider.CommandProvider;

import java.util.ArrayList;

/**
 * @author Enaium
 */
public interface CommandContainer {
    /**
     * NotNull
     *
     * @return command list
     */
    ArrayList<CommandProvider> getAll();

    /**
     * Nullable
     *
     * @param instance command
     * @return command
     */
    CommandProvider getByInstance(Object instance);

    /**
     * @param klass class
     * @param <T>   command
     * @return command
     */
    <T> CommandProvider getByClass(Class<T> klass);

    /**
     * Nullable
     *
     * @param key command key
     * @return command
     */
    CommandProvider getByKey(String key);

    /**
     * NotNull
     *
     * @param rawMessage message
     * @return Whether the execution is success
     */
    boolean execCommand(String rawMessage);
}
