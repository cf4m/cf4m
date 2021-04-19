package cn.enaium.cf4m.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add this annotation to the class/field to the field of this class will be auto put
 * <p>
 * Support class {@link cn.enaium.cf4m.annotation.module.Module}
 * <p>
 * Support class {@link cn.enaium.cf4m.annotation.command.Command}
 * <p>
 * Support class {@link cn.enaium.cf4m.annotation.config.Config}
 * <p>
 * Support field {@link cn.enaium.cf4m.container.ClassContainer}
 * <p>
 * Support field {@link cn.enaium.cf4m.container.EventContainer}
 * <p>
 * Support field {@link cn.enaium.cf4m.container.ModuleContainer}
 * <p>
 * Support field {@link cn.enaium.cf4m.container.CommandContainer}
 * <p>
 * Support field {@link cn.enaium.cf4m.container.ConfigContainer}
 * <p>
 * Support field {@link cn.enaium.cf4m.configuration.IConfiguration}
 * <p>
 * Support field {@link cn.enaium.cf4m.provider.ModuleProvider}
 * <p>
 * Support field {@link cn.enaium.cf4m.provider.CommandProvider}
 * <p>
 * Support field {@link cn.enaium.cf4m.provider.ConfigProvider}
 *
 * @author Enaium
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
}
