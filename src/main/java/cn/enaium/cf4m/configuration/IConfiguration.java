package cn.enaium.cf4m.configuration;

/**
 * @author Enaium
 */
public interface IConfiguration {
    /**
     * command configuration
     *
     * @return command configuration
     */
    ICommandConfiguration getCommand();

    /**
     * config configuration
     *
     * @return config configuration
     */
    IConfigConfiguration getConfig();
}
