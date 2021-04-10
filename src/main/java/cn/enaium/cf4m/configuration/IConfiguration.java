package cn.enaium.cf4m.configuration;

/**
 * @author Enaium
 */
public interface IConfiguration {
    default ICommandConfiguration getCommand() {
        return new ICommandConfiguration() {
        };
    }

    default IModuleConfiguration getModule() {
        return new IModuleConfiguration() {
        };
    }

    default IConfigConfiguration getConfig() {
        return new IConfigConfiguration() {
        };
    }
}
