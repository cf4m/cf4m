package cn.enaium.cf4m.provider;

import java.util.List;

/**
 * @author Enaium
 */
public interface CommandProvider extends Provider {
    /**
     * NotNull
     *
     * @return command key
     */
    String[] getKey();

    /**
     * NotNull
     *
     * @return command param
     */
    List<List<String>> getParam();
}
