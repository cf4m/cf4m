package cn.enaium.cf4m.annotation.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add this annotation to the field to that this class is a configuration value
 * <p>
 * The default is the field name
 *
 * @author Enaium
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    /**
     * @return key
     */
    String value() default "";

    /**
     * @return description
     */
    String description() default "";
}
