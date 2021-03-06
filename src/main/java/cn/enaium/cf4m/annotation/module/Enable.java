package cn.enaium.cf4m.annotation.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Attached {@link Module}
 * <p>
 * Add this annotation to the method to that this class will be invoke at module enable
 *
 * @author Enaium
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Enable {
}
