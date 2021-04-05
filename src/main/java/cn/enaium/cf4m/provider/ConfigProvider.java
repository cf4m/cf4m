package cn.enaium.cf4m.provider;

/**
 * @author Enaium
 */
public interface ConfigProvider extends Provider {
    /**
     * NotNull
     *
     * @return config path
     */
    String getPath();
}
