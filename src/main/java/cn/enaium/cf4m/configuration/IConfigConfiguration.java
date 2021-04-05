package cn.enaium.cf4m.configuration;

/**
 * @author Enaium
 */
public interface IConfigConfiguration {
    /**
     * @return enable or disable config.
     */
    default boolean enable() {
        return true;
    }
}
