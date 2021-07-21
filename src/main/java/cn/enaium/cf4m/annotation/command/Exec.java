package cn.enaium.cf4m.annotation.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Attached to {@link Command}
 * <p>
 * Add this annotation to the method to that this method is a executable command
 *
 * @author Enaium
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Exec {
    /**
     * @return wait for the command to finish
     */
    boolean await() default true;
}
