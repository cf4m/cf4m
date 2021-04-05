package cn.enaium.cf4m.configuration;

/**
 * Project: cf4m
 *
 * @author Enaium
 */
public interface IConfiguration {
    default ICommandConfiguration command() {
        return new ICommandConfiguration() {
        };
    }

    default IModuleConfiguration module() {
        return new IModuleConfiguration() {
        };
    }

    default IConfigConfiguration config() {
        return new IConfigConfiguration() {
        };
    }
}
