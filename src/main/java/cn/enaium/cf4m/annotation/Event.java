package cn.enaium.cf4m.annotation;

import java.lang.annotation.*;

/**
 * Add this annotation to the method to that this method will be invoke at event fire
 * <p>
 * Must register instance
 *
 * @author Enaium
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Event {
    int priority() default 0;
}
