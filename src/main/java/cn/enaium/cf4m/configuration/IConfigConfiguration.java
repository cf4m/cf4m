package cn.enaium.cf4m.configuration;

/**
 * @author Enaium
 */
public interface IConfigConfiguration {
    /**
     * @return enable or disable config.
     */
    default boolean getEnable() {
        return true;
    }
}
