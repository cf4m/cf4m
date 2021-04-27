package cn.enaium.cf4m.configuration;

/**
 * @author Enaium
 */
public interface ICommandConfiguration {
    /**
     * @return Command prefix
     */
    String getPrefix();

    /**
     * @param message print chat message
     */
    void message(String message);
}
