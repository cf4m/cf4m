package cn.enaium.cf4m.provider;

/**
 * @author Enaium
 */
public interface Provider {
    /**
     * NotNull
     *
     * @return name
     */
    String getName();

    /**
     * NotNull
     *
     * @return description
     */
    String getDescription();

    /**
     * NotNull
     *
     * @return instance
     */
    Object getInstance();
}
