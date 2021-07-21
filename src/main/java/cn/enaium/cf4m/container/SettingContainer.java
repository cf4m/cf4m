package cn.enaium.cf4m.container;

import cn.enaium.cf4m.provider.SettingProvider;
import java.util.ArrayList;

/**
 * @author Enaium
 */
public interface SettingContainer {
    /**
     * NotNull
     *
     * @return setting list
     */
    ArrayList<SettingProvider> getAll();

    /**
     * Nullable
     *
     * @param name setting name
     * @return setting
     */
    SettingProvider getByName(String name);
}
