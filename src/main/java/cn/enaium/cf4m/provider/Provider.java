package cn.enaium.cf4m.provider;

/**
 * @author Enaium
 */
public interface Provider {
    /**
     * @return name
     */
    String getName();

    /**
     * @return description
     */
    String getDescription();

    /**
     * @return instance
     */
    Object getInstance();
}
