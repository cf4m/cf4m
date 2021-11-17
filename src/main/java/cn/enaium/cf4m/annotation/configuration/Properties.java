package cn.enaium.cf4m.annotation.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add this annotation to the class to that this class need to read properties
 *
 * @author Enaium
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Properties {
    /**
     * @return file name
     */
    String value();

    /**
     * @return description
     */
    String description() default "";
}
