package cn.enaium.cf4m.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add this annotation to the class to that this class is a config
 *
 * @author Enaium
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
    /**
     * @return config name
     */
    String value();

    /**
     * @return config description
     */
    String description() default "";
}
