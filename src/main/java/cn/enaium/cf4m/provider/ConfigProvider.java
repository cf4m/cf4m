package cn.enaium.cf4m.provider;

/**
 * @author Enaium
 */
public interface ConfigProvider extends Provider {
    /**
     * @return config path
     */
    String getPath();
}
