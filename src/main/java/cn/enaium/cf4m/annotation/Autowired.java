package cn.enaium.cf4m.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add this annotation to the class/field to the field of this class will be auto put
 * <p>
 * Support {@link cn.enaium.cf4m.annotation.module.Module}
 * <p>
 * Support {@link cn.enaium.cf4m.annotation.command.Command}
 * <p>
 * Support {@link cn.enaium.cf4m.annotation.config.Config}
 * <p>
 * Support {@link cn.enaium.cf4m.container.ClassContainer}
 * <p>
 * Support {@link cn.enaium.cf4m.container.EventContainer}
 * <p>
 * Support {@link cn.enaium.cf4m.container.ModuleContainer}
 * <p>
 * Support {@link cn.enaium.cf4m.container.CommandContainer}
 * <p>
 * Support {@link cn.enaium.cf4m.container.ConfigContainer}
 * <p>
 * Support {@link cn.enaium.cf4m.configuration.IConfiguration}
 * <p>
 * Support {@link cn.enaium.cf4m.provider.ModuleProvider}
 * <p>
 * Support {@link cn.enaium.cf4m.provider.CommandProvider}
 * <p>
 * Support {@link cn.enaium.cf4m.provider.ConfigProvider}
 *
 * @author Enaium
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
}
