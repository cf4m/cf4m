package cn.enaium.cf4m.annotation.module;

import java.lang.annotation.*;

/**
 * Add this annotation to the class to that this class is a module
 *
 * @author Enaium
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {
    String value();

    int key() default 0;

    String type() default "NONE";

    String description() default "";
}
