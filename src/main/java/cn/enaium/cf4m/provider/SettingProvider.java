package cn.enaium.cf4m.provider;

/**
 * @author Enaium
 */
public interface SettingProvider extends Provider {
    /**
     * Nullable
     *
     * @param <T> setting class
     * @return setting instance
     */
    <T> T getSetting();

    /**
     * @param value setting value
     * @param <T>   setting class
     * @return setting instance
     */
    <T> T setSetting(Object value);
}
