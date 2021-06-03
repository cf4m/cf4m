package cn.enaium.cf4m.container;

/**
 * @author Enaium
 */
public interface ConfigurationContainer {
    <T> T getByKey(String key);

    <T> T getByClass(Class<T> klass);
}
