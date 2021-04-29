package cn.enaium.cf4m.configuration;

/**
 * @author Enaium
 */
public interface ICommandConfiguration {
    /**
     * prefix
     *
     * @return Command prefix
     */
    String getPrefix();

    /**
     * sendMessage
     *
     * @param message print chat message
     */
    void message(String message);
}
