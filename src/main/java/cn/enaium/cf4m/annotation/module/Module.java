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
    /**
     * @return module name
     */
    String value();

    /**
     * @return module key
     */
    int key() default 0;

    /**
     * @return module type
     */
    String type() default "NONE";

    /**
     * @return module description
     */
    String description() default "";
}
