package cn.enaium.cf4m.provider;

import java.util.List;

/**
 * @author Enaium
 */
public interface CommandProvider extends Provider {
    /**
     * @return command key
     */
    String[] getKey();

    /**
     * @return command param
     */
    List<List<String>> getParam();
}
