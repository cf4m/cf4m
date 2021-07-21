package cn.enaium.cf4m.annotation.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Attach to {@link Module}
 * <p>
 * Add this annotation to the field to that this field is module setting
 *
 * @author Enaium
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Setting {
    /**
     * @return module setting name
     */
    String value();

    /**
     * @return module setting description
     */
    String description() default "";
}
