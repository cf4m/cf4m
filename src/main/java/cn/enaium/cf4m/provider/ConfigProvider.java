package cn.enaium.cf4m.provider;

/**
 * Project: cf4m
 * Author: Enaium
 */
public interface ConfigProvider extends Provider {
    /**
     * NotNull
     *
     * @return config path
     */
    String getPath();
}
